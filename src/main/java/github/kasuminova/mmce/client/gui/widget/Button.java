package github.kasuminova.mmce.client.gui.widget;

import github.kasuminova.mmce.client.gui.util.RenderPos;
import github.kasuminova.mmce.client.gui.util.TextureProperties;
import github.kasuminova.mmce.client.gui.widget.base.DynamicWidget;
import github.kasuminova.mmce.client.gui.widget.base.WidgetGui;
import net.minecraft.util.ResourceLocation;

public class Button extends DynamicWidget {

    protected TextureProperties texture = TextureProperties.EMPTY;
    protected ResourceLocation textureLocation;
    protected TextureProperties mouseDownTexture = TextureProperties.EMPTY;
    protected TextureProperties hoveredTexture = TextureProperties.EMPTY;
    protected TextureProperties clickedTexture = TextureProperties.EMPTY;
    protected TextureProperties unavailableTexture = TextureProperties.EMPTY;
    protected boolean clicked = false;
    protected boolean mouseDown = false;

    private Runnable onClickListener;

    public void setOnClickListener(Runnable onClickListener) {
        this.onClickListener = onClickListener;
    }

    public Runnable getOnClickListener() {
        return onClickListener;
    }

    public boolean isUnavailable() {
        return false;
    }

    @Override
    public void render(RenderPos renderPos, WidgetGui widgetGui) {
        // Stub
    }

}
