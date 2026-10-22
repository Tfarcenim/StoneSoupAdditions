package tfar.stonesoupadditions.platform;

import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.WidgetSprites;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.core.Registry;
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
import net.neoforged.neoforge.attachment.AttachmentType;
import net.neoforged.neoforge.attachment.IAttachmentHolder;
import net.neoforged.neoforge.common.extensions.IMenuTypeExtension;
import net.neoforged.neoforge.network.PacketDistributor;
import net.neoforged.neoforge.network.registration.PayloadRegistrar;
import net.neoforged.neoforge.registries.NeoForgeRegistries;
import org.jetbrains.annotations.Nullable;
import tfar.stonesoupadditions.ContainerFactory;
import tfar.stonesoupadditions.StileCurioMenu;
import tfar.stonesoupadditions.attachments.CommonDataAttachment;
import tfar.stonesoupadditions.entity.StileEntity;
import tfar.stonesoupadditions.client.CurioButton;
import tfar.stonesoupadditions.network.client.S2CModPacket;
import tfar.stonesoupadditions.network.server.C2SModPacket;
import tfar.stonesoupadditions.platform.services.IPlatformHelper;
import net.neoforged.fml.ModList;
import net.neoforged.fml.loading.FMLLoader;

import java.util.OptionalInt;
import java.util.function.Consumer;
import java.util.function.Function;

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
    public <MSG extends S2CModPacket> void registerClientPacket(CustomPacketPayload.Type<MSG> type, StreamCodec<RegistryFriendlyByteBuf,MSG> streamCodec) {
        registrar.playToClient(type, streamCodec, (p, t) -> p.handleClient());
    }

    @Override
    public <MSG extends C2SModPacket> void registerServerPacket(CustomPacketPayload.Type<MSG> type, StreamCodec<RegistryFriendlyByteBuf, MSG> streamCodec) {
        registrar.playToServer(type, streamCodec, (p, t) -> p.handleServer((ServerPlayer) t.player()));
    }


    @Override
    public void sendToClient(S2CModPacket msg, ServerPlayer player) {
        PacketDistributor.sendToPlayer(player,msg);
    }

    @Override
    public void sendToServer(C2SModPacket msg) {
        PacketDistributor.sendToServer(msg);
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


    @Override
    public <T> void registerDataAttachment(CommonDataAttachment<T> attachment) {
        AttachmentType.Builder<T> builder = AttachmentType.builder((Function<IAttachmentHolder, T>) (Object) attachment.getDefaultValueSupplier());
        if (attachment.getCodec() != null) {
            builder.serialize(attachment.getCodec());
        }
        if (attachment.isCopyOnDeath()) {
            builder.copyOnDeath();
        }
        if (attachment.canSync()) {
            builder.sync(attachment.getStreamCodec());
        }
        AttachmentType<T> type = builder.build();
        Registry.register(NeoForgeRegistries.ATTACHMENT_TYPES, attachment.getName(), type);
        attachment.setAttachment(type);
    }

    @SuppressWarnings({"unchecked"})
    @Override
    @Nullable
    public <T> T getAttachedValue(Object object, CommonDataAttachment<T> attachment) {
        AttachmentType<T> type = (AttachmentType<T>) attachment.getAttachment();
        if (object instanceof IAttachmentHolder attachmentHolder) {
            return attachmentHolder.getExistingDataOrNull(type);
        } else {
            throw new IllegalStateException("Cannot attach data to " + object);
        }
    }

    @SuppressWarnings({"unchecked"})
    @Override
    public <T> void setAttachedValue(Object object, CommonDataAttachment<T> attachment, @Nullable T value) {
        AttachmentType<T> type = (AttachmentType<T>) attachment.getAttachment();
        if (object instanceof IAttachmentHolder attachmentHolder) {
            if (value == null) {
                attachmentHolder.removeData(type);
            } else {
                attachmentHolder.setData(type, value);
            }
        } else {
            throw new IllegalStateException("Cannot attach data to " + object);
        }
    }

}