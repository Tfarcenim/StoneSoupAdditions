package tfar.stonesoupadditions.client;

import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraft.client.renderer.entity.EntityRenderers;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.client.event.EntityRenderersEvent;
import net.neoforged.neoforge.client.event.RegisterMenuScreensEvent;
import software.bernie.geckolib.renderer.GeoEntityRenderer;
import tfar.stonesoupadditions.Constants;
import tfar.stonesoupadditions.init.SSAEntityTypes;
import tfar.stonesoupadditions.init.SSAMenuTypes;

@Mod(value = Constants.MOD_ID,dist = Dist.CLIENT)
public class StoneSoupClientNeoForge {
    public StoneSoupClientNeoForge(IEventBus bus) {
        bus.addListener(this::models);
        bus.addListener(this::screens);
    }

    void models(final EntityRenderersEvent.RegisterRenderers event) {
        EntityRenderers.register(SSAEntityTypes.STILE,c -> new GeoEntityRenderer<>(c,SSAEntityTypes.STILE));
    }

    void screens(RegisterMenuScreensEvent event) {
        MenuScreens.register(SSAMenuTypes.STILE,StileScreen::new);
    }
}
