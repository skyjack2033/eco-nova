package github.kasuminova.mmce.client.gui.widget.slot;

import github.kasuminova.mmce.client.gui.util.MousePos;
import github.kasuminova.mmce.client.gui.util.RenderPos;
import github.kasuminova.mmce.client.gui.util.RenderSize;
import github.kasuminova.mmce.client.gui.util.TextureProperties;
import github.kasuminova.mmce.client.gui.widget.base.DynamicWidget;
import github.kasuminova.mmce.client.gui.widget.base.WidgetGui;
import net.minecraft.item.ItemStack;

public class SlotItemVirtual extends DynamicWidget {

    protected ItemStack stackInSlot;

    public SlotItemVirtual() {
        this.stackInSlot = null;
    }

    public void setStackInSlot(final ItemStack stackInSlot) {
        this.stackInSlot = stackInSlot;
    }

    public ItemStack getStackInSlot() {
        return stackInSlot;
    }

    public SlotItemVirtual setSlotTex(final TextureProperties properties) {
        return this;
    }

    @Override
    public void render(final WidgetGui widgetGui, final RenderSize renderSize, final RenderPos renderPos, final MousePos mousePos) {
        super.render(widgetGui, renderSize, renderPos, mousePos);
    }

}
