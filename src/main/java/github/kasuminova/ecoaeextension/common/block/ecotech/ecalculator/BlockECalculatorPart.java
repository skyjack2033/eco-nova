package github.kasuminova.ecoaeextension.common.block.ecotech.ecalculator;

import github.kasuminova.ecoaeextension.common.core.CreativeTabNovaEng;
import net.minecraft.block.BlockContainer;

import net.minecraft.block.material.Material;
import net.minecraft.entity.Entity;



import javax.annotation.Nonnull;

@SuppressWarnings("deprecation")
public abstract class BlockECalculatorPart extends BlockContainer {

    protected BlockECalculatorPart(final Material materialIn) {
        super(materialIn);
        this.lightOpacity = 0;
        this.setCreativeTab(CreativeTabNovaEng.INSTANCE);
        this.setHardness(20.0F);
        this.setResistance(2000.0F);
        this.setStepSound(Block.soundTypeMetal);
        this.setHarvestLevel("pickaxe", 2);
    }

    @Override
    public boolean hasTileEntity() {
        return true;
    }

    @Override
    public boolean isOpaqueCube() {
        return false;
    }

    public boolean canEntitySpawn(@Nonnull final Entity entityIn) {
        return false;
    }

}
