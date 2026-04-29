package github.kasuminova.ecoaeextension.client.gui.widget.efabricator;

import github.kasuminova.mmce.client.gui.util.MousePos;
import github.kasuminova.mmce.client.gui.util.RenderPos;
import github.kasuminova.mmce.client.gui.util.RenderSize;
import github.kasuminova.mmce.client.gui.widget.base.WidgetGui;
import github.kasuminova.mmce.client.gui.widget.slot.SlotItemVirtualJEI;
import net.minecraft.client.gui.GuiScreen;
import org.lwjgl.opengl.GL11;

import net.minecraft.item.ItemStack;

import java.awt.*;

public class SlotItemVirtualJEISmall extends SlotItemVirtualJEI {

    public SlotItemVirtualJEISmall() {
        this(null);
    }

    public SlotItemVirtualJEISmall(final ItemStack stackInSlot) {
        super(stackInSlot);
        setWidthHeight(10, 10);
    }

    public static SlotItemVirtualJEISmall of() {
        return new SlotItemVirtualJEISmall();
    }

    public static SlotItemVirtualJEISmall of(final ItemStack stackInSlot) {
        return new SlotItemVirtualJEISmall(stackInSlot);
    }

    @Override
    public void render(final WidgetGui widgetGui, final RenderSize renderSize, final RenderPos renderPos, final MousePos mousePos) {
        GL11.glPushMatrix();
        GL11.glScalef(.5F, .5F, 1F);
        super.render(widgetGui, renderSize, renderPos.add(renderPos).add(new RenderPos(1, 1)), mousePos);
        GL11.glPopMatrix();
    }

    @Override
    protected void drawHoverOverlay(final MousePos mousePos, final int rx, final int ry) {
        if (mouseOver) {
            GL11.glDisable(GL11.GL_LIGHTING);
            GL11.glDisable(GL11.GL_DEPTH_TEST);
            GL11.glColorMask(true, true, true, false);
            GuiScreen.drawRect(rx, ry, rx + 16, ry + 16, new Color(255, 255, 255, 150).getRGB());
            GL11.glEnable(GL11.GL_BLEND);
            GL11.glColorMask(true, true, true, true);
            GL11.glColor4f(1F, 1F, 1F, 1F);
            GL11.glEnable(GL11.GL_LIGHTING);
            GL11.glEnable(GL11.GL_DEPTH_TEST);
        }
    }

}
