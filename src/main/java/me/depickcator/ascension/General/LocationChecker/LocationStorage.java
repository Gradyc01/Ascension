package me.depickcator.ascension.General.LocationChecker;

import org.bukkit.block.Biome;
import org.bukkit.generator.structure.Structure;

public class LocationStorage {
    private final Biome biome;
    private final int points;
    private final Structure structure;
    private final String displayName;
    private boolean isInWorld;
    public LocationStorage(Biome biome, int points, String displayName) {
        this.biome = biome;
        this.points = points;
        this.displayName = displayName;
        this.structure = null;
    }

    public LocationStorage(Structure structure, int points, String displayName) {
        this.structure = structure;
        this.points = points;
        this.displayName = displayName;
        this.biome = null;
    }

    public Biome getBiome() {
        return biome;
    }

    public int getPoints() {
        return points;
    }

    public Structure getStructure() {
        return structure;
    }

    public String getDisplayName() {
        return displayName;
    }

    public boolean isInWorld() {
        return isInWorld;
    }

    public void setInWorld(boolean inWorld) {
        isInWorld = inWorld;
    }
}
