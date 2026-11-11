package tfar.stonesoupadditions.item;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.tags.FluidTags;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.ProjectileUtil;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
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
        return super.use(level, player, usedHand);
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
}
