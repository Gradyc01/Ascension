package me.depickcator.ascension.listeners.ChestGeneration.Tables;

import me.depickcator.ascension.LootTables.Blocks.ForageBlocks.ForageBlocks;
import me.depickcator.ascension.Player.Data.PlayerData;
import me.depickcator.ascension.listeners.ChestGeneration.ChestLootTable;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import java.util.List;

public class PillagerOutpost extends ChestLootTable {
    public PillagerOutpost() {
        super("minecraft:chests/pillager_outpost");
    }

    @Override
    public void addLootToTable(PlayerData pD, List<ItemStack> lootList) {
        lootList.add(new ItemStack(Material.GOAT_HORN, 1));
        addShardsOfTheFallen(1, 2, lootList);
        givePlayerForagingExp(pD, ForageBlocks.FORAGING_LEGENDARY);
    }
}
