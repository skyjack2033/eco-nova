package github.kasuminova.mmce.client.gui.widget.container;

import github.kasuminova.mmce.client.gui.util.RenderPos;
import github.kasuminova.mmce.client.gui.widget.base.DynamicWidget;
import github.kasuminova.mmce.client.gui.widget.base.WidgetGui;
import github.kasuminova.mmce.client.gui.widget.event.GuiEvent;

import java.util.ArrayList;
import java.util.List;

public abstract class WidgetContainer extends DynamicWidget {

    protected final List<DynamicWidget> widgets = new ArrayList<>();

    public List<DynamicWidget> getWidgets() {
        return widgets;
    }

    public void addWidget(DynamicWidget widget) {
        widgets.add(widget);
    }

    public void addWidgets(DynamicWidget... widgets) {
        for (DynamicWidget widget : widgets) {
            addWidget(widget);
        }
    }

    @Override
    public void render(RenderPos renderPos, WidgetGui widgetGui) {
        for (DynamicWidget widget : widgets) {
            if (widget.isVisible()) {
                widget.render(renderPos, widgetGui);
            }
        }
    }

    @Override
    public boolean onGuiEvent(GuiEvent event) {
        for (DynamicWidget widget : widgets) {
            widget.onGuiEvent(event);
        }
        return false;
    }

}
