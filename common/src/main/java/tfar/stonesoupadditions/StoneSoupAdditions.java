package tfar.stonesoupadditions;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import tfar.stonesoupadditions.attachments.CommonDataAttachments;
import tfar.stonesoupadditions.init.SSAEntityTypes;
import tfar.stonesoupadditions.init.SSAItems;
import tfar.stonesoupadditions.init.SSAMenuTypes;

// This class is part of the common project meaning it is shared between all supported loaders. Code written here can only
// import and access the vanilla codebase, libraries used by vanilla, and optionally third party libraries that provide
// common compatible binaries. This means common code can not directly use loader specific concepts such as Forge events
// however it will be compatible with all supported mod loaders.
public class StoneSoupAdditions {

    // The loader specific projects are able to import and use any code from the common project. This allows you to
    // write the majority of your code here and load it from your loader specific projects. This example has some
    // code that gets invoked by the entry point of the loader specific projects.
    public static void init() {
    }

    public static void register() {
        SSAEntityTypes.init();
        SSAItems.init();
        SSAMenuTypes.init();
        CommonDataAttachments.init();
    }

    public static ResourceLocation id(String name) {
        return ResourceLocation.fromNamespaceAndPath(Constants.MOD_ID, name);
    }

    public static boolean isDualShields(Player player) {
        return player.getMainHandItem().is(SSAItems.DUELING_SHIELD) && player.getOffhandItem().is(SSAItems.DUELING_SHIELD);
    }
}