package org.bingo4.Utility.FloodBuild;

import org.apache.commons.lang3.tuple.Pair;
import org.bingo4.Utility.TextUtil;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.*;

public class FloodBlocks {
    private final Set<Pair<Location, Double>> floodLocations;
    private final Location startingPoint;
    private final double successRate;
    private final Floodable floodable;
    private final int totalWeight;

    public FloodBlocks(Location startingPoint, double successRate, Floodable floodable) {
        floodLocations = new HashSet<>();
        this.startingPoint = startingPoint;
        this.successRate = successRate;
        this.floodable = floodable;
        totalWeight = calculateWeight();
    }

    public void start(Random r) {
        floodBlock(startingPoint, successRate, r);
    }

    public void flood(Random r) {
        TextUtil.debugText("Flood Size " + floodLocations.size());
        for (Pair<Location, Double> floodLocation : new HashSet<>(floodLocations)) {
            floodBlock(floodLocation.getLeft(), floodLocation.getRight(), r);
            floodLocations.remove(floodLocation);
        }
    }

    public void autoFlood(Random r) {
        floodBlock(startingPoint, successRate, r);
        while (!floodLocations.isEmpty()) {
            flood(r);
        }
    }

    public Material chooseNextMaterial(Random r) {
        int nextMaterial = r.nextInt(0, totalWeight);
        int indexWeight = 0;
        for (Map.Entry<Material, Integer> flood : floodable.getUnFloodables().entrySet()) {
            indexWeight += flood.getValue();
            if (indexWeight > nextMaterial) {
                return flood.getKey();
            }
        }
        return floodable.getUnFloodables().entrySet().iterator().next().getKey();
    }

    private void floodBlock(Location loc, double successRate, Random r) {
        if (r.nextDouble() > successRate) return;
        Block block = loc.getWorld().getBlockAt(loc);
        if (floodable.isUnFloodable(block)) return;
        floodable.changeBlock(block, r, this);
        double newSuccessRate = Double.max(0, floodable.getNewSuccessRate(successRate, r));
        floodLocations.add(Pair.of(loc.clone().add(1, 0, 1), newSuccessRate));
        floodLocations.add(Pair.of(loc.clone().add(1, 0, 0), newSuccessRate));
        floodLocations.add(Pair.of(loc.clone().add(1, 0, -1), newSuccessRate));

        floodLocations.add(Pair.of(loc.clone().add(0, 0, 1), newSuccessRate));
        floodLocations.add(Pair.of(loc.clone().add(0, 0, -1), newSuccessRate));

        floodLocations.add(Pair.of(loc.clone().add(-1, 0, 1), newSuccessRate));
        floodLocations.add(Pair.of(loc.clone().add(-1, 0, 0), newSuccessRate));
        floodLocations.add(Pair.of(loc.clone().add(-1, 0, -1), newSuccessRate));

        floodLocations.add(Pair.of(loc.clone().add(0, -1, 0), newSuccessRate));
        floodLocations.add(Pair.of(loc.clone().add(0, 1, 0), newSuccessRate));
    }

    private int calculateWeight() {
        int totalWeight = 0;
        for (Map.Entry<Material, Integer> flood : floodable.getUnFloodables().entrySet()) {
            totalWeight += flood.getValue();
        }
        return totalWeight;
    }
}
