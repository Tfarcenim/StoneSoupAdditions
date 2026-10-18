package tfar.stonesoupadditions.entity;

import com.mojang.datafixers.util.Pair;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.*;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.Brain;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.animal.ShoulderRidingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.tslat.smartbrainlib.api.SmartBrainOwner;
import net.tslat.smartbrainlib.api.core.BrainActivityGroup;
import net.tslat.smartbrainlib.api.core.SmartBrainProvider;
import net.tslat.smartbrainlib.api.core.behaviour.FirstApplicableBehaviour;
import net.tslat.smartbrainlib.api.core.behaviour.OneRandomBehaviour;
import net.tslat.smartbrainlib.api.core.behaviour.custom.attack.AnimatableMeleeAttack;
import net.tslat.smartbrainlib.api.core.behaviour.custom.look.LookAtTarget;
import net.tslat.smartbrainlib.api.core.behaviour.custom.misc.Idle;
import net.tslat.smartbrainlib.api.core.behaviour.custom.move.FollowOwner;
import net.tslat.smartbrainlib.api.core.behaviour.custom.move.MoveToWalkTarget;
import net.tslat.smartbrainlib.api.core.behaviour.custom.path.SetRandomWalkTarget;
import net.tslat.smartbrainlib.api.core.behaviour.custom.path.SetWalkTargetToAttackTarget;
import net.tslat.smartbrainlib.api.core.behaviour.custom.target.*;
import net.tslat.smartbrainlib.api.core.sensor.ExtendedSensor;
import net.tslat.smartbrainlib.api.core.sensor.vanilla.HurtBySensor;
import net.tslat.smartbrainlib.api.core.sensor.vanilla.NearbyLivingEntitySensor;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib.animatable.GeoEntity;
import software.bernie.geckolib.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.animation.AnimatableManager;
import software.bernie.geckolib.util.GeckoLibUtil;
import tfar.stonesoupadditions.PlayerDuck;
import tfar.stonesoupadditions.platform.Services;

import java.util.List;

public class StileEntity extends ShoulderRidingEntity implements GeoEntity, SmartBrainOwner<StileEntity>, ContainerListener,
        HasCustomInventoryScreen {

    private static final EntityDataAccessor<Integer> FAINT_TIMER = SynchedEntityData.defineId(StileEntity.class, EntityDataSerializers.INT);

    private final AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache(this);

    private boolean interacting;

    public StileEntity(EntityType<? extends StileEntity> entityType, Level level) {
        super(entityType, level);
    }

    public static StileEntity readStileEntity(Level level, FriendlyByteBuf buf) {
        int id = buf.readInt();
        Entity entity = level.getEntity(id);
        return entity instanceof StileEntity ? (StileEntity) entity : null;
    }

    @Override
    protected void defineSynchedData(SynchedEntityData.Builder builder) {
        super.defineSynchedData(builder);
        builder.define(FAINT_TIMER, 0);
    }

    public int getFaintTimer() {
        return entityData.get(FAINT_TIMER);
    }

    public void setFaintTimer(int value) {
        entityData.set(FAINT_TIMER, value);
    }

    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllers) {
        StileAnimations.registerControllers(this, controllers);
    }

    @Override
    protected Brain.Provider<StileEntity> brainProvider() {
        return new SmartBrainProvider<>(this);
    }


    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return cache;
    }

    @Override
    public List<? extends ExtendedSensor<StileEntity>> getSensors() {
        return List.of(new HurtBySensor<>(), new NearbyLivingEntitySensor<>());
    }

    @Override
    public boolean canBeSeenAsEnemy() {
        return !fainted() && super.canBeSeenAsEnemy();
    }

    @Override
    public boolean canAttack(LivingEntity target) {
        return !fainted() && super.canAttack(target);
    }

    @Override
    public BrainActivityGroup<? extends StileEntity> getIdleTasks() {
        return BrainActivityGroup.idleTasks(
                new FirstApplicableBehaviour<>( // Run only one of the below behaviours, trying each one in order. Include explicit generic typing because javac is silly
                        new StileTargetBehaviour(this), // Set the attack target
                        new SetPlayerLookTarget<>(), // Set the look target to a nearby player if available
                        new SetRandomLookTarget<>().startCondition(p -> !isInteracting() && !isOrderedToSit())
                                .stopIf(p -> isInteracting() || isOrderedToSit())), // Set the look target to a random nearby location
                new OneRandomBehaviour<>( // Run only one of the below behaviours, picked at random
                        new SetRandomWalkTarget<>() // Set the walk target to a nearby random pathable location
                                .speedModifier(1).startCondition(p -> !isInteracting() && !isOrderedToSit())
                                .stopIf(p -> isInteracting() || isOrderedToSit()),
                        new Idle<>() // Don't do anything for a bit
                                .runFor(entity -> entity.getRandom().nextInt(20, 40))));
    }

    @Override
    public BrainActivityGroup<? extends StileEntity> getCoreTasks() {
        return BrainActivityGroup.coreTasks(
                new LookAtTarget<>(),                      // Have the entity turn to face and look at its current look target
                new MoveToWalkTarget<>()
                        .startCondition(p -> isMobile())
                        .stopIf(p -> isInteracting() || isOrderedToSit()),//stop moving while equipping gear
                new FollowOwner<>().following(o -> o.isOrderedToSit() || o.isPassenger() ? null : o.getOwner())
        );
    }

    protected boolean isMobile() {
        boolean b = !isInteracting() && !isOrderedToSit();
        return b;
    }

    @Override
    public BrainActivityGroup<? extends StileEntity> getFightTasks() {
        return BrainActivityGroup.fightTasks(
                new InvalidateAttackTarget<>(), // Cancel fighting if the target is no longer valid
                new SetWalkTargetToAttackTarget<>().speedMod((owner, target) -> 1.5f),      // Set the walk target to the attack target
                new OneRandomBehaviour<>(
                        Pair.of(new AnimatableMeleeAttack<>(12) {
                            @Override
                            protected void start(Mob entity) {
                                super.start(entity);
                                triggerAnim("main", "short_attack");
                                //BehaviorUtils.lookAtEntity(entity, this.target);
                            }
                        }, 9)
                        , Pair.of(new AnimatableMeleeAttack<>(8) {
                            @Override
                            protected void start(Mob entity) {
                                super.start(entity);
                                triggerAnim("main", "jump_attack");
                                //BehaviorUtils.lookAtEntity(entity, this.target);
                                 }
                            }, 3)
                )
        );// Melee attack the target if close enough
    }

    boolean shouldRideOwner() {
        if (isOrderedToSit() || isPassenger()) return false;
        LivingEntity owner = this.getOwner();
        if (owner == null) return false;
        boolean passenger = owner.isPassenger();
        return passenger;
    }

    boolean shouldStopRiding() {
        if (!isPassenger()) return false;
        LivingEntity owner = this.getOwner();
        if (owner != getVehicle() || owner == null) return false;
        boolean passenger = owner.isPassenger();
        return !passenger;
    }

    @Override
    protected void customServerAiStep() {
        super.customServerAiStep();
        tickBrain(this);
        int faintTimer = getFaintTimer();
        if (faintTimer > 0) {
            faintTimer--;
            setFaintTimer(faintTimer);
            if (faintTimer == 0) {
                setInvulnerable(false);
            }
        }
    }

    public boolean fainted() {
        return getFaintTimer() > 0;
    }

    @Override
    public void die(DamageSource damageSource) {
        //this.triggerAnim("main", "faint");
        if (damageSource.is(DamageTypeTags.BYPASSES_INVULNERABILITY)) {
            super.die(damageSource);//kill needs to work
        } else {
            setHealth(getMaxHealth());
            setInvulnerable(true);
            setFaintTimer(REVIVE_TIME);
        }
    }

    @Override
    public boolean hurt(DamageSource source, float amount) {
        if (fainted()) {
            return source.is(DamageTypeTags.BYPASSES_INVULNERABILITY) && super.hurt(source, amount);
        }
        return super.hurt(source, amount);
    }

    public static final int REVIVE_TIME = 300;

    @Override
    public void addAdditionalSaveData(CompoundTag compound) {
        super.addAdditionalSaveData(compound);
    }

    @Override
    public void readAdditionalSaveData(CompoundTag compound) {
        super.readAdditionalSaveData(compound);
    }

    public static AttributeSupplier.Builder createStileAttributes() {
        return createMobAttributes().add(Attributes.MOVEMENT_SPEED, .3).add(Attributes.ATTACK_DAMAGE);
    }

    @Override
    public void containerChanged(Container container) {

    }

    @Override
    public void openCustomInventoryScreen(Player player) {
        if (!this.level().isClientSide && (!this.isVehicle() || this.hasPassenger(player))) {
            Services.PLATFORM.openExtendedMenu(player, Services.PLATFORM.curioMenuProvider(this), b -> b.writeInt(getId()));
        }
    }

    public InvWrapper getInvWrapper() {
        return new InvWrapper();
    }

    public class InvWrapper implements Container {
        @Override
        public int getContainerSize() {
            return INVENTORY_SIZE;
        }

        @Override
        public boolean isEmpty() {
            return false;
        }

        //head,chest,legs,feet,mainhand
        @Override
        public ItemStack getItem(int index) {
            EquipmentSlot slot = indexOf(index);
            return getItemBySlot(slot);
        }

        static EquipmentSlot indexOf(int slot) {
            return switch (slot) {
                case 0 -> EquipmentSlot.FEET;
                case 1 -> EquipmentSlot.LEGS;
                case 2 -> EquipmentSlot.CHEST;
                case 3 -> EquipmentSlot.HEAD;
                case 4 -> EquipmentSlot.MAINHAND;
                default -> throw new IllegalStateException("Unexpected value: " + slot);
            };
        }

        @Override
        public ItemStack removeItem(int index, int amount) {
            EquipmentSlot slot = indexOf(index);
            List<ItemStack> stacks = slot == EquipmentSlot.MAINHAND ? handItems : armorItems;
            ItemStack itemstack = ContainerHelper.removeItem(stacks, slot.getIndex(), amount);
            if (!itemstack.isEmpty()) {
                this.setChanged();
            }

            return itemstack;
        }

        @Override
        public ItemStack removeItemNoUpdate(int index) {
            EquipmentSlot slot = indexOf(index);
            List<ItemStack> stacks = slot == EquipmentSlot.MAINHAND ? handItems : armorItems;
            ItemStack itemstack = stacks.get(slot.getIndex());
            if (itemstack.isEmpty()) {
                return ItemStack.EMPTY;
            } else {
                stacks.set(slot.getIndex(), ItemStack.EMPTY);
                return itemstack;
            }
        }

        @Override
        public void setItem(int index, ItemStack stack) {
            EquipmentSlot slot = indexOf(index);
            List<ItemStack> stacks = slot == EquipmentSlot.MAINHAND ? handItems : armorItems;
            stacks.set(slot.getIndex(), stack);
        }

        @Override
        public void setChanged() {

        }

        @Override
        public boolean stillValid(Player player) {
            return isAlive();
        }

        @Override
        public void clearContent() {

        }
    }

    @Override
    public InteractionResult mobInteract(Player player, InteractionHand hand) {
        if (this.isVehicle() || this.isBaby()) {
            return super.mobInteract(player, hand);
        } else if (this.isTame() && player.isSecondaryUseActive()) {
            this.openCustomInventoryScreen(player);
            return InteractionResult.sidedSuccess(this.level().isClientSide);
        } else {
            ItemStack itemstack = player.getItemInHand(hand);
            if (itemstack.is(ItemTags.SAPLINGS) && !isTame()) {
                tryToTame(player);
            } else if (!itemstack.isEmpty()) {
                InteractionResult interactionresult = itemstack.interactLivingEntity(player, this, hand);
                if (interactionresult.consumesAction()) {
                    return interactionresult;
                }
            }

            if (isOwnedBy(player)) {
                setOrderedToSit(!this.isOrderedToSit());
                setInSittingPose(!this.isInSittingPose());
                this.jumping = false;
                this.navigation.stop();
                this.setTarget(null);
                return InteractionResult.SUCCESS_NO_ITEM_USED;
            }

            //this.doPlayerRide(player);
            return InteractionResult.sidedSuccess(this.level().isClientSide);
        }
    }

    private void tryToTame(Player player) {
        if (true/*&& !net.neoforged.neoforge.event.EventHooks.onAnimalTame(this, player)*/) {
            this.tame(player);
            this.navigation.stop();
            this.setTarget(null);
            this.level().broadcastEntityEvent(this, (byte) 7);
        } else {
            this.level().broadcastEntityEvent(this, (byte) 6);
        }
    }

    @Override
    public boolean isFood(ItemStack stack) {
        return stack.is(ItemTags.SAPLINGS);
    }

    @Override
    public @Nullable AgeableMob getBreedOffspring(ServerLevel level, AgeableMob otherParent) {
        return null;
    }

    public static final int INVENTORY_SIZE = 5;

    public boolean setSelfOnShoulder(ServerPlayer player) {
        CompoundTag compoundtag = new CompoundTag();
        compoundtag.putString("id", this.getEncodeId());
        this.saveWithoutId(compoundtag);
        if (((PlayerDuck) player).setStileEntityOnShoulder(compoundtag)) {
            this.discard();
            return true;
        } else {
            return false;
        }
    }

    public void setInteracting(boolean interacting) {
        this.interacting = interacting;
    }

    public boolean isInteracting() {
        return interacting;
    }
}
