package tfar.stonesoupadditions.mixin;

import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;
import tfar.stonesoupadditions.item.LeftClickableItem;

@Mixin(Minecraft.class)
public class MinecraftMixin {
    @Redirect(method = "startAttack",at = @At(value = "INVOKE", target = "Lnet/minecraft/client/player/LocalPlayer;swing(Lnet/minecraft/world/InteractionHand;)V"))
    private void preventSwing(LocalPlayer instance, InteractionHand hand) {
        if (hand == InteractionHand.MAIN_HAND) {
            ItemStack stack = instance.getMainHandItem();
            if (stack.getItem() instanceof LeftClickableItem) {
                return;
            }
        }
        instance.swing(hand);
    }
}
