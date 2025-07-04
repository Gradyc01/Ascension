package me.depickcator.ascension.listeners.ChestGeneration.Tables;

import me.depickcator.ascension.Items.Uncraftable.HardenedSaddle;
import me.depickcator.ascension.Player.Data.PlayerData;
import me.depickcator.ascension.Skills.SkillExpAmount;
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
        lootList.add(HardenedSaddle.getInstance().getResult());
        addShardsOfTheFallen(1, 2, lootList);
        givePlayerForagingExp(pD, SkillExpAmount.FORAGING_VERY_RARE.getExp());
    }
}
