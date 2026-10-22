package tfar.stonesoupadditions.attachments;

import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.LivingEntity;
import tfar.stonesoupadditions.item.DuelingShieldItem;
import tfar.stonesoupadditions.platform.Services;

public class DataAttachmentUtil {

    public static <T> void clearValue(LivingEntity entity,CommonDataAttachment<T> attachment) {
        Services.PLATFORM.setAttachedValue(entity,attachment, attachment.defaultValueSupplier.apply(entity));
    }

    public static void setHasClickedLeftRecently(ServerPlayer entity) {
       Services.PLATFORM.setAttachedValue(entity,CommonDataAttachments.HAS_CLICKED_LEFT_RECENTLY,10);
       DuelingShieldItem.checkForDash(entity);
    }

    public static void setHasClickedRightRecently(ServerPlayer entity) {
        Services.PLATFORM.setAttachedValue(entity,CommonDataAttachments.HAS_CLICKED_RIGHT_RECENTLY,10);
        DuelingShieldItem.checkForDash(entity);
    }

    public static boolean hasClickedLeftRecently(LivingEntity entity) {
        return Services.PLATFORM.getOrCreateAttachedValue(entity,CommonDataAttachments.HAS_CLICKED_LEFT_RECENTLY) >0;
    }

    public static boolean hasClickedRightRecently(LivingEntity entity) {
        return Services.PLATFORM.getOrCreateAttachedValue(entity,CommonDataAttachments.HAS_CLICKED_RIGHT_RECENTLY) > 0;
    }

    public static void decrementRightClick(LivingEntity entity) {
        int i = Services.PLATFORM.getOrCreateAttachedValue(entity,CommonDataAttachments.HAS_CLICKED_RIGHT_RECENTLY);
        if (i > 0) {
            i--;
            Services.PLATFORM.setAttachedValue(entity,CommonDataAttachments.HAS_CLICKED_RIGHT_RECENTLY,i);
        }
    }

    public static void decrementLeftClick(LivingEntity entity) {
        int i = Services.PLATFORM.getOrCreateAttachedValue(entity,CommonDataAttachments.HAS_CLICKED_LEFT_RECENTLY);
        if (i > 0) {
            i--;
            Services.PLATFORM.setAttachedValue(entity,CommonDataAttachments.HAS_CLICKED_LEFT_RECENTLY,i);
        }
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

}
