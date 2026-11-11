package tfar.stonesoupadditions.network.server;

import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.ItemStack;
import tfar.stonesoupadditions.ClientControls;
import tfar.stonesoupadditions.attachments.DataAttachmentUtil;
import tfar.stonesoupadditions.item.DuelingShieldItem;
import tfar.stonesoupadditions.item.GS04Item;
import tfar.stonesoupadditions.network.PacketHandler;

public record C2SUpdateClientControlsPacket(boolean isHoldingLeft,boolean isHoldingRight) implements C2SModPacket {

    public static final StreamCodec<RegistryFriendlyByteBuf, C2SUpdateClientControlsPacket> STREAM_CODEC =
            StreamCodec.composite(ByteBufCodecs.BOOL, C2SUpdateClientControlsPacket::isHoldingLeft,
                    ByteBufCodecs.BOOL, C2SUpdateClientControlsPacket::isHoldingRight,
                    C2SUpdateClientControlsPacket::new);

    public static final Type<C2SUpdateClientControlsPacket> TYPE = new Type<>(
            PacketHandler.packet(C2SUpdateClientControlsPacket.class));

    public void handleServer(ServerPlayer player) {
        ClientControls prior = DataAttachmentUtil.getClientControls(player);

        if (prior.holdingLeftClick() && !isHoldingLeft) {
            onStopHoldingLeftClick(player,prior.ticksLeftClickHeld());
        }

        if (!prior.holdingLeftClick() && isHoldingLeft) {
            onClick(player);
        }

        if (!prior.holdingRightClick() && isHoldingRight) {
            onClick(player);
        }

        long timestamp = player.level().getGameTime();
        ClientControls updated = prior.update(timestamp,isHoldingLeft,isHoldingRight);

        DataAttachmentUtil.setClientControls(player, updated);
    }

    void onStopHoldingLeftClick(ServerPlayer player, long ticksLeftClickHeld) {
        ItemStack held = player.getMainHandItem();
        if (held.getItem() instanceof GS04Item) {
            GS04Item.fireShot(player,held,ticksLeftClickHeld);
        }
    }

    void onClick(ServerPlayer player) {
        DuelingShieldItem.checkForDash(player);
    }

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }
}

