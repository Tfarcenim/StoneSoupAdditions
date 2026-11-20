package tfar.stonesoupadditions.init;

import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;
import tfar.stonesoupadditions.StoneSoupAdditions;
import tfar.stonesoupadditions.block.HeartOfFireBlock;

public class SSABlocks {
    public static final Block HEART_OF_FIRE = register("heart_of_fire",new HeartOfFireBlock(BlockBehaviour.Properties.of()));

    public static Block register(String key, Block block) {
        return Registry.register(BuiltInRegistries.BLOCK, StoneSoupAdditions.id(key), block);
    }

    public static void init() {

    }
}
