package org.bingo4.Structures.Interfaces;

import org.bingo4.Structures.CampInstance;
import org.bukkit.Location;

import java.util.Objects;

public abstract class AbstractCampType {

    private final String id;
    private int width;
    private int depth;
    private int height;

    protected AbstractCampType(String id) {
        this.id = Objects.requireNonNull(id, "Camp type ID cannot be null");
    }

    public String getId() {
        return id;
    }

    /**
     * Places and initializes this camp in the world.
     *
     * Implementations can use SchematicPlacer, read structure markers,
     * remove marker blocks, start FloodBlocks, etc.
     */
    public abstract void place(CampInstance instance) throws Exception;

    public abstract CampInstance createInstance(Location origin, Location center) throws Exception;


    /**
     * Called after the camp has been successfully placed and registered.
     */
    public void onPlaced(CampInstance instance) {
    }

    public int getWidth() {
        return width;
    }

    public int getDepth() {
        return depth;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public void setDepth(int depth) {
        this.depth = depth;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

}
