package tfar.stonesoupadditions.datagen;

import net.minecraft.data.PackOutput;
import net.neoforged.neoforge.client.model.generators.BlockStateProvider;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import tfar.stonesoupadditions.Constants;
import tfar.stonesoupadditions.StoneSoupAdditions;
import tfar.stonesoupadditions.init.SSABlocks;

public class SSABlockStateProvider extends BlockStateProvider {
    public SSABlockStateProvider(PackOutput output, ExistingFileHelper exFileHelper) {
        super(output, Constants.MOD_ID, exFileHelper);
    }

    @Override
    protected void registerStatesAndModels() {
        simpleBlockWithItem(SSABlocks.HEART_OF_FIRE, models().getExistingFile(StoneSoupAdditions.id("block/heart_of_fire")));
    }
}
