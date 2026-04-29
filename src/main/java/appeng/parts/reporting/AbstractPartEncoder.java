package appeng.parts.reporting;

import appeng.api.networking.IGridNode;
import appeng.api.util.AEPartLocation;
import appeng.me.helpers.AENetworkProxy;
import appeng.me.helpers.IGridProxyable;

/**
 * Stub for 1.7.10 port
 */
public class AbstractPartEncoder implements IGridProxyable {


    public IGridNode getGridNode(AEPartLocation dir) {
        return null;
    }


    public AENetworkProxy getProxy() {
        return null;
    }


    public DimensionalCoord getLocation() {
        return null;
    }

    public void securityBreak() {
    }

    public void gridChanged() {
    }
}
