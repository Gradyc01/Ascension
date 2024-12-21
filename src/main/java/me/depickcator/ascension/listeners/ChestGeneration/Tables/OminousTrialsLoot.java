package me.depickcator.ascension.listeners.ChestGeneration.Tables;

import me.depickcator.ascension.LootTables.Blocks.ForageBlocks.ForageBlocks;
import me.depickcator.ascension.Player.Data.PlayerData;
import me.depickcator.ascension.listeners.ChestGeneration.ChestLootTable;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import java.util.List;

public class OminousTrialsLoot extends ChestLootTable {
    public OminousTrialsLoot() {
        super("minecraft:chests/trial_chambers/reward_ominous");
    }

    @Override
    public void addLootToTable(PlayerData pD, List<ItemStack> lootList) {
        lootList.add(new ItemStack(Material.HEAVY_CORE, 1));
        addShardsOfTheFallen(5, lootList);
        givePlayerForagingExp(pD, ForageBlocks.FORAGING_LEGENDARY);
    }
}
