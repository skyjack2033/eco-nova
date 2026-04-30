package github.kasuminova.ecoaeextension.common.block.ecotech.ecalculator;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import github.kasuminova.ecoaeextension.ECOAEExtension;
import github.kasuminova.ecoaeextension.common.core.CreativeTabNovaEng;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.Entity;
import net.minecraft.util.IIcon;

import javax.annotation.Nonnull;

@SuppressWarnings("deprecation")
public abstract class BlockECalculator extends Block {

    protected IIcon blockIcon;

    protected BlockECalculator() {
        super(Material.iron);
        this.lightOpacity = 0;
        this.setHardness(20.0F);
        this.setResistance(2000.0F);
        this.setStepSound(Block.soundTypeMetal);
        this.setHarvestLevel("pickaxe", 2);
        this.setCreativeTab(CreativeTabNovaEng.INSTANCE);
        setBlockTextureName(ECOAEExtension.MOD_ID + ":carbon_fiber_chassis");
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void registerBlockIcons(IIconRegister register) {
        this.blockIcon = register.registerIcon(getTextureName());
    }

    @Override
    @SideOnly(Side.CLIENT)
    public IIcon getIcon(int side, int meta) {
        return this.blockIcon;
    }

    @Override
    public boolean isOpaqueCube() {
        return false;
    }

    public boolean canEntitySpawn(@Nonnull final Entity entityIn) {
        return false;
    }

}
