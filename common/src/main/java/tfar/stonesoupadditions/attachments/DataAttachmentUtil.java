package tfar.stonesoupadditions.attachments;

import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.LivingEntity;
import tfar.stonesoupadditions.ClientControls;
import tfar.stonesoupadditions.item.DuelingShieldItem;
import tfar.stonesoupadditions.platform.Services;

public class DataAttachmentUtil {

    public static <T> void clearValue(LivingEntity entity,CommonDataAttachment<T> attachment) {
        Services.PLATFORM.setAttachedValue(entity,attachment, attachment.defaultValueSupplier.apply(entity));
    }

    public static void decrementDashing(LivingEntity entity) {
        int i = Services.PLATFORM.getOrCreateAttachedValue(entity,CommonDataAttachments.DASHING);
        if (i > 0) {
            i--;
            Services.PLATFORM.setAttachedValue(entity,CommonDataAttachments.DASHING,i);
        }
    }

    public static boolean isDashing(LivingEntity entity) {
        return Services.PLATFORM.getOrCreateAttachedValue(entity,CommonDataAttachments.DASHING) > 0;
    }

    public static void setDashing(LivingEntity entity) {
        Services.PLATFORM.setAttachedValue(entity,CommonDataAttachments.DASHING,10);
    }

    public static ClientControls getClientControls(ServerPlayer player) {
        return Services.PLATFORM.getOrCreateAttachedValue(player,CommonDataAttachments.CLIENT_CONTROLS);
    }

    public static void setClientControls(ServerPlayer player,ClientControls clientControls) {
        Services.PLATFORM.setAttachedValue(player,CommonDataAttachments.CLIENT_CONTROLS,clientControls);
    }
}
