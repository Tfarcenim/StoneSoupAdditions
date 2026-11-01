package tfar.stonesoupadditions;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerPlayer;
import tfar.stonesoupadditions.entity.LushScepterHookEntity;

import javax.annotation.Nullable;

public interface PlayerDuck {

    boolean setStileEntityOnShoulder(CompoundTag compoundTag);

    void setLushScepterHook(@Nullable LushScepterHookEntity lushScepterHookEntity);

    LushScepterHookEntity getLushScepterHook();

}
