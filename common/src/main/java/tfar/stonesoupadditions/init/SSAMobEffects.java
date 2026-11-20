package tfar.stonesoupadditions.init;

import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import tfar.stonesoupadditions.FlightEffect;
import tfar.stonesoupadditions.StoneSoupAdditions;
import tfar.stonesoupadditions.platform.Services;

public class SSAMobEffects {
    public static final Holder<MobEffect> FLIGHT = register("flight",new FlightEffect(MobEffectCategory.BENEFICIAL,0x0000ff)
            .addAttributeModifier(Services.PLATFORM.getCreativeFlightAttribute(),FlightEffect.ID, 1, AttributeModifier.Operation.ADD_VALUE));


    private static Holder<MobEffect> register(String name, MobEffect effect) {
        return Registry.registerForHolder(BuiltInRegistries.MOB_EFFECT, StoneSoupAdditions.id(name), effect);
    }

    public static void init() {}

}
