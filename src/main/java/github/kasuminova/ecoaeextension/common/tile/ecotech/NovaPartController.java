package github.kasuminova.ecoaeextension.common.tile.ecotech;

import github.kasuminova.ecoaeextension.ECOAEExtension;
import github.kasuminova.ecoaeextension.common.util.BlockPos;
import github.kasuminova.ecoaeextension.common.util.EPartMap;
import net.minecraft.block.Block;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;
import cpw.mods.fml.common.FMLCommonHandler;

import java.util.concurrent.Executors;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;

/**
 * Replacement for {@code EPartController} — manages part assembly/disassembly,
 * controller tick lifecycle, and component discovery. Uses {@link NovaMultiBlockBase}
 * instead of the MMCE base class hierarchy.
 */
public abstract class NovaPartController<P extends EPart<?>> extends NovaMultiBlockBase {

    protected final EPartMap<P> parts = new EPartMap<>();
    protected boolean assembled = false;
    protected int ticksExisted = 0;

    private static final ExecutorService ASYNC_EXECUTOR = Executors.newCachedThreadPool(r -> {
        Thread t = new Thread(r, "NovaEng-AsyncTick");
        t.setDaemon(true);
        return t;
    });

    private Future<?> asyncTaskFuture;

    // ---- Tick lifecycle ----

    public void doControllerTick() {
        boolean structureValid = this.doStructureCheck();
        if (structureValid) {
            this.setStructureFormed(true);
        } else {
            this.setStructureFormed(false);
        }

        if (!this.isStructureFormed()) {
            disassemble();
            return;
        }
        if (!assemble()) {
            return;
        }

        if (onSyncTick()) {
            asyncTaskFuture = ASYNC_EXECUTOR.submit(this::onAsyncTick);
        }
    }

    public boolean checkControllerShared() {
        BlockPos pos = getPos();
        World world = getWorld();
        TileEntity up = world.getTileEntity(pos.getX(), pos.getY() + 2, pos.getZ());
        if (up instanceof NovaPartController<?> partController) {
            if (partController.getControllerBlock() == getControllerBlock()) {
                return true;
            }
        }
        TileEntity down = world.getTileEntity(pos.getX(), pos.getY() - 2, pos.getZ());
        if (down instanceof NovaPartController<?> partController) {
            return partController.getControllerBlock() == getControllerBlock();
        }
        return false;
    }

    protected abstract boolean onSyncTick();

    protected void onAsyncTick() {
    }

    // ---- Component scanning ----

    @Override
    protected void updateComponents() {
        clearParts();
        clearFoundComponents();

        // Subclasses should override to add component scanning using
        // addFoundComponent() for each discovered sub-tile

        BlockPos pos = getPos();
        World world = getWorld();

        // Scan all parts using StructureLib-compatible iteration
        scanComponents(world, pos);
    }

    /**
     * Override in subclasses to scan for specific components.
     * Default implementation scans a bounding box around the controller.
     */
    protected void scanComponents(World world, BlockPos controllerPos) {
        int maxLen = getWorkerLength();
        if (maxLen <= 0) maxLen = 12;

        int dx = 0, dz = 0;
        ForgeDirection dir = controllerRotation;
        if (dir == ForgeDirection.NORTH) dz = -maxLen;
        else if (dir == ForgeDirection.SOUTH) dz = maxLen;
        else if (dir == ForgeDirection.WEST) dx = -maxLen;
        else if (dir == ForgeDirection.EAST) dx = maxLen;

        int startX = Math.min(controllerPos.getX(), controllerPos.getX() + dx);
        int endX = Math.max(controllerPos.getX(), controllerPos.getX() + dx);
        int startZ = Math.min(controllerPos.getZ(), controllerPos.getZ() + dz);
        int endZ = Math.max(controllerPos.getZ(), controllerPos.getZ() + dz);
        int startY = controllerPos.getY() - 1;
        int endY = controllerPos.getY() + 1;

        for (int x = startX; x <= endX; x++) {
            for (int y = startY; y <= endY; y++) {
                for (int z = startZ; z <= endZ; z++) {
                    if (!world.blockExists(x, y, z)) continue;
                    TileEntity te = world.getTileEntity(x, y, z);
                    if (te instanceof AbstractEPart<?>) {
                        try {
                            @SuppressWarnings("unchecked")
                            P part = (P) te;
                            if (part.getController() != this) {
                                ((EPart) part).setController(this);
                            }
                            parts.addPart(part);
                            onAddPart(part);
                        } catch (ClassCastException e) {
                            ECOAEExtension.log.error("Invalid EPart found at ({}, {}, {}) !", x, y, z);
                            ECOAEExtension.log.error(e);
                        }
                    }
                }
            }
        }
    }

    protected abstract void onAddPart(P part);

    // ---- Structure check scheduling ----

    protected boolean canCheckStructure() {
        if (lastStructureCheckTick == -1 || (isStructureFormed() && !assembled)) {
            return true;
        }
        if (ticksExisted % 40 == 0) {
            return true;
        }
        if (isStructureFormed()) {
            // Re-check formed structures less frequently
            if (structureCheckCounter == 0 && ticksExisted % 100 == 0) return true;
            structureCheckCounter = (structureCheckCounter + 1) % 10;
            return false;
        }
        return ticksExisted % Math.min(structureCheckDelay + this.structureCheckCounter * 5, maxStructureCheckDelay) == 0;
    }

    // ---- Assembly / Disassembly ----

    protected boolean assemble() {
        if (assembled) {
            return true;
        }
        if (checkControllerShared()) {
            disassemble();
            return false;
        }
        assembled = true;
        parts.assemble(this);
        return true;
    }

    protected void disassemble() {
        if (!assembled) {
            return;
        }
        assembled = false;
        parts.disassemble();
    }

    protected void clearParts() {
        parts.clear();
    }

    public EPartMap<P> getParts() {
        return parts;
    }

    public boolean isAssembled() {
        return assembled;
    }

    // ---- Rotation ----

    protected void checkRotation() {
        if (controllerRotation != null) {
            return;
        }
        BlockPos p = getPos();
        Block block = getWorld().getBlock(p.getX(), p.getY(), p.getZ());
        if (getControllerBlock().isInstance(block)) {
            int meta = getWorld().getBlockMetadata(p.getX(), p.getY(), p.getZ());
            if (meta >= 0 && meta < ForgeDirection.VALID_DIRECTIONS.length) {
                controllerRotation = ForgeDirection.VALID_DIRECTIONS[meta];
            } else {
                controllerRotation = ForgeDirection.NORTH;
            }
        } else {
            ECOAEExtension.log.warn("Invalid controller block at {} !", getPos());
            controllerRotation = ForgeDirection.NORTH;
        }
    }

    protected abstract Class<? extends Block> getControllerBlock();

    // ---- TileEntity lifecycle ----

    @Override
    public void validate() {
        tileEntityInvalid = false;
        loaded = true;
    }

    @Override
    public void invalidate() {
        tileEntityInvalid = true;
        loaded = false;
        disassemble();
        if (asyncTaskFuture != null) {
            asyncTaskFuture.cancel(false);
        }
    }

    @Override
    public void onLoad() {
        loaded = true;
    }

    @Override
    public void onChunkUnload() {
        super.onChunkUnload();
        disassemble();
    }

    public boolean isWorking() {
        return assembled;
    }

    @Override
    public void updateContainingBlockInfo() {
        super.updateContainingBlockInfo();
    }
}
