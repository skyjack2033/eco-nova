package github.kasuminova.ecoaeextension.common.block.ecotech.ecalculator;

import github.kasuminova.ecoaeextension.ECOAEExtension;
import github.kasuminova.ecoaeextension.common.tile.ecotech.ecalculator.ECalculatorMEChannel;
import net.minecraft.block.material.Material;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import javax.annotation.Nonnull;

public class BlockECalculatorMEChannel extends BlockECalculatorPart {

    public static final BlockECalculatorMEChannel INSTANCE = new BlockECalculatorMEChannel();

    protected BlockECalculatorMEChannel() {
        super(Material.iron);
        this.setUnlocalizedName(ECOAEExtension.MOD_ID + '.' + "ecalculator_me_channel");
    }

    @Nullable
    @Override
    public TileEntity createNewTileEntity(@Nonnull final World worldIn, final int meta) {
        return new ECalculatorMEChannel();
    }

}
