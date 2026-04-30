package hellfirepvp.modularmachinery.common.tiles.base;

import github.kasuminova.ecoaeextension.common.util.BlockPos;
import net.minecraft.block.state.IBlockState;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S35PacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import cpw.mods.fml.common.FMLCommonHandler;

import javax.annotation.Nullable;

public class TileEntitySynchronized extends TileEntity {

    protected boolean loaded = false;

    public void readCustomNBT(NBTTagCompound compound) {
        // Stub: no custom NBT to read.
    }

    public void writeCustomNBT(NBTTagCompound compound) {
        // Stub: no custom NBT to write.
    }


    public void readFromNBT(NBTTagCompound compound) {
        super.readFromNBT(compound);
        readCustomNBT(compound);
    }


    public void writeToNBT(NBTTagCompound compound) {
        super.writeToNBT(compound);
        writeCustomNBT(compound);
    }

    public void markForUpdate() {
        markDirty();
        if (worldObj != null) {
            worldObj.markBlockForUpdate(xCoord, yCoord, zCoord);
        }
    }

    public void markForUpdateSync() {
        markDirty();
        if (worldObj != null) {
            worldObj.markBlockForUpdate(xCoord, yCoord, zCoord);
        }
    }


    public World getWorld() {
        return worldObj;
    }

    public BlockPos getPos() {
        return new BlockPos(xCoord, yCoord, zCoord);
    }


    @Override
    public Packet getDescriptionPacket() {
        NBTTagCompound compound = new NBTTagCompound();
        writeToNBT(compound);
        return new S35PacketUpdateTileEntity(xCoord, yCoord, zCoord, 0, compound);
    }

    public void onLoad() {
        loaded = true;
    }

    public void updateContainingBlockInfo() {
        super.updateContainingBlockInfo();
        if (worldObj != null) {
            worldObj.markBlockForUpdate(xCoord, yCoord, zCoord);
        }
    }

    @Nullable
    public S35PacketUpdateTileEntity getUpdatePacket() {
        NBTTagCompound compound = new NBTTagCompound();
        writeToNBT(compound);
        return new S35PacketUpdateTileEntity(xCoord, yCoord, zCoord, 0, compound);
    }

    public NBTTagCompound getUpdateTag() {
        NBTTagCompound compound = new NBTTagCompound();
        writeToNBT(compound);
        return compound;
    }

    public void markNoUpdateSync() {
        markDirty();
    }

    public void notifyUpdate() {
        if (worldObj != null) {
            worldObj.markBlockForUpdate(xCoord, yCoord, zCoord);
        }
    }

    public void markChunkDirty() {
        if (worldObj != null) {
            worldObj.markBlockForUpdate(xCoord, yCoord, zCoord);
        }
    }
}
