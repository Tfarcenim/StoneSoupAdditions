package tfar.stonesoupadditions;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.Container;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import tfar.stonesoupadditions.entity.StileEntity;
import tfar.stonesoupadditions.init.SSAMenuTypes;
import tfar.stonesoupadditions.platform.Services;

public class StileMenu extends AbstractContainerMenu {

    public final int inventoryX = 8;
    public final int inventoryY = 84;
    private final Container container;
    public final StileEntity stileEntity;

    public StileMenu(int id, Inventory inventory, FriendlyByteBuf buf) {
        this(id, inventory,new SimpleContainer(StileEntity.INVENTORY_SIZE),readStileEntity(inventory.player.level(),buf));
    }

    static StileEntity readStileEntity(Level level, FriendlyByteBuf buf) {
        int id = buf.readInt();
        Entity entity = level.getEntity(id);
        return entity instanceof StileEntity ? (StileEntity) entity : null;
    }

    public StileMenu(int containerId, Inventory inventory, Container container, StileEntity stileEntity) {
        super(SSAMenuTypes.STILE, containerId);
        this.container = container;
        this.stileEntity = stileEntity;

        int y = 8;
        //armor
        addSlot(new Slot(container, 0, 8, y+ 18*3));//feet
        addSlot(new Slot(container, 1, 8, y+18 * 2));
        addSlot(new Slot(container, 2, 8, y+18));
        addSlot(new Slot(container, 3, 8, y));//head

        //hand
        addSlot(new Slot(container, 4, 77, y+18*3));

        for (int k = 0; k < 3; k++) {
            for (int i1 = 0; i1 < 9; i1++) {
                this.addSlot(new Slot(inventory, i1 + k * 9 + 9, inventoryX + i1 * 18, inventoryY + k * 18));
            }
        }

        for (int l = 0; l < 9; l++) {
            this.addSlot(new Slot(inventory, l, 8 + l * 18, inventoryY + 58));
        }
    }



    @Override
    public ItemStack quickMoveStack(Player player, int index) {
        return ItemStack.EMPTY;
    }

    @Override
    public boolean clickMenuButton(Player player, int id) {
        Services.PLATFORM.openExtendedMenu(player, Services.PLATFORM.curioMenuProvider(stileEntity),b -> b.writeInt(stileEntity.getId()));
        return true;
    }

    @Override
    public boolean stillValid(Player player) {
        return container.stillValid(player);
    }
}
