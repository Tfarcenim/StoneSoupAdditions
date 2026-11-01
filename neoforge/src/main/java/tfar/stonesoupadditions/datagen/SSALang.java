package tfar.stonesoupadditions.datagen;

import net.minecraft.data.PackOutput;
import net.neoforged.neoforge.common.data.LanguageProvider;
import tfar.stonesoupadditions.Constants;
import tfar.stonesoupadditions.init.SSAEntityTypes;
import tfar.stonesoupadditions.init.SSAItems;

public class SSALang extends LanguageProvider {
    public SSALang(PackOutput output) {
        super(output, Constants.MOD_ID, "en_us");
    }

    @Override
    protected void addTranslations() {
        add(SSAEntityTypes.STILE, "Stile");
        add(SSAEntityTypes.LUSH_SCEPTER_HOOK, "Lush Scepter Hook");
        add(SSAItems.DUELING_SHIELD, "Dueling Shield");
        add(SSAItems.LUSH_SCEPTER, "Lush Scepter");
    }
}
