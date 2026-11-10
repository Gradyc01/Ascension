package org.bingo4.LootTables.Blocks;

import org.bingo4.LootTables.Blocks.ForageBlocks.Aging;
import org.bingo4.LootTables.Blocks.ForageBlocks.DeadBushes;
import org.bingo4.LootTables.Blocks.ForageBlocks.Leaves;
import org.bingo4.LootTables.Blocks.ForageBlocks.Logs;
import org.bingo4.LootTables.Blocks.Ores.*;

public class BlockUtil {
//    public static int MINING_COMMON = 1;
//    public static int MINING_UNCOMMON = 5;
//    public static int MINING_RARE = 10;
//    public static int MINING_VERY_RARE = 20;
//    public static int MINING_LEGENDARY = 25;

    public BlockUtil() {
        ores();
        forageBlocks();
        new Spawner();
    }

    private void forageBlocks() {
        new Logs();
        new Aging();
        new Leaves();
        new DeadBushes();
    }

    private void ores() {
        new CoalOre();
        new CopperOre();
        new IronOre();
        new GoldOre();
        new LapisOre();
        new RedstoneOre();
        new DiamondOre();
        new EmeraldOre();
        new QuartzOre();
        new NetherGoldOre();
        new Stones();
    }
}
