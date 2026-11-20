package tfar.stonesoupadditions.datagen.loot;

import net.minecraft.core.HolderLookup;
import net.minecraft.data.loot.BlockLootSubProvider;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.level.block.Block;
import tfar.stonesoupadditions.init.SSABlocks;

import java.util.List;
import java.util.Set;

public class SSABlockLoot extends BlockLootSubProvider {
    public SSABlockLoot(HolderLookup.Provider registries) {
        super(Set.of(), FeatureFlags.VANILLA_SET, registries);
    }

    @Override
    protected void generate() {
        dropSelf(SSABlocks.HEART_OF_FIRE);
    }

    @Override
    protected Iterable<Block> getKnownBlocks() {
        return List.of(SSABlocks.HEART_OF_FIRE);
    }
}
