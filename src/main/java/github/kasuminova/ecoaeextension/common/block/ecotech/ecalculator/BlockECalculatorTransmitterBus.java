package github.kasuminova.ecoaeextension.common.block.ecotech.ecalculator;

import github.kasuminova.ecoaeextension.ECOAEExtension;
import github.kasuminova.ecoaeextension.common.tile.ecotech.ecalculator.ECalculatorTransmitterBus;
import github.kasuminova.ecoaeextension.common.util.BlockPos;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.MathHelper;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import javax.annotation.Nonnull;

@SuppressWarnings("deprecation")
public class BlockECalculatorTransmitterBus extends BlockECalculatorPart {

    public static final BlockECalculatorTransmitterBus INSTANCE = new BlockECalculatorTransmitterBus();

    protected BlockECalculatorTransmitterBus() {
        super(Material.iron);
        this.setBlockName(ECOAEExtension.MOD_ID + '.' + "ecalculator_transmitter_bus");
    }

    @Nullable
    @Override
    public TileEntity createNewTileEntity(@Nonnull final World worldIn, final int meta) {
        return new ECalculatorTransmitterBus();
    }

    @Override
    public void onNeighborBlockChange(World worldIn, int x, int y, int z, Block neighborBlock) {
        TileEntity te = worldIn.getTileEntity(x, y, z);
        if (te instanceof ECalculatorTransmitterBus bus) {
            bus.neighborChanged(new BlockPos(x, y, z));
        }
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
        TileEntity te = world.getTileEntity(x, y, z);
        if (te instanceof ECalculatorTransmitterBus bus) {
            if (bus.isAllConnected()) {
                return 12;
            } else if (bus.isUpConnected() || bus.isDownConnected()) {
                return 8;
            }
        }
        return 4;
    }

}
