package tfar.stonesoupadditions;


import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.event.entity.EntityAttributeCreationEvent;
import net.neoforged.neoforge.network.event.RegisterPayloadHandlersEvent;
import net.neoforged.neoforge.network.registration.PayloadRegistrar;
import net.neoforged.neoforge.registries.RegisterEvent;
import tfar.stonesoupadditions.init.SSAEntityTypes;
import tfar.stonesoupadditions.platform.NeoForgePlatformHelper;

@Mod(Constants.MOD_ID)
public class StoneSoupAdditionsNeoForge {

    public StoneSoupAdditionsNeoForge(IEventBus eventBus) {
        eventBus.addListener(this::register);
        eventBus.addListener(this::createAttributes);
        StoneSoupAdditions.init();
    }

    public void register(RegisterEvent event) {
        StoneSoupAdditions.register();
        SSAMenuTypesNeoForge.init();
    }

    void createAttributes(EntityAttributeCreationEvent event) {
        event.put(SSAEntityTypes.STILE,StileEntity.createStileAttributes().build());
    }

    void network(RegisterPayloadHandlersEvent event) {
        NeoForgePlatformHelper.registrar = event.registrar(Constants.MOD_ID);

    }
}