package tfar.stonesoupadditions.init;

import net.minecraft.core.component.DataComponents;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import tfar.stonesoupadditions.StoneSoupAdditions;
import tfar.stonesoupadditions.item.DuelingShieldItem;
import tfar.stonesoupadditions.item.GS04Item;
import tfar.stonesoupadditions.item.HallowedFragmentItem;
import tfar.stonesoupadditions.item.LushScepterItem;
import tfar.stonesoupadditions.platform.Services;

public class SSAItems {
    public static final Item DUELING_SHIELD = register("dueling_shield",new DuelingShieldItem(
            new Item.Properties().durability(2048).attributes(DuelingShieldItem.createAttributes( 6, -2.5F))));

    public static final Item LUSH_SCEPTER = register("lush_scepter",new LushScepterItem(
            new Item.Properties().durability(2048).attributes(DuelingShieldItem.createAttributes( 9, -2.2F))
                    .component(DataComponents.ENCHANTMENT_GLINT_OVERRIDE,false)));

    public static final GS04Item GS_04 = register("gs_04", Services.PLATFORM.gsO4Item(
            new Item.Properties().durability(2048).attributes(DuelingShieldItem.createAttributes( 9, -2.2F))
                    .component(DataComponents.ENCHANTMENT_GLINT_OVERRIDE,false)));

    public static final HallowedFragmentItem HALLOWED_FRAGMENT = register("hallowed_fragment",new HallowedFragmentItem(new Item.Properties()));

    public static final Item HEART_OF_FIRE = register("heart_of_fire",new BlockItem(SSABlocks.HEART_OF_FIRE,new Item.Properties()));

    public static void init() {

    }

    @SuppressWarnings("unchecked")
    protected static <I extends Item> I register(String key, I item) {
        return (I) Items.registerItem(StoneSoupAdditions.id(key), item);
    }
}
