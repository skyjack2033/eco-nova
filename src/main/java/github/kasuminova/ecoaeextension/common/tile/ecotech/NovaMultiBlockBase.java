package github.kasuminova.ecoaeextension.common.tile.ecotech;

import com.gtnewhorizon.structurelib.alignment.enumerable.ExtendedFacing;
import com.gtnewhorizon.structurelib.structure.IStructureDefinition;
import github.kasuminova.ecoaeextension.common.registry.RegistryBlocks;
import github.kasuminova.ecoaeextension.common.util.BlockPos;
import net.minecraft.block.Block;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

import java.util.HashMap;
import java.util.Map;

/**
 * Replacement for {@code TileMultiblockMachineController} — uses StructureLib for structure validation
 * instead of the custom MMCE StructureDefinition system.
 */
public abstract class NovaMultiBlockBase extends NovaTileBase {

    protected int lastStructureCheckTick = -1;
    protected int structureCheckDelay = 40;
    protected int structureCheckCounter = 0;
    protected int maxStructureCheckDelay = 100;

    protected boolean structureFormed = false;
    protected ForgeDirection controllerRotation = ForgeDirection.NORTH;
    protected Object tickExecutor = null;

    protected Map<Class<?>, java.util.List<Object>> foundComponents = new HashMap<>();

    public enum WorkMode {
        SYNC,
        SEMI_SYNC
    }
    protected WorkMode workMode = WorkMode.SYNC;

    /** Length of the dynamic/worker section (set by structure check). */
    protected int workerLength = 0;

    // ---- Structure check ----

    /**
     * Subclasses return the StructureLib definition for their machine shape.
     */
    protected abstract IStructureDefinition<? extends NovaMultiBlockBase> getStructureDefinition();

    /**
     * Subclasses return the shape name (e.g. "l4", "l6", "l9") based on controller tier.
     */
    protected abstract String getShapeName();

    public boolean doStructureCheck() {
        IStructureDefinition<NovaMultiBlockBase> def = getStructureDef();
        if (worldObj == null || def == null) return false;

        BlockPos controllerPos = getPos();
        ExtendedFacing facing = ExtendedFacing.of(controllerRotation);

        return def.check((NovaMultiBlockBase) this, getShapeName(), worldObj, facing,
                controllerPos.getX(), controllerPos.getY(), controllerPos.getZ(),
                0, 0, 0, false);
    }

    @SuppressWarnings("unchecked")
    protected IStructureDefinition<NovaMultiBlockBase> getStructureDef() {
        return (IStructureDefinition<NovaMultiBlockBase>) getStructureDefinition();
    }

    // ---- Structure formed state ----

    public boolean isStructureFormed() {
        return structureFormed;
    }

    public void setStructureFormed(boolean formed) {
        if (this.structureFormed != formed) {
            this.structureFormed = formed;
            notifyStructureFormedState(formed);
        }
    }

    public void notifyStructureFormedState(boolean formed) {
    }

    // ---- Rotation ----

    public ForgeDirection getControllerRotation() {
        return controllerRotation;
    }

    public void setControllerRotation(ForgeDirection rotation) {
        this.controllerRotation = rotation;
    }

    // ---- Component scanning ----

    protected abstract void updateComponents();

    /**
     * Return info for a named dynamic pattern. Used by subsystems to get the worker section length.
     * Override per controller to provide subsystem-specific dynamic pattern sizes.
     */
    public DynamicPatternInfo getDynamicPattern(String name) {
        if ("workers".equals(name)) {
            return new DynamicPatternInfo(workerLength);
        }
        return null;
    }

    public int getWorkerLength() {
        return workerLength;
    }

    public void setWorkerLength(int length) {
        this.workerLength = length;
    }

    // ---- NBT persistence ----

    protected void readMachineNBT(NBTTagCompound compound) {
        structureFormed = compound.getBoolean("structureFormed");
        workerLength = compound.getInteger("workerLength");
        if (compound.hasKey("controllerRotation")) {
            controllerRotation = ForgeDirection.getOrientation(compound.getByte("controllerRotation"));
        }
    }

    protected void writeMachineNBT(NBTTagCompound compound) {
        compound.setBoolean("structureFormed", structureFormed);
        compound.setInteger("workerLength", workerLength);
        if (controllerRotation != null) {
            compound.setByte("controllerRotation", (byte) controllerRotation.ordinal());
        }
    }

    @Override
    public void readCustomNBT(NBTTagCompound compound) {
        readMachineNBT(compound);
    }

    @Override
    public void writeCustomNBT(NBTTagCompound compound) {
        writeMachineNBT(compound);
    }

    // ---- Helpers ----

    /**
     * Check if a block at a world position is an instance of the given class.
     */
    protected boolean isBlockInstance(World world, BlockPos pos, Class<? extends Block> clazz) {
        return clazz.isInstance(world.getBlock(pos.getX(), pos.getY(), pos.getZ()));
    }

    /**
     * Register a found component by type for later processing.
     */
    @SuppressWarnings("unchecked")
    protected <T> void addFoundComponent(Class<T> type, T component) {
        foundComponents.computeIfAbsent(type, k -> new java.util.ArrayList<>()).add(component);
    }

    /**
     * Get all found components of a given type.
     */
    @SuppressWarnings("unchecked")
    protected <T> java.util.List<T> getFoundComponents(Class<T> type) {
        return (java.util.List<T>) foundComponents.getOrDefault(type, java.util.Collections.emptyList());
    }

    /**
     * Clear all found components (called before re-scanning).
     */
    protected void clearFoundComponents() {
        foundComponents.clear();
    }

    // ---- Simple Dynamic Pattern Info (replaces IDynamicPatternInfo) ----

    public static class DynamicPatternInfo {
        private final int size;
        private final int priority;

        public DynamicPatternInfo(int size) {
            this(size, 0);
        }

        public DynamicPatternInfo(int size, int priority) {
            this.size = size;
            this.priority = priority;
        }

        public int getSize() {
            return size;
        }

        public int getPriority() {
            return priority;
        }

        /** Returns an empty BlockPos map — override in subclass if needed. */
        public Map<BlockPos, Block> getTileBlocksMap() {
            return new HashMap<>();
        }
    }
}
