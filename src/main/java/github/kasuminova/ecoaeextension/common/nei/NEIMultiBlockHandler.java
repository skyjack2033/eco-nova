package github.kasuminova.ecoaeextension.common.nei;

import codechicken.nei.PositionedStack;
import codechicken.nei.recipe.TemplateRecipeHandler;
import github.kasuminova.mmce.common.util.StructureDefinition;
import hellfirepvp.modularmachinery.common.machine.DynamicMachine;
import hellfirepvp.modularmachinery.common.machine.MachineRegistry;
import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.item.ItemStack;

import java.util.ArrayList;
import java.util.List;

/**
 * NEI Multi-block structure preview handler for GTNH 1.7.10.
 * Shows multi-block structure layout when looking up controller blocks in NEI.
 */
public class NEIMultiBlockHandler extends TemplateRecipeHandler {

    public static void register() {
        codechicken.nei.api.API.registerRecipeHandler(new NEIMultiBlockHandler());
        codechicken.nei.api.API.registerUsageHandler(new NEIMultiBlockHandler());
    }

    @Override
    public String getRecipeName() {
        return "NovaEng Multi-block";
    }

    @Override
    public String getGuiTexture() {
        return "textures/gui/container/crafting_table.png";
    }

    @Override
    public void loadCraftingRecipes(String outputId, Object... results) {
        if (outputId.equals("item") && results.length > 0 && results[0] instanceof ItemStack) {
            loadCraftingRecipes((ItemStack) results[0]);
        }
    }

    @Override
    public void loadCraftingRecipes(ItemStack result) {
        Block block = Block.getBlockFromItem(result.getItem());
        if (block == null) return;

        for (DynamicMachine machine : MachineRegistry.getRegistry().getLoadedMachines()) {
            StructureDefinition def = machine.getStructureDef();
            if (def == null) continue;
            arecipes.add(new CachedMultiBlockRecipe(def, result));
        }
    }

    @Override
    public void loadUsageRecipes(ItemStack ingredient) {
        Block block = Block.getBlockFromItem(ingredient.getItem());
        if (block == null) return;

        for (DynamicMachine machine : MachineRegistry.getRegistry().getLoadedMachines()) {
            StructureDefinition def = machine.getStructureDef();
            if (def == null) continue;

            boolean found = false;
            for (StructureDefinition.PatternEntry entry : def.fixedParts) {
                if (entry.matches(block, ingredient.getItemDamage())) { found = true; break; }
            }
            if (!found) {
                for (StructureDefinition.DynamicPattern dp : def.dynamicPatterns) {
                    for (StructureDefinition.PatternEntry entry : dp.parts) {
                        if (entry.matches(block, ingredient.getItemDamage())) { found = true; break; }
                    }
                    if (found) break;
                }
            }

            if (found) {
                arecipes.add(new CachedMultiBlockRecipe(def, ingredient));
            }
        }
    }

    @Override
    public void drawExtras(int recipe) {
        if (recipe < 0 || recipe >= arecipes.size()) return;
        CachedMultiBlockRecipe cached = (CachedMultiBlockRecipe) arecipes.get(recipe);
        FontRenderer fr = Minecraft.getMinecraft().fontRenderer;
        fr.drawString(cached.def.registryName, 6, 8, 0xFF404040);
        fr.drawString("Parts: " + cached.def.fixedParts.size(), 6, 20, 0xFF808080);
    }

    public class CachedMultiBlockRecipe extends CachedRecipe {

        final StructureDefinition def;
        final ItemStack controllerStack;

        public CachedMultiBlockRecipe(StructureDefinition def, ItemStack controller) {
            this.def = def;
            this.controllerStack = controller.copy();
            this.controllerStack.stackSize = 1;
        }

        @Override
        public PositionedStack getResult() {
            return new PositionedStack(controllerStack, 120, 30);
        }

        @Override
        public List<PositionedStack> getIngredients() {
            List<PositionedStack> ingredients = new ArrayList<>();
            int x = 20, y = 40, col = 0;

            for (StructureDefinition.PatternEntry entry : def.fixedParts) {
                for (String element : entry.elements) {
                    ItemStack stack = parseElement(element);
                    if (stack != null) {
                        ingredients.add(new PositionedStack(stack, x + col * 18, y));
                        col++;
                        if (col >= 5) { col = 0; y += 18; }
                    }
                }
            }

            for (StructureDefinition.DynamicPattern dp : def.dynamicPatterns) {
                for (StructureDefinition.PatternEntry entry : dp.parts) {
                    for (String element : entry.elements) {
                        ItemStack stack = parseElement(element);
                        if (stack != null) {
                            ingredients.add(new PositionedStack(stack, x + col * 18, y));
                            col++;
                            if (col >= 5) { col = 0; y += 18; }
                        }
                    }
                }
            }

            return ingredients;
        }

        private ItemStack parseElement(String element) {
            try {
                String[] parts = element.split("@");
                String blockName = parts[0];
                int meta = parts.length > 1 ? Integer.parseInt(parts[1]) : 0;
                Block block = Block.getBlockFromName(blockName);
                if (block != null) {
                    return new ItemStack(block, 1, meta);
                }
            } catch (Exception ignored) {}
            return null;
        }
    }
}
