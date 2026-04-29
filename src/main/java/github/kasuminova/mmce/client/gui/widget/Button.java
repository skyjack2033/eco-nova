package github.kasuminova.mmce.client.gui.widget;

import github.kasuminova.mmce.client.gui.util.MousePos;
import github.kasuminova.mmce.client.gui.util.RenderPos;
import github.kasuminova.mmce.client.gui.util.RenderSize;
import github.kasuminova.mmce.client.gui.util.TextureProperties;
import github.kasuminova.mmce.client.gui.widget.base.DynamicWidget;
import github.kasuminova.mmce.client.gui.widget.base.WidgetGui;
import net.minecraft.util.ResourceLocation;

import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;

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
    private Consumer<Button> onClickedListener;
    private Function<Button, List<String>> tooltipFunction;

    public void setOnClickListener(Runnable onClickListener) {
        this.onClickListener = onClickListener;
    }

    public Runnable getOnClickListener() {
        return onClickListener;
    }

    public Button setOnClickedListener(Consumer<Button> onClickedListener) {
        this.onClickedListener = onClickedListener;
        return this;
    }

    public Button setTooltipFunction(Function<Button, List<String>> tooltipFunction) {
        this.tooltipFunction = tooltipFunction;
        return this;
    }

    @Override
    public List<String> getHoverTooltips(WidgetGui widgetGui, MousePos mousePos) {
        if (tooltipFunction != null) {
            return tooltipFunction.apply(this);
        }
        return super.getHoverTooltips(widgetGui, mousePos);
    }

    public boolean isUnavailable() {
        return false;
    }

    public boolean isClicked() {
        return clicked;
    }

    public Button setClicked(boolean clicked) {
        this.clicked = clicked;
        return this;
    }

    public Button setClickedTexture(int x, int y) {
        this.clickedTexture = TextureProperties.of(x, y);
        return this;
    }

    public Button setHoveredTexture(int x, int y) {
        this.hoveredTexture = TextureProperties.of(x, y);
        return this;
    }

    public Button setMouseDownTexture(int x, int y) {
        this.mouseDownTexture = TextureProperties.of(x, y);
        return this;
    }

    public Button setUnavailableTexture(int x, int y) {
        this.unavailableTexture = TextureProperties.of(x, y);
        return this;
    }

    public Button setUnavailableTexture(TextureProperties properties) {
        this.unavailableTexture = properties;
        return this;
    }

    public Button setTexture(int x, int y) {
        this.texture = TextureProperties.of(x, y);
        return this;
    }

    public Button setTexture(TextureProperties texture) {
        this.texture = texture;
        return this;
    }

    public Button setMouseDownTexture(TextureProperties texture) {
        this.mouseDownTexture = texture;
        return this;
    }

    public Button setHoveredTexture(TextureProperties texture) {
        this.hoveredTexture = texture;
        return this;
    }

    public Button setClickedTexture(TextureProperties texture) {
        this.clickedTexture = texture;
        return this;
    }

    public Button setTextureLocation(ResourceLocation textureLocation) {
        this.textureLocation = textureLocation;
        return this;
    }

    @Override
    public void render(RenderPos renderPos, WidgetGui widgetGui) {
        // Stub
    }

}
