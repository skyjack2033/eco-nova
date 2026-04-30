package github.kasuminova.ecoaeextension.common.registry;

import github.kasuminova.ecoaeextension.common.block.ecotech.ecalculator.*;
import github.kasuminova.ecoaeextension.common.block.ecotech.efabricator.*;
import github.kasuminova.ecoaeextension.common.block.ecotech.estorage.*;
import github.kasuminova.ecoaeextension.common.item.ecalculator.ECalculatorCell;
import github.kasuminova.ecoaeextension.common.item.estorage.EStorageCellFluid;
import github.kasuminova.ecoaeextension.common.item.estorage.EStorageCellItem;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import cpw.mods.fml.common.registry.GameRegistry;

@SuppressWarnings("MethodMayBeStatic")
public class RecipeRegistry {

    public static void registerRecipes() {
        // --- EStorage recipes ---
        // Controller L4
        GameRegistry.addShapedRecipe(new ItemStack(BlockEStorageController.L4),
                "CMC", "MFM", "CMC",
                'C', new ItemStack(Blocks.iron_block),
                'M', new ItemStack(Items.redstone),
                'F', new ItemStack(Blocks.glass));
        // Controller L6
        GameRegistry.addShapedRecipe(new ItemStack(BlockEStorageController.L6),
                "CMC", "MFM", "CMC",
                'C', new ItemStack(Items.diamond),
                'M', new ItemStack(Items.redstone),
                'F', new ItemStack(BlockEStorageController.L4));
        // Controller L9
        GameRegistry.addShapedRecipe(new ItemStack(BlockEStorageController.L9),
                "CMC", "MFM", "CMC",
                'C', new ItemStack(Items.emerald),
                'M', new ItemStack(Items.redstone),
                'F', new ItemStack(BlockEStorageController.L6));
        // Casing
        GameRegistry.addShapedRecipe(new ItemStack(BlockEStorageCasing.INSTANCE, 4),
                "ICI", "C C", "ICI",
                'I', new ItemStack(Items.iron_ingot),
                'C', new ItemStack(Blocks.iron_block));
        // Vent
        GameRegistry.addShapedRecipe(new ItemStack(BlockEStorageVent.INSTANCE, 2),
                " I ", "IGI", " I ",
                'I', new ItemStack(Items.iron_ingot),
                'G', new ItemStack(Blocks.iron_bars));
        // Cell Drive
        GameRegistry.addShapedRecipe(new ItemStack(BlockEStorageCellDrive.INSTANCE),
                "IGI", "CEC", "IGI",
                'I', new ItemStack(Items.iron_ingot),
                'G', new ItemStack(Blocks.glass),
                'C', new ItemStack(BlockEStorageCasing.INSTANCE),
                'E', new ItemStack(Items.ender_pearl));
        // ME Channel
        GameRegistry.addShapedRecipe(new ItemStack(BlockEStorageMEChannel.INSTANCE),
                "IGI", "GFG", "IGI",
                'I', new ItemStack(Items.iron_ingot),
                'G', new ItemStack(Blocks.glass),
                'F', new ItemStack(Items.quartz));
        // Energy Cell L4
        GameRegistry.addShapedRecipe(new ItemStack(BlockEStorageEnergyCell.L4),
                "IRI", "RER", "IRI",
                'I', new ItemStack(Items.iron_ingot),
                'R', new ItemStack(Items.redstone),
                'E', new ItemStack(Items.ender_pearl));
        // Energy Cell L6
        GameRegistry.addShapedRecipe(new ItemStack(BlockEStorageEnergyCell.L6),
                "IRI", "RER", "IRI",
                'I', new ItemStack(Items.diamond),
                'R', new ItemStack(Items.redstone),
                'E', new ItemStack(BlockEStorageEnergyCell.L4));
        // Energy Cell L9
        GameRegistry.addShapedRecipe(new ItemStack(BlockEStorageEnergyCell.L9),
                "IRI", "RER", "IRI",
                'I', new ItemStack(Items.emerald),
                'R', new ItemStack(Items.redstone),
                'E', new ItemStack(BlockEStorageEnergyCell.L6));

        // --- EFabricator recipes ---
        GameRegistry.addShapedRecipe(new ItemStack(BlockEFabricatorController.L4),
                "CMC", "MFM", "CMC",
                'C', new ItemStack(Blocks.iron_block),
                'M', new ItemStack(Items.gold_ingot),
                'F', new ItemStack(Items.redstone));
        GameRegistry.addShapedRecipe(new ItemStack(BlockEFabricatorController.L6),
                "CMC", "MFM", "CMC",
                'C', new ItemStack(Items.diamond),
                'M', new ItemStack(Items.gold_ingot),
                'F', new ItemStack(BlockEFabricatorController.L4));
        GameRegistry.addShapedRecipe(new ItemStack(BlockEFabricatorController.L9),
                "CMC", "MFM", "CMC",
                'C', new ItemStack(Items.emerald),
                'M', new ItemStack(Items.gold_ingot),
                'F', new ItemStack(BlockEFabricatorController.L6));
        GameRegistry.addShapedRecipe(new ItemStack(BlockEFabricatorCasing.INSTANCE, 4),
                "IGI", "GCG", "IGI",
                'I', new ItemStack(Items.iron_ingot),
                'G', new ItemStack(Items.gold_ingot),
                'C', new ItemStack(Blocks.iron_block));
        GameRegistry.addShapedRecipe(new ItemStack(BlockEFabricatorVent.INSTANCE, 2),
                " G ", "GIG", " G ",
                'G', new ItemStack(Items.gold_ingot),
                'I', new ItemStack(Blocks.iron_bars));
        GameRegistry.addShapedRecipe(new ItemStack(BlockEFabricatorMEChannel.INSTANCE),
                "IGI", "GFG", "IGI",
                'I', new ItemStack(Items.gold_ingot),
                'G', new ItemStack(Blocks.glass),
                'F', new ItemStack(Items.quartz));
        GameRegistry.addShapedRecipe(new ItemStack(BlockEFabricatorPatternBus.INSTANCE),
                "IGI", "GFG", "IGI",
                'I', new ItemStack(Items.gold_ingot),
                'G', new ItemStack(Blocks.glass),
                'F', new ItemStack(Items.diamond));
        GameRegistry.addShapedRecipe(new ItemStack(BlockEFabricatorWorker.INSTANCE, 2),
                "IPI", "PCP", "IPI",
                'I', new ItemStack(Items.iron_ingot),
                'P', new ItemStack(Blocks.piston),
                'C', new ItemStack(BlockEFabricatorCasing.INSTANCE));
        GameRegistry.addShapedRecipe(new ItemStack(BlockEFabricatorParallelProc.L4),
                "CRC", "RPR", "CRC",
                'C', new ItemStack(BlockEFabricatorCasing.INSTANCE),
                'R', new ItemStack(Items.redstone),
                'P', new ItemStack(Items.gold_ingot));
        GameRegistry.addShapedRecipe(new ItemStack(BlockEFabricatorParallelProc.L6),
                "CRC", "RPR", "CRC",
                'C', new ItemStack(Items.diamond),
                'R', new ItemStack(Items.redstone),
                'P', new ItemStack(BlockEFabricatorParallelProc.L4));
        GameRegistry.addShapedRecipe(new ItemStack(BlockEFabricatorParallelProc.L9),
                "CRC", "RPR", "CRC",
                'C', new ItemStack(Items.emerald),
                'R', new ItemStack(Items.redstone),
                'P', new ItemStack(BlockEFabricatorParallelProc.L6));

        // --- ECalculator recipes ---
        GameRegistry.addShapedRecipe(new ItemStack(BlockECalculatorController.L4),
                "CMC", "MFM", "CMC",
                'C', new ItemStack(Blocks.iron_block),
                'M', new ItemStack(Items.diamond),
                'F', new ItemStack(Items.blaze_rod));
        GameRegistry.addShapedRecipe(new ItemStack(BlockECalculatorController.L6),
                "CMC", "MFM", "CMC",
                'C', new ItemStack(Items.diamond),
                'M', new ItemStack(Items.diamond),
                'F', new ItemStack(BlockECalculatorController.L4));
        GameRegistry.addShapedRecipe(new ItemStack(BlockECalculatorController.L9),
                "CMC", "MFM", "CMC",
                'C', new ItemStack(Items.emerald),
                'M', new ItemStack(Items.diamond),
                'F', new ItemStack(BlockECalculatorController.L6));
        GameRegistry.addShapedRecipe(new ItemStack(BlockECalculatorCasing.INSTANCE, 4),
                "IDI", "DCD", "IDI",
                'I', new ItemStack(Items.iron_ingot),
                'D', new ItemStack(Items.diamond),
                'C', new ItemStack(Blocks.iron_block));
        GameRegistry.addShapedRecipe(new ItemStack(BlockECalculatorTail.L4, 2),
                " I ", "ICI", " I ",
                'I', new ItemStack(Items.iron_ingot),
                'C', new ItemStack(BlockECalculatorCasing.INSTANCE));
        GameRegistry.addShapedRecipe(new ItemStack(BlockECalculatorTail.L6, 2),
                " I ", "ICI", " I ",
                'I', new ItemStack(Items.diamond),
                'C', new ItemStack(BlockECalculatorCasing.INSTANCE));
        GameRegistry.addShapedRecipe(new ItemStack(BlockECalculatorTail.L9, 2),
                " I ", "ICI", " I ",
                'I', new ItemStack(Items.emerald),
                'C', new ItemStack(BlockECalculatorCasing.INSTANCE));
        GameRegistry.addShapedRecipe(new ItemStack(BlockECalculatorMEChannel.INSTANCE),
                "IDI", "GFG", "IDI",
                'I', new ItemStack(Items.diamond),
                'D', new ItemStack(Items.diamond),
                'G', new ItemStack(Blocks.glass),
                'F', new ItemStack(Items.quartz));
        GameRegistry.addShapedRecipe(new ItemStack(BlockECalculatorCellDrive.INSTANCE),
                "IDI", "CEC", "IDI",
                'I', new ItemStack(Items.diamond),
                'D', new ItemStack(Items.diamond),
                'C', new ItemStack(BlockECalculatorCasing.INSTANCE),
                'E', new ItemStack(Items.ender_pearl));
        GameRegistry.addShapedRecipe(new ItemStack(BlockECalculatorTransmitterBus.INSTANCE),
                "IDI", "DRD", "IDI",
                'I', new ItemStack(Items.diamond),
                'D', new ItemStack(Items.diamond),
                'R', new ItemStack(Items.redstone));
        GameRegistry.addShapedRecipe(new ItemStack(BlockECalculatorThreadCore.L4),
                "DPD", "PCP", "DPD",
                'D', new ItemStack(Items.diamond),
                'P', new ItemStack(Items.redstone),
                'C', new ItemStack(BlockECalculatorCasing.INSTANCE));
        GameRegistry.addShapedRecipe(new ItemStack(BlockECalculatorThreadCore.L6),
                "DPD", "PCP", "DPD",
                'D', new ItemStack(Items.diamond),
                'P', new ItemStack(Items.redstone),
                'C', new ItemStack(BlockECalculatorThreadCore.L4));
        GameRegistry.addShapedRecipe(new ItemStack(BlockECalculatorThreadCore.L9),
                "DPD", "PCP", "DPD",
                'D', new ItemStack(Items.emerald),
                'P', new ItemStack(Items.redstone),
                'C', new ItemStack(BlockECalculatorThreadCore.L6));
        GameRegistry.addShapedRecipe(new ItemStack(BlockECalculatorParallelProc.L4),
                "DCD", "CPC", "DCD",
                'D', new ItemStack(Items.diamond),
                'C', new ItemStack(BlockECalculatorCasing.INSTANCE),
                'P', new ItemStack(Items.redstone));
        GameRegistry.addShapedRecipe(new ItemStack(BlockECalculatorParallelProc.L6),
                "DCD", "CPC", "DCD",
                'D', new ItemStack(Items.diamond),
                'C', new ItemStack(Items.diamond),
                'P', new ItemStack(BlockECalculatorParallelProc.L4));
        GameRegistry.addShapedRecipe(new ItemStack(BlockECalculatorParallelProc.L9),
                "DCD", "CPC", "DCD",
                'D', new ItemStack(Items.emerald),
                'C', new ItemStack(Items.diamond),
                'P', new ItemStack(BlockECalculatorParallelProc.L6));

        // --- Storage Cell recipes ---
        GameRegistry.addShapedRecipe(new ItemStack(EStorageCellItem.LEVEL_A),
                " Q ", "RSR", " Q ",
                'Q', new ItemStack(Items.quartz),
                'R', new ItemStack(Items.redstone),
                'S', new ItemStack(Items.iron_ingot));
        GameRegistry.addShapedRecipe(new ItemStack(EStorageCellItem.LEVEL_B),
                " Q ", "RSR", " Q ",
                'Q', new ItemStack(Items.quartz),
                'R', new ItemStack(Items.redstone),
                'S', new ItemStack(EStorageCellItem.LEVEL_A));
        GameRegistry.addShapedRecipe(new ItemStack(EStorageCellItem.LEVEL_C),
                " Q ", "RSR", " Q ",
                'Q', new ItemStack(Items.quartz),
                'R', new ItemStack(Items.redstone),
                'S', new ItemStack(EStorageCellItem.LEVEL_B));

        GameRegistry.addShapedRecipe(new ItemStack(EStorageCellFluid.LEVEL_A),
                " Q ", "RSR", " Q ",
                'Q', new ItemStack(Items.quartz),
                'R', new ItemStack(Items.redstone),
                'S', new ItemStack(Items.bucket));
        GameRegistry.addShapedRecipe(new ItemStack(EStorageCellFluid.LEVEL_B),
                " Q ", "RSR", " Q ",
                'Q', new ItemStack(Items.quartz),
                'R', new ItemStack(Items.redstone),
                'S', new ItemStack(EStorageCellFluid.LEVEL_A));
        GameRegistry.addShapedRecipe(new ItemStack(EStorageCellFluid.LEVEL_C),
                " Q ", "RSR", " Q ",
                'Q', new ItemStack(Items.quartz),
                'R', new ItemStack(Items.redstone),
                'S', new ItemStack(EStorageCellFluid.LEVEL_B));

        // --- ECalculator Cell recipes ---
        GameRegistry.addShapedRecipe(new ItemStack(ECalculatorCell.L4),
                " Q ", "RDR", " Q ",
                'Q', new ItemStack(Items.quartz),
                'R', new ItemStack(Items.redstone),
                'D', new ItemStack(Items.diamond));
        GameRegistry.addShapedRecipe(new ItemStack(ECalculatorCell.L6),
                " Q ", "RDR", " Q ",
                'Q', new ItemStack(Items.quartz),
                'R', new ItemStack(Items.redstone),
                'D', new ItemStack(ECalculatorCell.L4));
        GameRegistry.addShapedRecipe(new ItemStack(ECalculatorCell.L9),
                " Q ", "RDR", " Q ",
                'Q', new ItemStack(Items.quartz),
                'R', new ItemStack(Items.redstone),
                'D', new ItemStack(ECalculatorCell.L6));

        // Hyper thread core
        GameRegistry.addShapedRecipe(new ItemStack(BlockECalculatorThreadCoreHyper.L4),
                "DED", "ECE", "DED",
                'D', new ItemStack(Items.diamond),
                'E', new ItemStack(Items.ender_pearl),
                'C', new ItemStack(BlockECalculatorThreadCore.L4));
        GameRegistry.addShapedRecipe(new ItemStack(BlockECalculatorThreadCoreHyper.L6),
                "DED", "ECE", "DED",
                'D', new ItemStack(Items.diamond),
                'E', new ItemStack(Items.ender_pearl),
                'C', new ItemStack(BlockECalculatorThreadCoreHyper.L4));
        GameRegistry.addShapedRecipe(new ItemStack(BlockECalculatorThreadCoreHyper.L9),
                "DED", "ECE", "DED",
                'D', new ItemStack(Items.emerald),
                'E', new ItemStack(Items.ender_pearl),
                'C', new ItemStack(BlockECalculatorThreadCoreHyper.L6));
    }
}
