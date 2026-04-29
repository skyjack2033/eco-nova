package appeng.parts.reporting;

import appeng.api.networking.IGridNode;
import appeng.api.util.AECableType;
import appeng.api.util.AEPartLocation;
import appeng.api.util.DimensionalCoord;
import appeng.me.helpers.AENetworkProxy;
import appeng.me.helpers.IGridProxyable;
import net.minecraftforge.common.util.ForgeDirection;

/**
 * Stub for 1.7.10 port
 */
public class AbstractPartEncoder implements IGridProxyable {


    public IGridNode getGridNode(ForgeDirection dir) {
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

    public AECableType getCableConnectionType(ForgeDirection dir) {
        return AECableType.SMART;
    }
}
