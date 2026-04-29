package github.kasuminova.ecoaeextension.common.block.ecotech.efabricator;

import github.kasuminova.ecoaeextension.ECOAEExtension;
import github.kasuminova.ecoaeextension.common.tile.ecotech.efabricator.EFabricatorMEChannel;

import net.minecraft.block.material.Material;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import javax.annotation.Nonnull;

@SuppressWarnings("deprecation")
public class BlockEFabricatorMEChannel extends BlockEFabricatorPart {

    public static final BlockEFabricatorMEChannel INSTANCE = new BlockEFabricatorMEChannel();

    protected BlockEFabricatorMEChannel() {
        super(Material.iron);
        this.setBlockName(ECOAEExtension.MOD_ID + '.' + "efabricator_me_channel");
    }

    @Nullable
    @Override
    public TileEntity createNewTileEntity(@Nonnull final World worldIn, final int meta) {
        return new EFabricatorMEChannel();
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

}