package tfar.stonesoupadditions.network;

import net.minecraft.resources.ResourceLocation;
import tfar.stonesoupadditions.StoneSoupAdditions;
import tfar.stonesoupadditions.network.server.C2SOpenStileCuriosPacket;
import tfar.stonesoupadditions.platform.Services;

import java.util.Locale;

public class PacketHandler {

    public static void registerPackets() {

        Services.PLATFORM.registerServerPacket(C2SOpenStileCuriosPacket.TYPE, C2SOpenStileCuriosPacket.STREAM_CODEC);

        ///////server to client
    }

    public static ResourceLocation packet(Class<?> clazz) {
        return StoneSoupAdditions.id(clazz.getName().toLowerCase(Locale.ROOT));
    }

}
