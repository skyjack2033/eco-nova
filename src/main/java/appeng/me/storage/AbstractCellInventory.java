package appeng.me.storage;

import appeng.api.storage.ICellInventory;
import appeng.api.storage.ISaveProvider;
import appeng.api.storage.data.IAEStack;
import appeng.api.storage.data.IItemList;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

/**
 * Stub for 1.7.10 port
 */
public abstract class AbstractCellInventory<T extends IAEStack<T>> implements ICellInventory<T> {

    protected IItemList<T> cellItems;
    protected ISaveProvider container;
    protected int itemsPerByte = 1;
    private int maxItemTypes;
    private boolean isPersisted;
    private short storedItemTypes;
    private long storedItemCount;
    private NBTTagCompound tagCompound;

    protected AbstractCellInventory(final ItemStack o, final ISaveProvider container) {
        this.container = container;
    }

    protected AbstractCellInventory(final Object cellType, final ItemStack o, final ISaveProvider container) {
        this(o, container);
    }

    public void saveChanges() {
        if (this.container != null) {
            this.container.saveChanges(this);
        }
    }

    public long getFreeBytes() {
        return 0;
    }

    public int getBytesPerType() {
        return 0;
    }

    public long getStoredItemCount() {
        return 0;
    }

    public long getStoredItemTypes() {
        return 0;
    }

    public ItemStack getItemStack() {
        return null;
    }

    protected boolean loadCellItem(NBTTagCompound compoundTag, long stackSize) {
        return true;
    }

    public boolean canHoldNewItem() {
        return false;
    }
}
