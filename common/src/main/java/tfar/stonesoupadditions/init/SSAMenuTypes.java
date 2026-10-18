package tfar.stonesoupadditions.init;

import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.MenuType;
import tfar.stonesoupadditions.ContainerFactory;
import tfar.stonesoupadditions.platform.Services;

public class SSAMenuTypes {
    public static void init() {}

    private static <T extends AbstractContainerMenu> MenuType<T> register(String key, MenuType.MenuSupplier<T> factory) {
        return Registry.register(BuiltInRegistries.MENU, key, new MenuType<>(factory, FeatureFlags.VANILLA_SET));
    }

    public static <T extends AbstractContainerMenu> MenuType<T> registerExtended(String key, ContainerFactory<T> factory) {
        return Registry.register(BuiltInRegistries.MENU, key, Services.PLATFORM.createExtendedMenu(factory));
    }
}
