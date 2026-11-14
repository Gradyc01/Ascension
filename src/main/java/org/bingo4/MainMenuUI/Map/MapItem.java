package org.bingo4.MainMenuUI.Map;

import org.bingo4.Bingo4;
import org.apache.commons.lang3.tuple.MutablePair;
import org.apache.commons.lang3.tuple.Pair;
import org.bukkit.Location;
import org.bukkit.map.MapCursor;
import org.bukkit.map.MapView;

public class MapItem {
    private final String name;
    private final int x;
    private final int z;
    private final int y;
    private final int type;
    public static final int SPAWN = 0;
    public static final int CARE_PACKAGE = 1;
    public static final int SCAVENGER = 2;
    public static final int ASCENSION = 3;
    public static final int ACTIVE_ASCENSION = 4;
    public static final int BINGO_TURN_IN = 5;
    public static final int MINING_CAMP_1 = 6;
    public static final int MINING_CAMP_2 = 7;
    public static final int MINING_CAMP_3 = 8;
    public static final int ANCHOR_POINT = 9;

    public MapItem(String name, int x, int z, int type) {
        this.name = name;
        this.x = x;
        this.z = z;
        this.y = Bingo4.getInstance().getWorld().getHighestBlockYAt(x, z);
        this.type = type;
    }

    public MapItem(String name, Location location, int type) {
        this.name = name;
        this.x = location.getBlockX();
        this.y = location.getBlockY();
        this.z = location.getBlockZ();
        this.type = type;
    }


    public String getName() {
        return name;
    }

    public Pair<Integer, Integer> getCoords() {
        return new MutablePair<>(x, z);
    }

    public Location getLocation() {
        return new Location(Bingo4.getInstance().getWorld(), x, y, z);
    }

    public int getType() {
        return type;
    }

    public static int[] worldToMap(
            MapView map,
            Location location
    ) {
        int blocksPerPixel = 1 << map.getScale().ordinal();

        int pixelX = 64 + (location.getBlockX() - map.getCenterX())
                / blocksPerPixel;

        int pixelZ = 64 + (location.getBlockZ() - map.getCenterZ())
                / blocksPerPixel;

        return new int[] { pixelX, pixelZ };
    }

    public MapCursor.Type getCursorType() {
        return switch (type) {
            case BINGO_TURN_IN -> MapCursor.Type.TARGET_X;
            case MINING_CAMP_1 -> MapCursor.Type.BANNER_WHITE;
            case MINING_CAMP_2 -> MapCursor.Type.BANNER_ORANGE;
            case MINING_CAMP_3 -> MapCursor.Type.BANNER_YELLOW;
            case ANCHOR_POINT -> MapCursor.Type.TARGET_POINT;
            default -> MapCursor.Type.RED_X;
        };
    }
}
