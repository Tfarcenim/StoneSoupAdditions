package tfar.stonesoupadditions.client;

import net.minecraft.client.Minecraft;
import net.minecraft.client.Options;
import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRenderers;
import net.minecraft.client.renderer.entity.NoopRenderer;
import net.minecraft.client.renderer.entity.player.PlayerRenderer;
import net.minecraft.client.resources.PlayerSkin;
import net.minecraft.world.entity.player.Player;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.client.event.ClientTickEvent;
import net.neoforged.neoforge.client.event.EntityRenderersEvent;
import net.neoforged.neoforge.client.event.RegisterMenuScreensEvent;
import net.neoforged.neoforge.client.extensions.common.IClientItemExtensions;
import net.neoforged.neoforge.client.extensions.common.RegisterClientExtensionsEvent;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.entity.player.PlayerInteractEvent;
import software.bernie.geckolib.renderer.GeoItemRenderer;
import software.bernie.geckolib.renderer.layer.AutoGlowingGeoLayer;
import tfar.stonesoupadditions.Constants;
import tfar.stonesoupadditions.SSAMenuTypesNeoForge;
import tfar.stonesoupadditions.init.SSAEntityTypes;
import tfar.stonesoupadditions.init.SSAItems;
import tfar.stonesoupadditions.item.GS04Item;
import tfar.stonesoupadditions.network.server.C2SUpdateClientControlsPacket;
import tfar.stonesoupadditions.platform.Services;

import java.util.Set;

@Mod(value = Constants.MOD_ID,dist = Dist.CLIENT)
public class StoneSoupClientNeoForge {
    public StoneSoupClientNeoForge(IEventBus bus) {
        bus.addListener(this::models);
        bus.addListener(this::layers);
        bus.addListener(this::screens);
        bus.addListener(this::clientSetup);
        NeoForge.EVENT_BUS.addListener(this::clientTick);
    }

    void clientTick(ClientTickEvent.Pre event) {
        LocalPlayer player = Minecraft.getInstance().player;
        if (player != null) {
            Options options = Minecraft.getInstance().options;
            Services.PLATFORM.sendToServer(new C2SUpdateClientControlsPacket(options.keyAttack.isDown(), options.keyUse.isDown()));
        }
    }

    void models(final EntityRenderersEvent.RegisterRenderers event) {
        EntityRenderers.register(SSAEntityTypes.STILE,c -> new StileRenderer(c,SSAEntityTypes.STILE));
        EntityRenderers.register(SSAEntityTypes.LUSH_SCEPTER_HOOK, NoopRenderer::new);
    }

    void clientSetup(RegisterClientExtensionsEvent event) {
        event.registerItem(new IClientItemExtensions() {

            GeoItemRenderer<GS04Item> renderer;

            @Override
            public BlockEntityWithoutLevelRenderer getCustomRenderer() {
                if (renderer == null) {
                    renderer = new GeoItemRenderer<>(SSAItems.GS_04);
                    renderer.addRenderLayer(new AutoGlowingGeoLayer<>(renderer));
                }
                return renderer;
            }
        }, SSAItems.GS_04);
    }

    void layers(EntityRenderersEvent.AddLayers event) {
        Set<PlayerSkin.Model> skins = event.getSkins();
        for (PlayerSkin.Model skin : skins) {
            EntityRenderer<? extends Player> renderer = event.getSkin(skin);
            if (renderer instanceof PlayerRenderer playerRenderer) {
                playerRenderer.addLayer(new StileOnShoulderLayer<>(playerRenderer,event.getEntityModels(),event.getContext()));
            }
        }
    }

    void screens(RegisterMenuScreensEvent event) {
        MenuScreens.register(SSAMenuTypesNeoForge.STILE_CURIOS,StileCurioScreen::new);
    }
}
