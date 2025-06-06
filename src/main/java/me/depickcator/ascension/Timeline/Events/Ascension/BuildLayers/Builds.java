package me.depickcator.ascension.Timeline.Events.Ascension.BuildLayers;

import me.depickcator.ascension.Ascension;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.metadata.FixedMetadataValue;

import java.util.Random;

public class Builds {
    private final Ascension plugin;

    public Builds() {
        plugin = Ascension.getInstance();
    }
    public void fillBlock(Location loc, int x1, int y1, int z1, int x2, int y2, int z2, Block b) {
        int blockX = loc.getBlockX();
        int blockY = loc.getBlockY();
        int blockZ = loc.getBlockZ();
        for (int y = Math.min(y1, y2); y <= Math.max(y1, y2); y++) {
            for (int x = Math.min(x1, x2); x <= Math.max(x1, x2); x++) {
                for (int z = Math.min(z1, z2); z <= Math.max(z1, z2); z++) {
                    Block block = loc.getWorld().getBlockAt(blockX + x, blockY + y, blockZ + z);
                    block.setType(b.getType());
                    block.setBlockData(b.getBlockData());
                    block.setMetadata("UNBREAKABLE", new FixedMetadataValue(plugin, true));
//                    TextUtil.debugText("Placed block at (" +
//                            block.getLocation().getBlockX() + ", " +
//                            block.getLocation().getBlockY() + ", " +
//                            block.getLocation().getBlockZ() + ")");
                }
            }
        }
    }

    public void fillFloor(Location loc, int x1, int z1, int x2, int z2, Block b) {
        int blockX = loc.getBlockX();
        int blockY = loc.getBlockY();
        int blockZ = loc.getBlockZ();
        for (int x = Math.min(x1, x2); x <= Math.max(x1, x2); x++) {
            for (int z = Math.min(z1, z2); z <= Math.max(z1, z2); z++) {
//                setBlock(loc, x, loc.getWorld().getHighestBlockYAt(blockX + x, blockZ + z) - blockY, z, b);
                int y = loc.getWorld().getHighestBlockYAt(blockX + x, blockZ + z) - blockY;
                Random r = new Random();
                fillBlock(loc, x, y, z, x, y - r.nextInt(0, 5), z, b);
            }
        }
    }


    protected void floodBlock(Location loc, double successRate, Random r) {
        if (r.nextDouble() > successRate) return;
        int blockX = loc.getBlockX();
        int blockY = loc.getBlockY();
        int blockZ = loc.getBlockZ();
        Block block = loc.getWorld().getBlockAt(blockX, blockY, blockZ);
        if (block.getType() == Material.END_STONE
                || block.getType() == Material.OBSIDIAN
                || block.getType() == Material.AIR) return;
        Material type = r.nextDouble() <= 0.95 ? Material.END_STONE : Material.OBSIDIAN;
        block.setType(type);
//        block.setBlockData(b.getBlockData());
//        block.setMetadata("UNBREAKABLE", new FixedMetadataValue(Ascension.getInstance(), true));

        double newSuccessRate = Double.max(0, successRate - r.nextDouble(0.02, 0.10));

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

    // private void setBlock(Location loc, int x1, int y1, int z1, Block block) {
    //      fillBlock(loc, x1, y1, z1, x1, y1, z1, block);
    // }
}

