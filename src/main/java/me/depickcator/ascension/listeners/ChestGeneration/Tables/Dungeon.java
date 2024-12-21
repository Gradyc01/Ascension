package me.depickcator.ascension.listeners.ChestGeneration.Tables;

import me.depickcator.ascension.LootTables.Blocks.ForageBlocks.ForageBlocks;
import me.depickcator.ascension.Player.Data.PlayerData;
import me.depickcator.ascension.listeners.ChestGeneration.ChestLootTable;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import java.util.List;

public class Dungeon extends ChestLootTable {
    public Dungeon() {
        super("minecraft:chests/simple_dungeon");
    }

    @Override
    public void addLootToTable(PlayerData pD, List<ItemStack> lootList) {
        lootList.add(new ItemStack(Material.SADDLE, 1));
        addShardsOfTheFallen(1, lootList);
        givePlayerForagingExp(pD, ForageBlocks.FORAGING_VERY_RARE);
    }
}
