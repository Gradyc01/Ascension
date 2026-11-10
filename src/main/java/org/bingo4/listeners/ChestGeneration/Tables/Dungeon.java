package org.bingo4.listeners.ChestGeneration.Tables;

import org.bingo4.Items.Uncraftable.HardenedSaddle;
import org.bingo4.Player.Data.PlayerData;
//import org.bingo4.Skills.SkillExpAmount;
import org.bingo4.listeners.ChestGeneration.ChestLootTable;
import org.bukkit.inventory.ItemStack;

import java.util.List;

public class Dungeon extends ChestLootTable {
    public Dungeon() {
        super("minecraft:chests/simple_dungeon");
    }

    @Override
    public void addLootToTable(PlayerData pD, List<ItemStack> lootList) {
        lootList.add(HardenedSaddle.getInstance().getResult());
        addShardsOfTheFallen(1, 2, lootList);
//        givePlayerForagingExp(pD, SkillExpAmount.FORAGING_VERY_RARE.getExp());
    }
}
