package tfar.stonesoupadditions.attachments;

import com.mojang.serialization.Codec;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.resources.ResourceLocation;
import tfar.stonesoupadditions.ClientControls;
import tfar.stonesoupadditions.platform.Services;


import java.util.*;

public class CommonDataAttachments {

    private static final Map<ResourceLocation,CommonDataAttachment<?>> MAP =new HashMap<>();

    public static final CommonDataAttachment<ClientControls> CLIENT_CONTROLS =
            register(CommonDataAttachment.create(o -> new ClientControls(false,false,-1,-1,0))
                    .build("client_controls"));




    public static final CommonDataAttachment<Integer> DASHING =
            register(CommonDataAttachment.create(o -> 0)
                    .copyOnDeath()
                    .networkSynchronized(ByteBufCodecs.INT)
                    .codec(Codec.INT)
                    .build("dashing"));

    public static CommonDataAttachment<?> lookup(ResourceLocation location) {
        return MAP.get(location);
    }

    static <T> CommonDataAttachment<T> register(CommonDataAttachment<T> type) {
        Services.PLATFORM.registerDataAttachment(type);
        Objects.requireNonNull(type.getAttachment());
        MAP.put(type.name,type);
        return type;
    }

    public static void init() {

    }
}