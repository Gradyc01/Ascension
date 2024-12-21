package me.depickcator.ascension.listeners.ChestGeneration.Tables;

import me.depickcator.ascension.LootTables.Blocks.ForageBlocks.ForageBlocks;
import me.depickcator.ascension.Player.Data.PlayerData;
import me.depickcator.ascension.listeners.ChestGeneration.ChestLootTable;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class BastionTreasure extends ChestLootTable {
    public BastionTreasure() {
        super("minecraft:chests/bastion_treasure");
    }

    @Override
    public void addLootToTable(PlayerData pD, List<ItemStack> lootList) {
        lootList.add(new ItemStack(Material.LODESTONE, 1));
        ArrayList<ItemStack> items = new ArrayList<>(List.of(
                new ItemStack(Material.NETHERITE_UPGRADE_SMITHING_TEMPLATE),
                new ItemStack(Material.NETHERITE_INGOT),
                new ItemStack(Material.MUSIC_DISC_PIGSTEP),
                new ItemStack(Material.GOLD_BLOCK)
        ));
        Collections.shuffle(items);
        items.removeFirst();
        lootList.addAll(items);
        addShardsOfTheFallen(1, 3, lootList);
        givePlayerForagingExp(pD, ForageBlocks.FORAGING_VERY_RARE);
    }
}
