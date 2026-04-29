package github.kasuminova.ecoaeextension.common.block.ecotech.ecalculator;

import appeng.tile.inventory.AppEngInternalInventory;
import github.kasuminova.ecoaeextension.ECOAEExtension;
import github.kasuminova.ecoaeextension.common.item.ecalculator.ECalculatorCell;
import github.kasuminova.ecoaeextension.common.tile.ecotech.ecalculator.ECalculatorCellDrive;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.MathHelper;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import javax.annotation.Nonnull;

@SuppressWarnings("deprecation")
public class BlockECalculatorCellDrive extends BlockECalculatorPart {

    public static final BlockECalculatorCellDrive INSTANCE = new BlockECalculatorCellDrive();

    protected BlockECalculatorCellDrive() {
        super(Material.iron);
        this.setUnlocalizedName(ECOAEExtension.MOD_ID + '.' + "ecalculator_cell_drive");
    }

    @Nullable
    @Override
    public TileEntity createNewTileEntity(@Nonnull final World worldIn, final int meta) {
        return new ECalculatorCellDrive();
    }

    @Override
    public void onBlockPlacedBy(@Nonnull final World worldIn,
                                final int x,
                                final int y,
                                final int z,
                                @Nonnull final EntityLivingBase placer,
                                @Nonnull final ItemStack stack)
    {
        int facingMeta = MathHelper.floor_double((double) (placer.rotationYaw * 4.0F / 360.0F) + 0.5D) & 3;
        facingMeta = (facingMeta + 2) & 3;
        worldIn.setBlockMetadataWithNotify(x, y, z, facingMeta, 2);
    }

    @Override
    public void breakBlock(@Nonnull final World worldIn, final int x, final int y, final int z, @Nonnull final Block block, final int meta) {
        TileEntity te = worldIn.getTileEntity(x, y, z);
        if (te instanceof ECalculatorCellDrive) {
            ECalculatorCellDrive drive = (ECalculatorCellDrive) te;
            AppEngInternalInventory inv = drive.getDriveInv();
            for (int i = 0; i < inv.getSlots(); i++) {
                ItemStack stack = inv.getStackInSlot(i);
                if (stack != null && stack.stackSize > 0) {
                    dropBlockAsItem(worldIn, x, y, z, stack);
                    inv.setStackInSlot(i, null);
                }
            }
        }

        super.breakBlock(worldIn, x, y, z, block, meta);
    }

    @Override
    public int getLightValue(@Nonnull final IBlockAccess world, final int x, final int y, final int z) {
        TileEntity te = world.getTileEntity(x, y, z);
        if (te instanceof ECalculatorCellDrive) {
            ECalculatorCellDrive drive = (ECalculatorCellDrive) te;
            boolean isOn = drive.getControllerLevel() != null;
            AppEngInternalInventory driveInv = drive.getDriveInv();
            ItemStack stack = driveInv.getStackInSlot(0);
            boolean hasCell = stack != null && stack.stackSize > 0 && stack.getItem() instanceof ECalculatorCell;

            if (isOn && hasCell) {
                return 12;
            }
            return hasCell ? 8 : 4;
        }
        return 4;
    }

}
