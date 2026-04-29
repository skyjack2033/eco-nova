package github.kasuminova.ecoaeextension.common.tile.ecotech;

import github.kasuminova.mmce.common.world.MMWorldEventListener;
import github.kasuminova.mmce.common.world.MachineComponentManager;
import github.kasuminova.ecoaeextension.ECOAEExtension;
import github.kasuminova.ecoaeextension.common.block.prop.FacingProp;
import github.kasuminova.ecoaeextension.common.tile.TileCustomController;
import github.kasuminova.ecoaeextension.common.util.EPartMap;
import hellfirepvp.modularmachinery.ModularMachinery;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.util.ForgeDirection;
import github.kasuminova.ecoaeextension.common.util.BlockPos;
import net.minecraft.util.math.Vec3i;
import net.minecraft.world.World;
import cpw.mods.fml.common.FMLCommonHandler;

public abstract class EPartController<P extends EPart<?>> extends TileCustomController {

    protected final EPartMap<P> parts = new EPartMap<>();
    protected boolean assembled = false;
    protected int ticksExisted = 0;

    
    public void doControllerTick() {
        if (!this.doStructureCheck() || !this.isStructureFormed()) {
            disassemble();
            return;
        }
        if (!assemble()) {
            return;
        }

        if (onSyncTick()) {
            this.tickExecutor = ModularMachinery.EXECUTE_MANAGER.addTask(this::onAsyncTick, timeRecorder.usedTimeAvg());
        }
    }

    public boolean checkControllerShared() {
        BlockPos pos = getPos();
        World world = getWorld();
        if (world.getTileEntity(pos.up(2).getX(), pos.up(2).getY(), pos.up(2).getZ()) instanceof EPartController<?> partController) {
            if (partController.getControllerBlock() == getControllerBlock()) {
                return true;
            }
        }
        if (world.getTileEntity(pos.down(2).getX(), pos.down(2).getY(), pos.down(2).getZ()) instanceof EPartController<?> partController) {
            return partController.getControllerBlock() == getControllerBlock();
        }
        return false;
    }

    protected abstract boolean onSyncTick();

    protected void onAsyncTick() {
    }

    
    protected void updateComponents() {
        super.updateComponents();
        clearParts();
        this.foundPattern.getTileBlocksArray().forEach((pos, info) -> {
            BlockPos realPos = getPos().add(pos);
            if (!this.getWorld().blockExists(realPos.getX(), realPos.getY(), realPos.getZ())) {
                return;
            }
            TileEntity te = this.getWorld().getTileEntity(realPos.getX(), realPos.getY(), realPos.getZ());
            if (!(te instanceof AbstractEPart<?>)) {
                return;
            }
            try {
                P part = (P) te;
                part.setController(this);
                parts.addPart(part);
                onAddPart(part);
            } catch (ClassCastException e) {
                ECOAEExtension.log.error("Invalid EPart found at {} !", realPos);
                ECOAEExtension.log.error(e);
            }
        });
    }

    protected abstract void onAddPart(P part);

    
    protected boolean canCheckStructure() {
        if (lastStructureCheckTick == -1 || (isStructureFormed() && !assembled)) {
            return true;
        }
        if (ticksExisted % 40 == 0) {
            return true;
        }
        if (isStructureFormed()) {
            BlockPos pos = getPos();
            Vec3i min = foundPattern.getMin();
            Vec3i max = foundPattern.getMax();
            BlockPos minPos = new BlockPos(pos.getX() + min.getX(), pos.getY() + min.getY(), pos.getZ() + min.getZ());
            BlockPos maxPos = new BlockPos(pos.getX() + max.getX(), pos.getY() + max.getY(), pos.getZ() + max.getZ());
            return MMWorldEventListener.INSTANCE.isAreaChanged(getWorld(), minPos, maxPos);
        }
        return ticksExisted % Math.min(structureCheckDelay + this.structureCheckCounter * 5, maxStructureCheckDelay) == 0;
    }

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

    
    protected void checkRotation() {
        if (controllerRotation != null) {
            return;
        }
        BlockPos p = getPos();
        Block block = getWorld().getBlock(p.getX(), p.getY(), p.getZ());
        if (getControllerBlock().isInstance(block)) {
            IBlockState state = block.getStateFromMeta(getWorld().getBlockMetadata(p.getX(), p.getY(), p.getZ()));
            controllerRotation = state.getValue(FacingProp.HORIZONTALS);
        } else {
            ECOAEExtension.log.warn("Invalid EPartController block at {} !", getPos());
            controllerRotation = ForgeDirection.NORTH;
        }
    }

    protected abstract Class<? extends Block> getControllerBlock();

    
    public void validate() {
        tileEntityInvalid = false;
        loaded = true;
    }

    
    public void invalidate() {
        tileEntityInvalid = true;
        loaded = false;
        disassemble();
    }

    
    public void onLoad() {
        loaded = true;
    }

    
    public void onChunkUnload() {
        super.onChunkUnload();
        disassemble();
    }

    
    public boolean isWorking() {
        return assembled;
    }

    
    public void updateContainingBlockInfo() {
        super.updateContainingBlockInfo();
    }

}
