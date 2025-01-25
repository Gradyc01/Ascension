package me.depickcator.ascension.General;

import me.depickcator.ascension.Ascension;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.ArmorStand;
import org.bukkit.scheduler.BukkitScheduler;

public class BuildLobby implements Runnable {
    private final Location armorStand;
    private final Ascension plugin;
    public BuildLobby(Location armor) {
        armorStand = armor;
        this.plugin = Ascension.getInstance();
        run();
    }

    @Override
    public void run() {
        BukkitScheduler scheduler = plugin.getScheduler();
        scheduler.runTaskLater(plugin, this::buildBox, 10);
        scheduler.runTaskLater(plugin, this::buildSpawn, 20);
        scheduler.runTaskLater(plugin, this::buildBoards, 30);
        scheduler.runTaskLater(plugin, this::buildParkour, 40);
    }

    public static void fillArea(int x1, int y1, int z1, int x2, int y2, int z2, String material, Location entity) {
        int x = entity.getBlockX();
        int y = entity.getBlockY();
        int z = entity.getBlockZ();
        Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "fill " +
                (x + x1) + " " + (y + y1) + " " + (z + z1) + " " + (x + x2) + " " + (y + y2) + " " + (z + z2) + " " + material);
    }

    public static void fillArea(int x1, int y1, int z1, int x2, int y2, int z2, String material, ArmorStand entity) {
        fillArea(x1, y1, z1, x2, y2, z2, material, entity.getLocation());
    }

    public static void clone(int x1, int y1, int z1, int x2, int y2, int z2, int x3, int y3, int z3, Location entity) {
        int x = entity.getBlockX();
        int y = entity.getBlockY();
        int z = entity.getBlockZ();
        Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "clone " +
                (x + x1) + " " + (y + y1) + " " + (z + z1) + " " +
                (x + x2) + " " + (y + y2) + " " + (z + z2) + " " +
                (x + x3) + " " + (y + y3) + " " + (z + z3));
    }

    public static void placeBlock(int x1, int y1, int z1, String material, Location entity) {
        int x = entity.getBlockX();
        int y = entity.getBlockY();
        int z = entity.getBlockZ();
        Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "setblock " +
                (x + x1) + " " + (y + y1) + " " + (z + z1) + " " + material);

    }

    private void buildParkour() {
        //fill -19 179 -8 -19 177 -8 minecraft:ladder[facing=east]
        fillArea(6, 105, -19, 6, 101, -19, "minecraft:ladder[facing=east]", armorStand);
        fillArea(-19, 107, -8, -19, 108, -8, "minecraft:ladder[facing=east]", armorStand);
        placeBlock(-19, 110, -10, "minecraft:ladder[facing=east]", armorStand);
        placeBlock(-19, 111, -14, "minecraft:oak_trapdoor[facing=east,half=bottom]", armorStand);
        placeBlock(-19, 111, -19, "minecraft:oak_trapdoor[facing=south,half=top]", armorStand);

        placeBlock(-16, 112, -19, "minecraft:smooth_stone_slab[type=top]", armorStand);
        placeBlock(-14, 112, -19, "minecraft:smooth_stone_slab[type=top]", armorStand);
        placeBlock(-12, 112, -19, "minecraft:smooth_stone_slab[type=top]", armorStand);

        placeBlock(-16, 115, -19, "minecraft:smooth_stone_slab[type=bottom]", armorStand);
        placeBlock(-14, 115, -19, "minecraft:smooth_stone_slab[type=bottom]", armorStand);
        placeBlock(-12, 115, -19, "minecraft:smooth_stone_slab[type=bottom]", armorStand);

        placeBlock(-9, 112, -16, "minecraft:oak_fence", armorStand);

        fillArea(-6, 112, -14, -4, 112, -14, "minecraft:oak_fence", armorStand);
        fillArea(-5, 112, -13, -5, 112, -15, "minecraft:oak_fence", armorStand);
        fillArea(-5, 112, -14, -5, 115, -14, "minecraft:oak_planks", armorStand);
        fillArea(-5, 116, -14, -5, 119, -14, "minecraft:oak_fence", armorStand);

        fillArea(0, 112, -14, 2, 112, -14, "minecraft:oak_fence", armorStand);
        fillArea(1, 112, -13, 1, 112, -15, "minecraft:oak_fence", armorStand);
        fillArea(1, 112, -14, 1, 115, -14, "minecraft:oak_planks", armorStand);
        fillArea(1, 116, -14, 1, 119, -14, "minecraft:oak_fence", armorStand);


        placeBlock(6, 113, -14, "minecraft:oak_stairs[facing=east, half=bottom]", armorStand);
        placeBlock(7, 113, -14, "minecraft:oak_stairs[facing=west, half=bottom]", armorStand);
        placeBlock(6, 112, -14, "minecraft:oak_stairs[facing=east, half=top]", armorStand);
        placeBlock(7, 112, -14, "minecraft:oak_stairs[facing=west, half=top]", armorStand);

        fillArea(10, 114, -15, 10, 114, -16, "minecraft:oak_stairs[facing=west, half=bottom]", armorStand);
        fillArea(11, 113, -15, 11, 113, -16, "minecraft:oak_stairs[facing=west, half=bottom]", armorStand);

        fillArea(16, 115, -17, 16, 115, -18, "minecraft:oak_stairs[facing=east, half=bottom]", armorStand);
        fillArea(15, 114, -17, 15, 114, -18, "minecraft:oak_stairs[facing=east, half=bottom]", armorStand);

        fillArea(17, 116, -17, 19, 116, -19, "minecraft:quartz_slab[type=top]", armorStand);
    }

    private void buildBox() {
        fillArea(20, 100, 20, -20, 110, -20, "minecraft:barrier", armorStand);
        fillArea(20, 110, 20, -20, 120, -20, "minecraft:barrier", armorStand);
        fillArea(19, 101, 19, -19, 119, -19, "minecraft:air", armorStand);
    }

    private void buildBoards() {
        fillArea(-5, 105, -19, 5, 101, -19, "minecraft:black_concrete", armorStand);

        // Fill commands for the positive Z axis
        fillArea(10, 105, 17, 11, 101, 17, "minecraft:black_concrete", armorStand);
        fillArea(12, 105, 16, 12, 101, 16, "minecraft:black_concrete", armorStand);
        fillArea(13, 105, 15, 13, 101, 15, "minecraft:black_concrete", armorStand);
        fillArea(14, 105, 14, 14, 101, 14, "minecraft:black_concrete", armorStand);
        fillArea(15, 105, 13, 15, 101, 13, "minecraft:black_concrete", armorStand);
        fillArea(16, 105, 12, 16, 101, 12, "minecraft:black_concrete", armorStand);
        fillArea(17, 105, 11, 17, 101, 10, "minecraft:black_concrete", armorStand);

        // Fill commands for the negative X, positive Z axis
        fillArea(-10, 105, 17, -11, 101, 17, "minecraft:black_concrete", armorStand);
        fillArea(-12, 105, 16, -12, 101, 16, "minecraft:black_concrete", armorStand);
        fillArea(-13, 105, 15, -13, 101, 15, "minecraft:black_concrete", armorStand);
        fillArea(-14, 105, 14, -14, 101, 14, "minecraft:black_concrete", armorStand);
        fillArea(-15, 105, 13, -15, 101, 13, "minecraft:black_concrete", armorStand);
        fillArea(-16, 105, 12, -16, 101, 12, "minecraft:black_concrete", armorStand);
        fillArea(-17, 105, 11, -17, 101, 10, "minecraft:black_concrete", armorStand);

        // Fill commands for the positive X, negative Z axis
        fillArea(10, 105, -17, 11, 101, -17, "minecraft:black_concrete", armorStand);
        fillArea(12, 105, -16, 12, 101, -16, "minecraft:black_concrete", armorStand);
        fillArea(13, 105, -15, 13, 101, -15, "minecraft:black_concrete", armorStand);
        fillArea(14, 105, -14, 14, 101, -14, "minecraft:black_concrete", armorStand);
        fillArea(15, 105, -13, 15, 101, -13, "minecraft:black_concrete", armorStand);
        fillArea(16, 105, -12, 16, 101, -12, "minecraft:black_concrete", armorStand);
        fillArea(17, 105, -11, 17, 101, -10, "minecraft:black_concrete", armorStand);

        // Center fill line on positive Z axis
        fillArea(-5, 105, 19, 5, 101, 19, "minecraft:black_concrete", armorStand);

        // Fill commands for the negative X, negative Z axis
        fillArea(-10, 105, -17, -11, 101, -17, "minecraft:black_concrete", armorStand);
        fillArea(-12, 105, -16, -12, 101, -16, "minecraft:black_concrete", armorStand);
        fillArea(-13, 105, -15, -13, 101, -15, "minecraft:black_concrete", armorStand);
        fillArea(-14, 105, -14, -14, 101, -14, "minecraft:black_concrete", armorStand);
        fillArea(-15, 105, -13, -15, 101, -13, "minecraft:black_concrete", armorStand);
        fillArea(-16, 105, -12, -16, 101, -12, "minecraft:black_concrete", armorStand);
        fillArea(-17, 105, -11, -17, 101, -10, "minecraft:black_concrete", armorStand);

        // Vertical lines on the X axis
        fillArea(19, 105, 5, 19, 101, -5, "minecraft:black_concrete", armorStand);
        fillArea(-19, 105, 5, -19, 101, -5, "minecraft:black_concrete", armorStand);
    }

    private void buildSpawn() {
        fillArea(5, 101, 5, -5, 101, -5, "minecraft:quartz_block", armorStand);

        placeBlock(5, 101, 5, "minecraft:air", armorStand);
        placeBlock(5, 101, -5, "minecraft:air", armorStand);
        placeBlock(-5, 101, 5, "minecraft:air", armorStand);
        placeBlock(-5, 101, -5, "minecraft:air", armorStand);

        fillArea(3, 101, 5, -3, 101, 5, "minecraft:quartz_slab", armorStand);
        fillArea(3, 101, -5, -3, 101, -5, "minecraft:quartz_slab", armorStand);
        fillArea(5, 101, 3, 5, 101, -3, "minecraft:quartz_slab", armorStand);
        fillArea(-5, 101, 3, -5, 101, -3, "minecraft:quartz_slab", armorStand);

        fillArea(1, 101, 5, -1, 101, 5, "minecraft:quartz_bricks", armorStand);
        fillArea(1, 101, -5, -1, 101, -5, "minecraft:quartz_bricks", armorStand);
        fillArea(5, 101, 1, 5, 101, -1, "minecraft:quartz_bricks", armorStand);
        fillArea(-5, 101, 1, -5, 101, -1, "minecraft:quartz_bricks", armorStand);

        fillArea(2, 101, 6, -2, 101, 6, "minecraft:quartz_slab", armorStand);
        fillArea(2, 101, -6, -2, 101, -6, "minecraft:quartz_slab", armorStand);
        fillArea(6, 101, 2, 6, 101, -2, "minecraft:quartz_slab", armorStand);
        fillArea(-6, 101, 2, -6, 101, -2, "minecraft:quartz_slab", armorStand);

        fillArea(2, 101, 4, -2, 101, 4, "minecraft:quartz_bricks", armorStand);
        fillArea(2, 101, -4, -2, 101, -4, "minecraft:quartz_bricks", armorStand);
        fillArea(4, 101, 2, 4, 101, -2, "minecraft:quartz_bricks", armorStand);
        fillArea(-4, 101, 2, -4, 101, -2, "minecraft:quartz_bricks", armorStand);

        fillArea(2, 101, 2, -2, 101, 2, "minecraft:chiseled_quartz_block", armorStand);
        fillArea(2, 101, 2, 2, 101, -2, "minecraft:chiseled_quartz_block", armorStand);
        fillArea(-2, 101, -2, -2, 101, 2, "minecraft:chiseled_quartz_block", armorStand);
        fillArea(-2, 101, -2, 2, 101, -2, "minecraft:chiseled_quartz_block", armorStand);

        placeBlock(2, 101, 0, "minecraft:quartz_block", armorStand);
        placeBlock(-2, 101, 0, "minecraft:quartz_block", armorStand);
        placeBlock(0, 101, 2, "minecraft:quartz_block", armorStand);
        placeBlock(0, 101, -2, "minecraft:quartz_block", armorStand);

        placeBlock(2, 101, 3, "minecraft:quartz_stairs[facing=south]", armorStand);
        placeBlock(-2, 101, 3, "minecraft:quartz_stairs[facing=south]", armorStand);
        placeBlock(2, 101, -3, "minecraft:quartz_stairs[facing=north]", armorStand);
        placeBlock(-2, 101, -3, "minecraft:quartz_stairs[facing=north]", armorStand);

        placeBlock(3, 101, 2, "minecraft:quartz_stairs[facing=east]", armorStand);
        placeBlock(-3, 101, 2, "minecraft:quartz_stairs[facing=west]", armorStand);
        placeBlock(3, 101, -2, "minecraft:quartz_stairs[facing=east]", armorStand);
        placeBlock(-3, 101, -2, "minecraft:quartz_stairs[facing=west]", armorStand);

        placeBlock(3, 101, 3, "minecraft:quartz_stairs[facing=east,shape=inner_right]", armorStand);
        placeBlock(-3, 101, 3, "minecraft:quartz_stairs[facing=west,shape=inner_right]", armorStand);
        placeBlock(3, 101, -3, "minecraft:quartz_stairs[shape=inner_left]", armorStand);
        placeBlock(-3, 101, -3, "minecraft:quartz_stairs[shape=inner_left]", armorStand);

        placeBlock(4, 102, 5, "minecraft:quartz_stairs[facing=north]", armorStand);
        placeBlock(4, 102, 3, "minecraft:quartz_stairs[facing=south]", armorStand);
        placeBlock(3, 102, 4, "minecraft:quartz_stairs[facing=east]", armorStand);
        placeBlock(5, 102, 4, "minecraft:quartz_stairs[facing=west]", armorStand);

        fillArea(4, 102, 4, 4, 106, 4, "minecraft:quartz_pillar", armorStand);
        placeBlock(4, 107, 4, "minecraft:netherrack", armorStand);
        placeBlock(4, 108, 4, "minecraft:fire", armorStand);

        placeBlock(4, 107, 5, "minecraft:quartz_stairs[facing=north,half=top]", armorStand);
        placeBlock(4, 107, 3, "minecraft:quartz_stairs[facing=south,half=top]", armorStand);
        placeBlock(3, 107, 4, "minecraft:quartz_stairs[facing=east,half=top]", armorStand);
        placeBlock(5, 107, 4, "minecraft:quartz_stairs[facing=west,half=top]", armorStand);

        placeBlock(4, 108, 5, "minecraft:quartz_slab", armorStand);
        placeBlock(4, 108, 3, "minecraft:quartz_slab", armorStand);
        placeBlock(3, 108, 4, "minecraft:quartz_slab", armorStand);
        placeBlock(5, 108, 4, "minecraft:quartz_slab", armorStand);

// Clone commands
        clone(3, 102, 3, 5, 108, 5, -5, 102, 3, armorStand);
        clone(3, 102, 3, 5, 108, 5, 3, 102, -5, armorStand);
        clone(3, 102, 3, 5, 108, 5, -5, 102, -5, armorStand);

        placeBlock(-4, 107, 2, "minecraft:quartz_slab[type=top]", armorStand);
        placeBlock(-4, 107, -2, "minecraft:quartz_slab[type=top]", armorStand);
        placeBlock(-4, 108, 0, "minecraft:quartz_slab[type=top]", armorStand);
        placeBlock(-4, 109, 0, "minecraft:quartz_slab[type=bottom]", armorStand);

        placeBlock(-4, 108, 2, "minecraft:quartz_stairs[half=bottom,facing=north]", armorStand);
        placeBlock(-4, 108, -2, "minecraft:quartz_stairs[half=bottom,facing=south]", armorStand);
        placeBlock(-4, 108, 1, "minecraft:quartz_stairs[half=top,facing=south]", armorStand);
        placeBlock(-4, 108, -1, "minecraft:quartz_stairs[half=top,facing=north]", armorStand);

        placeBlock(2, 107, 4, "minecraft:quartz_slab[type=top]", armorStand);
        placeBlock(-2, 107, 4, "minecraft:quartz_slab[type=top]", armorStand);
        placeBlock(0, 108, 4, "minecraft:quartz_slab[type=top]", armorStand);
        placeBlock(0, 109, 4, "minecraft:quartz_slab[type=bottom]", armorStand);

        placeBlock(2, 108, 4, "minecraft:quartz_stairs[half=bottom,facing=west]", armorStand);
        placeBlock(-2, 108, 4, "minecraft:quartz_stairs[half=bottom,facing=east]", armorStand);
        placeBlock(1, 108, 4, "minecraft:quartz_stairs[half=top,facing=east]", armorStand);
        placeBlock(-1, 108, 4, "minecraft:quartz_stairs[half=top,facing=west]", armorStand);

// Additional clone commands
        clone(-4, 107, 2, -4, 109, -2, 4, 107, -2, armorStand);
        clone(2, 107, 4, -2, 109, 4, -2, 107, -4, armorStand);

        placeBlock(0, 101, 0, "minecraft:beacon", armorStand);
    }


}
