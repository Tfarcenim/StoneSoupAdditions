package tfar.stonesoupadditions.platform;

import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.MenuType;
import net.neoforged.neoforge.common.extensions.IMenuTypeExtension;
import tfar.stonesoupadditions.ContainerFactory;
import tfar.stonesoupadditions.platform.services.IPlatformHelper;
import net.neoforged.fml.ModList;
import net.neoforged.fml.loading.FMLLoader;

import java.util.OptionalInt;
import java.util.function.Consumer;

public class NeoForgePlatformHelper implements IPlatformHelper {

    @Override
    public String getPlatformName() {

        return "NeoForge";
    }

    @Override
    public boolean isModLoaded(String modId) {

        return ModList.get().isLoaded(modId);
    }

    @Override
    public boolean isDevelopmentEnvironment() {

        return !FMLLoader.isProduction();
    }

    @Override
    public <T extends AbstractContainerMenu> MenuType<T> createExtendedMenu(ContainerFactory<T> factory) {
        return IMenuTypeExtension.create(factory::create);
    }

    @Override
    public OptionalInt openExtendedMenu(Player player, MenuProvider provider, Consumer<RegistryFriendlyByteBuf> buffer) {
        return player.openMenu(provider,buffer);
    }

    @Override
    public boolean fireTameEvent() {
        return false;
    }
}