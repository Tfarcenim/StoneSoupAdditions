package tfar.stonesoupadditions;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;

public class FlightEffect extends MobEffect {

    public static final ResourceLocation ID = StoneSoupAdditions.id("flight");

    public FlightEffect(MobEffectCategory category, int color) {
        super(category, color);
    }
}
