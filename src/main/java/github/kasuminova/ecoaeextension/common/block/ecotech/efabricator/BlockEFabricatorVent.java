package github.kasuminova.ecoaeextension.common.block.ecotech.efabricator;

import github.kasuminova.ecoaeextension.ECOAEExtension;
import github.kasuminova.ecoaeextension.common.block.prop.FacingProp;
import github.kasuminova.ecoaeextension.common.util.EnumFacingCompat;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

import javax.annotation.Nonnull;

@SuppressWarnings("deprecation")
public class BlockEFabricatorVent extends BlockEFabricator {

    public static final BlockEFabricatorVent INSTANCE = new BlockEFabricatorVent();

    protected BlockEFabricatorVent() {
        this.setDefaultState(this.stateContainer.getBaseState()
                .withProperty(FacingProp.HORIZONTALS, ForgeDirection.NORTH)
        );
        this.setBlockName(ECOAEExtension.MOD_ID + '.' + "efabricator_vent");
    }

    public IBlockState getStateFromMeta(final int meta) {
        return getDefaultState().withProperty(FacingProp.HORIZONTALS, EnumFacingCompat.byHorizontalIndex(meta));
    }

    public int getMetaFromState(@Nonnull final IBlockState state) {
        return EnumFacingCompat.toHorizontalIndex(state.getValue(FacingProp.HORIZONTALS));
    }

    @Override
    public void onBlockPlacedBy(@Nonnull final World world,
                                final int x, final int y, final int z,
                                @Nonnull final EntityLivingBase placer,
                                @Nonnull final ItemStack stack)
    {
        // 鉴于某人把模型做反了，所以这里不反向。
        int dir = MathHelper.floor_double((double) (placer.rotationYaw * 4.0F / 360.0F) + 0.5D) & 3;
        world.setBlockMetadataWithNotify(x, y, z, dir, 2);
    }

    @Nonnull
    @Override
    protected BlockStateContainer createBlockState() {
        return new BlockStateContainer(this, FacingProp.HORIZONTALS);
    }

}
