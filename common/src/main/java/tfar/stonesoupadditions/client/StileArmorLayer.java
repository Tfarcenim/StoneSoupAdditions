package tfar.stonesoupadditions.client;

import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib.cache.object.GeoBone;
import software.bernie.geckolib.renderer.layer.ItemArmorGeoLayer;
import tfar.stonesoupadditions.entity.StileEntity;

public class StileArmorLayer extends ItemArmorGeoLayer<StileEntity> {
    public StileArmorLayer(StileRenderer geoRenderer) {
        super(geoRenderer);
    }

    @Override
    protected @Nullable ItemStack getArmorItemForBone(GeoBone bone, StileEntity animatable) {
        String name = bone.getName();
        if (name.equals("Head")) {
            return helmetStack;
        }
        return null;//animatable.getItemBySlot(EquipmentSlot.HEAD);
    }
}
