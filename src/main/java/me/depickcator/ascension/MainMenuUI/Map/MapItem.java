package me.depickcator.ascension.MainMenuUI.Map;

import me.depickcator.ascension.Ascension;
import org.apache.commons.lang3.tuple.MutablePair;
import org.apache.commons.lang3.tuple.Pair;
import org.bukkit.Location;

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
    public MapItem(String name, int x, int z, int type) {
        this.name = name;
        this.x = x;
        this.z = z;
        this.y = Ascension.getInstance().getWorld().getHighestBlockYAt(x, z);
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
        return new Location(Ascension.getInstance().getWorld(), x, y, z);
    }

    public int getType() {
        return type;
    }
}
