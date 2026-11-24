package org.bingo4.MainMenuUI.Map;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.bingo4.Bingo4;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.map.MapCanvas;
import org.bukkit.map.MapRenderer;
import org.bukkit.map.MapView;
import org.jetbrains.annotations.NotNull;

import java.io.DataInputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Draws the terrain of a world onto a map from a pre-generated "map-cache" folder that lives
 * inside the dimension's own folder (world.getWorldFolder()/map-cache).
 *
 * Because the colors come from the cache instead of the chunks, the map never needs
 * to be explored and the map center can be moved freely.
 *
 * Cache layout (see meta.json): one overworld_scaleN.bin per zoom level (N = MapView.Scale ordinal,
 * 1 / 2 / 4 / 8 / 16 blocks per pixel). Each file is a 28 byte big-endian header
 * ("CPMC", version, scale, originPixelX, originPixelZ, width, height) followed by width * height
 * bytes, one map color byte (base color * 4 + shade) per pixel, rows ordered along +Z.
 * A pixel (px, pz) of a level covers the blocks floor(x / blocksPerPixel) == px.
 *
 * Add this renderer BEFORE NeutralMapRenderer so markers/cursors draw on top. Use install() to add it
 * while keeping the vanilla renderer (which provides the player cursors).
 */
public class WorldMapRenderer extends MapRenderer {

    private static final String CACHE_FOLDER = "map-cache";
    private static final String META_FILE = "meta.json";
    private static final int HEADER_BYTES = 28;
    private static final byte[] MAGIC = "CPMC".getBytes(StandardCharsets.US_ASCII);
    private static final int MAP_SIZE = 128;

    /*Loaded caches by world folder, missing caches are remembered as empty so we don't hit the disk every tick*/
    private static final Map<Path, WorldMapCache> CACHES = new ConcurrentHashMap<>();

    /*Last state drawn per map id, so the 16k pixels are only redrawn when something changed*/
    private final Map<Integer, long[]> lastDrawn = new HashMap<>();

    public WorldMapRenderer() {
    }

    /**
     * Makes the map show the cached world render. The vanilla renderer is KEPT: it is what draws the
     * player cursors (and keeps showing vanilla terrain in worlds without a cache). This renderer is added
     * after it, so its terrain draws over the vanilla one. Returns false (leaving the map untouched) if the
     * map's world has no cache.
     */
    public static boolean install(MapView mapView) {
        World world = mapView.getWorld();
        if (world == null || getCache(world) == null) return false;
        mapView.addRenderer(new WorldMapRenderer());
        return true;
    }

    /*Forgets the loaded cache of the world so it is read from disk again (e.g. after a new world was downloaded)*/
    public static void reload(World world) {
        CACHES.remove(cacheFolder(world));
    }

    /*Forgets every loaded cache*/
    public static void reloadAll() {
        CACHES.clear();
    }

    /*Forces the map to be fully redrawn on the next render*/
    public void invalidate() {
        lastDrawn.clear();
    }

    @Override
    public void render(@NotNull MapView map, @NotNull MapCanvas canvas, @NotNull Player player) {
        World world = map.getWorld();
        if (world == null || !world.equals(player.getWorld())) return;

        WorldMapCache cache = getCache(world);
        if (cache == null) return;

        int scale = map.getScale().ordinal(); // CLOSEST=0 (1 block/pixel) ... FARTHEST=4 (16 blocks/pixel)
        WorldMapCache.Level level = cache.getLevel(scale);
        if (level == null) return;

        int centerX = map.getCenterX();
        int centerZ = map.getCenterZ();

        long[] state = {centerX, centerZ, scale};
        long[] previous = lastDrawn.get(map.getId());
        if (previous != null && previous[0] == state[0] && previous[1] == state[1] && previous[2] == state[2]) return;
        lastDrawn.put(map.getId(), state);

        int bpp = level.blocksPerPixel;
        // Pixel i of the canvas starts at block centerX + (i - 64) * bpp, matching how the cursors are placed.
        int firstPixelX = Math.floorDiv(centerX, bpp) - MAP_SIZE / 2;
        int firstPixelZ = Math.floorDiv(centerZ, bpp) - MAP_SIZE / 2;

        for (int x = 0; x < MAP_SIZE; x++) {
            for (int z = 0; z < MAP_SIZE; z++) {
                // 0 = transparent, used for everything outside the cached area
                canvas.setPixel(x, z, level.getColor(firstPixelX + x, firstPixelZ + z));
            }
        }
    }

    // ------------------------------------------------------------------ cache loading

    private static Path cacheFolder(World world) {
        return world.getWorldFolder().toPath().resolve(CACHE_FOLDER).toAbsolutePath().normalize();
    }

    /*Returns the cache of the world, null if the world has no (valid) map-cache folder*/
    private static WorldMapCache getCache(World world) {
        Path folder = cacheFolder(world);
        WorldMapCache cache = CACHES.computeIfAbsent(folder, WorldMapCache::load);
        return cache == WorldMapCache.EMPTY ? null : cache;
    }

    /** All the zoom levels of one world's map-cache. */
    private static final class WorldMapCache {
        static final WorldMapCache EMPTY = new WorldMapCache(new Level[5]);

        private final Level[] levels;

        private WorldMapCache(Level[] levels) {
            this.levels = levels;
        }

        Level getLevel(int scale) {
            return scale >= 0 && scale < levels.length ? levels[scale] : null;
        }

        static WorldMapCache load(Path folder) {
            Path metaPath = folder.resolve(META_FILE);
            if (!Files.isRegularFile(metaPath)) return EMPTY;
            try {
                JsonObject meta = JsonParser.parseString(Files.readString(metaPath, StandardCharsets.UTF_8)).getAsJsonObject();
                Level[] levels = new Level[5];
                for (JsonElement el : meta.getAsJsonArray("levels")) {
                    JsonObject entry = el.getAsJsonObject();
                    int scale = entry.get("scale").getAsInt();
                    if (scale < 0 || scale >= levels.length) continue;
                    levels[scale] = Level.load(folder.resolve(entry.get("file").getAsString()),
                            scale, entry.get("blocksPerPixel").getAsInt());
                }
                Bingo4.getInstance().getLogger().info("Loaded map-cache from " + folder);
                return new WorldMapCache(levels);
            } catch (Exception e) {
                Bingo4.getInstance().getLogger().severe("Failed to load map-cache from " + folder + ": " + e.getMessage());
                return EMPTY;
            }
        }

        /** One zoom level: a width * height grid of map color bytes. */
        private static final class Level {
            final int blocksPerPixel;
            final int originPixelX;
            final int originPixelZ;
            final int width;
            final int height;
            final byte[] pixels;

            private Level(int blocksPerPixel, int originPixelX, int originPixelZ, int width, int height, byte[] pixels) {
                this.blocksPerPixel = blocksPerPixel;
                this.originPixelX = originPixelX;
                this.originPixelZ = originPixelZ;
                this.width = width;
                this.height = height;
                this.pixels = pixels;
            }

            static Level load(Path file, int expectedScale, int blocksPerPixel) throws IOException {
                try (DataInputStream in = new DataInputStream(Files.newInputStream(file))) {
                    byte[] header = new byte[HEADER_BYTES];
                    in.readFully(header);
                    for (int i = 0; i < MAGIC.length; i++) {
                        if (header[i] != MAGIC[i]) throw new IOException(file.getFileName() + " is not a map cache file");
                    }
                    ByteBuffer buf = ByteBuffer.wrap(header); // big-endian
                    buf.position(MAGIC.length);
                    int version = buf.getInt();
                    int scale = buf.getInt();
                    int originX = buf.getInt();
                    int originZ = buf.getInt();
                    int width = buf.getInt();
                    int height = buf.getInt();
                    if (version != 1) throw new IOException("Unsupported map cache version " + version);
                    if (scale != expectedScale) throw new IOException(file.getFileName() + " has scale " + scale);
                    if (width <= 0 || height <= 0 || (long) width * height > Integer.MAX_VALUE - 8) {
                        throw new IOException(file.getFileName() + " has an invalid size");
                    }
                    byte[] pixels = new byte[width * height];
                    in.readFully(pixels);
                    return new Level(blocksPerPixel, originX, originZ, width, height, pixels);
                }
            }

            /*Map color byte of the level pixel (px, pz); 0 (transparent) outside of the cached area*/
            byte getColor(int px, int pz) {
                int x = px - originPixelX;
                int z = pz - originPixelZ;
                if (x < 0 || z < 0 || x >= width || z >= height) return 0;
                return pixels[z * width + x];
            }
        }
    }
}