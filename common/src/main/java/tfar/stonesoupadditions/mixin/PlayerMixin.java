package tfar.stonesoupadditions.mixin;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import tfar.stonesoupadditions.PlayerDuck;
import tfar.stonesoupadditions.attachments.DataAttachmentUtil;
import tfar.stonesoupadditions.item.DuelingShieldItem;

@Mixin(Player.class)
public abstract class PlayerMixin extends LivingEntity implements PlayerDuck{


    @Shadow
    public abstract CompoundTag getShoulderEntityLeft();

    @Shadow
    protected abstract void setShoulderEntityLeft(CompoundTag entityCompound);

    @Shadow
    private long timeEntitySatOnShoulder;

    @Shadow
    public abstract CompoundTag getShoulderEntityRight();

    @Shadow
    protected abstract void setShoulderEntityRight(CompoundTag entityCompound);

    protected PlayerMixin(EntityType<? extends LivingEntity> entityType, Level level) {
        super(entityType, level);
    }

    @Override
    public boolean setStileEntityOnShoulder(CompoundTag compoundTag) {
        if (this.getShoulderEntityLeft().isEmpty()) {
            this.setShoulderEntityLeft(compoundTag);
            this.timeEntitySatOnShoulder = this.level().getGameTime();
            return true;
        } else if (this.getShoulderEntityRight().isEmpty()) {
            this.setShoulderEntityRight(compoundTag);
            this.timeEntitySatOnShoulder = this.level().getGameTime();
            return true;
        } else {
            return false;
        }
    }

    @ModifyArg(method = "disableShield",at = @At(value = "INVOKE",
            target = "Lnet/minecraft/world/item/ItemCooldowns;addCooldown(Lnet/minecraft/world/item/Item;I)V"))
    private int changeCooldown(int cooldown) {
        return cooldown;
    }

    @Inject(method = "touch",at = @At("RETURN"))
    private void doDashAttack(Entity entity, CallbackInfo ci) {
        if (DataAttachmentUtil.isDashing((Player)(Object)this)) {
            DuelingShieldItem.doDashAttack((Player)(Object)this,entity);
        }
    }
}
