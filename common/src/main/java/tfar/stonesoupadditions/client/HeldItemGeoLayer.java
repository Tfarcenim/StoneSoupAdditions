package tfar.stonesoupadditions.client;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.HumanoidArm;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib.animatable.GeoEntity;
import software.bernie.geckolib.cache.object.GeoBone;
import software.bernie.geckolib.renderer.GeoRenderer;
import software.bernie.geckolib.renderer.layer.BlockAndItemGeoLayer;

import java.util.Objects;

public class HeldItemGeoLayer<E extends LivingEntity & GeoEntity> extends BlockAndItemGeoLayer<E> {

    public HeldItemGeoLayer(GeoRenderer<E> renderer, @Nullable String rightHandBoneName, @Nullable String leftHandBoneName) {
        super(renderer,(geoBone, e) -> {
            HumanoidArm arm = e.getMainArm();
            String boneName = geoBone.getName();
            String validBone = switch (arm) {
                case RIGHT ->  rightHandBoneName;
                case LEFT ->  leftHandBoneName;
            };
            return Objects.equals(validBone,boneName) ? e.getItemBySlot(EquipmentSlot.MAINHAND) : null;
        },(geoBone, e) -> null);
    }

    @Override
    protected ItemDisplayContext getTransformTypeForStack(GeoBone bone, ItemStack stack, E animatablee) {
        return ItemDisplayContext.THIRD_PERSON_RIGHT_HAND;
    }

    @Override
    protected void renderStackForBone(PoseStack poseStack, GeoBone bone, ItemStack stack, E animatable, MultiBufferSource bufferSource, float partialTick, int packedLight, int packedOverlay) {
       // poseStack.translate(0,-.25,-.375);//y = forward, z = up
      //  poseStack.mulPose(Axis.XN.rotationDegrees(75));
        super.renderStackForBone(poseStack, bone, stack, animatable, bufferSource, partialTick, packedLight, packedOverlay);
    }
}
