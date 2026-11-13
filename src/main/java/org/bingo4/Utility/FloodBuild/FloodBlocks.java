package org.bingo4.Utility.FloodBuild;

import org.apache.commons.lang3.tuple.Pair;
import org.bingo4.Bingo4;
import org.bingo4.Utility.TextUtil;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.*;

public class FloodBlocks {
    private final Set<Pair<Location, Double>> floodLocations;
    private final Location startingPoint;
    private final double successRate;
    private final Floodable floodable;
    private final int totalWeight;
    private final List<Set<Pair<Block, Material>>> blocksChanged; //Current, Original Material

    public FloodBlocks(Location startingPoint, double successRate, Floodable floodable) {
        floodLocations = new HashSet<>();
        this.startingPoint = startingPoint;
        this.successRate = successRate;
        this.floodable = floodable;
        totalWeight = calculateWeight();
        blocksChanged = new ArrayList<>();
    }

    public void start(Random r) {
        Pair<Block, Material> startingPair = floodBlock(startingPoint, successRate, r);
        if (startingPair != null) {
            blocksChanged.add(Set.of(startingPair));
        }

        TextUtil.debugText("Flood Starting Location " + TextUtil.formatLocation(startingPoint));
    }

    public void flood(Random r) {
        TextUtil.debugText("Flood Size " + floodLocations.size());
        Set<Pair<Block, Material>> blocksChanged = new HashSet<>();
        for (Pair<Location, Double> floodLocation : new HashSet<>(floodLocations)) {

            Pair<Block, Material> b = floodBlock(floodLocation.getLeft(), floodLocation.getRight(), r);
            if (b != null) blocksChanged.add(b);
            floodLocations.remove(floodLocation);
        }

        this.blocksChanged.add(blocksChanged);
    }

    public boolean unFlood() {
        if (this.blocksChanged.isEmpty()) return false;
        TextUtil.debugText(String.format("[FloodBlocks] Unflooding %s blocks", this.blocksChanged.getLast().size()));
        for (Pair<Block, Material> b : this.blocksChanged.removeLast()) {
            unFloodChanges(b);
        }
        return !this.blocksChanged.isEmpty();
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

    public void unFloodChanges(Pair<Block, Material> b) {
        b.getLeft().setType(b.getRight());
        b.getLeft().removeMetadata("UNBREAKABLE", Bingo4.getInstance());
    }

    private Pair<Block, Material> floodBlock(Location loc, double successRate, Random r) {
        if (r.nextDouble() > successRate) return null;
        Block block = loc.getWorld().getBlockAt(loc);
        Material material = block.getType();
        if (floodable.isUnFloodable(block)) return null;
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

        return Pair.of(block, material);
    }

    private int calculateWeight() {
        int totalWeight = 0;
        for (Map.Entry<Material, Integer> flood : floodable.getUnFloodables().entrySet()) {
            totalWeight += flood.getValue();
        }
        return totalWeight;
    }
}
