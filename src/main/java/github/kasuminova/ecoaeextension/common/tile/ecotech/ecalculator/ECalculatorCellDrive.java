package github.kasuminova.ecoaeextension.common.tile.ecotech.ecalculator;

import appeng.tile.inventory.AppEngInternalInventory;
import appeng.util.helpers.ItemHandlerUtil;
import appeng.util.inv.IAEAppEngInventory;
import appeng.util.inv.InvOperation;
import appeng.util.inv.filter.IAEItemFilter;
import github.kasuminova.ecoaeextension.common.block.ecotech.ecalculator.prop.DriveStorageLevel;
import github.kasuminova.ecoaeextension.common.block.ecotech.ecalculator.prop.Levels;
import github.kasuminova.ecoaeextension.common.item.ecalculator.ECalculatorCell;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.common.util.ForgeDirection;
import cpw.mods.fml.common.FMLCommonHandler;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import static appeng.helpers.ItemStackHelper.stackFromNBT;
import static appeng.helpers.ItemStackHelper.stackWriteToNBT;

public class ECalculatorCellDrive extends ECalculatorPart implements IAEAppEngInventory {

    protected final AppEngInternalInventory driveInv = new AppEngInternalInventory(this, 1);
    protected ForgeDirection connectedSide = null;

    public int getInventoryStackLimit() { return 64; }

    public ECalculatorCellDrive() {
        this.driveInv.setFilter(CellInvFilter.INSTANCE);
    }


    public void onChangeInventory(final IItemHandler inv, final int slot, final InvOperation mc, final ItemStack removedStack, final ItemStack newStack) {
        disconnectTransmitter();
        final ECalculatorController controller = getController();
        if (controller != null) {
            controller.recalculateTotalBytes();
            controller.createVirtualCPU();
        }
        this.markForUpdateSync();
    }

    public long getSuppliedBytes() {
        final ItemStack stackInSlot = driveInv.getStackInSlot(0);
        if (stackInSlot == null || stackInSlot.stackSize <= 0) {
            return 0;
        }
        if (!(stackInSlot.getItem() instanceof ECalculatorCell)) {
            return 0;
        }
        ECalculatorCell cell = (ECalculatorCell) stackInSlot.getItem();

        Levels level = getControllerLevel();
        DriveStorageLevel cellLevel = cell.getLevel();
        switch (cellLevel) {
            case B: {
                if (level == Levels.L4) {
                    return 0;
                }
                break;
            }
            case C: {
                if (level == Levels.L4 || level == Levels.L6) {
                    return 0;
                }
                break;
            }
        }

        return cell.getTotalBytes();
    }

    public boolean connectTransmitter(final ForgeDirection side, final Levels level) {
        ItemStack stackInSlot = driveInv.getStackInSlot(0);
        if (stackInSlot == null || stackInSlot.stackSize <= 0 || !(stackInSlot.getItem() instanceof ECalculatorCell)) {
            return false;
        }
        ECalculatorCell cell = (ECalculatorCell) stackInSlot.getItem();

        DriveStorageLevel cellLevel = cell.getLevel();
        switch (cellLevel) {
            case B: {
                if (level == Levels.L4) {
                    return false;
                }
                break;
            }
            case C: {
                if (level == Levels.L4 || level == Levels.L6) {
                    return false;
                }
                break;
            }
        }

        if (this.connectedSide != side) {
            this.connectedSide = side;
            markForUpdateSync();
        }
        return true;
    }

    public void disconnectTransmitter() {
        if (this.connectedSide != null) {
            this.connectedSide = null;
            markForUpdateSync();
        }
    }


    public void onDisassembled() {
        super.onDisassembled();
        disconnectTransmitter();
    }


    public void onAssembled() {
        super.onAssembled();
    }

    public AppEngInternalInventory getDriveInv() {
        return driveInv;
    }

    public ForgeDirection getConnectedSide() {
        return connectedSide;
    }



    public void readCustomNBT(final NBTTagCompound tag) {
        super.readCustomNBT(tag);

        final NBTTagCompound opt = tag.getCompoundTag("driveInv");
        for (int x = 0; x < driveInv.getSlots(); x++) {
            final NBTTagCompound item = opt.getCompoundTag("item" + x);
            ItemHandlerUtil.setStackInSlot(driveInv, x, stackFromNBT(item));
        }
        if (tag.hasKey("connectedSide")) {
            this.connectedSide = ForgeDirection.VALID_DIRECTIONS[tag.getByte("connectedSide")];
        } else {
            this.connectedSide = null;
        }

        if (FMLCommonHandler.instance().getEffectiveSide().isClient()) {
            notifyUpdate();
        }
    }


    public void writeCustomNBT(final NBTTagCompound tag) {
        super.writeCustomNBT(tag);

        final NBTTagCompound opt = new NBTTagCompound();
        for (int x = 0; x < driveInv.getSlots(); x++) {
            final NBTTagCompound itemNBT = new NBTTagCompound();
            final ItemStack is = driveInv.getStackInSlot(x);
            if (is != null && is.stackSize > 0) {
                stackWriteToNBT(is, itemNBT);
            }
            opt.setTag("item" + x, itemNBT);
        }
        tag.setTag("driveInv", opt);

        if (this.connectedSide != null) {
            tag.setByte("connectedSide", (byte) this.connectedSide.ordinal());
        }
    }


    public void saveChanges() {
        markDirty();
    }


    public void notifyUpdate() {
        super.notifyUpdate();
        if (worldObj == null) {
            return;
        }
        worldObj.notifyBlocksOfNeighborChange(xCoord, yCoord, zCoord, worldObj.getBlock(xCoord, yCoord, zCoord));
    }


    public void markDirty() {
        markChunkDirty();
    }

    private static class CellInvFilter implements IAEItemFilter {

        private static final CellInvFilter INSTANCE = new CellInvFilter();


        public boolean allowExtract(IItemHandler inv, int slot, int amount) {
            return true;
        }


        public boolean allowInsert(IItemHandler inv, int slot, ItemStack stack) {
            return stack != null && stack.stackSize > 0 && stack.getItem() instanceof ECalculatorCell;
        }

    }

}
