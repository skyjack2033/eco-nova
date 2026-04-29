package appeng.core.features.registries.cell;

import net.minecraft.item.ItemStack;
import appeng.api.storage.IAccessType;
import appeng.api.storage.ICellHandler;
import appeng.api.storage.ICellInventoryHandler;
import appeng.api.storage.ICellRegistry;
import appeng.api.storage.StorageChannel;
import appeng.api.storage.ISaveProvider;

import java.util.ArrayList;
import java.util.List;

public class CellRegistry {

    private List<ICellHandler> handlers = new ArrayList<>();

    public CellRegistry(ICellHandler handler) {
        this.handlers.add(handler);
    }

    public void addCellHandler(ICellHandler handler) {
    }

    public ICellInventoryHandler getCellInventory(ItemStack stack, ISaveProvider saveProvider, StorageChannel channel) {
        return null;
    }

    public ICellInventoryHandler getCellInventory(ItemStack stack, ISaveProvider saveProvider, StorageChannel channel, IAccessType accessType) {
        return null;
    }

    public ICellHandler getHandler(ItemStack stack) {
        return null;
    }

    public List<ICellHandler> getHandlerNames() {
        return new ArrayList<ICellHandler>();
    }

    public int getHandlerCount() {
        return 0;
    }

    public List<ICellHandler> handlers() {
        return handlers;
    }
}
