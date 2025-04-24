package me.depickcator.ascension.listeners.ChestGeneration.Tables;

import me.depickcator.ascension.Player.Data.PlayerData;
import me.depickcator.ascension.Skills.SkillExpAmount;
import me.depickcator.ascension.listeners.ChestGeneration.ChestLootTable;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class DesertPyramid extends ChestLootTable {
    public DesertPyramid() {
        super("minecraft:chests/desert_pyramid");
    }

    @Override
    public void addLootToTable(PlayerData pD, List<ItemStack> lootList) {
        ArrayList<ItemStack> items = new ArrayList<>(List.of(
                new ItemStack(Material.DIAMOND_HORSE_ARMOR),
                new ItemStack(Material.IRON_HORSE_ARMOR),
                new ItemStack(Material.GOLDEN_HORSE_ARMOR)
        ));
        Collections.shuffle(items);
        lootList.add(items.getFirst());
        addShardsOfTheFallen(0, 1, lootList);
        givePlayerForagingExp(pD, SkillExpAmount.FORAGING_RARE.getExp());
    }
}
