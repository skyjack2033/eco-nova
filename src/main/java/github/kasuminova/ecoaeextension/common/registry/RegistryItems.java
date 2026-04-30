package github.kasuminova.ecoaeextension.common.registry;

import github.kasuminova.ecoaeextension.common.item.ecalculator.ECalculatorCell;
import github.kasuminova.ecoaeextension.common.item.estorage.EStorageCellFluid;
import github.kasuminova.ecoaeextension.common.item.estorage.EStorageCellGas;
import github.kasuminova.ecoaeextension.common.item.estorage.EStorageCellItem;
import hellfirepvp.modularmachinery.common.base.Mods;
import net.minecraft.item.Item;
import cpw.mods.fml.common.registry.GameRegistry;

import java.util.LinkedList;
import java.util.List;

@SuppressWarnings({"MethodMayBeStatic", "UnusedReturnValue"})
public class RegistryItems {
    public static final List<Item> ITEMS_TO_REGISTER = new LinkedList<>();

    public static void registerItems() {
        ITEMS_TO_REGISTER.add(EStorageCellItem.LEVEL_A);
        ITEMS_TO_REGISTER.add(EStorageCellItem.LEVEL_B);
        ITEMS_TO_REGISTER.add(EStorageCellItem.LEVEL_C);
        ITEMS_TO_REGISTER.add(EStorageCellFluid.LEVEL_A);
        ITEMS_TO_REGISTER.add(EStorageCellFluid.LEVEL_B);
        ITEMS_TO_REGISTER.add(EStorageCellFluid.LEVEL_C);
        if (Mods.MEKENG.isPresent()) {
            ITEMS_TO_REGISTER.add(EStorageCellGas.LEVEL_A);
            ITEMS_TO_REGISTER.add(EStorageCellGas.LEVEL_B);
            ITEMS_TO_REGISTER.add(EStorageCellGas.LEVEL_C);
        }
        ITEMS_TO_REGISTER.add(ECalculatorCell.L4);
        ITEMS_TO_REGISTER.add(ECalculatorCell.L6);
        ITEMS_TO_REGISTER.add(ECalculatorCell.L9);

        for (Item item : ITEMS_TO_REGISTER) {
            registerItem(item);
        }
        ITEMS_TO_REGISTER.clear();
    }

    public static void registerItemModels() {
        // Item model registration - stubbed for 1.7.10 port
    }

    public static <T extends Item> T registerItem(T item) {
        String name = item.getUnlocalizedName();
        if (name != null && name.startsWith("item.")) {
            name = name.substring(5);
        }
        // Strip modid prefix if present
        int dotIndex = name.indexOf('.');
        if (dotIndex > 0) {
            name = name.substring(dotIndex + 1);
        }
        GameRegistry.registerItem(item, name);

        return item;
    }
}
