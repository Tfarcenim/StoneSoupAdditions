package tfar.stonesoupadditions;


import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.entity.EntityAttributeCreationEvent;
import net.neoforged.neoforge.event.entity.player.AttackEntityEvent;
import net.neoforged.neoforge.event.tick.PlayerTickEvent;
import net.neoforged.neoforge.network.event.RegisterPayloadHandlersEvent;
import net.neoforged.neoforge.registries.RegisterEvent;
import tfar.stonesoupadditions.attachments.CommonDataAttachment;
import tfar.stonesoupadditions.attachments.DataAttachmentUtil;
import tfar.stonesoupadditions.datagen.SSADatagen;
import tfar.stonesoupadditions.entity.StileEntity;
import tfar.stonesoupadditions.init.SSAEntityTypes;
import tfar.stonesoupadditions.init.SSAItems;
import tfar.stonesoupadditions.network.PacketHandler;
import tfar.stonesoupadditions.platform.NeoForgePlatformHelper;

@Mod(Constants.MOD_ID)
public class StoneSoupAdditionsNeoForge {

    public StoneSoupAdditionsNeoForge(IEventBus eventBus) {
        eventBus.addListener(this::register);
        eventBus.addListener(this::createAttributes);
        StoneSoupAdditions.init();
        eventBus.addListener(SSADatagen::gather);
        NeoForge.EVENT_BUS.addListener(this::playerTick);
        NeoForge.EVENT_BUS.addListener(this::onAttack);
        eventBus.addListener(this::network);
    }

    public void register(RegisterEvent event) {
        StoneSoupAdditions.register();
        SSAMenuTypesNeoForge.init();
    }

    void playerTick(PlayerTickEvent.Pre event) {
        Player player = event.getEntity();
        if (player instanceof ServerPlayer serverPlayer) {
            if (player.tickCount % 20 == 0) {
                ItemStack mainHandStack = player.getMainHandItem();
                ItemStack offHandStack = player.getOffhandItem();
                int resistanceLevel = 0;
                if (mainHandStack.getItem() == SSAItems.DUELING_SHIELD) {
                    resistanceLevel++;
                }
                if (offHandStack.getItem() == SSAItems.DUELING_SHIELD) {
                    resistanceLevel++;
                }
                if (resistanceLevel > 0) {
                    player.addEffect(new MobEffectInstance(MobEffects.DAMAGE_RESISTANCE, 25, resistanceLevel - 1,true,false));
                }
            }
            DataAttachmentUtil.decrementRightClick(player);
            DataAttachmentUtil.decrementLeftClick(player);
            DataAttachmentUtil.decrementDashing(player);

        }
    }

    void onAttack(AttackEntityEvent event) {
        Player player = event.getEntity();
        if (player instanceof ServerPlayer serverPlayer) {
            DataAttachmentUtil.setHasClickedLeftRecently(serverPlayer);
        }
    }

    void createAttributes(EntityAttributeCreationEvent event) {
        event.put(SSAEntityTypes.STILE, StileEntity.createStileAttributes().build());
    }

    void network(RegisterPayloadHandlersEvent event) {
        NeoForgePlatformHelper.registrar = event.registrar(Constants.MOD_ID);
        PacketHandler.registerPackets();
    }
}