package github.kasuminova.mmce.client.gui.widget.container;

import github.kasuminova.mmce.client.gui.widget.Button;
import github.kasuminova.mmce.client.gui.widget.base.DynamicWidget;

public class Scrollbar extends DynamicWidget {

    private final Button scroll = new Button();
    private int scrollUnit = 1;

    public Scrollbar setScrollUnit(int scrollUnit) {
        this.scrollUnit = scrollUnit;
        return this;
    }

    public int getRange() {
        return 0;
    }

    public Button getScroll() {
        return scroll;
    }

}
