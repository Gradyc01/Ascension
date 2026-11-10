package org.bingo4.camps.manager;

import org.bukkit.Location;
import org.bukkit.World;

import java.util.UUID;

/**
 * A terrain-approved camp base footprint.
 *
 * x/z are the minimum X/Z corner of the footprint, not its center.
 * width/depth are the size of the evaluated footprint along X/Z.
 * groundY is the surface level selected by TerrainAnalyzer.
 */
public record CampLocation(
        UUID worldId,
        int x,
        int z,
        int groundY,
        double terrainScore,
        int width,
        int depth
) {
    /** Convenience constructor for the classic 16x16 footprint. */
    public CampLocation(UUID worldId, int x, int z, int groundY, double terrainScore) {
        this(worldId, x, z, groundY, terrainScore, 16, 16);
    }

    public Location toLocation(World world) {
        if (world == null || !world.getUID().equals(worldId)) {
            throw new IllegalArgumentException("Location belongs to a different world");
        }
        return new Location(world, x, groundY + 1, z);
    }

    /** Center in block-index space (7.5 for a 16-wide footprint). */
    public double centerX() {
        return x + (width - 1) / 2.0;
    }

    public double centerZ() {
        return z + (depth - 1) / 2.0;
    }
}
