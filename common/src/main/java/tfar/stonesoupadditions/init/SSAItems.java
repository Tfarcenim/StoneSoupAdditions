package tfar.stonesoupadditions.init;

import net.minecraft.core.component.DataComponents;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import tfar.stonesoupadditions.StoneSoupAdditions;
import tfar.stonesoupadditions.item.DuelingShieldItem;
import tfar.stonesoupadditions.item.LushScepterItem;

public class SSAItems {
    public static final Item DUELING_SHIELD = register("dueling_shield",new DuelingShieldItem(
            new Item.Properties().durability(2048).attributes(DuelingShieldItem.createAttributes( 6, -2.5F))));

    public static final Item LUSH_SCEPTER = register("lush_scepter",new LushScepterItem(
            new Item.Properties().durability(2048).attributes(DuelingShieldItem.createAttributes( 9, -2.2F))
                    .component(DataComponents.ENCHANTMENT_GLINT_OVERRIDE,false)));
    public static void init() {

    }

    protected static Item register(String key, Item item) {
        return Items.registerItem(StoneSoupAdditions.id(key), item);
    }

}
