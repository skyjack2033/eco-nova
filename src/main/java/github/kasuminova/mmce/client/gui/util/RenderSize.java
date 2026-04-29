package github.kasuminova.mmce.client.gui.util;

public class RenderSize {

    public int width;
    public int height;

    public RenderSize() {
        this(0, 0);
    }

    public RenderSize(int width, int height) {
        this.width = width;
        this.height = height;
    }

    public int width() {
        return width;
    }

    public int height() {
        return height;
    }

}
