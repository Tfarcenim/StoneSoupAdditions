package tfar.stonesoupadditions.client;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.world.entity.EntityType;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib.cache.object.BakedGeoModel;
import software.bernie.geckolib.renderer.GeoEntityRenderer;
import tfar.stonesoupadditions.StileEntity;

public class StileRenderer extends GeoEntityRenderer<StileEntity> {
    public StileRenderer(EntityRendererProvider.Context context, EntityType<? extends StileEntity> entityType) {
        super(context, entityType);
        addRenderLayer(new StileArmorLayer(this));
        addRenderLayer(new HeldItemGeoLayer<>(this,"RightHand","LeftHand"));
    }

    @Override
    public void preRender(PoseStack poseStack, StileEntity animatable, BakedGeoModel model, @Nullable MultiBufferSource bufferSource, @Nullable VertexConsumer buffer, boolean isReRender, float partialTick, int packedLight, int packedOverlay, int colour) {
        super.preRender(poseStack, animatable, model, bufferSource, buffer, isReRender, partialTick, packedLight, packedOverlay, colour);
        //model.getBone("RightHand").ifPresent(geoBone -> geoBone.setHidden(true));
    }
}
