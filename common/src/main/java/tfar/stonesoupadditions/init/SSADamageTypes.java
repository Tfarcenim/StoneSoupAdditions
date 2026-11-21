package tfar.stonesoupadditions.init;

import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.damagesource.DamageType;
import tfar.stonesoupadditions.StoneSoupAdditions;

public class SSADamageTypes {
    public static final ResourceKey<DamageType> GS_04_CHARGED = ResourceKey.create(Registries.DAMAGE_TYPE, StoneSoupAdditions.id("gs_04_charged"));

    public static void bootstrap(BootstrapContext<DamageType> context) {
        context.register(GS_04_CHARGED,new DamageType("gs_04_charged",.1f));
    }
}