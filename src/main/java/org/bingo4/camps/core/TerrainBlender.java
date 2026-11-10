package org.bingo4.camps.core;

import org.bukkit.HeightMap;
import org.bukkit.Material;
import org.bukkit.Tag;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.TileState;
import org.bukkit.block.data.BlockData;

import java.util.HashMap;
import java.util.Map;

/** Flattens the base footprint to its median surface Y and smoothsteps the surrounding ring. */
public final class TerrainBlender {

    /**
     * @param baseWidth base size along X as placed (after rotation)
     * @param baseDepth base size along Z as placed (after rotation)
     */
    public void blend(World world, Site site, int radius, int baseWidth, int baseDepth) {
        if (radius <= 0) return;
        int minX = site.x() - radius;
        int maxX = site.x() + baseWidth - 1 + radius;
        int minZ = site.z() - radius;
        int maxZ = site.z() + baseDepth - 1 + radius;

        // Snapshot all original surface heights/materials before changing any columns.
        Map<Long, SurfaceColumn> snapshot = new HashMap<>();
        for (int x = minX; x <= maxX; x++) {
            for (int z = minZ; z <= maxZ; z++) {
                Block top = world.getHighestBlockAt(x, z, HeightMap.MOTION_BLOCKING_NO_LEAVES);
                Material type = top.getType();
                if (!type.isSolid() || top.isLiquid() || type == Material.BEDROCK
                        || Tag.LOGS.isTagged(type) || top.getState() instanceof TileState) {
                    continue; // Preserve trees/fluids/block entities just outside the camp.
                }
                snapshot.put(key(x, z), new SurfaceColumn(top.getY(), top.getBlockData().clone(), type));
            }
        }

        for (int x = minX; x <= maxX; x++) {
            for (int z = minZ; z <= maxZ; z++) {
                SurfaceColumn column = snapshot.get(key(x, z));
                if (column == null) continue;
                double distance = distanceToFootprint(x, z, site.x(), site.z(), baseWidth, baseDepth);
                double t = Math.min(1.0, distance / radius);
                double smooth = t * t * (3.0 - 2.0 * t);
                int targetY = (int) Math.round(site.groundY()
                        + (column.y() - site.groundY()) * smooth);
                targetY = Math.max(world.getMinHeight() + 1,
                        Math.min(world.getMaxHeight() - 2, targetY));
                adjustColumn(world, x, z, column, targetY);
            }
        }
    }

    private void adjustColumn(World world, int x, int z, SurfaceColumn original, int targetY) {
        Material filler = fillerFor(original.material());
        if (targetY < original.y()) {
            for (int y = original.y(); y > targetY; y--) {
                world.getBlockAt(x, y, z).setType(Material.AIR, false);
            }
        } else if (targetY > original.y()) {
            for (int y = original.y() + 1; y < targetY; y++) {
                world.getBlockAt(x, y, z).setType(filler, false);
            }
        }
        world.getBlockAt(x, targetY, z).setBlockData(original.blockData(), false);
    }

    private Material fillerFor(Material surface) {
        return switch (surface) {
            case SAND -> Material.SAND;
            case RED_SAND -> Material.RED_SAND;
            case GRAVEL -> Material.GRAVEL;
            case STONE -> Material.STONE;
            case DEEPSLATE -> Material.DEEPSLATE;
            case PODZOL -> Material.DIRT;
            case MYCELIUM -> Material.DIRT;
            case MOSS_BLOCK -> Material.MOSS_BLOCK;
            case SNOW_BLOCK -> Material.SNOW_BLOCK;
            default -> Material.DIRT;
        };
    }

    private double distanceToFootprint(int x, int z, int originX, int originZ,
                                       int baseWidth, int baseDepth) {
        int dx = Math.max(0, Math.max(originX - x, x - (originX + baseWidth - 1)));
        int dz = Math.max(0, Math.max(originZ - z, z - (originZ + baseDepth - 1)));
        return Math.sqrt((double) dx * dx + (double) dz * dz);
    }

    private long key(int x, int z) {
        return ((long) x << 32) ^ (z & 0xffffffffL);
    }

    private record SurfaceColumn(int y, BlockData blockData, Material material) {}
}
