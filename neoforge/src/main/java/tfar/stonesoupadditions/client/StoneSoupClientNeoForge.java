package tfar.stonesoupadditions.client;

import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRenderers;
import net.minecraft.client.renderer.entity.player.PlayerRenderer;
import net.minecraft.client.resources.PlayerSkin;
import net.minecraft.world.entity.player.Player;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.client.event.EntityRenderersEvent;
import net.neoforged.neoforge.client.event.RegisterMenuScreensEvent;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.entity.player.PlayerInteractEvent;
import software.bernie.geckolib.renderer.GeoEntityRenderer;
import tfar.stonesoupadditions.Constants;
import tfar.stonesoupadditions.SSAMenuTypesNeoForge;
import tfar.stonesoupadditions.init.SSAEntityTypes;
import tfar.stonesoupadditions.init.SSAMenuTypes;
import tfar.stonesoupadditions.network.server.C2SEmptyClickPacket;

import java.util.Set;

@Mod(value = Constants.MOD_ID,dist = Dist.CLIENT)
public class StoneSoupClientNeoForge {
    public StoneSoupClientNeoForge(IEventBus bus) {
        bus.addListener(this::models);
        bus.addListener(this::layers);
        bus.addListener(this::screens);
        NeoForge.EVENT_BUS.addListener(this::handleLeftClick);
    }

    void models(final EntityRenderersEvent.RegisterRenderers event) {
        EntityRenderers.register(SSAEntityTypes.STILE,c -> new StileRenderer(c,SSAEntityTypes.STILE));
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

    void handleLeftClick(PlayerInteractEvent.LeftClickEmpty event) {
        C2SEmptyClickPacket.INSTANCE.send();
    }

    void screens(RegisterMenuScreensEvent event) {
        MenuScreens.register(SSAMenuTypesNeoForge.STILE_CURIOS,StileCurioScreen::new);
    }
}
