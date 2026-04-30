package github.kasuminova.ecoaeextension.common.block.ecotech.efabricator;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import github.kasuminova.ecoaeextension.ECOAEExtension;
import github.kasuminova.ecoaeextension.common.core.CreativeTabNovaEng;
import github.kasuminova.ecoaeextension.common.util.EnumFacingCompat;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.IIcon;
import net.minecraft.util.MathHelper;
import net.minecraftforge.common.util.ForgeDirection;

import javax.annotation.Nonnull;

@SuppressWarnings("deprecation")
public abstract class BlockEFabricator extends Block {

    protected IIcon blockIcon;
    protected final BlockStateContainer stateContainer;
    protected IBlockState defaultState;

    protected BlockEFabricator() {
        super(Material.iron);
        this.setHardness(20.0F);
        this.setResistance(2000.0F);
        this.setStepSound(Block.soundTypeMetal);
        this.setHarvestLevel("pickaxe", 2);
        this.setCreativeTab(CreativeTabNovaEng.INSTANCE);
        this.stateContainer = this.createBlockState();
        this.defaultState = this.stateContainer.getBaseState();
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

    // IBlockState support (not overrides - Block doesn't have these in 1.7.10)
    protected BlockStateContainer createBlockState() {
        return new BlockStateContainer(this);
    }

    public IBlockState getDefaultState() {
        return defaultState;
    }

    public void setDefaultState(IBlockState state) {
        this.defaultState = state;
    }

    public static ForgeDirection getHorizontalFacingFromEntity(final EntityLivingBase entity) {
        int dir = MathHelper.floor_double(entity.rotationYaw * 4.0F / 360.0F + 0.5D) & 3;
        return EnumFacingCompat.byHorizontalIndex(dir);
    }

}

