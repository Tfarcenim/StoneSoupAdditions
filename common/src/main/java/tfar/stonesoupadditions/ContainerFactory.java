package tfar.stonesoupadditions;

import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.MenuType;

@FunctionalInterface
public interface ContainerFactory<T extends AbstractContainerMenu> extends MenuType.MenuSupplier<T> {
    T create(int var1, Inventory var2, RegistryFriendlyByteBuf var3);

    default T create(int p_create_1_, Inventory p_create_2_) {
        return this.create(p_create_1_, p_create_2_, null);
    }
}
