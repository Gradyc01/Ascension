package me.depickcator.ascensionBingo.LootTables.Blocks.ForageBlocks;


import org.bukkit.event.block.BlockPlaceEvent;

public interface ForageBlocks {
    public static int FORAGING_COMMON = 3;
    public static int FORAGING_UNCOMMON = 6;
    public static int FORAGING_RARE = 12;
    public static int FORAGING_VERY_RARE = 24;
    public static int FORAGING_LEGENDARY = 50;
    String PLACED_BY_PLAYER = "Placed_By_Player";
    void onPlacedForagingBlock(BlockPlaceEvent event);
}
