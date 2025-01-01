package me.depickcator.ascension.listeners.ChestGeneration.Tables;

import me.depickcator.ascension.LootTables.Blocks.ForageBlocks.ForageBlocks;
import me.depickcator.ascension.Player.Data.PlayerData;
import me.depickcator.ascension.Skills.SkillExpAmount;
import me.depickcator.ascension.listeners.ChestGeneration.ChestLootTable;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import java.util.List;

public class Mineshaft extends ChestLootTable {
    public Mineshaft() {
        super("minecraft:chests/abandoned_mineshaft");
    }

    @Override
    public void addLootToTable(PlayerData pD, List<ItemStack> lootList) {
        lootList.add(new ItemStack(Material.NAME_TAG, 1));
        addShardsOfTheFallen(1, lootList);
        givePlayerForagingExp(pD, SkillExpAmount.FORAGING_LEGENDARY.getExp());
    }
}
