package github.kasuminova.ecoaeextension.common.block.ecotech.efabricator;

import github.kasuminova.ecoaeextension.ECOAEExtension;
import github.kasuminova.ecoaeextension.common.tile.ecotech.efabricator.EFabricatorTail;

import net.minecraft.block.material.Material;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.MathHelper;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import javax.annotation.Nonnull;

@SuppressWarnings({"deprecation", "NullableProblems"})
public class BlockEFabricatorTail extends BlockEFabricatorPart {

    public static final BlockEFabricatorTail L4 = new BlockEFabricatorTail("l4");
    public static final BlockEFabricatorTail L6 = new BlockEFabricatorTail("l6");
    public static final BlockEFabricatorTail L9 = new BlockEFabricatorTail("l9");

    protected BlockEFabricatorTail(final String level) {
        super(Material.iron);
        this.setBlockName(ECOAEExtension.MOD_ID + '.' + "efabricator_tail_" + level);
    }

    @Nullable
    @Override
    public TileEntity createNewTileEntity(@Nonnull final World worldIn, final int meta) {
        return new EFabricatorTail();
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
        if (te instanceof EFabricatorTail tail) {
            return tail.isFormed() ? 10 : 0;
        }
        return 0;
    }

}
