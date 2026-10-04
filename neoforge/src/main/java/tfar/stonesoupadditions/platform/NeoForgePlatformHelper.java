package tfar.stonesoupadditions.platform;

import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.WidgetSprites;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.MenuType;
import net.neoforged.neoforge.common.extensions.IMenuTypeExtension;
import net.neoforged.neoforge.network.registration.PayloadRegistrar;
import org.jetbrains.annotations.Nullable;
import tfar.stonesoupadditions.ContainerFactory;
import tfar.stonesoupadditions.StileCurioMenu;
import tfar.stonesoupadditions.StileEntity;
import tfar.stonesoupadditions.client.CurioButton;
import tfar.stonesoupadditions.network.client.S2CModPacket;
import tfar.stonesoupadditions.network.server.C2SModPacket;
import tfar.stonesoupadditions.platform.services.IPlatformHelper;
import net.neoforged.fml.ModList;
import net.neoforged.fml.loading.FMLLoader;

import java.util.OptionalInt;
import java.util.function.Consumer;

public class NeoForgePlatformHelper implements IPlatformHelper {

    public static PayloadRegistrar registrar;

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
    public Button createCurioButton(AbstractContainerScreen<?> parentGui, int xIn, int yIn, int widthIn, int heightIn,
                                    WidgetSprites sprites) {
        return new CurioButton(parentGui, xIn, yIn, widthIn, heightIn, sprites);
    }

    @Override
    public boolean fireTameEvent() {
        return false;
    }

    @Override
    public <MSG extends S2CModPacket> void registerClientPacket(CustomPacketPayload.Type<MSG> type, StreamCodec<RegistryFriendlyByteBuf, MSG> streamCodec) {

    }

    @Override
    public <MSG extends C2SModPacket> void registerServerPacket(CustomPacketPayload.Type<MSG> type, StreamCodec<RegistryFriendlyByteBuf, MSG> streamCodec) {

    }

    @Override
    public void sendToClient(S2CModPacket msg, ServerPlayer player) {

    }

    @Override
    public void sendToServer(C2SModPacket msg) {

    }

    @Override
    public MenuProvider curioMenuProvider(StileEntity stileEntity) {
        return new MenuProvider() {
            @Override
            public Component getDisplayName() {
                return stileEntity.getDisplayName();
            }

            @Override
            public @Nullable AbstractContainerMenu createMenu(int containerId, Inventory playerInventory, Player player) {
                return new StileCurioMenu(containerId,playerInventory,stileEntity);
            }
        };
    }
}