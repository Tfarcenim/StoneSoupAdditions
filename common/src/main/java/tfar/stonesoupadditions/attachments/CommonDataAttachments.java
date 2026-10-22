package tfar.stonesoupadditions.attachments;

import com.mojang.serialization.Codec;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.resources.ResourceLocation;
import tfar.stonesoupadditions.platform.Services;


import java.util.*;

public class CommonDataAttachments {

    private static final Map<ResourceLocation,CommonDataAttachment<?>> MAP =new HashMap<>();

    public static final CommonDataAttachment<Integer> HAS_CLICKED_LEFT_RECENTLY =
            register(CommonDataAttachment.create(o -> 0)
                    .copyOnDeath()
                    .networkSynchronized(ByteBufCodecs.INT)
                    .codec(Codec.INT)
                    .build("has_clicked_left_recently"));

    public static final CommonDataAttachment<Integer> HAS_CLICKED_RIGHT_RECENTLY =
            register(CommonDataAttachment.create(o -> 0)
                    .copyOnDeath()
                    .networkSynchronized(ByteBufCodecs.INT)
                    .codec(Codec.INT)
                    .build("has_clicked_right_recently"));

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