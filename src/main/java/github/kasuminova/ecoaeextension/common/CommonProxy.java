package github.kasuminova.ecoaeextension.common;

import appeng.api.AEApi;
import appeng.api.storage.ICellHandler;
import github.kasuminova.ecoaeextension.ECOAEExtension;
import github.kasuminova.ecoaeextension.common.container.*;
import github.kasuminova.ecoaeextension.common.data.ModDataHolder;
import github.kasuminova.ecoaeextension.common.estorage.EStorageCellHandler;
import github.kasuminova.ecoaeextension.common.handler.ECalculatorEventHandler;
import github.kasuminova.ecoaeextension.common.handler.EFabricatorEventHandler;
import github.kasuminova.ecoaeextension.common.handler.EStorageEventHandler;
import github.kasuminova.ecoaeextension.common.registry.RegistryBlocks;
import github.kasuminova.ecoaeextension.common.registry.RegistryItems;
import github.kasuminova.ecoaeextension.common.tile.ecotech.ecalculator.ECalculatorController;
import github.kasuminova.ecoaeextension.common.tile.ecotech.efabricator.EFabricatorController;
import github.kasuminova.ecoaeextension.common.tile.ecotech.efabricator.EFabricatorPatternBus;
import github.kasuminova.ecoaeextension.common.tile.ecotech.estorage.EStorageController;
import github.kasuminova.ecoaeextension.common.util.MachineCoolants;
import java.io.File;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;
import cpw.mods.fml.common.network.IGuiHandler;
import cpw.mods.fml.common.network.NetworkRegistry;

@SuppressWarnings("MethodMayBeStatic")
public class CommonProxy implements IGuiHandler {

    public static final ModDataHolder dataHolder = new ModDataHolder();

    public CommonProxy() {
        MinecraftForge.EVENT_BUS.register(new RegistryBlocks());
        MinecraftForge.EVENT_BUS.register(new RegistryItems());
    }

    public static void loadModData(File configDir) {
        dataHolder.setup(configDir);
        if (dataHolder.requiresDefaultMachinery()) {
            dataHolder.copyDefaultMachinery();
        }
    }

    public void preInit() {
        RegistryBlocks.registerBlocks();
        RegistryBlocks.registerTileEntities();
        RegistryItems.registerItems();

        NetworkRegistry.INSTANCE.registerGuiHandler(ECOAEExtension.instance, this);

        MinecraftForge.EVENT_BUS.register(EStorageEventHandler.INSTANCE);
        MinecraftForge.EVENT_BUS.register(EFabricatorEventHandler.INSTANCE);
        MinecraftForge.EVENT_BUS.register(ECalculatorEventHandler.INSTANCE);
    }

    public void init() {
        if (AEApi.instance() != null) {
            AEApi.instance().registries().cell().addCellHandler(EStorageCellHandler.INSTANCE);
        }
    }

    public void postInit() {
        MachineCoolants.INSTANCE.init();
    }

    @Override
    public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
        GuiType type = GuiType.values()[MathHelper.clamp_int(ID, 0, GuiType.values().length - 1)];
        Class<? extends TileEntity> required = type.requiredTileEntity;
        TileEntity present = null;
        if (required != null) {
            TileEntity te = world.getTileEntity(x, y, z);
            if (te != null && required.isAssignableFrom(te.getClass())) {
                present = te;
            } else {
                return null;
            }
        }

        switch (type) {
            case ESTORAGE_CONTROLLER:
                return new ContainerEStorageController((EStorageController) present, player);
            case EFABRICATOR_CONTROLLER: {
                EFabricatorController efController = (EFabricatorController) present;
                if (efController.getChannel() != null && !checkSecurity(player, efController)) {
                    return null;
                }
                return new ContainerEFabricatorController(efController, player);
            }
            case EFABRICATOR_PATTERN_SEARCH: {
                EFabricatorController efController = (EFabricatorController) present;
                if (efController.getChannel() != null && !checkSecurity(player, efController)) {
                    return null;
                }
                return new ContainerEFabricatorPatternSearch(efController, player);
            }
            case EFABRICATOR_PATTERN_BUS: {
                EFabricatorPatternBus efPatternBus = (EFabricatorPatternBus) present;
                EFabricatorController efController = efPatternBus.getController();
                if (efController != null && efController.getChannel() != null && !checkSecurity(player, efController)) {
                    return null;
                }
                return new ContainerEFabricatorPatternBus(efPatternBus, player);
            }
            case ECALCULATOR_CONTROLLER: {
                ECalculatorController ecController = (ECalculatorController) present;
                if (ecController.getChannel() != null && !checkSecurity(player, ecController)) {
                    return null;
                }
                return new ContainerECalculatorController((ECalculatorController) present, player);
            }
        }
        return null;
    }

    private boolean checkSecurity(EntityPlayer player, TileEntity te) {
        // TODO: Implement proper AE2 security check for 1.7.10
        return false;
    }

    @Override
    public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
        return null;
    }

    public enum GuiType {
        ESTORAGE_CONTROLLER(EStorageController.class),
        EFABRICATOR_CONTROLLER(EFabricatorController.class),
        EFABRICATOR_PATTERN_SEARCH(EFabricatorController.class),
        EFABRICATOR_PATTERN_BUS(EFabricatorPatternBus.class),
        ECALCULATOR_CONTROLLER(ECalculatorController.class),
        ;

        public final Class<? extends TileEntity> requiredTileEntity;

        GuiType(Class<? extends TileEntity> requiredTileEntity) {
            this.requiredTileEntity = requiredTileEntity;
        }
    }
}
