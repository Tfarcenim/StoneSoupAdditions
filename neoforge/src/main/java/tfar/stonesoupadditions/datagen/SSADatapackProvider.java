package tfar.stonesoupadditions.datagen;

import net.minecraft.core.HolderLookup;
import net.minecraft.core.RegistrySetBuilder;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.PackOutput;
import net.neoforged.neoforge.common.data.DatapackBuiltinEntriesProvider;
import tfar.stonesoupadditions.Constants;
import tfar.stonesoupadditions.StoneSoupAdditions;
import tfar.stonesoupadditions.init.SSADamageTypes;

import java.util.Set;
import java.util.concurrent.CompletableFuture;

public class SSADatapackProvider extends DatapackBuiltinEntriesProvider {


    public static final RegistrySetBuilder BUILDER = new RegistrySetBuilder()
            .add(Registries.DAMAGE_TYPE, SSADamageTypes::bootstrap);

    public SSADatapackProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> registries) {
        super(output, registries,BUILDER, Set.of(Constants.MOD_ID));
    }
}
