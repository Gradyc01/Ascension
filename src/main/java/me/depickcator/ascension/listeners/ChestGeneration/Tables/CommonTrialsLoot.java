package me.depickcator.ascension.listeners.ChestGeneration.Tables;

import me.depickcator.ascension.Player.Data.PlayerData;
import me.depickcator.ascension.Skills.SkillExpAmount;
import me.depickcator.ascension.Utility.TextUtil;
import me.depickcator.ascension.listeners.ChestGeneration.ChestLootTable;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import java.util.List;
import java.util.Random;

public class CommonTrialsLoot extends ChestLootTable {
    public CommonTrialsLoot() {
        super("minecraft:chests/trial_chambers/reward");
    }

    @Override
    public void addLootToTable(PlayerData pD, List<ItemStack> lootList) {
        Random r = new Random();
        if (r.nextInt(0, 5) == 0) lootList.add(new ItemStack(Material.OMINOUS_BOTTLE, 1));
        lootList.add(new ItemStack(Material.BREEZE_ROD, 1));
        addShardsOfTheFallen(3, lootList);
        givePlayerForagingExp(pD, SkillExpAmount.FORAGING_VERY_RARE.getExp());
    }
}
