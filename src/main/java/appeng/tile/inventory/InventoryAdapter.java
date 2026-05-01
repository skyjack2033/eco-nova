package appeng.tile.inventory;

import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraftforge.items.IItemHandler;

/**
 * Wrapper to make IInventory work as IItemHandler for compatibility.
 * AE2 rv3-beta-690 uses IInventory internally.
 */
public class InventoryAdapter implements IItemHandler {

    private final IInventory inventory;

    public InventoryAdapter(IInventory inventory) {
        this.inventory = inventory;
    }

    public IInventory getInventory() {
        return inventory;
    }

    @Override
    public int getSlots() {
        return inventory.getSizeInventory();
    }

    @Override
    public ItemStack getStackInSlot(int slot) {
        if (slot < 0 || slot >= getSlots()) {
            return null;
        }
        return inventory.getStackInSlot(slot);
    }

    @Override
    public ItemStack insertItem(int slot, ItemStack stack, boolean simulate) {
        if (stack == null || stack.stackSize <= 0) {
            return null;
        }
        if (slot < 0 || slot >= getSlots()) {
            return stack;
        }
        ItemStack existing = inventory.getStackInSlot(slot);
        if (existing == null) {
            if (!simulate) {
                ItemStack toInsert = stack.copy();
                toInsert.stackSize = Math.min(toInsert.stackSize, inventory.getInventoryStackLimit());
                inventory.setInventorySlotContents(slot, toInsert);
                inventory.markDirty();
            }
            return null;
        }
        if (existing.isItemEqual(stack) && ItemStack.areItemStackTagsEqual(existing, stack)) {
            int space = inventory.getInventoryStackLimit() - existing.stackSize;
            if (space > 0) {
                int toAdd = Math.min(space, stack.stackSize);
                if (!simulate && toAdd > 0) {
                    existing.stackSize += toAdd;
                    inventory.markDirty();
                }
                ItemStack remaining = stack.copy();
                remaining.stackSize -= toAdd;
                return remaining.stackSize > 0 ? remaining : null;
            }
        }
        return stack;
    }

    @Override
    public ItemStack extractItem(int slot, int amount, boolean simulate) {
        if (amount <= 0 || slot < 0 || slot >= getSlots()) {
            return null;
        }
        ItemStack existing = inventory.getStackInSlot(slot);
        if (existing == null || existing.stackSize <= 0) {
            return null;
        }
        int toExtract = Math.min(amount, existing.stackSize);
        if (simulate) {
            ItemStack result = existing.copy();
            result.stackSize = toExtract;
            return result;
        }
        if (toExtract >= existing.stackSize) {
            inventory.setInventorySlotContents(slot, null);
            inventory.markDirty();
            return existing;
        }
        ItemStack result = existing.copy();
        result.stackSize = toExtract;
        existing.stackSize -= toExtract;
        inventory.markDirty();
        return result;
    }

    @Override
    public int getSlotLimit(int slot) {
        return inventory.getInventoryStackLimit();
    }

    @Override
    public boolean isItemValid(int slot, ItemStack stack) {
        return inventory.isItemValidForSlot(slot, stack);
    }

    public void setStackInSlot(int slot, ItemStack stack) {
        if (slot < 0 || slot >= getSlots()) {
            return;
        }
        inventory.setInventorySlotContents(slot, stack);
        inventory.markDirty();
    }
}
