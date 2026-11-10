package org.bingo4.MainMenuUI.Map;

import org.bingo4.Bingo4;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.map.MapCanvas;
import org.bukkit.map.MapCursor;
import org.bukkit.map.MapRenderer;
import org.bukkit.map.MapView;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class NeutralMapRenderer extends MapRenderer {

    private final Bingo4 plugin = Bingo4.getInstance();

    public NeutralMapRenderer() {
        super(true);
    }

    @Override
    public void render(
            @NotNull MapView map,
            @NotNull MapCanvas canvas,
            @NotNull Player player
    ) {
        if (map.getWorld() == null ||
                !map.getWorld().equals(player.getWorld())) {
            return;
        }

        clearCursors(canvas);

        int blocksPerPixel = getBlocksPerPixel(map);

        int centerX = map.getCenterX();
        int centerZ = map.getCenterZ();

        MapItems mapItems = plugin.getSettingsUI()
                .getSettings()
                .getTimeline()
                .getMapItems();

        for (MapItem marker : getVisibleMarkers(map, mapItems, blocksPerPixel)) {

            Location location = marker.getLocation();

            int cursorX = (int) Math.round(
                    (location.getX() - centerX) * 2.0 / blocksPerPixel
            );

            int cursorY = (int) Math.round(
                    (location.getZ() - centerZ) * 2.0 / blocksPerPixel
            );

            if (cursorX < -128 || cursorX > 127 ||
                    cursorY < -128 || cursorY > 127) {
                continue;
            }

            canvas.getCursors().addCursor(
                    new MapCursor(
                            (byte) cursorX,
                            (byte) cursorY,
                            (byte) 0,
                            marker.getCursorType(),
                            true
                    )
            );
        }
    }

    /**
     * Returns the number of world blocks represented by one map pixel.
     */
    private int getBlocksPerPixel(MapView map) {
        return switch (map.getScale()) {
            case CLOSEST -> 1;
            case CLOSE -> 2;
            case NORMAL -> 4;
            case FAR -> 8;
            case FARTHEST -> 16;
        };
    }

    /**
     * Removes previously rendered cursors.
     */
    private void clearCursors(MapCanvas canvas) {
        while (canvas.getCursors().size() > 0) {
            canvas.getCursors().removeCursor(
                    canvas.getCursors().getCursor(0)
            );
        }
    }

    /**
     * Gets markers within the visible map boundaries.
     */
    private List<MapItem> getVisibleMarkers(
            MapView map,
            MapItems mapItems,
            int blocksPerPixel
    ) {
        List<MapItem> visibleMarkers = new ArrayList<>();

        int radius = 64 * blocksPerPixel;

        int minX = map.getCenterX() - radius;
        int maxX = map.getCenterX() + radius;
        int minZ = map.getCenterZ() - radius;
        int maxZ = map.getCenterZ() + radius;

        for (MapItem marker : mapItems.getMapItems()) {

            Location location = marker.getLocation();

            if (location.getWorld() == null ||
                    !location.getWorld().equals(map.getWorld())) {
                continue;
            }

            double x = location.getX();
            double z = location.getZ();

            if (x < minX || x > maxX ||
                    z < minZ || z > maxZ) {
                continue;
            }

            visibleMarkers.add(marker);
        }

        return visibleMarkers;
    }
}