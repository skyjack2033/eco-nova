package github.kasuminova.mmce.client.gui.util;

import github.kasuminova.mmce.client.gui.GuiContainerDynamic;
import github.kasuminova.mmce.client.gui.widget.base.WidgetGui;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

public class TextureProperties {

    public static final TextureProperties EMPTY = new TextureProperties(null, 0, 0, 0, 0);

    public ResourceLocation texture;
    public int u;
    public int v;
    public int width;
    public int height;

    public TextureProperties(ResourceLocation texture, int u, int v, int width, int height) {
        this.texture = texture;
        this.u = u;
        this.v = v;
        this.width = width;
        this.height = height;
    }

    public int texX() {
        return u;
    }

    public int texY() {
        return v;
    }

    public int width() {
        return width;
    }

    public int height() {
        return height;
    }

    public void bind(TextureManager tm) {
        if (texture != null) {
            tm.bindTexture(texture);
        }
    }

    public static TextureProperties of(ResourceLocation texture, int u, int v, int width, int height) {
        return new TextureProperties(texture, u, v, width, height);
    }

    public static TextureProperties of(int u, int v) {
        return new TextureProperties(null, u, v, 0, 0);
    }

    public void render(RenderPos pos, GuiContainerDynamic<?> gui) {
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        Minecraft.getMinecraft().getTextureManager().bindTexture(texture);
        float texW = 256F;
        float texH = 256F;
        Tessellator tessellator = Tessellator.instance;
        tessellator.startDrawingQuads();
        tessellator.addVertexWithUV(pos.x, pos.y + height, 0, (float) u / texW, (float) (v + height) / texH);
        tessellator.addVertexWithUV(pos.x + width, pos.y + height, 0, (float) (u + width) / texW, (float) (v + height) / texH);
        tessellator.addVertexWithUV(pos.x + width, pos.y, 0, (float) (u + width) / texW, (float) v / texH);
        tessellator.addVertexWithUV(pos.x, pos.y, 0, (float) u / texW, (float) v / texH);
        tessellator.draw();
    }

    public void render(RenderPos pos, GuiScreen gui) {
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        Minecraft.getMinecraft().getTextureManager().bindTexture(texture);
        float texW = 256F;
        float texH = 256F;
        Tessellator tessellator = Tessellator.instance;
        tessellator.startDrawingQuads();
        tessellator.addVertexWithUV(pos.x, pos.y + height, 0, (float) u / texW, (float) (v + height) / texH);
        tessellator.addVertexWithUV(pos.x + width, pos.y + height, 0, (float) (u + width) / texW, (float) (v + height) / texH);
        tessellator.addVertexWithUV(pos.x + width, pos.y, 0, (float) (u + width) / texW, (float) v / texH);
        tessellator.addVertexWithUV(pos.x, pos.y, 0, (float) u / texW, (float) v / texH);
        tessellator.draw();
    }

    public void render(RenderPos pos, WidgetGui gui) {
        render(pos, gui.gui);
    }

    public void render(ResourceLocation textureLocation, RenderPos pos, RenderSize size, WidgetGui gui) {
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        ResourceLocation tex = this.texture != null ? this.texture : textureLocation;
        if (tex != null) {
            Minecraft.getMinecraft().getTextureManager().bindTexture(tex);
        }
        float texW = 256F;
        float texH = 256F;
        Tessellator tessellator = Tessellator.instance;
        tessellator.startDrawingQuads();
        tessellator.addVertexWithUV(pos.x, pos.y + size.height, 0, (float) u / texW, (float) (v + size.height) / texH);
        tessellator.addVertexWithUV(pos.x + size.width, pos.y + size.height, 0, (float) (u + size.width) / texW, (float) (v + size.height) / texH);
        tessellator.addVertexWithUV(pos.x + size.width, pos.y, 0, (float) (u + size.width) / texW, (float) v / texH);
        tessellator.addVertexWithUV(pos.x, pos.y, 0, (float) u / texW, (float) v / texH);
        tessellator.draw();
    }

}
