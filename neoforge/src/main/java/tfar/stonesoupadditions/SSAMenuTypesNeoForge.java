package tfar.stonesoupadditions;

import net.minecraft.world.inventory.MenuType;
import tfar.stonesoupadditions.init.SSAMenuTypes;

public class SSAMenuTypesNeoForge {

    public static final MenuType<StileCurioMenu> STILE_CURIOS = SSAMenuTypes.registerExtended("stile_curios",StileCurioMenu::new);
    public static void init() {}

}
