package tfar.stonesoupadditions.datagen.loot;

import net.minecraft.core.HolderLookup;
import net.minecraft.data.loot.EntityLootSubProvider;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.level.storage.loot.LootTable;
import tfar.stonesoupadditions.init.SSAEntityTypes;

import java.util.stream.Stream;

public class SSAEntityLoot extends EntityLootSubProvider {
    public SSAEntityLoot(HolderLookup.Provider registries) {
        super(FeatureFlags.VANILLA_SET, registries);
    }

    @Override
    public void generate() {
        add(SSAEntityTypes.STILE, LootTable.lootTable());
    }

    @Override
    protected Stream<EntityType<?>> getKnownEntityTypes() {
        return Stream.of(SSAEntityTypes.STILE);
    }
}
