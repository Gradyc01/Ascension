package me.depickcator.ascension.General;

import me.depickcator.ascension.Ascension;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.data.Bisected;
import org.bukkit.block.data.BlockData;
import org.bukkit.block.data.Directional;
import org.bukkit.block.data.type.Ladder;
import org.bukkit.block.data.type.Slab;
import org.bukkit.block.data.type.Stairs;
import org.bukkit.block.data.type.TrapDoor;
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

    public static void fillArea(int x1, int y1, int z1, int x2, int y2, int z2, BlockData blockRef, Location entity) {
        World world = entity.getWorld();
        int baseX = entity.getBlockX();
        int baseY = entity.getBlockY();
        int baseZ = entity.getBlockZ();

        for (int x = Math.min(x1, x2); x <= Math.max(x1, x2); x++) {
            for (int y = Math.min(y1, y2); y <= Math.max(y1, y2); y++) {
                for (int z = Math.min(z1, z2); z <= Math.max(z1, z2); z++) {
                    Block targetBlock = world.getBlockAt(baseX + x, baseY + y, baseZ + z);
                    targetBlock.setBlockData(blockRef);
                }
            }
        }
    }

    public static void fillArea(int x1, int y1, int z1, int x2, int y2, int z2, BlockData blockRef, ArmorStand entity) {
        fillArea(x1, y1, z1, x2, y2, z2, blockRef, entity.getLocation());
    }

    public static void placeBlock(int x1, int y1, int z1, BlockData blockRef, Location entity) {
        World world = entity.getWorld();
//            Material material = blockRef.getType();

        int x = entity.getBlockX() + x1;
        int y = entity.getBlockY() + y1;
        int z = entity.getBlockZ() + z1;

        Block targetBlock = world.getBlockAt(x, y, z);
        targetBlock.setBlockData(blockRef);
    }

    public static void clone(int x1, int y1, int z1, int x2, int y2, int z2, int x3, int y3, int z3, Location entity) {
        World world = entity.getWorld();
        int baseX = entity.getBlockX();
        int baseY = entity.getBlockY();
        int baseZ = entity.getBlockZ();

        int minX = Math.min(x1, x2);
        int minY = Math.min(y1, y2);
        int minZ = Math.min(z1, z2);
        int maxX = Math.max(x1, x2);
        int maxY = Math.max(y1, y2);
        int maxZ = Math.max(z1, z2);

        for (int x = 0; x <= maxX - minX; x++) {
            for (int y = 0; y <= maxY - minY; y++) {
                for (int z = 0; z <= maxZ - minZ; z++) {
                    int sourceX = baseX + minX + x;
                    int sourceY = baseY + minY + y;
                    int sourceZ = baseZ + minZ + z;

                    Block sourceBlock = world.getBlockAt(sourceX, sourceY, sourceZ);
//                    Material material = sourceBlock.getType();

                    int targetX = baseX + x3 + x;
                    int targetY = baseY + y3 + y;
                    int targetZ = baseZ + z3 + z;

                    Block targetBlock = world.getBlockAt(targetX, targetY, targetZ);
                    targetBlock.setBlockData(sourceBlock.getBlockData());
                }
            }
        }
    }


    private void buildParkour() {
        //fill -19 179 -8 -19 177 -8 minecraft:ladder[facing=east]
        Directional ladder = (Directional) Material.LADDER.createBlockData();
        ladder.setFacing(BlockFace.EAST);
        fillArea(6, 105, -19, 6, 101, -19, ladder, armorStand);
        fillArea(-19, 107, -8, -19, 108, -8, ladder, armorStand);
        placeBlock(-19, 109, -11, ladder, armorStand);

        TrapDoor trapDoor1 = (TrapDoor) Material.OAK_TRAPDOOR.createBlockData();
        trapDoor1.setFacing(BlockFace.EAST);
        trapDoor1.setHalf(Bisected.Half.BOTTOM);
        placeBlock(-19, 111, -14, trapDoor1, armorStand);
        TrapDoor trapDoor2 = (TrapDoor) Material.OAK_TRAPDOOR.createBlockData();
        trapDoor2.setFacing(BlockFace.SOUTH);
        trapDoor2.setHalf(Bisected.Half.TOP);
        placeBlock(-19, 111, -18, trapDoor2, armorStand);

        Slab smoothStoneSlab1 = (Slab) Material.SMOOTH_STONE_SLAB.createBlockData();
        smoothStoneSlab1.setType(Slab.Type.TOP);
        placeBlock(-16, 112, -19, smoothStoneSlab1, armorStand);
        placeBlock(-14, 112, -19, smoothStoneSlab1, armorStand);
        placeBlock(-12, 112, -19, smoothStoneSlab1, armorStand);

        Slab smoothStoneSlab2 = (Slab) Material.SMOOTH_STONE_SLAB.createBlockData();
        smoothStoneSlab2.setType(Slab.Type.BOTTOM);
        placeBlock(-16, 115, -19, smoothStoneSlab2, armorStand);
        placeBlock(-14, 115, -19, smoothStoneSlab2, armorStand);
        placeBlock(-12, 115, -19, smoothStoneSlab2, armorStand);

        BlockData oakFence = Material.OAK_FENCE.createBlockData();
        BlockData oakPlanks = Material.OAK_PLANKS.createBlockData();
        placeBlock(-9, 112, -16, oakFence, armorStand);

        fillArea(-6, 112, -14, -4, 112, -14, oakFence, armorStand);
        fillArea(-5, 112, -13, -5, 112, -15, oakFence, armorStand);
        fillArea(-5, 112, -14, -5, 115, -14, oakPlanks, armorStand);
        fillArea(-5, 116, -14, -5, 119, -14, oakFence, armorStand);

        fillArea(0, 112, -14, 2, 112, -14, oakFence, armorStand);
        fillArea(1, 112, -13, 1, 112, -15, oakFence, armorStand);
        fillArea(1, 112, -14, 1, 115, -14, oakPlanks, armorStand);
        fillArea(1, 116, -14, 1, 119, -14, oakFence, armorStand);

        Stairs oakStairs_east_bottom = (Stairs) Material.OAK_STAIRS.createBlockData();
        oakStairs_east_bottom.setFacing(BlockFace.EAST);
        oakStairs_east_bottom.setHalf(Bisected.Half.BOTTOM);

        Stairs oakStairs_west_bottom = (Stairs) Material.OAK_STAIRS.createBlockData();
        oakStairs_west_bottom.setFacing(BlockFace.WEST);
        oakStairs_west_bottom.setHalf(Bisected.Half.BOTTOM);

        Stairs oakStairs_east_top = (Stairs) Material.OAK_STAIRS.createBlockData();
        oakStairs_east_top.setFacing(BlockFace.EAST);
        oakStairs_east_top.setHalf(Bisected.Half.TOP);

        Stairs oakStairs_west_top = (Stairs) Material.OAK_STAIRS.createBlockData();
        oakStairs_west_top.setFacing(BlockFace.WEST);
        oakStairs_west_top.setHalf(Bisected.Half.TOP);


        placeBlock(6, 113, -14, oakStairs_east_bottom, armorStand);
        placeBlock(7, 113, -14, oakStairs_west_bottom, armorStand);
        placeBlock(6, 112, -14, oakStairs_east_top, armorStand);
        placeBlock(7, 112, -14, oakStairs_west_top, armorStand);

        fillArea(10, 114, -15, 10, 114, -16, oakStairs_west_bottom, armorStand);
        fillArea(11, 113, -15, 11, 113, -16, oakStairs_west_bottom, armorStand);

        fillArea(16, 115, -17, 16, 115, -18, oakStairs_east_bottom, armorStand);
        fillArea(15, 114, -17, 15, 114, -18, oakStairs_east_bottom, armorStand);

        Slab quartzSlab =  (Slab) Material.QUARTZ_SLAB.createBlockData();
        quartzSlab.setType(Slab.Type.TOP);

        fillArea(17, 116, -17, 19, 116, -19, quartzSlab, armorStand);
    }

    private void buildBox() {
        fillArea(20, 100, 20, -20, 110, -20, Material.BARRIER.createBlockData(), armorStand);
        fillArea(20, 110, 20, -20, 120, -20, Material.BARRIER.createBlockData(), armorStand);
        fillArea(19, 101, 19, -19, 119, -19, Material.AIR.createBlockData(), armorStand);
    }

    private void buildBoards() {
        BlockData black_concrete = Material.BLACK_CONCRETE.createBlockData();
        fillArea(-5, 105, -19, 5, 101, -19, black_concrete, armorStand);

        // Fill commands for the positive Z axis
        fillArea(10, 105, 17, 11, 101, 17, black_concrete, armorStand);
        fillArea(12, 105, 16, 12, 101, 16, black_concrete, armorStand);
        fillArea(13, 105, 15, 13, 101, 15, black_concrete, armorStand);
        fillArea(14, 105, 14, 14, 101, 14, black_concrete, armorStand);
        fillArea(15, 105, 13, 15, 101, 13, black_concrete, armorStand);
        fillArea(16, 105, 12, 16, 101, 12, black_concrete, armorStand);
        fillArea(17, 105, 11, 17, 101, 10, black_concrete, armorStand);

        // Fill commands for the negative X, positive Z axis
        fillArea(-10, 105, 17, -11, 101, 17, black_concrete, armorStand);
        fillArea(-12, 105, 16, -12, 101, 16, black_concrete, armorStand);
        fillArea(-13, 105, 15, -13, 101, 15, black_concrete, armorStand);
        fillArea(-14, 105, 14, -14, 101, 14, black_concrete, armorStand);
        fillArea(-15, 105, 13, -15, 101, 13, black_concrete, armorStand);
        fillArea(-16, 105, 12, -16, 101, 12, black_concrete, armorStand);
        fillArea(-17, 105, 11, -17, 101, 10, black_concrete, armorStand);

        // Fill commands for the positive X, negative Z axis
        fillArea(10, 105, -17, 11, 101, -17, black_concrete, armorStand);
        fillArea(12, 105, -16, 12, 101, -16, black_concrete, armorStand);
        fillArea(13, 105, -15, 13, 101, -15, black_concrete, armorStand);
        fillArea(14, 105, -14, 14, 101, -14, black_concrete, armorStand);
        fillArea(15, 105, -13, 15, 101, -13, black_concrete, armorStand);
        fillArea(16, 105, -12, 16, 101, -12, black_concrete, armorStand);
        fillArea(17, 105, -11, 17, 101, -10, black_concrete, armorStand);

        // Center fill line on positive Z axis
        fillArea(-5, 105, 19, 5, 101, 19, black_concrete, armorStand);

        // Fill commands for the negative X, negative Z axis
        fillArea(-10, 105, -17, -11, 101, -17, black_concrete, armorStand);
        fillArea(-12, 105, -16, -12, 101, -16, black_concrete, armorStand);
        fillArea(-13, 105, -15, -13, 101, -15, black_concrete, armorStand);
        fillArea(-14, 105, -14, -14, 101, -14, black_concrete, armorStand);
        fillArea(-15, 105, -13, -15, 101, -13, black_concrete, armorStand);
        fillArea(-16, 105, -12, -16, 101, -12, black_concrete, armorStand);
        fillArea(-17, 105, -11, -17, 101, -10, black_concrete, armorStand);

        // Vertical lines on the X axis
        fillArea(19, 105, 5, 19, 101, -5, black_concrete, armorStand);
        fillArea(-19, 105, 5, -19, 101, -5, black_concrete, armorStand);
    }

    private void buildSpawn() {
        BlockData quartz_block = Material.QUARTZ_BLOCK.createBlockData();
        fillArea(5, 101, 5, -5, 101, -5, quartz_block, armorStand);
        BlockData air = Material.AIR.createBlockData();

        placeBlock(5, 101, 5, air, armorStand);
        placeBlock(5, 101, -5, air, armorStand);
        placeBlock(-5, 101, 5, air, armorStand);
        placeBlock(-5, 101, -5, air, armorStand);

        BlockData quartz_slab = Material.QUARTZ_SLAB.createBlockData();
        fillArea(3, 101, 5, -3, 101, 5, quartz_slab, armorStand);
        fillArea(3, 101, -5, -3, 101, -5, quartz_slab, armorStand);
        fillArea(5, 101, 3, 5, 101, -3, quartz_slab, armorStand);
        fillArea(-5, 101, 3, -5, 101, -3, quartz_slab, armorStand);

        BlockData quartz_bricks = Material.QUARTZ_BRICKS.createBlockData();
        fillArea(1, 101, 5, -1, 101, 5, quartz_bricks, armorStand);
        fillArea(1, 101, -5, -1, 101, -5, quartz_bricks, armorStand);
        fillArea(5, 101, 1, 5, 101, -1, quartz_bricks, armorStand);
        fillArea(-5, 101, 1, -5, 101, -1, quartz_bricks, armorStand);

        fillArea(2, 101, 6, -2, 101, 6, quartz_slab, armorStand);
        fillArea(2, 101, -6, -2, 101, -6, quartz_slab, armorStand);
        fillArea(6, 101, 2, 6, 101, -2, quartz_slab, armorStand);
        fillArea(-6, 101, 2, -6, 101, -2, quartz_slab, armorStand);

        fillArea(2, 101, 4, -2, 101, 4, quartz_bricks, armorStand);
        fillArea(2, 101, -4, -2, 101, -4, quartz_bricks, armorStand);
        fillArea(4, 101, 2, 4, 101, -2, quartz_bricks, armorStand);
        fillArea(-4, 101, 2, -4, 101, -2, quartz_bricks, armorStand);

        BlockData chiseled_quartz_block = Material.CHISELED_QUARTZ_BLOCK.createBlockData();
        fillArea(2, 101, 2, -2, 101, 2, chiseled_quartz_block, armorStand);
        fillArea(2, 101, 2, 2, 101, -2, chiseled_quartz_block, armorStand);
        fillArea(-2, 101, -2, -2, 101, 2, chiseled_quartz_block, armorStand);
        fillArea(-2, 101, -2, 2, 101, -2, chiseled_quartz_block, armorStand);

        placeBlock(2, 101, 0, quartz_block, armorStand);
        placeBlock(-2, 101, 0, quartz_block, armorStand);
        placeBlock(0, 101, 2, quartz_block, armorStand);
        placeBlock(0, 101, -2, quartz_block, armorStand);

        Stairs quartz_stairs_south = (Stairs) Material.QUARTZ_STAIRS.createBlockData();
        quartz_stairs_south.setFacing(BlockFace.SOUTH);

        Stairs quartz_stairs_north = (Stairs) Material.QUARTZ_STAIRS.createBlockData();
        quartz_stairs_north.setFacing(BlockFace.NORTH);

        placeBlock(2, 101, 3, quartz_stairs_south, armorStand);
        placeBlock(-2, 101, 3, quartz_stairs_south, armorStand);
        placeBlock(2, 101, -3, quartz_stairs_north, armorStand);
        placeBlock(-2, 101, -3, quartz_stairs_north, armorStand);

        Stairs quartz_stairs_east = (Stairs) Material.QUARTZ_STAIRS.createBlockData();
        quartz_stairs_east.setFacing(BlockFace.EAST);

        Stairs quartz_stairs_west = (Stairs) Material.QUARTZ_STAIRS.createBlockData();
        quartz_stairs_west.setFacing(BlockFace.WEST);

        placeBlock(3, 101, 2, quartz_stairs_east, armorStand);
        placeBlock(-3, 101, 2, quartz_stairs_west, armorStand);
        placeBlock(3, 101, -2, quartz_stairs_east, armorStand);
        placeBlock(-3, 101, -2, quartz_stairs_west, armorStand);

        Stairs stairs1 = (Stairs) quartz_stairs_east.clone();
        stairs1.setShape(Stairs.Shape.INNER_RIGHT);
        placeBlock(3, 101, 3, stairs1, armorStand);
        Stairs stairs2 = (Stairs) quartz_stairs_west.clone();
        stairs2.setShape(Stairs.Shape.INNER_LEFT);
        placeBlock(-3, 101, 3, stairs2, armorStand);
        Stairs stairs3 = (Stairs) Material.QUARTZ_STAIRS.createBlockData();
        stairs3.setShape(Stairs.Shape.INNER_RIGHT);
        placeBlock(3, 101, -3, stairs3, armorStand);
        Stairs stairs4 = (Stairs) Material.QUARTZ_STAIRS.createBlockData();
        stairs4.setShape(Stairs.Shape.INNER_LEFT);
        placeBlock(-3, 101, -3, stairs4, armorStand);


        placeBlock(4, 102, 5, quartz_stairs_north, armorStand);
        placeBlock(4, 102, 3, quartz_stairs_south, armorStand);
        placeBlock(3, 102, 4, quartz_stairs_east, armorStand);
        placeBlock(5, 102, 4, quartz_stairs_west, armorStand);

        fillArea(4, 102, 4, 4, 106, 4, Material.QUARTZ_PILLAR.createBlockData(), armorStand);
        placeBlock(4, 107, 4, Material.NETHERRACK.createBlockData(), armorStand);
        placeBlock(4, 108, 4, Material.FIRE.createBlockData(), armorStand);

        Stairs quartz_stairs_north_top = (Stairs) quartz_stairs_north.clone();
        quartz_stairs_north_top.setHalf(Stairs.Half.TOP);
        placeBlock(4, 107, 5, quartz_stairs_north_top, armorStand);
        Stairs quartz_stairs_south_top = (Stairs) quartz_stairs_south.clone();
        quartz_stairs_south_top.setHalf(Stairs.Half.TOP);
        placeBlock(4, 107, 3, quartz_stairs_south_top, armorStand);
        Stairs quartz_stairs_east_top = (Stairs) quartz_stairs_east.clone();
        quartz_stairs_east_top.setHalf(Stairs.Half.TOP);
        placeBlock(3, 107, 4, quartz_stairs_east_top, armorStand);
        Stairs quartz_stairs_west_top = (Stairs) quartz_stairs_west.clone();
        quartz_stairs_west_top.setHalf(Stairs.Half.TOP);
        placeBlock(5, 107, 4, quartz_stairs_west_top, armorStand);

        placeBlock(4, 108, 5, quartz_slab, armorStand);
        placeBlock(4, 108, 3, quartz_slab, armorStand);
        placeBlock(3, 108, 4, quartz_slab, armorStand);
        placeBlock(5, 108, 4, quartz_slab, armorStand);

        // Clone commands
        clone(3, 102, 3, 5, 108, 5, -5, 102, 3, armorStand);
        clone(3, 102, 3, 5, 108, 5, 3, 102, -5, armorStand);
        clone(3, 102, 3, 5, 108, 5, -5, 102, -5, armorStand);

        Slab quartz_slab_top =  (Slab) Material.QUARTZ_SLAB.createBlockData();
        quartz_slab_top.setType(Slab.Type.TOP);
        placeBlock(-4, 107, 2, quartz_slab_top, armorStand);
        placeBlock(-4, 107, -2, quartz_slab_top, armorStand);
        placeBlock(-4, 108, 0, quartz_slab_top, armorStand);
        Slab quartz_slab_bottom =  (Slab) Material.QUARTZ_SLAB.createBlockData();
        quartz_slab_top.setType(Slab.Type.BOTTOM);
        placeBlock(-4, 109, 0, quartz_slab_bottom, armorStand);

        placeBlock(-4, 108, 2, quartz_stairs_north, armorStand);
        placeBlock(-4, 108, -2, quartz_stairs_south, armorStand);
        placeBlock(-4, 108, 1, quartz_stairs_south_top, armorStand);
        placeBlock(-4, 108, -1, quartz_stairs_north_top, armorStand);


        Slab quartz_slab_top2 =  (Slab) Material.QUARTZ_SLAB.createBlockData();
        quartz_slab_top2.setType(Slab.Type.TOP);
        placeBlock(2, 107, 4, quartz_slab_top2, armorStand);
        placeBlock(-2, 107, 4, quartz_slab_top2, armorStand);
        placeBlock(0, 108, 4, quartz_slab_top2, armorStand);
        placeBlock(0, 109, 4, quartz_slab_bottom, armorStand);

        placeBlock(2, 108, 4, quartz_stairs_west, armorStand);
        placeBlock(-2, 108, 4, quartz_stairs_east, armorStand);
        placeBlock(1, 108, 4, quartz_stairs_east_top, armorStand);
        placeBlock(-1, 108, 4, quartz_stairs_west_top, armorStand);

        // Additional clone commands
        clone(-4, 107, 2, -4, 109, -2, 4, 107, -2, armorStand);
        clone(2, 107, 4, -2, 109, 4, -2, 107, -4, armorStand);

        placeBlock(0, 101, 0, Material.BEACON.createBlockData(), armorStand);
    }


}
