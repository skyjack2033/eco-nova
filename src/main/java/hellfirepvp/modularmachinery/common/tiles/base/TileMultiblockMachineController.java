package hellfirepvp.modularmachinery.common.tiles.base;

import github.kasuminova.ecoaeextension.common.util.BlockPos;
import github.kasuminova.mmce.common.helper.IDynamicPatternInfo;
import github.kasuminova.mmce.common.util.TimeRecorder;
import hellfirepvp.modularmachinery.common.machine.DynamicMachine;
import hellfirepvp.modularmachinery.common.util.MachinePattern;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraftforge.common.util.ForgeDirection;

import java.util.HashMap;
import java.util.Map;

public class TileMultiblockMachineController extends TileEntitySynchronized {

    // Structure check
    protected int lastStructureCheckTick = -1;
    protected int structureCheckDelay = 0;
    protected int structureCheckCounter = 0;
    protected int maxStructureCheckDelay = 100;

    // Controller state
    protected boolean structureFormed = false;
    protected ForgeDirection controllerRotation = ForgeDirection.NORTH;
    protected DynamicMachine foundMachine = null;
    protected MachinePattern foundPattern = null;
    protected Object tickExecutor = null;
    protected TimeRecorder timeRecorder = new TimeRecorder();

    // Work mode
    public enum WorkMode {
        SYNC,
        SEMI_SYNC
    }
    protected WorkMode workMode = WorkMode.SYNC;

    // Components
    protected Map<Object, Object> foundComponents = new HashMap<>();

    public TileMultiblockMachineController getController() {
        return this;
    }

    public Object getMachine() {
        return null;
    }

    public Object getFoundMachine() {
        return foundMachine;
    }

    public void setFoundMachine(DynamicMachine machine) {
        this.foundMachine = machine;
    }

    public boolean doStructureCheck() {
        return true;
    }

    public boolean isStructureFormed() {
        return structureFormed;
    }

    public void setStructureFormed(boolean formed) {
        this.structureFormed = formed;
    }

    public void markNoUpdateSync() {
        markDirty();
    }

    public IDynamicPatternInfo getDynamicPattern(String name) {
        return null;
    }

    public void notifyStructureFormedState(boolean formed) {
        if (worldObj == null) return;
        BlockPos pos = getPos();
        if (pos == null) return;
        // Stub for 1.7.10 port
    }

    protected void updateComponents() {
    }

    protected void readMachineNBT(net.minecraft.nbt.NBTTagCompound compound) {
    }
}
