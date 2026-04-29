package github.kasuminova.mmce.client.gui.widget.base;

import net.minecraft.client.gui.GuiScreen;

public class WidgetGui {

    public GuiScreen gui;
    public int mouseX;
    public int mouseY;

    public WidgetGui(GuiScreen gui, int mouseX, int mouseY) {
        this.gui = gui;
        this.mouseX = mouseX;
        this.mouseY = mouseY;
    }

    public GuiScreen getGui() {
        return gui;
    }

    public static WidgetGui of(GuiScreen gui) {
        return new WidgetGui(gui, 0, 0);
    }

}
