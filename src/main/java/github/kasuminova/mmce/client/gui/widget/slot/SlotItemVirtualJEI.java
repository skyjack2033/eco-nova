package github.kasuminova.mmce.client.gui.widget.slot;

import github.kasuminova.mmce.client.gui.util.MousePos;
import github.kasuminova.mmce.client.gui.widget.base.DynamicWidget;
import net.minecraft.item.ItemStack;

public class SlotItemVirtualJEI extends DynamicWidget {

    protected ItemStack stackInSlot;
    protected boolean mouseOver = false;

    public SlotItemVirtualJEI() {
        super();
        this.stackInSlot = null;
    }

    public SlotItemVirtualJEI(final ItemStack stackInSlot) {
        super();
        this.stackInSlot = stackInSlot;
    }

    public void setStackInSlot(final ItemStack stackInSlot) {
        this.stackInSlot = stackInSlot;
    }

    public ItemStack getStackInSlot() {
        return stackInSlot;
    }

    protected void drawHoverOverlay(MousePos mousePos, int rx, int ry) {
    }

}
