package github.kasuminova.ecoaeextension.common.registry;

import github.kasuminova.ecoaeextension.common.block.ecotech.ecalculator.*;
import github.kasuminova.ecoaeextension.common.block.ecotech.efabricator.*;
import github.kasuminova.ecoaeextension.common.block.ecotech.estorage.*;
import github.kasuminova.ecoaeextension.common.item.ItemBlockME;
import github.kasuminova.ecoaeextension.common.item.ecalculator.*;
import github.kasuminova.ecoaeextension.common.item.efabriactor.*;
import github.kasuminova.ecoaeextension.common.item.estorage.ItemEStorageController;
import github.kasuminova.ecoaeextension.common.tile.ecotech.ecalculator.*;
import github.kasuminova.ecoaeextension.common.tile.ecotech.efabricator.*;
import github.kasuminova.ecoaeextension.common.tile.ecotech.estorage.EStorageCellDrive;
import github.kasuminova.ecoaeextension.common.tile.ecotech.estorage.EStorageController;
import github.kasuminova.ecoaeextension.common.tile.ecotech.estorage.EStorageEnergyCell;
import github.kasuminova.ecoaeextension.common.tile.ecotech.estorage.EStorageMEChannel;
import hellfirepvp.modularmachinery.common.block.BlockCustomName;
import hellfirepvp.modularmachinery.common.block.BlockDynamicColor;
import hellfirepvp.modularmachinery.common.block.BlockMachineComponent;
import hellfirepvp.modularmachinery.common.item.ItemBlockCustomName;
import hellfirepvp.modularmachinery.common.item.ItemBlockMachineComponent;
import hellfirepvp.modularmachinery.common.item.ItemBlockMachineComponentCustomName;
import net.minecraft.block.Block;
import net.minecraft.item.ItemBlock;
import net.minecraft.tileentity.TileEntity;
import cpw.mods.fml.common.registry.GameRegistry;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@SuppressWarnings({"MethodMayBeStatic", "UnusedReturnValue"})
public class RegistryBlocks {
    public static final List<Block> BLOCK_MODEL_TO_REGISTER = new ArrayList<>();
    private static final Map<Block, Class<? extends ItemBlock>> CUSTOM_ITEM_BLOCKS = new HashMap<>();

    public static void registerBlocks() {
        // EStorage
        registerCustomItemBlock(BlockEStorageController.L4, ItemEStorageController.class);
        registerCustomItemBlock(BlockEStorageController.L6, ItemEStorageController.class);
        registerCustomItemBlock(BlockEStorageController.L9, ItemEStorageController.class);
        registerBlock(BlockEStorageEnergyCell.L4);
        registerBlock(BlockEStorageEnergyCell.L6);
        registerBlock(BlockEStorageEnergyCell.L9);
        registerBlock(BlockEStorageCellDrive.INSTANCE);
        registerCustomItemBlock(BlockEStorageMEChannel.INSTANCE, ItemBlockME.class);
        registerBlock(BlockEStorageVent.INSTANCE);
        registerBlock(BlockEStorageCasing.INSTANCE);

        // EFabricator
        registerCustomItemBlock(BlockEFabricatorController.L4, ItemEFabricatorController.class);
        registerCustomItemBlock(BlockEFabricatorController.L6, ItemEFabricatorController.class);
        registerCustomItemBlock(BlockEFabricatorController.L9, ItemEFabricatorController.class);
        registerCustomItemBlock(BlockEFabricatorParallelProc.L4, ItemEFabricatorParallelProc.class);
        registerCustomItemBlock(BlockEFabricatorParallelProc.L6, ItemEFabricatorParallelProc.class);
        registerCustomItemBlock(BlockEFabricatorParallelProc.L9, ItemEFabricatorParallelProc.class);
        registerCustomItemBlock(BlockEFabricatorMEChannel.INSTANCE, ItemEFabricatorMEChannel.class);
        registerCustomItemBlock(BlockEFabricatorPatternBus.INSTANCE, ItemEFabricatorPatternBus.class);
        registerCustomItemBlock(BlockEFabricatorWorker.INSTANCE, ItemEFabricatorWorker.class);
        registerBlock(BlockEFabricatorVent.INSTANCE);
        registerBlock(BlockEFabricatorCasing.INSTANCE);

        // ECalculator
        registerCustomItemBlock(BlockECalculatorController.L4, ItemECalculatorController.class);
        registerCustomItemBlock(BlockECalculatorController.L6, ItemECalculatorController.class);
        registerCustomItemBlock(BlockECalculatorController.L9, ItemECalculatorController.class);
        registerCustomItemBlock(BlockECalculatorParallelProc.L4, ItemECalculatorParallelProc.class);
        registerCustomItemBlock(BlockECalculatorParallelProc.L6, ItemECalculatorParallelProc.class);
        registerCustomItemBlock(BlockECalculatorParallelProc.L9, ItemECalculatorParallelProc.class);
        registerCustomItemBlock(BlockECalculatorThreadCore.L4, ItemECalculatorThreadCore.class);
        registerCustomItemBlock(BlockECalculatorThreadCore.L6, ItemECalculatorThreadCore.class);
        registerCustomItemBlock(BlockECalculatorThreadCore.L9, ItemECalculatorThreadCore.class);
        registerCustomItemBlock(BlockECalculatorThreadCoreHyper.L4, ItemECalculatorThreadCore.class);
        registerCustomItemBlock(BlockECalculatorThreadCoreHyper.L6, ItemECalculatorThreadCore.class);
        registerCustomItemBlock(BlockECalculatorThreadCoreHyper.L9, ItemECalculatorThreadCore.class);
        registerBlock(BlockECalculatorTail.L4);
        registerBlock(BlockECalculatorTail.L6);
        registerBlock(BlockECalculatorTail.L9);
        registerCustomItemBlock(BlockECalculatorMEChannel.INSTANCE, ItemECalculatorMEChannel.class);
        registerCustomItemBlock(BlockECalculatorCellDrive.INSTANCE, ItemECalculatorCellDrive.class);
        registerBlock(BlockECalculatorTransmitterBus.INSTANCE);
        registerBlock(BlockECalculatorCasing.INSTANCE);

        finalizeBlockRegistration();
    }

    public static void registerTileEntities() {
        // EStorage
        registerTileEntity(EStorageController.class, "estorage_controller");
        registerTileEntity(EStorageEnergyCell.class, "estorage_energy_cell");
        registerTileEntity(EStorageCellDrive.class, "estorage_cell_drive");
        registerTileEntity(EStorageMEChannel.class, "estorage_me_channel");

        // EFabricator
        registerTileEntity(EFabricatorController.class, "efabricator_controller");
        registerTileEntity(EFabricatorParallelProc.class, "efabricator_parallel_proc");
        registerTileEntity(EFabricatorTail.class, "efabricator_tail");
        registerTileEntity(EFabricatorPatternBus.class, "efabricator_pattern_bus");
        registerTileEntity(EFabricatorWorker.class, "efabricator_worker");
        registerTileEntity(EFabricatorMEChannel.class, "efabricator_me_channel");

        // ECalculator
        registerTileEntity(ECalculatorController.class, "ecalculator_controller");
        registerTileEntity(ECalculatorParallelProc.class, "ecalculator_parallel_proc");
        registerTileEntity(ECalculatorThreadCore.class, "ecalculator_thread_core");
        registerTileEntity(ECalculatorTail.class, "ecalculator_tail");
        registerTileEntity(ECalculatorMEChannel.class, "ecalculator_me_channel");
        registerTileEntity(ECalculatorCellDrive.class, "ecalculator_cell_drive");
        registerTileEntity(ECalculatorTransmitterBus.class, "ecalculator_transmitter_bus");
    }

    public static void registerBlockModels() {
        // Block model registration - stubbed for 1.7.10 port
        BLOCK_MODEL_TO_REGISTER.clear();
    }

    public static void registerTileEntity(Class<? extends TileEntity> tile, String name) {
        GameRegistry.registerTileEntity(tile, name);
    }

    public static <T extends Block> T registerBlock(T block) {
        BLOCK_MODEL_TO_REGISTER.add(block);
        GenericRegistryPrimer.INSTANCE.register(block);
        return block;
    }

    private static <T extends Block> void registerCustomItemBlock(T block, Class<? extends ItemBlock> itemClass) {
        CUSTOM_ITEM_BLOCKS.put(block, itemClass);
        registerBlock(block);
    }

    private static void finalizeBlockRegistration() {
        for (Block block : BLOCK_MODEL_TO_REGISTER) {
            String name = extractRegistryName(block);
            Class<? extends ItemBlock> itemClass = CUSTOM_ITEM_BLOCKS.getOrDefault(block, ItemBlock.class);
            GameRegistry.registerBlock(block, itemClass, name);
        }
    }

    private static String extractRegistryName(Block block) {
        String name = block.getUnlocalizedName();
        if (name != null && name.startsWith("tile.")) {
            name = name.substring(5);
        }
        // Strip modid prefix if present (e.g. "ecoaeextension.blockname" -> "blockname")
        int dotIndex = name.indexOf('.');
        if (dotIndex > 0) {
            name = name.substring(dotIndex + 1);
        }
        return name;
    }

    public static void registerBlockModel(final Block block) {
        // Block model registration - stubbed for 1.7.10 port
    }
}
