package org.bingo4.camps.paper;

import org.bingo4.Utility.FloodBuild.FloodBlocks;
import org.bingo4.camps.api.*;
import org.bingo4.camps.storage.CampRegistry;
import org.bingo4.camps.storage.CampStore;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.BlockState;
import org.bukkit.block.structure.Mirror;
import org.bukkit.block.structure.StructureRotation;
import org.bukkit.structure.Palette;
import org.bukkit.structure.Structure;
import org.bukkit.util.BlockVector;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/** Paper's native NBT structure backend. */
public final class PaperStructureTemplate implements CampTemplate {
    private final String id;
    private final Structure structure;
    private final List<CampMarker> sourceMarkers;
    private final int width;
    private final int depth;
    private final int height;
    private final int groundOffsetY;
    private final int baseWidth;
    private final int baseDepth;
    private final int baseOffsetX;
    private final int baseOffsetZ;

    private PaperStructureTemplate(String id, Structure structure, int groundOffsetY,
                                   int baseWidth, int baseDepth, int baseOffsetX, int baseOffsetZ) {
        this.id = id;
        this.structure = structure;
        this.groundOffsetY = groundOffsetY;
        this.baseWidth = baseWidth;
        this.baseDepth = baseDepth;
        this.baseOffsetX = baseOffsetX;
        this.baseOffsetZ = baseOffsetZ;
        BlockVector size = structure.getSize();
        this.width = size.getBlockX();
        this.depth = size.getBlockZ();
        this.height = size.getBlockY();
        validateDimensions();
        if (structure.getPalettes().isEmpty()) {
            throw new IllegalArgumentException("Structure has no palettes: " + id);
        }
        this.sourceMarkers = extractMarkers(structure.getPalettes().get(0));
    }

    /** Loads a structure whose whole footprint is the 16x16 base (no offset). */
    public static PaperStructureTemplate load(String id, File file, int groundOffsetY)
            throws IOException {
        return load(id, file, groundOffsetY, 16, 16, 0, 0);
    }

    /**
     * Loads a structure of any size with a baseWidth x baseDepth ground footprint whose
     * minimum corner is at (baseOffsetX, baseOffsetZ) in the structure's local coordinates.
     */
    public static PaperStructureTemplate load(String id, File file, int groundOffsetY,
                                              int baseWidth, int baseDepth,
                                              int baseOffsetX, int baseOffsetZ)
            throws IOException {
        Structure structure = Bukkit.getStructureManager().loadStructure(file);
        return new PaperStructureTemplate(id, structure, groundOffsetY,
                baseWidth, baseDepth, baseOffsetX, baseOffsetZ);
    }

    private void validateDimensions() {
        if (width < 1 || depth < 1) {
            throw new IllegalArgumentException("Template " + id + " has an invalid size: "
                    + width + "x" + depth);
        }
        if (baseWidth < 1 || baseDepth < 1) {
            throw new IllegalArgumentException("Template " + id + " has an invalid base size: "
                    + baseWidth + "x" + baseDepth);
        }
        if (baseOffsetX < 0 || baseOffsetZ < 0
                || baseOffsetX + baseWidth > width || baseOffsetZ + baseDepth > depth) {
            throw new IllegalArgumentException("Template " + id + " base (" + baseWidth + "x"
                    + baseDepth + " at " + baseOffsetX + "," + baseOffsetZ
                    + ") does not fit inside the " + width + "x" + depth + " structure");
        }
        if (height < 1) throw new IllegalArgumentException("Template height must be positive: " + id);
        if (groundOffsetY < 0 || groundOffsetY >= height) {
            throw new IllegalArgumentException("groundOffsetY must be within template height: " + id);
        }
    }

    private List<CampMarker> extractMarkers(Palette palette) {
        List<CampMarker> markers = new ArrayList<>();
        for (BlockState block : palette.getBlocks()) {
            Material type = block.getType();
            LocalPos pos = new LocalPos(block.getX(), block.getY(), block.getZ());
            if (pos.x() < 0 || pos.x() >= width || pos.z() < 0 || pos.z() >= depth
                    || pos.y() < 0 || pos.y() >= height) {
                throw new IllegalArgumentException("Marker outside the " + width + "x" + depth
                        + " template bounds in " + id + ": " + pos
                        + ". Re-save the NBT with its origin at the minimum X/Z corner.");
            }
            if (type == Material.BEDROCK) {
                markers.add(new CampMarker(pos, pos, MarkerType.SPAWN_POINT));
            } else if (isCommandBlock(type)) {
                markers.add(new CampMarker(pos, pos, MarkerType.COMMAND_PANEL));
            }
        }
        return List.copyOf(markers);
    }

    private boolean isCommandBlock(Material material) {
        return material == Material.COMMAND_BLOCK
                || material == Material.CHAIN_COMMAND_BLOCK
                || material == Material.REPEATING_COMMAND_BLOCK;
    }

    @Override public String id() { return id; }
    @Override public int width() { return width; }
    @Override public int depth() { return depth; }
    @Override public int height() { return height; }
    @Override public int groundOffsetY() { return groundOffsetY; }
    @Override public int baseWidth() { return baseWidth; }
    @Override public int baseDepth() { return baseDepth; }
    @Override public int baseOffsetX() { return baseOffsetX; }
    @Override public int baseOffsetZ() { return baseOffsetZ; }

    @Override
    public PreparedCamp prepare(CampRotation rotation) {
        List<CampMarker> rotated = sourceMarkers.stream()
                .map(marker -> new CampMarker(marker.sourcePosition(),
                        rotate(marker.sourcePosition(), rotation), marker.type()))
                .toList();
        return new PreparedCamp() {
            @Override public List<CampMarker> markers() { return rotated; }

            @Override
            public void paste(World world, int x, int y, int z, long seed) {
                StructureRotation paperRotation = switch (rotation) {
                    case NONE -> StructureRotation.NONE;
                    case CLOCKWISE_90 -> StructureRotation.CLOCKWISE_90;
                    case CLOCKWISE_180 -> StructureRotation.CLOCKWISE_180;
                    case COUNTERCLOCKWISE_90 -> StructureRotation.COUNTERCLOCKWISE_90;
                };

                // Paper rotates around the placement point, so the rotated structure extends
                // into negative X/Z. Shift by the FULL structure size so that (x, z) ends up
                // as the minimum corner of the rotated structure.
                int pasteX = x;
                int pasteZ = z;

                switch (rotation) {
                    case NONE -> {
                    }
                    case CLOCKWISE_90 -> pasteX += depth - 1;
                    case CLOCKWISE_180 -> {
                        pasteX += width - 1;
                        pasteZ += depth - 1;
                    }
                    case COUNTERCLOCKWISE_90 -> pasteZ += width - 1;
                }

                structure.place(new Location(world, pasteX, y, pasteZ), false,
                        paperRotation, Mirror.NONE, 0, 1.0f, new Random(seed));
//                structure.place(new Location(world, x, y, z), false,
//                        paperRotation, Mirror.NONE, 0, 1.0f, new Random(seed));
            }
        };
    }
}
