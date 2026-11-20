package tfar.stonesoupadditions.init;

import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import tfar.stonesoupadditions.HeartOfFireBlockEntity;

public class SSABlockEntityTypes {
    public static final BlockEntityType<HeartOfFireBlockEntity> HEART_OF_FIRE = register("heart_of_fire",BlockEntityType.Builder.of(HeartOfFireBlockEntity::new,SSABlocks.HEART_OF_FIRE));


    private static <T extends BlockEntity> BlockEntityType<T> register(String key, BlockEntityType.Builder<T> builder) {
        return Registry.register(BuiltInRegistries.BLOCK_ENTITY_TYPE, key, builder.build(null));
    }

    public static void init() {

    }
}
