package tfar.stonesoupadditions.network.server;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.server.level.ServerPlayer;
import tfar.stonesoupadditions.attachments.DataAttachmentUtil;
import tfar.stonesoupadditions.network.PacketHandler;
import tfar.stonesoupadditions.platform.Services;

public enum C2SEmptyClickPacket implements C2SModPacket{
    INSTANCE;

    public static final StreamCodec<RegistryFriendlyByteBuf, C2SEmptyClickPacket> STREAM_CODEC =
            StreamCodec.of(FriendlyByteBuf::writeEnum,
                    buffer -> buffer.readEnum(C2SEmptyClickPacket.class));

    public static final CustomPacketPayload.Type<C2SEmptyClickPacket> TYPE = new CustomPacketPayload.Type<>(
            PacketHandler.packet(C2SEmptyClickPacket.class));

    @Override
    public void handleServer(ServerPlayer player) {
        DataAttachmentUtil.setHasClickedLeftRecently(player);
    }

    public void send() {
        Services.PLATFORM.sendToServer(this);
    }

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }
}
