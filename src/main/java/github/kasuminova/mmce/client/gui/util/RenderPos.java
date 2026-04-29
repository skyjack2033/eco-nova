package github.kasuminova.mmce.client.gui.util;

public class RenderPos {

    public int x;
    public int y;

    public RenderPos() {
        this(0, 0);
    }

    public RenderPos(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int posX() {
        return x;
    }

    public int posY() {
        return y;
    }

    public RenderPos add(RenderPos other) {
        return new RenderPos(x + other.x, y + other.y);
    }

}
