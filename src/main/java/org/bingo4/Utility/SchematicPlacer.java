package org.bingo4.Utility;

import com.sk89q.worldedit.EditSession;
import com.sk89q.worldedit.WorldEdit;
import com.sk89q.worldedit.WorldEditException;
import com.sk89q.worldedit.bukkit.BukkitAdapter;
import com.sk89q.worldedit.extent.clipboard.Clipboard;
import com.sk89q.worldedit.function.operation.Operation;
import com.sk89q.worldedit.function.operation.Operations;
import com.sk89q.worldedit.math.BlockVector3;
import com.sk89q.worldedit.regions.Region;
import com.sk89q.worldedit.session.ClipboardHolder;

import org.bingo4.Bingo4;
import org.bukkit.Material;
import org.bukkit.Tag;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.plugin.Plugin;

import java.util.*;

/**
 * Places one WorldEdit clipboard using a rectangular X/Z terrain footprint.
 *
 * Coordinates supplied to SchematicSpec are relative to the clipboard
 * region's minimum point, not the clipboard origin.
 *
 * Bukkit block metadata is runtime-only and is not persisted across restarts.
 */
public final class SchematicPlacer {

    private static final String METADATA_KEY = "ascension_schematic";

    private final Bingo4 plugin = Bingo4.getInstance();

    public SchematicPlacer() {
    }

    public record SchematicSpec(
            Clipboard clipboard,
            int footprintOffsetX,
            int footprintOffsetZ,
            int footprintWidthX,
            int footprintDepthZ,
            List<BlockVector3> supportPoints
    ) {
        public SchematicSpec {
            Objects.requireNonNull(clipboard, "clipboard");
            supportPoints = supportPoints == null
                    ? List.of()
                    : List.copyOf(supportPoints);

            if (footprintWidthX <= 0 || footprintDepthZ <= 0) {
                throw new IllegalArgumentException(
                        "Footprint dimensions must be positive."
                );
            }
        }
    }

    public static class TerrainNotSuitableException extends Exception {
        public TerrainNotSuitableException(String message) {
            super(message);
        }
    }

    public record TerrainAnalysis(
            int minGroundY,
            int maxGroundY,
            int heightDifference,
            int schematicMinY,
            int[][] groundHeights
    ) {}

    public record PlacementResult(
            int schematicMinY,
            int heightDifference,
            int taggedBlocks,
            int supportBlocks,
            Set<Block> blocks
    ) {}

    private record Bounds(
            int minX, int minY, int minZ,
            int maxX, int maxY, int maxZ
    ) {
        int width() { return maxX - minX + 1; }
        int height() { return maxY - minY + 1; }
        int depth() { return maxZ - minZ + 1; }
    }

    /**
     * Checks the terrain without changing the world.
     *
     * baseWorldX/baseWorldZ identify the world-space minimum X/Z corner
     * of the base footprint (not the full schematic).
     */
    public TerrainAnalysis checkTerrain(
            World world,
            SchematicSpec spec,
            int baseWorldX,
            int baseWorldZ,
            int maxHeightDifference
    ) throws TerrainNotSuitableException {
        if (maxHeightDifference < 0) {
            throw new IllegalArgumentException(
                    "Maximum height difference cannot be negative."
            );
        }

        return analyze(
                world, spec, baseWorldX, baseWorldZ,
                true, maxHeightDifference
        );
    }

    /**
     * Places without checking slope or obstructions. Terrain is still
     * sampled to determine the placement Y and support-column lengths.
     */
    public PlacementResult placeUnchecked(
            World world,
            SchematicSpec spec,
            int baseWorldX,
            int baseWorldZ,
            Material supportMaterial,
            String metadataValue
    ) throws TerrainNotSuitableException, WorldEditException {
        TerrainAnalysis analysis = analyze(
                world, spec, baseWorldX, baseWorldZ, false, 0
        );

        return paste(
                world, spec, analysis, baseWorldX, baseWorldZ,
                supportMaterial, metadataValue
        );
    }

    /**
     * Checks terrain first, then places only if all checks pass.
     */
    public PlacementResult placeChecked(
            World world,
            SchematicSpec spec,
            int baseWorldX,
            int baseWorldZ,
            int maxHeightDifference,
            Material supportMaterial,
            String metadataValue
    ) throws TerrainNotSuitableException, WorldEditException {
        TerrainAnalysis analysis = checkTerrain(
                world, spec, baseWorldX, baseWorldZ, maxHeightDifference
        );

        return paste(
                world, spec, analysis, baseWorldX, baseWorldZ,
                supportMaterial, metadataValue
        );
    }

    private TerrainAnalysis analyze(
            World world,
            SchematicSpec spec,
            int baseWorldX,
            int baseWorldZ,
            boolean checkObstructions,
            int maxHeightDifference
    ) throws TerrainNotSuitableException {
        Objects.requireNonNull(world, "world");
        Objects.requireNonNull(spec, "spec");
        validateSpec(spec);

        Bounds bounds = getBounds(spec.clipboard());

        int schematicMinX = baseWorldX - spec.footprintOffsetX();
        int schematicMinZ = baseWorldZ - spec.footprintOffsetZ();

        int[][] groundHeights = new int[bounds.width()][bounds.depth()];

        for (int x = 0; x < bounds.width(); x++) {
            for (int z = 0; z < bounds.depth(); z++) {
                groundHeights[x][z] = findGroundSurfaceY(
                        world, schematicMinX + x, schematicMinZ + z
                );
            }
        }

        int minGroundY = Integer.MAX_VALUE;
        int maxGroundY = Integer.MIN_VALUE;

        for (int x = spec.footprintOffsetX();
             x < spec.footprintOffsetX() + spec.footprintWidthX(); x++) {
            for (int z = spec.footprintOffsetZ();
                 z < spec.footprintOffsetZ() + spec.footprintDepthZ(); z++) {
                int y = groundHeights[x][z];
                minGroundY = Math.min(minGroundY, y);
                maxGroundY = Math.max(maxGroundY, y);
            }
        }

        int heightDifference = maxGroundY - minGroundY;

        // Align the schematic's minimum Y with one block above the
        // highest ground column. This assumes the base bottom is at
        // the clipboard's minimum Y.
        int schematicMinY = maxGroundY + 1;
        int schematicMaxY = schematicMinY + bounds.height() - 1;

        if (schematicMinY < world.getMinHeight()
                || schematicMaxY >= world.getMaxHeight()) {
            throw new TerrainNotSuitableException(
                    "Schematic exceeds world height limits."
            );
        }

        if (checkObstructions) {
            if (heightDifference > maxHeightDifference) {
                throw new TerrainNotSuitableException(
                        "Terrain is too uneven. Difference: "
                                + heightDifference
                                + ", allowed: " + maxHeightDifference
                );
            }

            for (int x = 0; x < bounds.width(); x++) {
                for (int z = 0; z < bounds.depth(); z++) {
                    int worldX = schematicMinX + x;
                    int worldZ = schematicMinZ + z;
                    int groundY = groundHeights[x][z];

                    if (groundY >= schematicMinY) {
                        throw new TerrainNotSuitableException(
                                "Terrain intersects the schematic volume at "
                                        + worldX + ", " + groundY + ", " + worldZ
                        );
                    }

//                    for (int y = groundY + 1; y <= schematicMaxY; y++) {
//                        Block block = world.getBlockAt(worldX, y, worldZ);
//                        if (isBlocking(block)) {
//                            throw new TerrainNotSuitableException(
//                                    "Obstruction detected: " + block.getType()
//                                            + " at " + worldX + ", " + y
//                                            + ", " + worldZ
//                            );
//                        }
//                    }
                }
            }
        }

        return new TerrainAnalysis(
                minGroundY,
                maxGroundY,
                heightDifference,
                schematicMinY,
                groundHeights
        );
    }

    /**
     * Finds the highest solid ground block while ignoring tree logs/leaves
     * and snow layers. Customize this policy for your terrain.
     */
    private int findGroundSurfaceY(
            World world,
            int x,
            int z
    ) throws TerrainNotSuitableException {
        int highestY = Math.min(
                world.getHighestBlockYAt(x, z),
                world.getMaxHeight() - 1
        );

        for (int y = highestY; y >= world.getMinHeight(); y--) {
            Block block = world.getBlockAt(x, y, z);
            Material material = block.getType();

            if (!material.isSolid()) continue;
            if (Tag.LOGS.isTagged(material) || Tag.LEAVES.isTagged(material)) {
                continue;
            }
            if (material == Material.SNOW) continue;

            return y;
        }

        throw new TerrainNotSuitableException(
                "No suitable ground found at " + x + ", " + z
        );
    }

    private boolean isBlocking(Block block) {
        Material material = block.getType();

        if (Tag.LOGS.isTagged(material) || Tag.LEAVES.isTagged(material)) {
            return true;
        }
        if (block.isLiquid()) return true;

        return !material.isAir() && !block.isPassable();
    }

    private PlacementResult paste(
            World world,
            SchematicSpec spec,
            TerrainAnalysis analysis,
            int baseWorldX,
            int baseWorldZ,
            Material supportMaterial,
            String metadataValue
    ) throws WorldEditException {
        Objects.requireNonNull(supportMaterial, "supportMaterial");

        if (!supportMaterial.isBlock() || !supportMaterial.isSolid()) {
            throw new IllegalArgumentException(
                    "Support material must be a solid block."
            );
        }
        if (metadataValue == null || metadataValue.isBlank()) {
            throw new IllegalArgumentException(
                    "Metadata value cannot be empty."
            );
        }

        Bounds bounds = getBounds(spec.clipboard());
        int schematicMinX = baseWorldX - spec.footprintOffsetX();
        int schematicMinZ = baseWorldZ - spec.footprintOffsetZ();
        int schematicMinY = analysis.schematicMinY();

        BlockVector3 clipboardMin =
                spec.clipboard().getRegion().getMinimumPoint();
        BlockVector3 clipboardOrigin = spec.clipboard().getOrigin();

        BlockVector3 targetOrigin = BlockVector3.at(
                schematicMinX + clipboardOrigin.x()
                        - clipboardMin.x(),
                schematicMinY + clipboardOrigin.y()
                        - clipboardMin.y(),
                schematicMinZ + clipboardOrigin.z()
                        - clipboardMin.z()
        );

        try (EditSession editSession =
                     WorldEdit.getInstance().newEditSession(
                             BukkitAdapter.adapt(world)
                     )) {
            Operation operation = new ClipboardHolder(spec.clipboard())
                    .createPaste(editSession)
                    .to(targetOrigin)
                    .ignoreAirBlocks(false)
                    .copyEntities(false)
                    .build();

            Operations.complete(operation);
        }

        Set<Block> taggedBlocks = tagSchematicBlocks(
                world, spec, schematicMinX, schematicMinY,
                schematicMinZ, metadataValue
        );

        Set<Block> supportBlocks = placeSupports(
                world, spec, analysis, schematicMinX,
                schematicMinY, schematicMinZ,
                supportMaterial, metadataValue
        );

        taggedBlocks.addAll(supportBlocks);

        return new PlacementResult(
                schematicMinY,
                analysis.heightDifference(),
                taggedBlocks.size() + supportBlocks.size(),
                supportBlocks.size(),
                taggedBlocks
        );
    }

    private Set<Block> tagSchematicBlocks(
            World world,
            SchematicSpec spec,
            int schematicMinX,
            int schematicMinY,
            int schematicMinZ,
            String metadataValue
    ) {
        Clipboard clipboard = spec.clipboard();
        Region region = clipboard.getRegion();
        BlockVector3 min = region.getMinimumPoint();
        Set<Block> blocks = new HashSet<>();

        for (BlockVector3 sourcePos : region) {
            if (clipboard.getBlock(sourcePos)
                    .getBlockType()
                    .getMaterial()
                    .isAir()) {
                continue;
            }

            int localX = sourcePos.x() - min.x();
            int localY = sourcePos.y() - min.y();
            int localZ = sourcePos.z() - min.z();

            Block target = world.getBlockAt(
                    schematicMinX + localX,
                    schematicMinY + localY,
                    schematicMinZ + localZ
            );

            if (!target.getType().isAir()) {
                applyMetadata(target, metadataValue);
                blocks.add(target);
            }
        }

        return blocks;
    }

    private Set<Block> placeSupports(
            World world,
            SchematicSpec spec,
            TerrainAnalysis analysis,
            int schematicMinX,
            int schematicMinY,
            int schematicMinZ,
            Material supportMaterial,
            String metadataValue
    ) {
        int[][] groundHeights = analysis.groundHeights();
        Set<Block> blocks = new HashSet<>();

        for (BlockVector3 local : spec.supportPoints()) {
            int localX = local.x();
            int localY = local.y();
            int localZ = local.z();

            int worldX = schematicMinX + localX;
            int worldZ = schematicMinZ + localZ;

            int groundY = groundHeights[localX][localZ];
            int supportTopY = schematicMinY + localY;

            for (int y = groundY + 1; y < supportTopY; y++) {
                Block block = world.getBlockAt(worldX, y, worldZ);
                block.setType(supportMaterial, false);
                applyMetadata(block, metadataValue);
                blocks.add(block);
            }
        }

        return blocks;
    }

    @SuppressWarnings("deprecation")
    private void applyMetadata(Block block, String metadataValue) {
        block.setMetadata(
                metadataValue,
                new FixedMetadataValue(plugin, true)
        );
    }

    private Bounds getBounds(Clipboard clipboard) {
        Region region = clipboard.getRegion();
        BlockVector3 min = region.getMinimumPoint();
        BlockVector3 max = region.getMaximumPoint();

        return new Bounds(
                min.x(), min.y(), min.z(),
                max.x(), max.y(), max.z()
        );
    }

    private void validateSpec(SchematicSpec spec) {
        Bounds bounds = getBounds(spec.clipboard());

        if (spec.footprintOffsetX() < 0
                || spec.footprintOffsetZ() < 0
                || spec.footprintOffsetX() + spec.footprintWidthX()
                        > bounds.width()
                || spec.footprintOffsetZ() + spec.footprintDepthZ()
                        > bounds.depth()) {
            throw new IllegalArgumentException(
                    "Footprint is outside the schematic bounds."
            );
        }

        for (BlockVector3 point : spec.supportPoints()) {
            if (point.x() < 0 || point.x() >= bounds.width()
                    || point.y() < 0 || point.y() >= bounds.height()
                    || point.z() < 0 || point.z() >= bounds.depth()) {
                throw new IllegalArgumentException(
                        "Invalid support coordinate: " + point
                );
            }
        }
    }
}
