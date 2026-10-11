package tfar.stonesoupadditions.entity;

import net.minecraft.world.entity.LivingEntity;
import net.tslat.smartbrainlib.api.core.behaviour.custom.target.TargetOrRetaliate;

public class StileTargetBehaviour extends TargetOrRetaliate<StileEntity> {

    public StileTargetBehaviour(StileEntity entity) {
        attackablePredicate(target -> shouldAttack(entity,target));
    }

    public boolean shouldAttack(StileEntity entity,LivingEntity target) {
        if (!entity.canAttack(target)) return false;//don't attack
        if (target == entity.getLastAttacker()) return true;//retaliation
        LivingEntity owner = entity.getOwner();
        if (owner == null) return false;
        boolean ally = target.isAlliedTo(owner);
        if (ally) return false;
        if (owner.getLastAttacker() == target) return true;
        if (owner.getLastHurtMob() == target) return true;
        return false;
    }
}
