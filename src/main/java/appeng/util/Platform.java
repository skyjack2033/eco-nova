package appeng.util;

import appeng.api.networking.energy.IEnergySource;
import appeng.api.networking.security.BaseActionSource;
import appeng.api.storage.IMEInventory;
import appeng.api.storage.data.IAEStack;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

public class Platform {
    public static boolean canAccess(EntityPlayer player, Object security) { return true; }
    public static boolean isClient() { return false; }
    public static boolean isServer() { return true; }
    public static boolean hasPermissions(Object security, EntityPlayer player) { return true; }
    public static ItemStack extractItemsByRecipe(Object o1, Object o2, Object o3, Object o4, boolean b) { return null; }
    public static <StackType extends IAEStack> StackType poweredInsert(IEnergySource energy, IMEInventory<StackType> inv, StackType stack, BaseActionSource source) { return stack; }
    public static <StackType extends IAEStack> StackType poweredExtraction(IEnergySource energy, IMEInventory<StackType> inv, StackType stack, BaseActionSource source) { return null; }
    public static NBTTagCompound openNbtData(ItemStack i) { return i.getTagCompound(); }
}
