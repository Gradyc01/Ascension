package me.depickcator.ascension.LootTables.Blocks;

import me.depickcator.ascension.Ascension;
import me.depickcator.ascension.LootTables.Blocks.ForageBlocks.Logs;
import me.depickcator.ascension.LootTables.Blocks.Ores.*;

public class BlockUtil {
    public static int MINING_COMMON = 1;
    public static int MINING_UNCOMMON = 5;
    public static int MINING_RARE = 10;
    public static int MINING_VERY_RARE = 20;
    public static int MINING_LEGENDARY = 25;
    private Ascension plugin;

    public BlockUtil(Ascension plugin) {
        this.plugin = plugin;
        ores();
        forageBlocks();
    }

    private void forageBlocks() {
        new Logs(plugin);
    }

    private void ores() {
        new CoalOre(plugin);
        new CopperOre(plugin);
        new IronOre(plugin);
        new GoldOre(plugin);
        new LapisOre(plugin);
        new RedstoneOre(plugin);
        new DiamondOre(plugin);
        new EmeraldOre(plugin);
        new QuartzOre(plugin);
        new NetherGoldOre(plugin);
    }
}
