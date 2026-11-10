package org.bingo4.General.Game.Load.Sequences;

import org.bingo4.Bingo4;
import org.bingo4.General.Game.GameLauncher;
import org.bingo4.General.Game.GameSequences;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;

import java.util.Random;

public class BuildPlatform extends GameSequences {
    @Override
    public void run(GameLauncher game) {
        Location spawnCoordsLocation = Bingo4.getSpawn();
        String quartzBlock = "quartz_block";
        floodBlock(spawnCoordsLocation, 1.5, new Random());
        game.callback();
    }

    private void floodBlock(Location loc, double successRate, Random r) {
        if (r.nextDouble() > successRate) return;
        int blockX = loc.getBlockX();
        int blockY = loc.getBlockY();
        int blockZ = loc.getBlockZ();
        Block block = loc.getWorld().getBlockAt(loc);
        if (block.getType() == Material.END_STONE
                || block.getType() == Material.OBSIDIAN
                || block.getType() == Material.AIR
                || block.getType() == Material.SNOW) return;
        Material type = r.nextDouble() <= 0.95 ? Material.END_STONE : Material.OBSIDIAN;
        block.setType(type);
//        block.setBlockData(b.getBlockData());
//        block.setMetadata("UNBREAKABLE", new FixedMetadataValue(Bingo4.getInstance(), true));

        double newSuccessRate = Double.max(0, successRate - r.nextDouble(0.1, 0.20));

        floodBlock(new Location(loc.getWorld(), blockX + 1, blockY, blockZ + 1), newSuccessRate, r);
        floodBlock(new Location(loc.getWorld(), blockX + 1, blockY, blockZ + 0), newSuccessRate, r);
        floodBlock(new Location(loc.getWorld(), blockX + 1, blockY, blockZ - 1), newSuccessRate, r);

        floodBlock(new Location(loc.getWorld(), blockX + 0, blockY, blockZ + 1), newSuccessRate, r);
        floodBlock(new Location(loc.getWorld(), blockX + 0, blockY, blockZ - 1), newSuccessRate, r);

        floodBlock(new Location(loc.getWorld(), blockX - 1, blockY, blockZ + 1), newSuccessRate, r);
        floodBlock(new Location(loc.getWorld(), blockX - 1, blockY, blockZ + 0), newSuccessRate, r);
        floodBlock(new Location(loc.getWorld(), blockX - 1, blockY, blockZ - 1), newSuccessRate, r);

        floodBlock(new Location(loc.getWorld(), blockX, blockY - 1, blockZ), newSuccessRate, r);
        floodBlock(new Location(loc.getWorld(), blockX, blockY + 1, blockZ), newSuccessRate, r);
    }
}
