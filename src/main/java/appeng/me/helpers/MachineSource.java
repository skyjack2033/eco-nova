package appeng.me.helpers;

import appeng.api.networking.security.BaseActionSource;
import appeng.api.networking.security.IActionHost;
import appeng.api.networking.security.IActionSource;

public class MachineSource extends BaseActionSource implements IActionSource {
    private final IActionHost via;

    public MachineSource(IActionHost via) {
        this.via = via;
    }

    @Override
    public IActionHost via() { return via; }

    @Override
    public boolean isPlayer() { return false; }

    @Override
    public boolean isMachine() { return true; }
}
