package appeng.me.cluster.implementations;

import appeng.api.networking.IGrid;
import appeng.api.networking.IGridNode;
import appeng.api.networking.crafting.ICraftingCPU;
import appeng.api.networking.security.BaseActionSource;
import appeng.api.storage.IMEMonitorHandlerReceiver;
import appeng.api.util.WorldCoord;
import appeng.tile.inventory.AppEngInternalInventory;
import java.util.List;

public class CraftingCPUCluster implements ICraftingCPU {
    public CraftingCPUCluster() {}
    public CraftingCPUCluster(WorldCoord a, WorldCoord b) {}

    public List<IGridNode> getNodes() { return java.util.Collections.emptyList(); }
    public boolean isDestroyed() { return false; }
    public long getAvailableStorage() { return 0; }
    public int getCoProcessors() { return 0; }
    public String getName() { return ""; }
    public IGrid getGrid() { return null; }
    public AppEngInternalInventory getInventory() { return null; }
    public boolean isBusy() { return false; }
    public void cancel() {}
    public long getUsedStorage() { return 0; }
    public int getUsedCoprocessors() { return 0; }
    public void updateCraftingLogic(IGrid grid) {}
    public long getElapsedTime() { return 0; }
    public long getRemainingTime() { return 0; }
    public BaseActionSource getActionSource() { return null; }
    public void destroy() {}
    public void addListener(IMEMonitorHandlerReceiver listener, Object filter) {}
    public void removeListener(IMEMonitorHandlerReceiver listener) {}
    public void readFromNBT(net.minecraft.nbt.NBTTagCompound compound) {}
    public void writeToNBT(net.minecraft.nbt.NBTTagCompound compound) {}
}
