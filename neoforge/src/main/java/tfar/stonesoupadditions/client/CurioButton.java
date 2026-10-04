package tfar.stonesoupadditions.client;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.ImageButton;
import net.minecraft.client.gui.components.WidgetSprites;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.gui.screens.inventory.CreativeModeInventoryScreen;
import net.minecraft.util.Tuple;
import net.minecraft.world.item.ItemStack;

import javax.annotation.Nonnull;

import top.theillusivec4.curios.client.gui.CuriosScreen;

public class CurioButton extends ImageButton {
    private final AbstractContainerScreen<?> parentGui;

    public CurioButton(AbstractContainerScreen<?> parentGui, int xIn, int yIn, int widthIn, int heightIn,
                       WidgetSprites sprites) {
        super(xIn, yIn, widthIn, heightIn, sprites,
                (button) -> {
                    Minecraft mc = Minecraft.getInstance();

                    if (mc.player != null) {
                        ItemStack stack = mc.player.containerMenu.getCarried();
                        mc.player.containerMenu.setCarried(ItemStack.EMPTY);
                        if (parentGui instanceof StileScreen stileScreen) {
                            stileScreen.sendButtonClick(0);
                        }
                    }
                });
        this.parentGui = parentGui;
    }

    @Override
    public void renderWidget(@Nonnull GuiGraphics guiGraphics, int mouseX, int mouseY,
                             float partialTicks) {
        Tuple<Integer, Integer> offsets =
                CuriosScreen.getButtonOffset(parentGui instanceof CreativeModeInventoryScreen);
        this.setX(parentGui.getGuiLeft() + offsets.getA() + 2);
        int yOffset = parentGui instanceof CreativeModeInventoryScreen ? 70 : 85;
        this.setY(parentGui.getGuiTop() + offsets.getB() + yOffset);

        if (parentGui instanceof CreativeModeInventoryScreen gui) {
            boolean isInventoryTab = gui.isInventoryOpen();
            this.active = isInventoryTab;

            if (!isInventoryTab) {
                return;
            }
        }
        super.renderWidget(guiGraphics, mouseX, mouseY, partialTicks);
    }
}
