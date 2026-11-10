//package org.bingo4.camps.worldedit;
//
//import com.example.campplacement.api.*;
//import com.sk89q.worldedit.EditSession;
//import com.sk89q.worldedit.WorldEdit;
//import com.sk89q.worldedit.bukkit.BukkitAdapter;
//import com.sk89q.worldedit.extent.clipboard.Clipboard;
//import com.sk89q.worldedit.extent.clipboard.io.ClipboardFormat;
//import com.sk89q.worldedit.extent.clipboard.io.ClipboardFormats;
//import com.sk89q.worldedit.extent.clipboard.io.ClipboardReader;
//import com.sk89q.worldedit.function.operation.Operation;
//import com.sk89q.worldedit.function.operation.Operations;
//import com.sk89q.worldedit.math.BlockVector3;
//import com.sk89q.worldedit.math.Vector3;
//import com.sk89q.worldedit.math.transform.AffineTransform;
//import com.sk89q.worldedit.session.ClipboardHolder;
//import com.sk89q.worldedit.world.block.BaseBlock;
//import org.bukkit.World;
//
//import java.io.File;
//import java.io.FileInputStream;
//import java.io.IOException;
//import java.util.ArrayList;
//import java.util.EnumMap;
//import java.util.List;
//import java.util.Map;
//
///** Sponge .schem backend. All four rotated clipboards are prepared during construction. */
//public final class WorldEditSchematicTemplate implements CampTemplate {
//    private final String id;
//    private final File file;
//    private final int width;
//    private final int depth;
//    private final int height;
//    private final int groundOffsetY;
//    private final List<CampMarker> sourceMarkers;
//    private final Map<CampRotation, PreparedCamp> prepared = new EnumMap<>(CampRotation.class);
//
//    public WorldEditSchematicTemplate(String id, File file, int groundOffsetY) throws IOException {
//        this.id = id;
//        this.file = file;
//        this.groundOffsetY = groundOffsetY;
//        Clipboard source = readClipboard(file);
//        BlockVector3 dimensions = source.getDimensions();
//        this.width = dimensions.x();
//        this.depth = dimensions.z();
//        this.height = dimensions.y();
//        if (width != 16 || depth != 16) {
//            throw new IllegalArgumentException("Template " + id + " must be 16x16; found "
//                    + width + "x" + depth);
//        }
//        if (height < 1 || groundOffsetY < 0 || groundOffsetY >= height) {
//            throw new IllegalArgumentException("Invalid template height/groundOffsetY: " + id);
//        }
//        this.sourceMarkers = extractMarkers(source);
//        for (CampRotation rotation : CampRotation.values()) {
//            prepared.put(rotation, prepareClipboard(rotation));
//        }
//    }
//
//    private Clipboard readClipboard(File file) throws IOException {
//        ClipboardFormat format = ClipboardFormats.findByFile(file);
//        if (format == null) throw new IOException("Unsupported schematic format: " + file);
//        try (ClipboardReader reader = format.getReader(new FileInputStream(file))) {
//            return reader.read();
//        }
//    }
//
//    private List<CampMarker> extractMarkers(Clipboard clipboard) {
//        List<CampMarker> markers = new ArrayList<>();
//        BlockVector3 min = clipboard.getMinimumPoint();
//        for (BlockVector3 point : clipboard.getRegion()) {
//            BaseBlock block = clipboard.getFullBlock(point);
//            String id = block.getBlockType().getId();
//            LocalPos local = new LocalPos(point.x() - min.x(),
//                    point.y() - min.y(), point.z() - min.z());
//            if (id.equals("minecraft:bedrock")) {
//                markers.add(new CampMarker(local, local, MarkerType.SPAWN_POINT));
//            } else if (id.equals("minecraft:command_block")
//                    || id.equals("minecraft:chain_command_block")
//                    || id.equals("minecraft:repeating_command_block")) {
//                markers.add(new CampMarker(local, local, MarkerType.COMMAND_PANEL));
//            }
//        }
//        return List.copyOf(markers);
//    }
//
//    private PreparedCamp prepareClipboard(CampRotation rotation) throws IOException {
//        try {
//            Clipboard clipboard = readClipboard(file);
//            BlockVector3 sourceMin = clipboard.getMinimumPoint();
//            clipboard.setOrigin(sourceMin);
//
//            // WorldEdit's positive Y transform maps +X toward -Z. In Bukkit/Paper
//            // terms that is counter-clockwise, so invert the sign for consistent
//            // CampRotation semantics across both backends.
//            double worldEditAngle = switch (rotation) {
//                case NONE -> 0.0;
//                case CLOCKWISE_90 -> -90.0;
//                case CLOCKWISE_180 -> 180.0;
//                case COUNTERCLOCKWISE_90 -> 90.0;
//            };
//            AffineTransform transform = new AffineTransform().rotateY(worldEditAngle);
//            Clipboard rotated = rotation == CampRotation.NONE ? clipboard : clipboard.transform(transform);
//            BlockVector3 rotatedMin = rotated.getMinimumPoint();
//            rotated.setOrigin(rotatedMin);
//
//            List<CampMarker> rotatedMarkers = new ArrayList<>();
//            for (CampMarker marker : sourceMarkers) {
//                LocalPos p = marker.sourcePosition();
//                Vector3 transformed = transform.apply(Vector3.at(p.x(), p.y(), p.z()));
//                LocalPos placed = new LocalPos(
//                        (int) Math.round(transformed.x() - transformedBoundsMinX(transform)),
//                        (int) Math.round(transformed.y() - transformedBoundsMinY(transform)),
//                        (int) Math.round(transformed.z() - transformedBoundsMinZ(transform))
//                );
//                rotatedMarkers.add(new CampMarker(marker.sourcePosition(), placed, marker.type()));
//            }
//            return new WorldEditPreparedCamp(rotated, List.copyOf(rotatedMarkers));
//        } catch (Exception ex) {
//            if (ex instanceof IOException io) throw io;
//            throw new IOException("Could not prepare rotated schematic " + id + " / " + rotation, ex);
//        }
//    }
//
//    // The 16x16xH corner bounds transformed around the normalized clipboard origin.
//    private double transformedBoundsMinX(AffineTransform transform) {
//        double min = Double.POSITIVE_INFINITY;
//        for (int x : new int[]{0, 15}) for (int z : new int[]{0, 15})
//            min = Math.min(min, transform.apply(Vector3.at(x, 0, z)).x());
//        return min;
//    }
//
//    private double transformedBoundsMinY(AffineTransform transform) {
//        double min = Double.POSITIVE_INFINITY;
//        for (int y : new int[]{0, height - 1})
//            for (int x : new int[]{0, 15}) for (int z : new int[]{0, 15})
//                min = Math.min(min, transform.apply(Vector3.at(x, y, z)).y());
//        return min;
//    }
//
//    private double transformedBoundsMinZ(AffineTransform transform) {
//        double min = Double.POSITIVE_INFINITY;
//        for (int x : new int[]{0, 15}) for (int z : new int[]{0, 15})
//            min = Math.min(min, transform.apply(Vector3.at(x, 0, z)).z());
//        return min;
//    }
//
//    @Override public String id() { return id; }
//    @Override public int width() { return width; }
//    @Override public int depth() { return depth; }
//    @Override public int height() { return height; }
//    @Override public int groundOffsetY() { return groundOffsetY; }
//    @Override public PreparedCamp prepare(CampRotation rotation) { return prepared.get(rotation); }
//
//    private record WorldEditPreparedCamp(Clipboard clipboard, List<CampMarker> markers)
//            implements PreparedCamp {
//        @Override public void paste(World world, int x, int y, int z, long seed) {
//            com.sk89q.worldedit.world.World weWorld = BukkitAdapter.adapt(world);
//            try (EditSession editSession = WorldEdit.getInstance().newEditSession(weWorld)) {
//                Operation operation = new ClipboardHolder(clipboard)
//                        .createPaste(editSession)
//                        .to(BlockVector3.at(x, y, z))
//                        .ignoreAirBlocks(false)
//                        .ignoreStructureVoidBlocks(true)
//                        .copyEntities(false)
//                        .copyBiomes(false)
//                        .build();
//                Operations.complete(operation);
//            }
//        }
//    }
//}
