package github.kasuminova.ecoaeextension.common.command;

import github.kasuminova.ecoaeextension.ECOAEExtension;
import github.kasuminova.ecoaeextension.common.util.MachineCoolants;
import hellfirepvp.modularmachinery.common.machine.MachineRegistry;
import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.server.MinecraftServer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ChatComponentText;
import net.minecraft.world.World;
import cpw.mods.fml.common.FMLCommonHandler;

import java.util.List;

/**
 * Hot reload command: /novaeng reload
 * Reloads machine structure definitions and coolants without server restart.
 * Active multi-blocks are re-validated against the new definitions.
 */
public class CommandNovaEngReload extends CommandBase {

    @Override
    public String getCommandName() {
        return "novaeng";
    }

    @Override
    public String getCommandUsage(ICommandSender sender) {
        return "/novaeng reload - Reload machine definitions and coolants";
    }

    @Override
    public boolean canCommandSenderUseCommand(ICommandSender sender) {
        return sender.canCommandSenderUseCommand(4, getCommandName());
    }

    @Override
    public List<String> addTabCompletionOptions(ICommandSender sender, String[] args) {
        return args.length == 1 ? getListOfStringsMatchingLastWord(args, "reload") : null;
    }

    @Override
    public void processCommand(ICommandSender sender, String[] args) {
        if (args.length < 1) {
            sender.addChatMessage(new ChatComponentText("§eUsage: " + getCommandUsage(sender)));
            return;
        }

        if ("reload".equals(args[0])) {
            reloadMachinery(sender);
        } else {
            sender.addChatMessage(new ChatComponentText("§cUnknown subcommand: " + args[0]));
        }
    }

    private void reloadMachinery(ICommandSender sender) {
        sender.addChatMessage(new ChatComponentText("§a[NovaEng] Reloading machine definitions..."));

        // Step 1: Clear and reload machine definitions
        int beforeCount = MachineRegistry.getRegistry().getLoadedMachines().size();
        MachineRegistry.getRegistry().clearMachinery();
        MachineRegistry.getRegistry().loadMachineryDefinitions();
        int afterCount = MachineRegistry.getRegistry().getLoadedMachines().size();
        sender.addChatMessage(new ChatComponentText(
                "§a[NovaEng] Machines: " + beforeCount + " -> " + afterCount));

        // Step 2: Reload coolants
        try {
            MachineCoolants.INSTANCE.clear();
            MachineCoolants.INSTANCE.init();
            sender.addChatMessage(new ChatComponentText("§a[NovaEng] Coolants reloaded"));
        } catch (Exception e) {
            sender.addChatMessage(new ChatComponentText("§c[NovaEng] Coolant reload failed: " + e.getMessage()));
            ECOAEExtension.log.error("Coolant reload failed", e);
        }

        // Step 3: Re-validate all loaded multi-blocks
        int revalidated = 0;
        MinecraftServer server = FMLCommonHandler.instance().getMinecraftServerInstance();
        if (server != null) {
            for (World world : server.worldServers) {
                for (Object obj : world.loadedTileEntityList) {
                    if (obj instanceof hellfirepvp.modularmachinery.common.tiles.base.TileMultiblockMachineController) {
                        hellfirepvp.modularmachinery.common.tiles.base.TileMultiblockMachineController ctrl =
                                (hellfirepvp.modularmachinery.common.tiles.base.TileMultiblockMachineController) obj;
                        // Reset structure state to force re-check
                        ctrl.setStructureFormed(false);
                        revalidated++;
                    }
                }
            }
        }
        sender.addChatMessage(new ChatComponentText(
                "§a[NovaEng] " + revalidated + " controllers flagged for re-validation"));

        // Step 4: Notify connected players
        if (sender instanceof EntityPlayerMP) {
            ECOAEExtension.log.info("Hot reload triggered by " + ((EntityPlayerMP) sender).getCommandSenderName());
        }

        sender.addChatMessage(new ChatComponentText("§a[NovaEng] Hot reload complete!"));
    }
}
