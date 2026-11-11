package tfar.stonesoupadditions.datagen;

import net.minecraft.client.renderer.block.model.BlockModel;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.neoforge.client.model.generators.ItemModelProvider;
import net.neoforged.neoforge.client.model.generators.ModelFile;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import tfar.stonesoupadditions.Constants;

public class SSAItemModelProvider extends ItemModelProvider {
    public SSAItemModelProvider(PackOutput output, ExistingFileHelper existingFileHelper) {
        super(output, Constants.MOD_ID, existingFileHelper);
    }

    @Override
    protected void registerModels() {
       /* getBuilder("gs_04")
                .parent(new ModelFile.UncheckedModelFile(ResourceLocation.withDefaultNamespace("builtin/entity")))
                .texture("particle",mcLoc("block/dark_oak_planks"))
                .guiLight(BlockModel.GuiLight.FRONT);*/
    }
}
