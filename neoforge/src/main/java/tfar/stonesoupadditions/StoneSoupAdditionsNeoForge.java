package tfar.stonesoupadditions;


import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.EntityHitResult;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.entity.EntityAttributeCreationEvent;
import net.neoforged.neoforge.event.entity.EntityTeleportEvent;
import net.neoforged.neoforge.event.entity.player.AttackEntityEvent;
import net.neoforged.neoforge.event.entity.player.PlayerInteractEvent;
import net.neoforged.neoforge.event.tick.PlayerTickEvent;
import net.neoforged.neoforge.network.event.RegisterPayloadHandlersEvent;
import net.neoforged.neoforge.registries.RegisterEvent;
import tfar.stonesoupadditions.attachments.CommonDataAttachment;
import tfar.stonesoupadditions.attachments.DataAttachmentUtil;
import tfar.stonesoupadditions.datagen.SSADatagen;
import tfar.stonesoupadditions.entity.LushScepterHookEntity;
import tfar.stonesoupadditions.entity.StileEntity;
import tfar.stonesoupadditions.init.SSAEntityTypes;
import tfar.stonesoupadditions.init.SSAItems;
import tfar.stonesoupadditions.item.GS04Item;
import tfar.stonesoupadditions.network.PacketHandler;
import tfar.stonesoupadditions.platform.NeoForgePlatformHelper;

import java.util.List;

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
        NeoForge.EVENT_BUS.addListener(this::onUseItem);
        NeoForge.EVENT_BUS.addListener(this::attemptTeleport);
    }

    public void register(RegisterEvent event) {
        StoneSoupAdditions.register();
        SSAMenuTypesNeoForge.init();
    }

    void onUseItem(PlayerInteractEvent.RightClickItem event) {
        Player player = event.getEntity();
        if (isHooked(player)) {
            event.setCanceled(true);
        }
    }

    public static boolean isHooked(Entity entity) {
        AABB aabb = entity.getBoundingBox();
        List<Entity> entityList = entity.level().getEntities(entity,aabb);
        return entityList.stream().anyMatch(entity1 -> entity1 instanceof LushScepterHookEntity lushScepterHookEntity &&
                lushScepterHookEntity.getHookedIn() ==entity);
    }

    void attemptTeleport(EntityTeleportEvent event) {
        Entity entity = event.getEntity();
        if (isHooked(entity)) {
            event.setCanceled(true);
        }
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

            DataAttachmentUtil.decrementDashing(player);

        }
    }

    void onAttack(AttackEntityEvent event) {
        Player player = event.getEntity();
        ItemStack stack = player.getMainHandItem();
        if (stack.is(SSAItems.GS_04)) {
            event.setCanceled(true);
            if (!player.level().isClientSide) {
                EntityHitResult entityHitResult = GS04Item.raycast(player);
                if (entityHitResult != null && entityHitResult.getEntity() instanceof LivingEntity livingEntity) {
                    livingEntity.hurt(player.damageSources().playerAttack(player), 1);
                    stack.hurtAndBreak(1, player, EquipmentSlot.MAINHAND);
                }
            }
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