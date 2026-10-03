package tfar.stonesoupadditions.entity;

import com.mojang.datafixers.util.Pair;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.TamableAnimal;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.memory.MemoryStatus;
import net.tslat.smartbrainlib.api.core.behaviour.ExtendedBehaviour;
import net.tslat.smartbrainlib.api.core.behaviour.custom.move.FollowOwner;

import java.util.List;
import java.util.function.Predicate;

public class RideOwner<E extends TamableAnimal> extends ExtendedBehaviour<E> {

    protected LivingEntity owner;

    public RideOwner() {
       stopIf(entity -> {
           LivingEntity entity1 = entity.getOwner();
           return entity1 == null || entity1.isPassenger();
       });
    }

    @Override
    protected boolean checkExtraStartConditions(ServerLevel level, E entity) {
        LivingEntity owner = getOwner(entity);
        return !entity.isOrderedToSit() && owner != null && owner.isPassenger();
    }

    protected LivingEntity getOwner(E entity) {
        if (this.owner != null && (this.owner.isRemoved() || !this.owner.getUUID().equals(entity.getOwnerUUID())))
            this.owner = null;

        if (this.owner == null)
            this.owner = entity.getOwner();

        return this.owner;
    }

    @Override
    protected boolean timedOut(long gameTime) {
        return false;
    }

    @Override
    protected boolean canStillUse(ServerLevel level, E entity, long gameTime) {
        LivingEntity owner = getOwner(entity);
        return P.test(owner);
    }

    protected static final Predicate<LivingEntity> P = e -> e != null && e.isPassenger();

    @Override
    protected List<Pair<MemoryModuleType<?>, MemoryStatus>> getMemoryRequirements() {
        return List.of();
    }

    @Override
    protected void start(E entity) {
        super.start(entity);
        entity.startRiding(owner);
    }

    @Override
    protected void stop(E entity) {
        super.stop(entity);
        entity.stopRiding();
    }
}
