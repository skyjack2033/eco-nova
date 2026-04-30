package appeng.me.storage;

import appeng.api.config.AccessRestriction;
import appeng.api.config.Actionable;
import appeng.api.config.IncludeExclude;
import appeng.api.networking.security.BaseActionSource;
import appeng.api.storage.ICellInventory;
import appeng.api.storage.ICellInventoryHandler;
import appeng.api.storage.StorageChannel;
import appeng.api.storage.data.IAEStack;
import appeng.api.storage.data.IItemList;

@SuppressWarnings("rawtypes")
public class BasicCellInventoryHandler implements ICellInventoryHandler {
    public BasicCellInventoryHandler(Object cellInventory, Object channel) {}

    public IncludeExclude getIncludeExcludeMode() {
        return IncludeExclude.WHITELIST;
    }

    public boolean isFuzzy() {
        return false;
    }

    public boolean isPreformatted() {
        return false;
    }

    public ICellInventory getCellInv() {
        return null;
    }

    public boolean validForPass(int pass) {
        return true;
    }

    public int getSlot() {
        return 0;
    }

    public int getPriority() {
        return 0;
    }

    public AccessRestriction getAccess() {
        return AccessRestriction.READ_WRITE;
    }

    public boolean isPrioritized(IAEStack input) {
        return false;
    }

    public boolean canAccept(IAEStack input) {
        return true;
    }

    public IAEStack injectItems(IAEStack input, Actionable type, BaseActionSource src) {
        return input;
    }

    public IAEStack extractItems(IAEStack request, Actionable type, BaseActionSource src) {
        return null;
    }

    public StorageChannel getChannel() {
        return null;
    }
}
