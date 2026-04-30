package github.kasuminova.ecoaeextension.common.block.ecotech.efabricator;

import github.kasuminova.ecoaeextension.ECOAEExtension;
import github.kasuminova.ecoaeextension.common.CommonProxy;
import github.kasuminova.ecoaeextension.common.core.CreativeTabNovaEng;
import github.kasuminova.ecoaeextension.common.tile.ecotech.efabricator.EFabricatorController;
import hellfirepvp.modularmachinery.ModularMachinery;
import hellfirepvp.modularmachinery.common.block.BlockController;
import hellfirepvp.modularmachinery.common.machine.DynamicMachine;
import hellfirepvp.modularmachinery.common.machine.MachineRegistry;
import hellfirepvp.modularmachinery.common.util.IOInventory;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.util.ForgeDirection;

import net.minecraft.util.ResourceLocation;
import github.kasuminova.ecoaeextension.common.util.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import cpw.mods.fml.common.FMLCommonHandler;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Random;
import java.util.UUID;

@SuppressWarnings("deprecation")
public class BlockEFabricatorController extends BlockController {
    public static final Map<ResourceLocation, BlockEFabricatorController> REGISTRY = new LinkedHashMap<>();
    public static final BlockEFabricatorController L4;
    public static final BlockEFabricatorController L6;
    public static final BlockEFabricatorController L9;

    static {
        L4 = new BlockEFabricatorController("l4");
        REGISTRY.put(L4.registryName, L4);
        L6 = new BlockEFabricatorController("l6");
        REGISTRY.put(L6.registryName, L6);
        L9 = new BlockEFabricatorController("l9");
        REGISTRY.put(L9.registryName, L9);
    }

    protected final ResourceLocation registryName;
    protected final ResourceLocation machineRegistryName;

    public BlockEFabricatorController(final String level) {
        this.setHardness(20.0F);
        this.setResistance(2000.0F);
        this.setHarvestLevel("pickaxe", 2);
        this.setCreativeTab(CreativeTabNovaEng.INSTANCE);
        registryName = new ResourceLocation(ECOAEExtension.MOD_ID, "extendable_fabricator_subsystem_" + level);
        machineRegistryName = new ResourceLocation(ModularMachinery.MODID, registryName.getResourcePath());
        setBlockName(ECOAEExtension.MOD_ID + '.' + registryName.getResourcePath());
    }

    public int getLightValue(@Nonnull final IBlockState state) {
        return state.getValue(FORMED) ? 10 : 0;
    }


    @Override
    public boolean onBlockActivated(final World worldIn, final int x, final int y, final int z,
                                    final EntityPlayer playerIn, final int side,
                                    final float hitX, final float hitY, final float hitZ) {
        if (!worldIn.isRemote) {
            TileEntity te = worldIn.getTileEntity(x, y, z);
            if (te instanceof EFabricatorController && ((EFabricatorController) te).isStructureFormed()) {
                playerIn.openGui(ECOAEExtension.MOD_ID, CommonProxy.GuiType.EFABRICATOR_CONTROLLER.ordinal(),
                        worldIn, x, y, z);
            }
        }
        return true;
    }

    public DynamicMachine getParentMachine() {
        return MachineRegistry.getRegistry().getMachine(machineRegistryName);
    }

    @Nullable

    public TileEntity createTileEntity(final World world, final IBlockState state) {
        return new EFabricatorController(machineRegistryName);
    }

    @Nullable

    public TileEntity createNewTileEntity(final World worldIn, final int meta) {
        return new EFabricatorController(machineRegistryName);
    }
}
