package me.depickcator.ascension.Timeline.Events.Ascension.BuildLayers;

import me.depickcator.ascension.Ascension;
import me.depickcator.ascension.Utility.TextUtil;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.entity.EnderCrystal;
import org.bukkit.entity.EntityType;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class AscensionBuildLayers extends Builds {
    private final Ascension plugin;
    private final Location spawn;
    private final List<Location> pillarFoundations;
    private final List<EnderCrystal> endCrystals;
    private final int heightIncrease;

    public AscensionBuildLayers(Location spawn) {
        this.plugin = Ascension.getInstance();
        this.spawn = spawn;
        heightIncrease = 10;
        this.pillarFoundations = new ArrayList<>();
        endCrystals = new ArrayList<>();
        initPillarLocations();
    }

    public void buildInitialLayer() {
        Block b = spawn.getWorld().getHighestBlockAt(spawn.getBlockX(), spawn.getBlockZ());
        floodBlock(b.getLocation(), 1.1, new Random());
    }

    public void buildPillars(int height) {
        for (Location loc : pillarFoundations) {
            buildPillar(loc, height);
        }
    }

    public void destroyCrystals() {
        for (EnderCrystal crystal : endCrystals) {
            crystal.remove();
        }
    }

    private void buildPillar(Location loc, int height) {

        Block b = loc.getBlock();
        b.setType(Material.OBSIDIAN);
        new BukkitRunnable() {
            World world = loc.getWorld();
            int x = loc.getBlockX();
            int z = loc.getBlockZ();
            int y = Integer.min(loc.getBlockY(), height - heightIncrease);
            @Override
            public void run() {
                if (y >= height) {
                    cancel();
                    EnderCrystal e = (EnderCrystal) world.spawnEntity(
                                    new Location(world, x + 0.5, y + 1, z + 0.5)
                                    , EntityType.END_CRYSTAL);
                    e.setBeamTarget(new Location(world, spawn.getX(), spawn.getY() + heightIncrease, spawn.getZ()));
                    endCrystals.add(e);
                    return;
                }

                buildLayer();
                y++;

            }
            private void buildLayer() {
                fillBlock(new Location(world, x, y, z), 1, 0, 3, -1, 0, -3, b);
                fillBlock(new Location(world, x, y, z), 3, 0, 1, -3, 0, -1, b);
                fillBlock(new Location(world, x, y, z), 2, 0, 2, 2, 0, 2, b);
                fillBlock(new Location(world, x, y, z), -2, 0, 2, -2, 0, 2, b);
                fillBlock(new Location(world, x, y, z), 2, 0, -2, 2, 0, -2, b);
                fillBlock(new Location(world, x, y, z), -2, 0, -2, -2, 0, -2, b);

            }
        }.runTaskTimer(plugin, 10, 2);
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
