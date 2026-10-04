package tfar.stonesoupadditions.client;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.model.PlayerModel;
import net.minecraft.client.model.geom.EntityModelSet;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.player.Player;
import software.bernie.geckolib.renderer.GeoEntityRenderer;
import tfar.stonesoupadditions.StileEntity;
import tfar.stonesoupadditions.init.SSAEntityTypes;

public class StileOnShoulderLayer<T extends Player> extends RenderLayer<T, PlayerModel<T>> {
    private final GeoEntityRenderer<StileEntity> model;

    public StileOnShoulderLayer(RenderLayerParent<T, PlayerModel<T>> renderer, EntityModelSet modelSet, EntityRendererProvider.Context context) {
        super(renderer);
        this.model = new GeoEntityRenderer<>(context,SSAEntityTypes.STILE);
    }

    public void render(
            PoseStack poseStack,
            MultiBufferSource buffer,
            int packedLight,
            T livingEntity,
            float limbSwing,
            float limbSwingAmount,
            float partialTicks,
            float ageInTicks,
            float netHeadYaw,
            float headPitch
    ) {
        this.render(poseStack, buffer, packedLight, livingEntity, limbSwing, limbSwingAmount, netHeadYaw, headPitch, true);
        this.render(poseStack, buffer, packedLight, livingEntity, limbSwing, limbSwingAmount, netHeadYaw, headPitch, false);
    }

    private void render(
            PoseStack poseStack,
            MultiBufferSource buffer,
            int packedLight,
            T livingEntity,
            float limbSwing,
            float limbSwingAmount,
            float netHeadYaw,
            float headPitch,
            boolean leftShoulder
    ) {
        CompoundTag compoundtag = leftShoulder ? livingEntity.getShoulderEntityLeft() : livingEntity.getShoulderEntityRight();
        EntityType.byString(compoundtag.getString("id"))
                .filter(p_117294_ -> p_117294_ == SSAEntityTypes.STILE)
                .ifPresent(
                        p_262538_ -> {
                            poseStack.pushPose();
                            poseStack.translate(leftShoulder ? 0.4F : -0.4F, livingEntity.isCrouching() ? -1.3F : -1.5F, 0.0F);

                        //    this.model.render(
                        ////                     poseStack,  packedLight, OverlayTexture.NO_OVERLAY, limbSwing, limbSwingAmount, netHeadYaw, headPitch, livingEntity.tickCount
                         //            );
                            poseStack.popPose();
                        }
                );
    }
}
