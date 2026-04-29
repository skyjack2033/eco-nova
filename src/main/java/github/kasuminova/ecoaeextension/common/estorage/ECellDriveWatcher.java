package github.kasuminova.ecoaeextension.common.estorage;

import appeng.api.config.Actionable;
import appeng.api.networking.security.BaseActionSource;
import appeng.api.storage.ICellInventoryHandler;
import appeng.api.storage.IMEInventory;
import appeng.api.storage.StorageChannel;
import appeng.api.storage.data.AEStackTypeRegistry;
import appeng.api.storage.data.IAEStack;
import appeng.api.storage.data.IAEStackType;
import appeng.me.GridAccessException;
import appeng.me.helpers.AENetworkProxy;
import appeng.me.storage.MEInventoryHandler;
import github.kasuminova.ecoaeextension.ECOAEExtension;
import github.kasuminova.ecoaeextension.common.tile.ecotech.estorage.EStorageCellDrive;
import github.kasuminova.ecoaeextension.common.tile.ecotech.estorage.EStorageMEChannel;


import java.util.Collections;
import java.util.List;

public class ECellDriveWatcher<T extends IAEStack<T>> extends MEInventoryHandler<T> {

    protected final EStorageCellDrive drive;

    @SuppressWarnings("unchecked")
    public ECellDriveWatcher(final IMEInventory<? extends T> i, final StorageChannel channel, final EStorageCellDrive drive) {
        super((IMEInventory<T>) i, getStackType(channel));
        this.drive = drive;
    }

    @SuppressWarnings("unchecked")
    private static <T extends IAEStack<T>> IAEStackType<T> getStackType(final StorageChannel channel) {
        if (channel == StorageChannel.ITEMS) {
            return (IAEStackType<T>) AEStackTypeRegistry.getType("items");
        } else {
            return (IAEStackType<T>) AEStackTypeRegistry.getType("fluids");
        }
    }

    @Override
    public T injectItems(final T input, final Actionable type, final BaseActionSource src) {
        final long size = input.getStackSize();
        final T remainder = super.injectItems(input, type, src);

        if (type == Actionable.MODULATE && (remainder == null || remainder.getStackSize() != size)) {
            EStorageMEChannel channel = this.drive.getController().getChannel();
            AENetworkProxy proxy = channel.getProxy();
            if (proxy.isActive()) {
                try {
                    List<T> changed = Collections.singletonList(input.copy().setStackSize(input.getStackSize() - (remainder == null ? 0 : remainder.getStackSize())));
                    proxy.getStorage().postAlterationOfStoredItems(this.getChannel(), changed, (BaseActionSource) channel.getSource());
                } catch (GridAccessException e) {
                    ECOAEExtension.log.warn(e.toString());
                }
            }
            this.drive.onWriting();
        }

        return remainder;
    }

    @Override
    public T extractItems(final T request, final Actionable type, final BaseActionSource src) {
        final T extractable = super.extractItems(request, type, src);

        if (type == Actionable.MODULATE && extractable != null) {
            EStorageMEChannel channel = this.drive.getController().getChannel();
            AENetworkProxy proxy = channel.getProxy();
            if (proxy.isActive()) {
                try {
                    List<T> changed = Collections.singletonList(request.copy().setStackSize(-extractable.getStackSize()));
                    proxy.getStorage().postAlterationOfStoredItems(this.getChannel(), changed, (BaseActionSource) channel.getSource());
                } catch (GridAccessException e) {
                    ECOAEExtension.log.warn(e.toString());
                }
            }
            this.drive.onWriting();
        }

        return extractable;
    }

}
