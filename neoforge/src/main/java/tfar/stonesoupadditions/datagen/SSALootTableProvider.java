package tfar.stonesoupadditions.datagen;

import net.minecraft.core.HolderLookup;
import net.minecraft.core.WritableRegistry;
import net.minecraft.data.PackOutput;
import net.minecraft.data.loot.LootTableProvider;
import net.minecraft.data.loot.packs.*;
import net.minecraft.resources.ResourceKey;
import net.minecraft.util.ProblemReporter;
import net.minecraft.world.level.storage.loot.BuiltInLootTables;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.ValidationContext;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import tfar.stonesoupadditions.datagen.loot.SSABlockLoot;
import tfar.stonesoupadditions.datagen.loot.SSAEntityLoot;

import java.util.List;
import java.util.Set;
import java.util.concurrent.CompletableFuture;

public class SSALootTableProvider extends LootTableProvider {
    public SSALootTableProvider(PackOutput output, Set<ResourceKey<LootTable>> requiredTables, List<SubProviderEntry> subProviders, CompletableFuture<HolderLookup.Provider> registries) {
        super(output, requiredTables, subProviders, registries);
    }

    public static LootTableProvider create(PackOutput output, CompletableFuture<HolderLookup.Provider> registries) {
        return new SSALootTableProvider(
                output,
                BuiltInLootTables.all(),
                List.of(
                        new LootTableProvider.SubProviderEntry(SSAEntityLoot::new, LootContextParamSets.ENTITY),
                        new LootTableProvider.SubProviderEntry(SSABlockLoot::new, LootContextParamSets.BLOCK)
                ),
                registries
        );
    }

    @Override
    protected void validate(WritableRegistry<LootTable> writableregistry, ValidationContext validationcontext, ProblemReporter.Collector problemreporter$collector) {
        // Do not validate against all registered loot tables
    }
}
