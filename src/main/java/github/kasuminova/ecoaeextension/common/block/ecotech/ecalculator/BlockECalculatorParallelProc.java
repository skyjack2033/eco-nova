package github.kasuminova.ecoaeextension.common.block.ecotech.ecalculator;

import github.kasuminova.ecoaeextension.ECOAEExtension;
import github.kasuminova.ecoaeextension.common.tile.ecotech.ecalculator.ECalculatorParallelProc;
import github.kasuminova.ecoaeextension.common.tile.ecotech.ecalculator.ECalculatorPart;
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
public class BlockECalculatorParallelProc extends BlockECalculatorPart {

    public static final BlockECalculatorParallelProc L4 = new BlockECalculatorParallelProc("l4", 256);
    public static final BlockECalculatorParallelProc L6 = new BlockECalculatorParallelProc("l6", 2048);
    public static final BlockECalculatorParallelProc L9 = new BlockECalculatorParallelProc("l9", 16384);

    protected final int parallelism;

    protected BlockECalculatorParallelProc(final String level, final int parallelism) {
        super(Material.iron);
        this.parallelism = parallelism;
        this.setUnlocalizedName(ECOAEExtension.MOD_ID + '.' + "ecalculator_parallel_proc_" + level);
    }

    public int getParallelism() {
        return parallelism;
    }

    @Nullable
    @Override
    public TileEntity createNewTileEntity(@Nonnull final World worldIn, final int meta) {
        return new ECalculatorParallelProc();
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
        if (te instanceof ECalculatorPart) {
            ECalculatorPart part = (ECalculatorPart) te;
            if (part.getControllerLevel() != null) {
                return 12;
            }
        }
        return 6;
    }

}
