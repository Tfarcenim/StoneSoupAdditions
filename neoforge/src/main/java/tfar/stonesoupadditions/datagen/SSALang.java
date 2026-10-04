package tfar.stonesoupadditions.datagen;

import net.minecraft.data.PackOutput;
import net.neoforged.neoforge.common.data.LanguageProvider;
import tfar.stonesoupadditions.Constants;
import tfar.stonesoupadditions.init.SSAEntityTypes;

public class SSALang extends LanguageProvider {
    public SSALang(PackOutput output) {
        super(output, Constants.MOD_ID, "en_us");
    }

    @Override
    protected void addTranslations() {
        add(SSAEntityTypes.STILE, "Stile");
    }
}
