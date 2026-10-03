package tfar.stonesoupadditions;


import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.event.entity.EntityAttributeCreationEvent;
import net.neoforged.neoforge.registries.RegisterEvent;
import tfar.stonesoupadditions.init.SSAEntityTypes;

@Mod(Constants.MOD_ID)
public class StoneSoupAdditionsNeoForge {

    public StoneSoupAdditionsNeoForge(IEventBus eventBus) {
        eventBus.addListener(this::register);
        eventBus.addListener(this::createAttributes);
        StoneSoupAdditions.init();
    }

    public void register(RegisterEvent event) {StoneSoupAdditions.register();}

    void createAttributes(EntityAttributeCreationEvent event) {
        event.put(SSAEntityTypes.STILE,StileEntity.createStileAttributes().build());
    }
}