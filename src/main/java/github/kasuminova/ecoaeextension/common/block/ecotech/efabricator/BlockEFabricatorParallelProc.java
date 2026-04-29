package github.kasuminova.ecoaeextension.common.block.ecotech.efabricator;

import github.kasuminova.ecoaeextension.ECOAEExtension;
import github.kasuminova.ecoaeextension.common.tile.ecotech.efabricator.EFabricatorParallelProc;
import github.kasuminova.ecoaeextension.common.tile.ecotech.efabricator.EFabricatorParallelProc.Modifier;

import net.minecraft.block.material.Material;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.MathHelper;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import javax.annotation.Nonnull;
import java.util.Arrays;
import java.util.List;

@SuppressWarnings({"deprecation", "ArraysAsListWithZeroOrOneArgument"})
public class BlockEFabricatorParallelProc extends BlockEFabricatorPart {

    public static final BlockEFabricatorParallelProc L4 = new BlockEFabricatorParallelProc("l4",
            Arrays.asList(
                    new Modifier(EFabricatorParallelProc.Type.ADD, 24, false)
            ),
            Arrays.asList(
                    new Modifier(EFabricatorParallelProc.Type.ADD, 32, false),
                    new Modifier(EFabricatorParallelProc.Type.MULTIPLY, 0.99, true)
            )
    );
    public static final BlockEFabricatorParallelProc L6 = new BlockEFabricatorParallelProc("l6",
            Arrays.asList(
                    new Modifier(EFabricatorParallelProc.Type.ADD, 72, false)
            ),
            Arrays.asList(
                    new Modifier(EFabricatorParallelProc.Type.ADD, 96, false),
                    new Modifier(EFabricatorParallelProc.Type.MULTIPLY, 0.98, true)
            )
    );
    public static final BlockEFabricatorParallelProc L9 = new BlockEFabricatorParallelProc("l9",
            Arrays.asList(
                    new Modifier(EFabricatorParallelProc.Type.ADD, 256, false)
            ),
            Arrays.asList(
                    new Modifier(EFabricatorParallelProc.Type.ADD, 384, false),
                    new Modifier(EFabricatorParallelProc.Type.MULTIPLY, 0.97, true)
            )
    );

    protected final List<Modifier> modifiers;
    protected final List<Modifier> overclockModifiers;

    protected BlockEFabricatorParallelProc(final String level, final List<Modifier> modifiers, final List<Modifier> overclockModifiers) {
        super(Material.iron);
        this.setBlockName(ECOAEExtension.MOD_ID + '.' + "efabricator_parallel_proc_" + level);
        this.modifiers = modifiers;
        this.overclockModifiers = overclockModifiers;
    }

    public List<Modifier> getModifiers() {
        return modifiers;
    }

    public List<Modifier> getOverclockModifiers() {
        return overclockModifiers;
    }

    @Nullable
    @Override
    public TileEntity createNewTileEntity(@Nonnull final World worldIn, final int meta) {
        return new EFabricatorParallelProc(modifiers, overclockModifiers);
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

}
