package tfar.stonesoupadditions.init;

import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import tfar.stonesoupadditions.entity.StileEntity;
import tfar.stonesoupadditions.StoneSoupAdditions;

public class SSAEntityTypes {
    public static final EntityType<StileEntity> STILE = register("stile",EntityType.Builder.of(StileEntity::new, MobCategory.CREATURE)
            .sized(.6f,1.125f)
    );

    public static void init() {

    }

    private static <T extends Entity> EntityType<T> register(String key, EntityType.Builder<T> builder) {
        return Registry.register(BuiltInRegistries.ENTITY_TYPE, StoneSoupAdditions.id(key), builder.build(key));
    }

}
