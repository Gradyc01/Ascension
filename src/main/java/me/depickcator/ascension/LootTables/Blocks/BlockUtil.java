package me.depickcator.ascension.LootTables.Blocks;

import me.depickcator.ascension.LootTables.Blocks.ForageBlocks.Aging;
import me.depickcator.ascension.LootTables.Blocks.ForageBlocks.DeadBushes;
import me.depickcator.ascension.LootTables.Blocks.ForageBlocks.Leaves;
import me.depickcator.ascension.LootTables.Blocks.ForageBlocks.Logs;
import me.depickcator.ascension.LootTables.Blocks.Ores.*;

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
