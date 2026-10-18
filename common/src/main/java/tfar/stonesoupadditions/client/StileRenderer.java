package tfar.stonesoupadditions.client;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EquipmentSlot;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib.cache.object.BakedGeoModel;
import software.bernie.geckolib.renderer.GeoEntityRenderer;
import tfar.stonesoupadditions.entity.StileEntity;

public class StileRenderer extends GeoEntityRenderer<StileEntity> {
    public StileRenderer(EntityRendererProvider.Context context, EntityType<? extends StileEntity> entityType) {
        super(context, entityType);
        addRenderLayer(new StileArmorLayer(this));
        addRenderLayer(new HeldItemGeoLayer<>(this,"RightHand","LeftHand"));
    }

    @Override
    public void preRender(PoseStack poseStack, StileEntity animatable, BakedGeoModel model, @Nullable MultiBufferSource bufferSource, @Nullable VertexConsumer buffer, boolean isReRender, float partialTick, int packedLight, int packedOverlay, int colour) {
        super.preRender(poseStack, animatable, model, bufferSource, buffer, isReRender, partialTick, packedLight, packedOverlay, colour);
        decideBoneVisibility(animatable);
    }

    protected void decideBoneVisibility(StileEntity entity) {
        boolean helmet = entity.getItemBySlot(EquipmentSlot.HEAD).isEmpty();
        boolean chestplate = entity.getItemBySlot(EquipmentSlot.CHEST).isEmpty();
        boolean legging = entity.getItemBySlot(EquipmentSlot.LEGS).isEmpty();
        boolean boots = entity.getItemBySlot(EquipmentSlot.FEET).isEmpty();

        model.getBone("Helmet").ifPresent(geoBone -> geoBone.setHidden(helmet));
        model.getBone("Chestplate").ifPresent(geoBone -> geoBone.setHidden(chestplate));
        model.getBone("Leggings").ifPresent(geoBone -> geoBone.setHidden(legging));
        model.getBone("Boots").ifPresent(geoBone -> geoBone.setHidden(boots));
    }
}
