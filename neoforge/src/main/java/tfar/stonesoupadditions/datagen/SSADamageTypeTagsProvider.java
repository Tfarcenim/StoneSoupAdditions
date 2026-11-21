package tfar.stonesoupadditions.datagen;

import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.DamageTypeTagsProvider;
import net.minecraft.tags.DamageTypeTags;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.Nullable;
import tfar.stonesoupadditions.Constants;
import tfar.stonesoupadditions.init.SSADamageTypes;

import java.util.concurrent.CompletableFuture;

public class SSADamageTypeTagsProvider extends DamageTypeTagsProvider {
    public SSADamageTypeTagsProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider,@Nullable ExistingFileHelper existingFileHelper) {
        super(output, lookupProvider, Constants.MOD_ID, existingFileHelper);
    }

    @Override
    protected void addTags(HolderLookup.Provider provider) {
        tag(DamageTypeTags.BYPASSES_ARMOR).add(SSADamageTypes.GS_04_CHARGED);
    }
}
