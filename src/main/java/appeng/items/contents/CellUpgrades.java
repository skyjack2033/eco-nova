package appeng.items.contents;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;

public class CellUpgrades implements IInventory {
    private final ItemStack cell;
    private final int upgrades;

    public CellUpgrades(ItemStack cell, int upgrades) {
        this.cell = cell;
        this.upgrades = upgrades;
    }

    @Override
    public int getSizeInventory() { return upgrades; }

    @Override
    public ItemStack getStackInSlot(int slot) { return null; }

    @Override
    public ItemStack decrStackSize(int slot, int amount) { return null; }

    @Override
    public ItemStack getStackInSlotOnClosing(int slot) { return null; }

    @Override
    public void setInventorySlotContents(int slot, ItemStack stack) { }

    @Override
    public String getInventoryName() { return "cellupgrades"; }

    @Override
    public boolean hasCustomInventoryName() { return false; }

    @Override
    public int getInventoryStackLimit() { return 1; }

    @Override
    public void markDirty() { }

    @Override
    public boolean isUseableByPlayer(EntityPlayer player) { return true; }

    @Override
    public void openInventory() { }

    @Override
    public void closeInventory() { }

    @Override
    public boolean isItemValidForSlot(int slot, ItemStack stack) { return false; }
}
