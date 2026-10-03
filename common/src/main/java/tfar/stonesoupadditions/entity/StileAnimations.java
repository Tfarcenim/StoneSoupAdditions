package tfar.stonesoupadditions.entity;

import software.bernie.geckolib.animation.AnimatableManager;
import software.bernie.geckolib.animation.AnimationController;
import software.bernie.geckolib.animation.RawAnimation;
import software.bernie.geckolib.constant.DefaultAnimations;
import tfar.stonesoupadditions.StileEntity;

public class StileAnimations {

    public static final RawAnimation ATTACK_SHORT = RawAnimation.begin().thenPlay("attack.short");
    public static final RawAnimation ATTACK_JUMP = RawAnimation.begin().thenPlay("attack.jump");
    public static final RawAnimation HANGING = RawAnimation.begin().thenPlayAndHold("hanging");
    public static final RawAnimation FAINT = RawAnimation.begin().thenPlayAndHold("faint");

    public static void registerControllers(StileEntity stile, AnimatableManager.ControllerRegistrar controllers) {
        controllers.add(new AnimationController<>(stile, "main", 10, (state) -> {

            if (stile.fainted()) {
                return state.setAndContinue(FAINT);
            }

            RawAnimation currentAnim = state.isMoving() ? (stile.isSprinting() ? DefaultAnimations.RUN :DefaultAnimations.WALK)
                        : DefaultAnimations.IDLE;
            return state.setAndContinue(currentAnim);
        }).triggerableAnim("faint", FAINT));
    }
}
