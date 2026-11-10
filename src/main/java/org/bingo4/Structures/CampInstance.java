package org.bingo4.Structures;

import org.bingo4.Bingo4;
import org.bingo4.Structures.Interfaces.AbstractCampType;
import org.bukkit.Location;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

public class CampInstance {

    private final AbstractCampType type;
    private final Location origin;
    private Location center;
    protected final Bingo4 plugin = Bingo4.getInstance();

    private final List<Location> enemySpawns = new ArrayList<>();
    private final List<Location> friendlySpawns = new ArrayList<>();
    private final List<Location> interactPanels = new ArrayList<>();

    public CampInstance(
            AbstractCampType type,
            Location origin,
            Location center
    ) {
        this.type = Objects.requireNonNull(type);
        this.origin = origin.clone();
        this.center = center.clone();
    }

    public AbstractCampType getType() {
        return type;
    }

    public String getTypeId() {
        return type.getId();
    }

    public Location getOrigin() {
        return origin.clone();
    }

    public Location getCenter() {
        return center.clone();
    }

    public void addEnemySpawn(Location location) {
        enemySpawns.add(location.clone());
    }

    public void addFriendlySpawn(Location location) {
        friendlySpawns.add(location.clone());
    }

    public void addInteractPanel(Location location) {
        interactPanels.add(location.clone());
    }

    public List<Location> getEnemySpawns() {
        return Collections.unmodifiableList(enemySpawns);
    }

    public List<Location> getFriendlySpawns() {
        return Collections.unmodifiableList(friendlySpawns);
    }

    public List<Location> getInteractPanels() {
        return Collections.unmodifiableList(interactPanels);
    }

    public void setCenter(Location center) {
        this.center = center;
    }
}
