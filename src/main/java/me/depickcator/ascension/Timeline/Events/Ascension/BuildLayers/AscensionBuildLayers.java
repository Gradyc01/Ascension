package me.depickcator.ascension.Timeline.Events.Ascension.BuildLayers;

import me.depickcator.ascension.Ascension;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;

import java.util.ArrayList;
import java.util.List;

public class AscensionBuildLayers extends Builds {
    private final Ascension plugin;
    private final Location spawn;
    private final List<Location> pillarFoundations;

    public AscensionBuildLayers(Location spawn) {
        this.plugin = Ascension.getInstance();
        this.spawn = spawn;
        this.pillarFoundations = new ArrayList<>();
        initPillarLocations();
    }

    public void buildInitialLayer() {
        Block b = spawn.getWorld().getHighestBlockAt(spawn.getBlockX(), spawn.getBlockZ());
        b.setType(Material.END_STONE);
        fillFloor(spawn, -5, 7, 5, -7, b);
//        fillBlock(spawn, -5, 7, -5, -5 ,7, 5, Material.END_STONE);
    }

    private void initPillarLocations() {
        int x = spawn.getBlockX();
        int z = spawn.getBlockZ();
        int spread = 15;
        pillarFoundations.add(findPillarLocation(x + spread, z + spread));
        pillarFoundations.add(findPillarLocation(x + spread, z - spread));
        pillarFoundations.add(findPillarLocation(x - spread, z + spread));
        pillarFoundations.add(findPillarLocation(x - spread, z - spread));
    }

    private Location findPillarLocation(int x, int z) {
        return plugin.getWorld().getHighestBlockAt(x, z).getLocation();
    }


}
