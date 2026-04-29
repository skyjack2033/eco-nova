package github.kasuminova.ecoaeextension.common.block.ecotech.efabricator;

import appeng.tile.inventory.AppEngInternalInventory;
import github.kasuminova.ecoaeextension.ECOAEExtension;
import github.kasuminova.ecoaeextension.common.CommonProxy;
import github.kasuminova.ecoaeextension.common.tile.ecotech.efabricator.EFabricatorPatternBus;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.MathHelper;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import javax.annotation.Nonnull;

@SuppressWarnings("deprecation")
public class BlockEFabricatorPatternBus extends BlockEFabricatorPart {

    public static final BlockEFabricatorPatternBus INSTANCE = new BlockEFabricatorPatternBus();

    protected BlockEFabricatorPatternBus() {
        super(Material.iron);
        this.setBlockName(ECOAEExtension.MOD_ID + '.' + "efabricator_pattern_bus");
    }

    @Nullable
    @Override
    public TileEntity createNewTileEntity(@Nonnull final World worldIn, final int meta) {
        return new EFabricatorPatternBus();
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
    public int getLightValue(@Nonnull final IBlockAccess world, final int x, final int y, final int z) {
        return 10;
    }

    @Override
    public boolean onBlockActivated(final World worldIn, final int x, final int y, final int z, @Nonnull final EntityPlayer playerIn, final int side, final float hitX, final float hitY, final float hitZ) {
        if (!worldIn.isRemote) {
            TileEntity te = worldIn.getTileEntity(x, y, z);
            if (te instanceof EFabricatorPatternBus) {
                playerIn.openGui(ECOAEExtension.MOD_ID, CommonProxy.GuiType.EFABRICATOR_PATTERN_BUS.ordinal(), worldIn, x, y, z);
            }
        }
        return true;
    }

    @Override
    public void breakBlock(World worldIn, int x, int y, int z, Block block, int meta) {
        TileEntity te = worldIn.getTileEntity(x, y, z);
        if (te instanceof EFabricatorPatternBus terminal) {
            AppEngInternalInventory inv = terminal.getPatterns();
            for (int i = 0; i < inv.getSlots(); i++) {
                ItemStack stack = inv.getStackInSlot(i);
                if (stack != null && stack.stackSize > 0) {
                    dropBlockAsItem(worldIn, x, y, z, stack);
                    inv.setInventorySlotContents(i, null);
                }
            }
        }
        super.breakBlock(worldIn, x, y, z, block, meta);
    }

}
