package github.kasuminova.ecoaeextension.common.estorage;

import appeng.api.config.AccessRestriction;
import appeng.api.config.Actionable;
import appeng.api.config.FuzzyMode;
import appeng.api.config.IncludeExclude;
import appeng.api.networking.security.BaseActionSource;
import appeng.api.storage.ICellInventory;
import appeng.api.storage.ICellInventoryHandler;
import appeng.api.storage.StorageChannel;
import appeng.api.storage.data.IAEStack;
import appeng.api.storage.data.IItemList;

public class EStorageCellInventoryHandler<T extends IAEStack<T>> implements ICellInventoryHandler<T> {
    private final ICellInventory<T> cellInv;
    private final StorageChannel channel;
    private final AccessRestriction access;

    public EStorageCellInventoryHandler(ICellInventory<T> cellInv, StorageChannel channel) {
        this(cellInv, channel, AccessRestriction.READ_WRITE);
    }

    public EStorageCellInventoryHandler(ICellInventory<T> cellInv, StorageChannel channel, AccessRestriction access) {
        this.cellInv = cellInv;
        this.channel = channel;
        this.access = access;
    }

    @Override
    public ICellInventory<T> getCellInv() {
        return cellInv;
    }

    @Override
    public boolean isPreformatted() {
        return false;
    }

    @Override
    public boolean isFuzzy() {
        return cellInv.getFuzzyMode() != FuzzyMode.IGNORE_ALL;
    }

    @Override
    public IncludeExclude getIncludeExcludeMode() {
        return IncludeExclude.WHITELIST;
    }

    @Override
    public AccessRestriction getAccess() {
        return access;
    }

    @Override
    public boolean isPrioritized(T input) {
        return false;
    }

    @Override
    public boolean canAccept(T input) {
        return true;
    }

    @Override
    public int getPriority() {
        return 0;
    }

    @Override
    public int getSlot() {
        return 0;
    }

    @Override
    public boolean validForPass(int pass) {
        return true;
    }

    @Override
    public T injectItems(T input, Actionable type, BaseActionSource src) {
        return cellInv.injectItems(input, type, src);
    }

    @Override
    public T extractItems(T request, Actionable type, BaseActionSource src) {
        return cellInv.extractItems(request, type, src);
    }

    @Override
    public IItemList<T> getAvailableItems(IItemList<T> out, int iteration) {
        return cellInv.getAvailableItems(out, iteration);
    }

    @Override
    public T getAvailableItem(T request, int iteration) {
        return cellInv.getAvailableItem(request, iteration);
    }

    @Override
    public StorageChannel getChannel() {
        return channel;
    }
}
