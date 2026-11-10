package org.bingo4.Structures;

import org.bingo4.camps.core.Site;
import org.bukkit.HeightMap;
import org.bukkit.Material;
import org.bukkit.Tag;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.TileState;

import java.util.Arrays;
import java.util.Optional;

public final class TerrainAnalyzer {
    private final int maxHeightDifference;
    private final int maxNeighbourStep;

    public TerrainAnalyzer(int maxHeightDifference, int maxNeighbourStep) {
        this.maxHeightDifference = maxHeightDifference;
        this.maxNeighbourStep = maxNeighbourStep;
    }

    /**
     * Evaluates a baseWidth x baseDepth footprint whose minimum corner is (x, z).
     * Pass the base dimensions as they will be after rotation.
     * Must run on the server thread.
     */
    public Optional<Site> evaluate(World world, int x, int z, int structureHeight,
                                   int baseWidth, int baseDepth) {
        if (baseWidth < 1 || baseDepth < 1) {
            throw new IllegalArgumentException("Base footprint must be at least 1x1");
        }
        int[][] heights = new int[baseWidth][baseDepth];
        int minY = Integer.MAX_VALUE;
        int maxY = Integer.MIN_VALUE;
        int cells = baseWidth * baseDepth;
        int[] sorted = new int[cells];
        int index = 0;

        for (int dx = 0; dx < baseWidth; dx++) {
            for (int dz = 0; dz < baseDepth; dz++) {
                Block surface = world.getHighestBlockAt(
                        x + dx, z + dz, HeightMap.MOTION_BLOCKING_NO_LEAVES);
                Material type = surface.getType();
                if (!type.isSolid() || surface.isLiquid() || type == Material.BEDROCK
                        || Tag.LOGS.isTagged(type) || surface.getState() instanceof TileState) {
                    return Optional.empty();
                }
                int y = surface.getY();
                heights[dx][dz] = y;
                sorted[index++] = y;
                minY = Math.min(minY, y);
                maxY = Math.max(maxY, y);
            }
        }

        if (maxY - minY > maxHeightDifference) return Optional.empty();
        Arrays.sort(sorted);
        // Median: for 256 cells this is the average of sorted[127] and sorted[128].
        int groundY = (sorted[(cells - 1) / 2] + sorted[cells / 2]) / 2;

        int maxStep = 0;
        for (int dx = 0; dx < baseWidth; dx++) {
            for (int dz = 0; dz < baseDepth; dz++) {
                if (dx < baseWidth - 1) maxStep = Math.max(maxStep,
                        Math.abs(heights[dx][dz] - heights[dx + 1][dz]));
                if (dz < baseDepth - 1) maxStep = Math.max(maxStep,
                        Math.abs(heights[dx][dz] - heights[dx][dz + 1]));
            }
        }
        if (maxStep > maxNeighbourStep) return Optional.empty();

        // HeightMap.MOTION_BLOCKING_NO_LEAVES already exposes trunks and other
        // motion-blocking surface obstacles. Those were rejected above. The camp
        // paste overwrites ordinary terrain/vegetation inside its volume, so avoid
        // an expensive 16x16x(height) voxel scan for every candidate.

        double averageDeviation = 0.0;
        for (int[] row : heights) {
            for (int height : row) averageDeviation += Math.abs(height - groundY);
        }
        averageDeviation /= cells;
        double score = (maxY - minY) * 5.0 + maxStep * 3.0 + averageDeviation * 4.0;
        return Optional.of(new Site(x, z, groundY, score));
    }
}
