package github.kasuminova.ecoaeextension.common.block.ecotech.ecalculator;

import appeng.me.cluster.implementations.CraftingCPUCluster;
import github.kasuminova.mmce.common.util.Sides;
import github.kasuminova.ecoaeextension.ECOAEExtension;
import github.kasuminova.ecoaeextension.common.item.ecalculator.ItemECalculatorThreadCore;
import github.kasuminova.ecoaeextension.common.tile.ecotech.ecalculator.ECalculatorThreadCore;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompressedStreamTools;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.MathHelper;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import javax.annotation.Nonnull;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;

@SuppressWarnings("deprecation")
public class BlockECalculatorThreadCore extends BlockECalculatorPart {

    public static final BlockECalculatorThreadCore L4 = new BlockECalculatorThreadCore("l4", 1, 0);
    public static final BlockECalculatorThreadCore L6 = new BlockECalculatorThreadCore("l6", 2, 0);
    public static final BlockECalculatorThreadCore L9 = new BlockECalculatorThreadCore("l9", 4, 0);

    protected final int threads;
    protected final int hyperThreads;
    protected ItemECalculatorThreadCore item = null;

    protected BlockECalculatorThreadCore(final ResourceLocation registryName, final String translationKey, final int threads, final int hyperThreads) {
        super(Material.iron);
        this.threads = threads;
        this.hyperThreads = hyperThreads;
        this.setRegistryName(registryName);
        this.setTranslationKey(translationKey);
    }

    public ItemECalculatorThreadCore getItem() {
        return item;
    }

    public BlockECalculatorThreadCore setItem(final ItemECalculatorThreadCore item) {
        this.item = item;
        return this;
    }

    public int getThreads() {
        return threads;
    }

    public int getHyperThreads() {
        return hyperThreads;
    }

    protected BlockECalculatorThreadCore(final String level, final int threads, final int hyperThreads) {
        this(
                new ResourceLocation(ECOAEExtension.MOD_ID, "ecalculator_thread_core_" + level),
                ECOAEExtension.MOD_ID + '.' + "ecalculator_thread_core_" + level,
                threads, hyperThreads
        );
    }

    @Nullable
    @Override
    public TileEntity createNewTileEntity(@Nonnull final World worldIn, final int meta) {
        return new ECalculatorThreadCore(this.threads, this.hyperThreads);
    }

    @Override
    public void dropBlockAsItemWithChance(@Nonnull final World worldIn, final int x, final int y, final int z, final int meta, final float chance, final int fortune) {
    }

    @Override
    public void breakBlock(@Nonnull final World worldIn, final int x, final int y, final int z, @Nonnull final Block block, final int meta) {
        TileEntity te = worldIn.getTileEntity(x, y, z);
        ItemStack dropped = new ItemStack(item);

        if (te == null || Sides.isRunningOnClient()) {
            spawnAsEntity(worldIn, x, y, z, dropped);
            worldIn.removeTileEntity(x, y, z);
            return;
        }
        if (!(te instanceof ECalculatorThreadCore threadCore)) {
            spawnAsEntity(worldIn, x, y, z, dropped);
            worldIn.removeTileEntity(x, y, z);
            return;
        }
        final List<CraftingCPUCluster> cpus = threadCore.getCpus();
        if (cpus.isEmpty()) {
            spawnAsEntity(worldIn, x, y, z, dropped);
            worldIn.removeTileEntity(x, y, z);
            return;
        }

        final ByteArrayOutputStream bos = new ByteArrayOutputStream();
        final NBTTagCompound tag = new NBTTagCompound();
        threadCore.writeCPUNBT(tag);

        try {
            CompressedStreamTools.writeCompressed(tag, bos);
        } catch (IOException e) {
            ECOAEExtension.log.error("Failed to write CPU NBT to byte array!", e);
            spawnAsEntity(worldIn, x, y, z, dropped);
            worldIn.removeTileEntity(x, y, z);
            return;
        } finally {
            cpus.clear();
        }

        NBTTagCompound itemTag = new NBTTagCompound();
        itemTag.setByteArray("compressedCpuNBT", bos.toByteArray());
        dropped.setTagCompound(itemTag);
        threadCore.onBlockDestroyed();

        try {
            bos.close();
        } catch (IOException e) {
            ECOAEExtension.log.error("Failed to close byte array streams!", e);
        }

        spawnAsEntity(worldIn, x, y, z, dropped);
        worldIn.removeTileEntity(x, y, z);
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

        TileEntity te = worldIn.getTileEntity(x, y, z);
        NBTTagCompound tag = stack.getTagCompound();
        if (te instanceof ECalculatorThreadCore threadCore && tag != null && tag.hasKey("compressedCpuNBT")) {
            byte[] cpuNBTBytes = tag.getByteArray("compressedCpuNBT");
            if (cpuNBTBytes.length == 0) {
                return;
            }
            try (ByteArrayInputStream bis = new ByteArrayInputStream(cpuNBTBytes)) {
                NBTTagCompound cpuNBT = CompressedStreamTools.readCompressed(bis);
                threadCore.readCPUNBT(cpuNBT);
            } catch (IOException e) {
                ECOAEExtension.log.error("Failed to read CPU NBT from byte array!", e);
            }
        }
    }

    @Override
    public int getLightValue(@Nonnull final IBlockAccess world, final int x, final int y, final int z) {
        TileEntity te = world.getTileEntity(x, y, z);
        if (te instanceof ECalculatorThreadCore threadCore) {
            if (threadCore.getControllerLevel() != null) {
                if (threadCore.getThreads() > 0) {
                    return 15;
                }
                return 10;
            }
        }
        return 5;
    }

}
