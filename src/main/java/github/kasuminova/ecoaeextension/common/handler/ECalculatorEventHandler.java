package github.kasuminova.ecoaeextension.common.handler;

import appeng.api.config.SecurityPermissions;
import appeng.api.networking.IGrid;
import appeng.api.networking.IGridNode;
import appeng.api.networking.energy.IEnergyGrid;
import appeng.api.networking.security.ISecurityGrid;
import appeng.me.helpers.IGridProxyable;
import appeng.tile.inventory.AppEngInternalInventory;
import github.kasuminova.ecoaeextension.ECOAEExtension;
import github.kasuminova.ecoaeextension.common.container.ContainerECalculatorController;
import github.kasuminova.ecoaeextension.common.item.ecalculator.ECalculatorCell;
import github.kasuminova.ecoaeextension.common.tile.ecotech.ecalculator.ECalculatorCellDrive;
import github.kasuminova.ecoaeextension.common.tile.ecotech.ecalculator.ECalculatorController;
import github.kasuminova.ecoaeextension.common.tile.ecotech.ecalculator.ECalculatorMEChannel;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import cpw.mods.fml.common.eventhandler.EventPriority;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.TickEvent;
import cpw.mods.fml.relauncher.Side;

@SuppressWarnings("MethodMayBeStatic")
public class ECalculatorEventHandler {

    public static final ECalculatorEventHandler INSTANCE = new ECalculatorEventHandler();

    public static final int UPDATE_INTERVAL = 10;

    @SubscribeEvent
    public void onPlayerTick(TickEvent.PlayerTickEvent event) {
        if (event.phase != TickEvent.Phase.START || event.side == Side.CLIENT) {
            return;
        }
        if (!(event.player instanceof EntityPlayerMP)) {
            return;
        }
        EntityPlayerMP player = (EntityPlayerMP) event.player;
        if (!(player.openContainer instanceof ContainerECalculatorController)) {
            return;
        }
        ContainerECalculatorController containerECController = (ContainerECalculatorController) player.openContainer;
        World world = player.getEntityWorld();
        int tickExisted = containerECController.getTickExisted();
        containerECController.setTickExisted(tickExisted + 1);
        if (world.getTotalWorldTime() % UPDATE_INTERVAL != 0 && tickExisted > 1) {
            return;
        }
        ECalculatorController controller = containerECController.getOwner();
        ECOAEExtension.NET_CHANNEL.sendTo(controller.getGuiDataPacket(), player);
    }

    private static boolean canInteract(final EntityPlayer player, final IGridProxyable proxyable) {
        final IGridNode gn = proxyable.getProxy().getNode();
        if (gn != null) {
            final IGrid g = gn.getGrid();
            final IEnergyGrid eg = g.getCache(IEnergyGrid.class);
            if (!eg.isNetworkPowered()) {
                return true;
            }

            final ISecurityGrid sg = g.getCache(ISecurityGrid.class);
            return sg.hasPermission(player, SecurityPermissions.BUILD);
        }
        return true;
    }

    @SubscribeEvent(priority = EventPriority.LOW)
    public void onRightClickBlock(PlayerInteractEvent event) {
        if (event.action != PlayerInteractEvent.Action.RIGHT_CLICK_BLOCK) {
            return;
        }
        World world = event.world;
        if (world.isRemote) {
            return;
        }

        EntityPlayer player = event.entityPlayer;
        if (!player.isSneaking()) {
            return;
        }

        TileEntity te = world.getTileEntity(event.x, event.y, event.z);
        if (!(te instanceof ECalculatorCellDrive)) {
            return;
        }
        ECalculatorCellDrive drive = (ECalculatorCellDrive) te;

        ECalculatorController controller = drive.getController();
        if (controller != null) {
            ECalculatorMEChannel channel = controller.getChannel();
            if (channel != null && !canInteract(player, channel)) {
                player.addChatMessage(new ChatComponentText(StatCollector.translateToLocal("novaeng.ecalculator_cell_drive.player.no_permission")));
                event.setCanceled(true);
                return;
            }
        }

        ItemStack stackInHand = player.getHeldItem();

        AppEngInternalInventory inv = drive.getDriveInv();
        ItemStack stackInSlot = inv.getStackInSlot(0);
        if (stackInSlot == null || stackInSlot.stackSize <= 0) {
            if (stackInHand == null || stackInHand.stackSize <= 0 || !(stackInHand.getItem() instanceof ECalculatorCell)) {
                return;
            }
            player.inventory.setInventorySlotContents(player.inventory.currentItem, inv.insertItem(0, stackInHand.copy(), false));
            player.addChatMessage(new ChatComponentText(StatCollector.translateToLocal("novaeng.ecalculator_cell_drive.player.inserted")));
            event.setCanceled(true);
            return;
        }

        if (stackInHand != null && stackInHand.stackSize > 0) {
            return;
        }

        player.inventory.setInventorySlotContents(player.inventory.currentItem, inv.extractItem(0, stackInSlot.stackSize, false));
        player.addChatMessage(new ChatComponentText(StatCollector.translateToLocal("novaeng.ecalculator_cell_drive.player.removed")));
        event.setCanceled(true);
    }

}
