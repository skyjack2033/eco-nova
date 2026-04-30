package github.kasuminova.ecoaeextension.common.estorage;

import appeng.api.AEApi;
import appeng.api.config.Actionable;
import appeng.api.exceptions.AppEngException;
import appeng.api.implementations.items.IStorageCell;
import appeng.api.networking.security.BaseActionSource;
import appeng.api.storage.ICellInventory;
import appeng.api.storage.ICellInventoryHandler;
import appeng.api.storage.IMEInventoryHandler;
import appeng.api.storage.ISaveProvider;
import appeng.api.storage.StorageChannel;
import appeng.api.storage.data.IAEFluidStack;
import appeng.api.storage.data.IAEItemStack;
import appeng.api.storage.data.IAEStack;
import appeng.api.storage.data.IItemList;
import appeng.core.AELog;
import appeng.me.storage.CellInventory;
import appeng.util.item.AEFluidStack;
import appeng.util.item.AEItemStack;
import github.kasuminova.ecoaeextension.ECOAEExtension;
import github.kasuminova.ecoaeextension.common.item.estorage.EStorageCell;


import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import cpw.mods.fml.relauncher.ReflectionHelper;

public class EStorageCellInventory<T extends IAEStack<T>> extends CellInventory<T> {
    public static final String ITEM_SLOT = "#";
    public static final String ITEM_SLOT_COUNT = "@";
    public static final String ITEM_TYPE_TAG = "it";
    public static final String ITEM_COUNT_TAG = "ic";

    private final EStorageCell<T> cellType;
    private final StorageChannel channel;

    @Override
    public String getOreFilter() {
        return null;
    }

    @Override
    protected String getStackTypeTag() {
        return ITEM_TYPE_TAG;
    }

    @Override
    protected String getStackCountTag() {
        return ITEM_COUNT_TAG;
    }

    @Override
    public int getStatusForCell() {
        return 0;
    }

    @Override
    public long getRemainingItemsCountDist(T stack) {
        return 0;
    }

    @SuppressWarnings("deprecation")
    protected EStorageCellInventory(final EStorageCell<T> cellType, final ItemStack o, final ISaveProvider container) throws AppEngException {
        super(o, container);
        try {
            ReflectionHelper.setPrivateValue(CellInventory.class, this, cellType.getTotalTypes(o), "maxTypes");
        } catch (Exception e) {}
        this.cellType = cellType;
        this.channel = cellType.getChannel();
    }

    @SuppressWarnings({"unchecked", "rawtypes"})
    public static <T extends IAEStack<T>> ICellInventory<T> createInventory(final ItemStack o, final ISaveProvider container) {
        try {
            if (o == null) {
                throw new AppEngException("ItemStack was used as a cell, but was not a cell!");
            }

            final Item type = o.getItem();
            if (!(type instanceof EStorageCell cellType)) {
                throw new AppEngException("ItemStack was used as a cell, but was not a cell!");
            }

            if (!cellType.isStorageCell(o)) {
                throw new AppEngException("ItemStack was used as a cell, but was not a cell!");
            }

            return new EStorageCellInventory<T>(cellType, o, container);
        } catch (final AppEngException e) {
            ECOAEExtension.log.error(e.toString());
            return null;
        }
    }

    private boolean isStorageCell(final T input) {
        if (input instanceof IAEItemStack) {
            final IStorageCell type = getStorageCell(((IAEItemStack) input).getItemStack());

            return type != null && !type.storableInStorageCell();
        }

        return false;
    }

    private static IStorageCell getStorageCell(final ItemStack input) {
        if (input != null) {
            final Item type = input.getItem();

            if (type instanceof IStorageCell) {
                return (IStorageCell) type;
            }
        }

        return null;
    }

    @Override
    @SuppressWarnings("unchecked")
    protected T readStack(NBTTagCompound compoundTag) {
        IAEStack<?> stack;
        if (this.channel == StorageChannel.ITEMS) {
            stack = AEItemStack.loadItemStackFromNBT(compoundTag);
        } else if (this.channel == StorageChannel.FLUIDS) {
            stack = AEFluidStack.loadFluidStackFromNBT(compoundTag);
        } else {
            return null;
        }
        return (T) stack;
    }

    @SuppressWarnings({"rawtypes", "unchecked"})
    private static boolean isCellEmpty(ICellInventory inv) {
        if (inv != null) {
            IItemList items = inv.getAvailableItems(inv.getChannel().createList());
            long totalSize = 0;
            for (Object obj : items) {
                if (obj instanceof IAEStack) {
                    totalSize += ((IAEStack<?>) obj).getStackSize();
                }
            }
            return totalSize <= 0;
        }
        return true;
    }

    protected IItemList<T> getCellItems() {
        if (this.cellStacks.size() == 0 && this.getStoredItemTypes() > 0) {
            this.loadCellStacks();
        }
        return this.cellStacks;
    }

    public void persist() {
        NBTTagCompound tagCompound = this.tagCompound;

        long itemCount = 0;

        // add new pretty stuff...
        int x = 0;
        for (final T v : this.cellStacks) {
            itemCount += v.getStackSize();

            final NBTTagCompound g = new NBTTagCompound();
            v.writeToNBT(g);
            tagCompound.setTag(ITEM_SLOT + x, g);
            tagCompound.setLong(ITEM_SLOT_COUNT + x, v.getStackSize());

            x++;
        }

        final long oldStoredItems = this.getStoredItemTypes();

        // Now storedTypes is protected in CellInventory
        this.storedTypes = (short) this.cellStacks.size();

        if (itemCount <= 0) {
            tagCompound.removeTag(ITEM_TYPE_TAG);
        } else {
            tagCompound.setShort(ITEM_TYPE_TAG, this.storedTypes);
        }

        this.storedCount = itemCount;
        if (itemCount == 0) {
            tagCompound.removeTag(ITEM_COUNT_TAG);
        } else {
            tagCompound.setLong(ITEM_COUNT_TAG, itemCount);
        }

        // clean any old crusty stuff...
        for (; x >= oldStoredItems && x < this.getMaxTypes(); x++) {
            tagCompound.removeTag(ITEM_SLOT + x);
            tagCompound.removeTag(ITEM_SLOT_COUNT + x);
        }
    }

    @Override
    public T injectItems(T input, Actionable mode, BaseActionSource src) {
        if (input == null) {
            return null;
        }
        if (input.getStackSize() == 0) {
            return null;
        }

        if (this.cellType.isBlackListed(this.getItemStack(), input)) {
            return input;
        }
        // This is slightly hacky as it expects a read-only access, but fine for now.
        // TODO: Guarantee a read-only access. E.g. provide an isEmpty() method and ensure CellInventory does not write
        // any NBT data for empty cells instead of relying on an empty IItemContainer
        if (isStorageCell(input)) {
            ItemStack cellStack = ((IAEItemStack) input).getItemStack();
            IMEInventoryHandler<?> rawHandler = AEApi.instance().registries().cell().getCellInventory(cellStack, null, this.cellType.getChannel());
            ICellInventoryHandler<?> cellInvHandler = rawHandler instanceof ICellInventoryHandler ? (ICellInventoryHandler<?>) rawHandler : null;
            if (cellInvHandler != null && !isCellEmpty(cellInvHandler.getCellInv())) {
                return input;
            }
        }

        final T l = this.getCellItems().findPrecise(input);
        if (l != null) {
            final long remainingItemCount = this.getRemainingItemCount();
            if (remainingItemCount <= 0) {
                return input;
            }

            if (input.getStackSize() > remainingItemCount) {
                final T r = input.copy();
                r.setStackSize(r.getStackSize() - remainingItemCount);
                if (mode == Actionable.MODULATE) {
                    l.setStackSize(l.getStackSize() + remainingItemCount);
                    this.storedCount += remainingItemCount;
                    this.saveChangesES();
                }
                return r;
            } else {
                if (mode == Actionable.MODULATE) {
                    long prev = l.getStackSize();
                    l.setStackSize(l.getStackSize() + input.getStackSize());
                    if (prev == 0) {
                        this.storedTypes++;
                    }
                    this.storedCount += input.getStackSize();
                    this.saveChangesES();
                }
                return null;
            }
        }

        // room for new type, and for at least one item!
        if (this.canHoldNewItem()) {
            final long remainingItemCount = this.getRemainingItemCount() - (long) this.getBytesPerType() * 8;
            if (remainingItemCount > 0) {
                if (input.getStackSize() > remainingItemCount) {
                    final T toReturn = input.copy();
                    toReturn.setStackSize(input.getStackSize() - remainingItemCount);
                    if (mode == Actionable.MODULATE) {
                        final T toWrite = input.copy();
                        toWrite.setStackSize(remainingItemCount);

                        this.cellStacks.add(toWrite);

                        this.storedTypes++;
                        this.storedCount += remainingItemCount;
                        this.saveChangesES();
                    }
                    return toReturn;
                }

                if (mode == Actionable.MODULATE) {
                    this.cellStacks.add(input);

                    this.storedTypes++;
                    this.storedCount += input.getStackSize();
                    this.saveChangesES();
                }

                return null;
            }
        }

        return input;
    }

    @Override
    public T extractItems(T request, Actionable mode, BaseActionSource src) {
        if (request == null) {
            return null;
        }

        final long size =  request.getStackSize();

        T results = null;

        final T l = this.getCellItems().findPrecise(request);
        if (l != null) {
            results = l.copy();

            if (l.getStackSize() <= size) {
                results.setStackSize(l.getStackSize());
                if (mode == Actionable.MODULATE && l.getStackSize() > 0) {
                    l.setStackSize(0);
                    this.saveChanges();
                }
            } else {
                results.setStackSize(size);
                if (mode == Actionable.MODULATE) {
                    l.setStackSize(l.getStackSize() - size);
                    this.storedCount -= size;
                    this.saveChangesES();
                }
            }
        }

        return results;
    }

    @Override
    public StorageChannel getChannel() {
        return this.channel;
    }

    /**
     * 类似 {@link CellInventory#saveChanges()}，但是不对 cellStacks 进行完全扫描。
     */
    protected void saveChangesES() {
        if (this.container != null) {
            this.container.saveChanges(this);
        } else {
            saveChanges();
        }
    }

    @SuppressWarnings("unchecked")
    protected boolean loadCellItem(NBTTagCompound compoundTag, long stackSize) {
        // Now load the item stack
        final T t;
        try {
            IAEStack<?> stack;
            if (this.channel == StorageChannel.ITEMS) {
                stack = AEItemStack.loadItemStackFromNBT(compoundTag);
            } else if (this.channel == StorageChannel.FLUIDS) {
                stack = AEFluidStack.loadFluidStackFromNBT(compoundTag);
            } else {
                AELog.warn("Unknown storage channel for cell item " + compoundTag);
                return false;
            }
            t = (T) stack;
            if (t == null) {
                AELog.warn("Removing item " + compoundTag + " from storage cell because the associated item type couldn't be found.");
                return false;
            }
        } catch (Throwable ex) {
            AELog.warn(ex, "Removing item " + compoundTag + " from storage cell because loading the ItemStack crashed.");
            return false;
        }

        t.setStackSize(stackSize);
        t.setCraftable(false);

        if (stackSize > 0) {
            this.cellStacks.add(t);
        }

        return true;
    }

    @Override
    public long getUsedBytes() {
        final long bytesForItemCount = (this.getStoredItemCount() + this.getUnusedItemCount()) / ((long) 8 * cellType.getByteMultiplier());
        return this.getStoredItemTypes() * this.getBytesPerType() + bytesForItemCount;
    }

    @Override
    public long getRemainingItemCount() {
        final long remaining = this.getFreeBytes() * ((long) 8 * cellType.getByteMultiplier()) + this.getUnusedItemCount();
        return remaining > 0 ? remaining : 0;
    }

    @Override
    public int getUnusedItemCount() {
        final int div = (int) (this.getStoredItemCount() % (8 * cellType.getByteMultiplier()));

        if (div == 0) {
            return 0;
        }

        return (8 * cellType.getByteMultiplier()) - div;
    }

}
