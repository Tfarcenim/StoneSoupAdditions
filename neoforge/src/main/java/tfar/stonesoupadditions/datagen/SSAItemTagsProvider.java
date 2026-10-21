package tfar.stonesoupadditions.datagen;

import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.ItemTagsProvider;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.level.block.Block;
import tfar.stonesoupadditions.init.SSAItems;

import java.util.concurrent.CompletableFuture;

public class SSAItemTagsProvider extends ItemTagsProvider {
    public SSAItemTagsProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider, CompletableFuture<TagLookup<Block>> blockTags) {
        super(output, lookupProvider, blockTags);
    }

    @Override
    protected void addTags(HolderLookup.Provider provider) {
        tag(ItemTags.SWORD_ENCHANTABLE).add(SSAItems.DUELING_SHIELD);
    }
}
