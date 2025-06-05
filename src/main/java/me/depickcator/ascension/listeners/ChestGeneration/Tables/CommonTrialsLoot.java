package me.depickcator.ascension.listeners.ChestGeneration.Tables;

import me.depickcator.ascension.Player.Data.PlayerData;
import me.depickcator.ascension.Skills.SkillExpAmount;
import me.depickcator.ascension.listeners.ChestGeneration.ChestLootTable;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import java.util.List;

public class CommonTrialsLoot extends ChestLootTable {
    public CommonTrialsLoot() {
        super("minecraft:chests/trial_chambers/reward_common");
    }

    @Override
    public void addLootToTable(PlayerData pD, List<ItemStack> lootList) {
        lootList.add(new ItemStack(Material.OMINOUS_BOTTLE, 1));
        addShardsOfTheFallen(3, lootList);
        givePlayerForagingExp(pD, SkillExpAmount.FORAGING_VERY_RARE.getExp());
    }
}
