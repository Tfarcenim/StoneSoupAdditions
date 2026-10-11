package tfar.stonesoupadditions.network.server;

import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.server.level.ServerPlayer;
import tfar.stonesoupadditions.entity.StileEntity;
import tfar.stonesoupadditions.network.PacketHandler;
import tfar.stonesoupadditions.platform.Services;

public record C2SOpenStileCuriosPacket(int entityID) implements C2SModPacket {

    public static final StreamCodec<RegistryFriendlyByteBuf, C2SOpenStileCuriosPacket> STREAM_CODEC =
            StreamCodec.composite(ByteBufCodecs.INT, C2SOpenStileCuriosPacket::entityID, C2SOpenStileCuriosPacket::new);

    public static final CustomPacketPayload.Type<C2SOpenStileCuriosPacket> TYPE = new CustomPacketPayload.Type<>(
            PacketHandler.packet(C2SOpenStileCuriosPacket.class));


    public static void send(StileEntity stile) {
        Services.PLATFORM.sendToServer(new C2SOpenStileCuriosPacket(stile.getId()));
    }

    public void handleServer(ServerPlayer player) {

    }

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }
}

