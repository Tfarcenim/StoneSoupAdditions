package tfar.stonesoupadditions.datagen;

import net.minecraft.core.HolderLookup;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.PackOutput;
import net.neoforged.neoforge.common.data.BlockTagsProvider;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.neoforged.neoforge.common.data.internal.NeoForgeBlockTagsProvider;
import net.neoforged.neoforge.data.event.GatherDataEvent;

import java.util.concurrent.CompletableFuture;

public class SSADatagen {
    public static void gather(GatherDataEvent event) {
        DataGenerator gen = event.getGenerator();
        PackOutput output = gen.getPackOutput();
        CompletableFuture<HolderLookup.Provider> lookup = event.getLookupProvider();
        ExistingFileHelper existingFileHelper = event.getExistingFileHelper();
        event.addProvider(new SSALang(output));

        BlockTagsProvider blockTagsProvider = new SSABlockTagsProvider(output,lookup,existingFileHelper);

        event.addProvider(blockTagsProvider);

        event.addProvider(new SSAItemTagsProvider(output,lookup,blockTagsProvider.contentsGetter()));
    }

}
