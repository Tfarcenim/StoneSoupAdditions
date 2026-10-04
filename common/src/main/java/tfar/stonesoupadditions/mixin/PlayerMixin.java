package tfar.stonesoupadditions.mixin;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import tfar.stonesoupadditions.PlayerDuck;

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
}
