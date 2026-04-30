package github.kasuminova.ecoaeextension.common.network;

import github.kasuminova.ecoaeextension.ECOAEExtension;
import hellfirepvp.modularmachinery.ModularMachinery;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.StatCollector;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;

public class PktPatternTermUploadPattern implements IMessage, IMessageHandler<PktPatternTermUploadPattern, IMessage> {

    @Override
    public void fromBytes(final ByteBuf buf) {
    }

    @Override
    public void toBytes(final ByteBuf buf) {
    }

    @Override
    public IMessage onMessage(final PktPatternTermUploadPattern message, final MessageContext ctx) {
        EntityPlayerMP player = ctx.getServerHandler().playerEntity;
        ModularMachinery.EXECUTE_MANAGER.addSyncTask(() -> {
            player.addChatMessage(new ChatComponentText(
                StatCollector.translateToLocal("novaeng.efabricator_pattern_bus.upload_unavailable")));
        });
        return null;
    }

}
