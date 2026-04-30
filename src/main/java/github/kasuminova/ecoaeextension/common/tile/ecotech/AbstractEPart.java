package github.kasuminova.ecoaeextension.common.tile.ecotech;

import hellfirepvp.modularmachinery.common.tiles.base.TileEntitySynchronized;
import net.minecraft.block.state.IBlockState;
import net.minecraft.nbt.NBTTagCompound;
import github.kasuminova.ecoaeextension.common.util.BlockPos;
import net.minecraft.world.World;
import cpw.mods.fml.common.FMLCommonHandler;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public abstract class AbstractEPart<C> extends TileEntitySynchronized implements EPart<C> {

    protected C partController = null;
    protected boolean loaded = false;

    @Override
    public void setController(final C controller) {
        this.partController = controller;
    }

    @Nullable
    @Override
    public C getController() {
        return partController;
    }

    public void onAssembled() {
    }

    public void onDisassembled() {
    }

    @Override
    public void onLoad() {
        super.onLoad();
        this.loaded = true;
    }

    @Override
    public void onChunkUnload() {
        loaded = false;
        super.onChunkUnload();
        callDisassemble(partController);
    }

    @Override
    public void invalidate() {
        loaded = false;
        super.invalidate();
        callDisassemble(partController);
    }

    private static void callDisassemble(Object controller) {
        if (controller instanceof EPartController) {
            ((EPartController<?>) controller).disassemble();
        } else if (controller instanceof NovaPartController) {
            ((NovaPartController<?>) controller).disassemble();
        }
    }

    @Override
    public void readCustomNBT(final NBTTagCompound compound) {
        super.readCustomNBT(compound);
    }

    /**
     * Refresh client block state to actual state.
     */
    @Override
    @SuppressWarnings("ConstantValue")
    public void updateContainingBlockInfo() {
        super.updateContainingBlockInfo();
        final World world = getWorld();
        if (FMLCommonHandler.instance().getEffectiveSide().isClient() && worldObj != null) {
            worldObj.markBlockForUpdate(xCoord, yCoord, zCoord);
        }
    }

}
