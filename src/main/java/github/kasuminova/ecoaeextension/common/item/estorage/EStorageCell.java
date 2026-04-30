package github.kasuminova.ecoaeextension.common.item.estorage;

import appeng.api.config.FuzzyMode;
import appeng.api.implementations.items.IStorageCell;
import appeng.api.storage.ICellInventoryHandler;
import appeng.api.storage.StorageChannel;
import appeng.api.storage.data.IAEStack;
import appeng.api.storage.data.IItemList;
import appeng.items.AEBaseItem;
import appeng.items.contents.CellUpgrades;
import appeng.tile.inventory.AppEngInternalInventory;
import appeng.tile.inventory.IAEAppEngInventory;
import appeng.tile.inventory.InvOperation;
import appeng.util.Platform;
import github.kasuminova.ecoaeextension.common.block.ecotech.estorage.prop.DriveStorageLevel;
import github.kasuminova.ecoaeextension.common.core.CreativeTabNovaEng;
import github.kasuminova.ecoaeextension.common.estorage.EStorageCellHandler;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

import javax.annotation.Nonnull;
import java.util.List;

public abstract class EStorageCell<T extends IAEStack<T>> extends AEBaseItem implements IStorageCell {
    protected final DriveStorageLevel level;
    protected final int totalBytes;
    protected final int byteMultiplier;

    public DriveStorageLevel getLevel() {
        return level;
    }

    public int getByteMultiplier() {
        return byteMultiplier;
    }

    public EStorageCell(DriveStorageLevel level, final int millionBytes, final int byteMultiplier) {
        this.level = level;
        this.totalBytes = (millionBytes * 1000) * 1024;
        this.byteMultiplier = byteMultiplier;
        this.setMaxStackSize(1);
        this.setCreativeTab(CreativeTabNovaEng.INSTANCE);
    }

    @SideOnly(Side.CLIENT)
    @Override
    protected void addCheckedInformation(final ItemStack stack, final EntityPlayer player, final List<String> lines, final boolean advancedTooltips) {
        super.addCheckedInformation(stack, player, lines, advancedTooltips);
        EStorageCellHandler handler = EStorageCellHandler.getHandler(stack);
        if (handler != null) {
            ICellInventoryHandler cellHandler = handler.getCellInventory(stack, null, getChannel());
            if (cellHandler != null && cellHandler.getCellInv() != null) {
                IItemList<?> items = cellHandler.getCellInv().getAvailableItems(cellHandler.getCellInv().getChannel().createList());
                lines.add(I18n.format("novaeng.estorage_cell.bytes", items.size()));
            }
        }
        lines.add(I18n.format("novaeng.estorage_cell.insert.tip"));
        lines.add(I18n.format("novaeng.estorage_cell.extract.tip"));
        if (level == DriveStorageLevel.B) {
            lines.add(I18n.format("novaeng.estorage_cell.l6.tip"));
        }
        if (level == DriveStorageLevel.C) {
            lines.add(I18n.format("novaeng.estorage_cell.l9.tip"));
        }
    }

    public abstract StorageChannel getChannel();

    @Override
    public double getIdleDrain() {
        return (double) totalBytes / 1024 / 1024;
    }


    @Override
    public int getBytes(@Nonnull final ItemStack cellItem) {
        return totalBytes;
    }

    public boolean isBlackListed(@Nonnull final ItemStack cellItem, @Nonnull final IAEStack requestedAddition) {
        return false;
    }

    @Override
    public boolean storableInStorageCell() {
        return false;
    }

    @Override
    public boolean isStorageCell(@Nonnull final ItemStack i) {
        return true;
    }

    @Override
    public boolean isEditable(final ItemStack is) {
        return true;
    }

    @Override
    public IInventory getConfigInventory(final ItemStack is) {
        return null;
    }

    @Override
    public IInventory getUpgradesInventory(final ItemStack is) {
        return new CellUpgrades(is, 2);
    }

    @Override
    public FuzzyMode getFuzzyMode(final ItemStack is) {
        final String fz = Platform.openNbtData(is).getString("FuzzyMode");
        try {
            return FuzzyMode.valueOf(fz);
        } catch (final Throwable t) {
            return FuzzyMode.IGNORE_ALL;
        }
    }

    @Override
    public void setFuzzyMode(final ItemStack is, final FuzzyMode fzMode) {
        Platform.openNbtData(is).setString("FuzzyMode", fzMode.name());
    }
}
