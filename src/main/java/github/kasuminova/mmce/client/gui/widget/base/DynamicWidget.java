package github.kasuminova.mmce.client.gui.widget.base;

import github.kasuminova.mmce.client.gui.util.MousePos;
import github.kasuminova.mmce.client.gui.util.RenderPos;
import github.kasuminova.mmce.client.gui.util.RenderSize;
import github.kasuminova.mmce.client.gui.widget.event.GuiEvent;

import java.util.List;

public abstract class DynamicWidget {

    protected int width;
    protected int height;
    private boolean visible = true;
    private int absX;
    private int absY;
    private boolean rightAligned = false;
    private boolean verticalCentering = false;

    public void render(RenderPos renderPos, WidgetGui widgetGui) {
    }

    public void render(WidgetGui widgetGui, RenderSize renderSize, RenderPos renderPos, MousePos mousePos) {
        render(renderPos, widgetGui);
    }

    public boolean onGuiEvent(GuiEvent event) {
        return false;
    }

    public void update(WidgetGui gui) {
    }

    public void initWidget(WidgetGui gui) {
    }

    protected void preRenderInternal(WidgetGui gui, RenderSize renderSize, RenderPos renderPos, MousePos mousePos) {
    }

    protected void renderInternal(WidgetGui widgetGui, RenderSize renderSize, RenderPos renderPos, MousePos mousePos) {
    }

    protected void postRenderInternal(WidgetGui gui, RenderSize renderSize, RenderPos renderPos, MousePos mousePos) {
    }

    public List<String> getHoverTooltips(WidgetGui widgetGui, MousePos mousePos) {
        return java.util.Collections.emptyList();
    }

    public DynamicWidget setWidthHeight(int width, int height) {
        return setWidth(width).setHeight(height);
    }

    public DynamicWidget setUseScissor(boolean useScissor) {
        return this;
    }

    public boolean isInvisible() {
        return !visible;
    }

    public boolean isMouseOver(MousePos mousePos) {
        return false;
    }

    public DynamicWidget setMargin(int top, int right, int bottom, int left) {
        return this;
    }

    public DynamicWidget setMarginLeft(int left) {
        return this;
    }

    public DynamicWidget setMarginUp(int up) {
        return this;
    }

    public DynamicWidget setMarginRight(int right) {
        return this;
    }

    public DynamicWidget setMarginDown(int down) {
        return this;
    }

    public int getMarginUp() { return 0; }
    public int getMarginDown() { return 0; }
    public int getMarginLeft() { return 0; }
    public int getMarginRight() { return 0; }
    public boolean isDisabled() { return false; }
    public boolean onMouseDWheel(MousePos mousePos, RenderPos renderPos, int wheel) { return false; }
    public boolean onMouseClick(MousePos mousePos, RenderPos renderPos, int mouseButton) { return false; }
    public int getTotalHeight() { return height; }

    public int getAbsX() {
        return absX;
    }

    public DynamicWidget setAbsX(int absX) {
        this.absX = absX;
        return this;
    }

    public int getAbsY() {
        return absY;
    }

    public DynamicWidget setAbsY(int absY) {
        this.absY = absY;
        return this;
    }

    public DynamicWidget setAbsXY(int x, int y) {
        this.absX = x;
        this.absY = y;
        return this;
    }

    public int getWidth() {
        return width;
    }

    public DynamicWidget setWidth(int width) {
        this.width = width;
        return this;
    }

    public int getHeight() {
        return height;
    }

    public DynamicWidget setHeight(int height) {
        this.height = height;
        return this;
    }

    public boolean isVisible() {
        return visible;
    }

    public void setVisible(boolean visible) {
        this.visible = visible;
    }

    public boolean isRightAligned() {
        return rightAligned;
    }

    public DynamicWidget setRightAligned(boolean rightAligned) {
        this.rightAligned = rightAligned;
        return this;
    }

    public boolean isVerticalCentering() {
        return verticalCentering;
    }

    public DynamicWidget setVerticalCentering(boolean verticalCentering) {
        this.verticalCentering = verticalCentering;
        return this;
    }

}
