package tfar.stonesoupadditions.item;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.stats.Stats;
import net.minecraft.tags.FluidTags;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.ProjectileUtil;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ItemUtils;
import net.minecraft.world.item.UseAnim;
import net.minecraft.world.level.Explosion;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;
import software.bernie.geckolib.animatable.GeoItem;
import software.bernie.geckolib.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.animation.AnimatableManager;
import software.bernie.geckolib.util.GeckoLibUtil;

public class GS04Item extends Item  implements GeoItem{

    private final AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache(this);

    public GS04Item(Properties properties) {
        super(properties);
    }

    public static void fireShot(ServerPlayer player, ItemStack held, long ticksLeftClickHeld) {
        if (player.isEyeInFluid(FluidTags.WATER)) {
            ServerLevel level  =player.serverLevel();
            held.shrink(1);
            Explosion explosion =null;// new Explosion();
            level.explode(
                    null, level.damageSources().explosion(explosion),
                    null, player.position(), 5.0F, true, Level.ExplosionInteraction.BLOCK
            );
            player.level().explode(null,player.getX(),player.getY(),player.getZ(),5, Level.ExplosionInteraction.BLOCK);

            return;
        }

        EntityHitResult entityHitResult = raycast(player);
        if (entityHitResult != null) {
            if (entityHitResult.getEntity() instanceof LivingEntity livingEntity) {
                livingEntity.hurt(player.damageSources().playerAttack(player),1);
            }
        }
    }

    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllers) {

    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand usedHand) {
        player.playSound(SoundEvents.SPYGLASS_USE, 1.0F, 1.0F);
        player.awardStat(Stats.ITEM_USED.get(this));
        return ItemUtils.startUsingInstantly(level, player, usedHand);
    }

    @Override
    public boolean canAttackBlock(BlockState state, Level level, BlockPos pos, Player player) {
        return false;
    }

    public static EntityHitResult raycast(Player player) {
        HitResult hitResultOnViewVector = ProjectileUtil.getHitResultOnViewVector(player, e -> e instanceof LivingEntity, 128);
        return hitResultOnViewVector instanceof EntityHitResult ? (EntityHitResult) hitResultOnViewVector : null;
    }

    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return cache;
    }


    //@Override overridden in forge
    @SuppressWarnings("unused")
    public boolean shouldCauseReequipAnimation(ItemStack oldStack, ItemStack newStack, boolean slotChanged) {
        return slotChanged || ItemStack.isSameItem(oldStack, newStack);
    }

    /**
     * Called when the player finishes using this Item (E.g. finishes eating.). Not called when the player stops using the Item before the action is complete.
     */
    @Override
    public ItemStack finishUsingItem(ItemStack stack, Level level, LivingEntity livingEntity) {
        this.stopUsing(livingEntity);
        return stack;
    }

    /**
     * Called when the player stops using an Item (stops holding the right mouse button).
     */
    @Override
    public void releaseUsing(ItemStack stack, Level level, LivingEntity livingEntity, int timeCharged) {
        this.stopUsing(livingEntity);
    }

    @Override
    public int getUseDuration(ItemStack stack, LivingEntity entity) {
        return 72000;
    }

    /**
     * Returns the action that specifies what animation to play when the item is being used.
     */
    @Override
    public UseAnim getUseAnimation(ItemStack stack) {
        return UseAnim.SPYGLASS;
    }

    private void stopUsing(LivingEntity user) {
        user.playSound(SoundEvents.SPYGLASS_STOP_USING, 1.0F, 1.0F);
    }
}
