package appeng.items;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

import java.util.List;

public class AEBaseItem extends Item {

    @SideOnly(Side.CLIENT)
    protected void addCheckedInformation(ItemStack stack, EntityPlayer player, List<String> lines, boolean advancedTooltips) {
    }

}
