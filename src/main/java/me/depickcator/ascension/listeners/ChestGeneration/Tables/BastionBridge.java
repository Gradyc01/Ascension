package me.depickcator.ascension.listeners.ChestGeneration.Tables;

import me.depickcator.ascension.Player.Data.PlayerData;
import me.depickcator.ascension.Skills.SkillExpAmount;
import me.depickcator.ascension.listeners.ChestGeneration.ChestLootTable;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class BastionBridge extends ChestLootTable {
    public BastionBridge() {
        super("minecraft:chests/bastion_bridge");
    }

    @Override
    public void addLootToTable(PlayerData pD, List<ItemStack> lootList) {
        lootList.add(new ItemStack(Material.MUSIC_DISC_PIGSTEP, 1));
        ArrayList<ItemStack> items = new ArrayList<>(List.of(
                new ItemStack(Material.NETHERITE_UPGRADE_SMITHING_TEMPLATE),
                new ItemStack(Material.NETHERITE_INGOT),
                new ItemStack(Material.LODESTONE),
                new ItemStack(Material.GOLD_BLOCK)
        ));
        Collections.shuffle(items);
        items.removeFirst();
        lootList.addAll(items);
        addShardsOfTheFallen(1, 2, lootList);
        givePlayerForagingExp(pD, SkillExpAmount.FORAGING_VERY_RARE.getExp());
    }
}
