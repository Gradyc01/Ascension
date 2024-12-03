package me.depickcator.ascension.General;

import me.depickcator.ascension.Ascension;
import org.bukkit.Bukkit;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Entity;
import org.bukkit.scheduler.BukkitScheduler;

public class BuildLobby implements Runnable {
    private final ArmorStand armorStand;
    private final Ascension plugin;
    public BuildLobby(ArmorStand armor) {
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
    }

    public static void fillArea(int x1, int y1, int z1, int x2, int y2, int z2, String material, Entity entity) {
        int x = entity.getLocation().getBlockX();
        int y = entity.getLocation().getBlockY();
        int z = entity.getLocation().getBlockZ();
        Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "fill " +
                (x + x1) + " " + (y + y1) + " " + (z + z1) + " " + (x + x2) + " " + (y + y2) + " " + (z + z2) + " " + material);
    }

    public static void clone(int x1, int y1, int z1, int x2, int y2, int z2, int x3, int y3, int z3, Entity entity) {
        int x = entity.getLocation().getBlockX();
        int y = entity.getLocation().getBlockY();
        int z = entity.getLocation().getBlockZ();
        Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "clone " +
                (x + x1) + " " + (y + y1) + " " + (z + z1) + " " +
                (x + x2) + " " + (y + y2) + " " + (z + z2) + " " +
                (x + x3) + " " + (y + y3) + " " + (z + z3));
    }

    public static void placeBlock(int x1, int y1, int z1, String material, Entity entity) {
        int x = entity.getLocation().getBlockX();
        int y = entity.getLocation().getBlockY();
        int z = entity.getLocation().getBlockZ();
        Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "setblock " +
                (x + x1) + " " + (y + y1) + " " + (z + z1) + " " + material);

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


    // private void buildTeamSign() {
    //     // Letter T
    //     placeBlock(-10, 115, -19, "minecraft:black_concrete", armorStand);
    //     placeBlock(-9, 115, -19, "minecraft:black_concrete", armorStand);
    //     placeBlock(-8, 115, -19, "minecraft:black_concrete", armorStand);
    //     placeBlock(-9, 114, -19, "minecraft:black_concrete", armorStand);
    //     placeBlock(-9, 113, -19, "minecraft:black_concrete", armorStand);
    //     placeBlock(-9, 112, -19, "minecraft:black_concrete", armorStand);
    //     placeBlock(-9, 111, -19, "minecraft:black_concrete", armorStand);

    //     // Letter E
    //     fillArea(-6, 115, -19, -4, 115, -19, "minecraft:black_concrete", armorStand);
    //     fillArea(-6, 113, -19, -5, 113, -19, "minecraft:black_concrete", armorStand);
    //     fillArea(-6, 111, -19, -4, 111, -19, "minecraft:black_concrete", armorStand);
    //     fillArea(-6, 115, -19, -6, 111, -19, "minecraft:black_concrete", armorStand);

    //     // Letter A
    //     fillArea(-2, 115, -19, -2, 111, -19, "minecraft:black_concrete", armorStand);
    //     fillArea(0, 115, -19, 0, 111, -19, "minecraft:black_concrete", armorStand);
    //     placeBlock(-1, 113, -19, "minecraft:black_concrete", armorStand);
    //     placeBlock(-1, 115, -19, "minecraft:black_concrete", armorStand);

    //     // Letter M
    //     fillArea(2, 115, -19, 2, 111, -19, "minecraft:black_concrete", armorStand);
    //     fillArea(6, 115, -19, 6, 111, -19, "minecraft:black_concrete", armorStand);
    //     placeBlock(3, 115, -19, "minecraft:black_concrete", armorStand);
    //     placeBlock(5, 115, -19, "minecraft:black_concrete", armorStand);
    //     placeBlock(4, 114, -19, "minecraft:black_concrete", armorStand);
    //     placeBlock(4, 113, -19, "minecraft:black_concrete", armorStand);

    //     // Letter S
    //     fillArea(8, 115, -19, 10, 115, -19, "minecraft:black_concrete", armorStand);
    //     fillArea(8, 114, -19, 8, 113, -19, "minecraft:black_concrete", armorStand);
    //     fillArea(8, 113, -19, 10, 113, -19, "minecraft:black_concrete", armorStand);
    //     fillArea(10, 113, -19, 10, 111, -19, "minecraft:black_concrete", armorStand);
    //     fillArea(8, 111, -19, 10, 111, -19, "minecraft:black_concrete", armorStand);
    // }

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
