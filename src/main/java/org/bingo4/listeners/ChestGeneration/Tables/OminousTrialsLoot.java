package org.bingo4.listeners.ChestGeneration.Tables;

import org.bingo4.Player.Data.PlayerData;
//import org.bingo4.Skills.SkillExpAmount;
import org.bingo4.listeners.ChestGeneration.ChestLootTable;
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
//        givePlayerForagingExp(pD, SkillExpAmount.FORAGING_LEGENDARY.getExp());
    }
}
