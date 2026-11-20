package tfar.stonesoupadditions.item;

import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.common.ItemAbilities;
import net.neoforged.neoforge.common.ItemAbility;

public class GS04NeoForgeItem extends GS04Item {
    public GS04NeoForgeItem(Properties properties) {
        super(properties);
    }

    @Override
    public boolean canPerformAction(ItemStack stack, ItemAbility itemAbility) {
        return itemAbility == ItemAbilities.SPYGLASS_SCOPE;
    }
}
