package github.kasuminova.ecoaeextension.common.util;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;
import net.minecraftforge.common.util.ForgeDirection;

/**
 * Compatibility adapter for 1.12.2 ForgeDirection API on top of 1.7.10 ForgeDirection.
 */
public final class EnumFacingCompat {
    public static final List<ForgeDirection> HORIZONTALS = Arrays.stream(ForgeDirection.VALID_DIRECTIONS)
            .filter(d -> d != ForgeDirection.UP && d != ForgeDirection.DOWN)
            .collect(Collectors.toList());

    public static ForgeDirection byHorizontalIndex(int horizontalIndex) {
        switch (horizontalIndex) {
            case 0: return ForgeDirection.SOUTH;
            case 1: return ForgeDirection.WEST;
            case 2: return ForgeDirection.NORTH;
            case 3: return ForgeDirection.EAST;
            default: return ForgeDirection.NORTH;
        }
    }

    public static int toHorizontalIndex(ForgeDirection dir) {
        switch (dir) {
            case SOUTH: return 0;
            case WEST: return 1;
            case NORTH: return 2;
            case EAST: return 3;
            default: return 2; // NORTH
        }
    }
}
