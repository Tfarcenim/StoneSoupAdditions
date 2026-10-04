package tfar.stonesoupadditions.client;

import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.AbstractButton;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.gui.screens.inventory.InventoryScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Tuple;
import net.minecraft.world.entity.player.Inventory;
import tfar.stonesoupadditions.StileMenu;
import tfar.stonesoupadditions.StoneSoupAdditions;
import tfar.stonesoupadditions.platform.Services;
import top.theillusivec4.curios.client.gui.CuriosButton;

public class StileScreen extends AbstractContainerScreen<StileMenu> {

    public static final ResourceLocation INVENTORY_LOCATION = StoneSoupAdditions.id("textures/gui/stile.png");

    public StileScreen(StileMenu menu, Inventory playerInventory, Component title) {
        super(menu, playerInventory, title);
    }

    @Override
    protected void init() {
        super.init();
        Tuple<Integer, Integer> offsets = top.theillusivec4.curios.client.gui.CuriosScreen.getButtonOffset(false);
        int x = offsets.getA();
        int y = offsets.getB();
        int size = 10;
        int yOffset = 81;
        addRenderableWidget(Services.PLATFORM.createCurioButton(this,this.leftPos + x - 2,topPos + y + yOffset, size, size, CuriosButton.BIG));
    }

    @Override
    public void render(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick) {
        super.render(guiGraphics, mouseX, mouseY, partialTick);
        this.renderTooltip(guiGraphics, mouseX, mouseY);
    }

    @Override
    protected void renderLabels(GuiGraphics guiGraphics, int mouseX, int mouseY) {

    }

    public void sendButtonClick(int id) {
        this.minecraft.gameMode.handleInventoryButtonClick(this.menu.containerId, id);
    }

    @Override
    protected void renderBg(GuiGraphics guiGraphics, float partialTick, int mouseX, int mouseY) {
        int i = (this.width - this.imageWidth) / 2;
        int j = (this.height - this.imageHeight) / 2;
        guiGraphics.blit(INVENTORY_LOCATION, i, j, 0, 0, this.imageWidth, this.imageHeight);
        InventoryScreen.renderEntityInInventoryFollowsMouse(guiGraphics, i + 26, j + 18, i + 78, j + 70, 17,
                0.25F, mouseX,mouseY, menu.stileEntity);
    }
}
