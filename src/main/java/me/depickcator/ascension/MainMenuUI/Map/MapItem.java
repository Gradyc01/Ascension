package me.depickcator.ascension.MainMenuUI.Map;

import org.apache.commons.lang3.tuple.MutablePair;
import org.apache.commons.lang3.tuple.Pair;

public class MapItem {
    private final String name;
    private final int x;
    private final int z;
    private final int type;
    public static final int SPAWN = 0;
    public static final int CARE_PACKAGE = 1;
    public static final int SCAVENGER = 2;
    public static final int ASCENSION = 3;
    public MapItem(String name, int x, int z, int type) {
        this.name = name;
        this.x = x;
        this.z = z;
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public Pair<Integer, Integer> getCoords() {
        return new MutablePair<>(x, z);
    }

    public int getType() {
        return type;
    }
}
