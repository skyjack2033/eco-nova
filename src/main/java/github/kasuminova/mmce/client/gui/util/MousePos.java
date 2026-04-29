package github.kasuminova.mmce.client.gui.util;

public class MousePos {

    public int x;
    public int y;

    public MousePos(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int x() {
        return x;
    }

    public int y() {
        return y;
    }

    public int mouseX() {
        return x;
    }

    public int mouseY() {
        return y;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public MousePos relativeTo(RenderPos pos) {
        return new MousePos(x - pos.x, y - pos.y);
    }

}
