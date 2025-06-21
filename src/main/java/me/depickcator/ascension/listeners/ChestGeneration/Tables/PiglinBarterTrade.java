package me.depickcator.ascension.listeners.ChestGeneration.Tables;

import me.depickcator.ascension.Interfaces.CustomChestLootPool;
import me.depickcator.ascension.Interfaces.LootPoolItem;
import me.depickcator.ascension.Player.Data.PlayerData;
import me.depickcator.ascension.Skills.SkillExpAmount;
import me.depickcator.ascension.listeners.ChestGeneration.ChestLootTable;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class PiglinBarterTrade extends ChestLootTable {
    private final CustomChestLootPool pool;
    private final Random rand;
    public PiglinBarterTrade() {
        super("minecraft:gameplay/piglin_bartering");
        rand = new Random();
        pool = getPool();
    }

    private CustomChestLootPool getPool() {
        CustomChestLootPool pool =  new CustomChestLootPool();
        pool.addLootPoolItem(
                new LootPoolItem(Material.ENDER_PEARL, 5),
                new LootPoolItem(Material.SOUL_SAND, 5),
                new LootPoolItem(Material.DRIED_GHAST, 3),
                new LootPoolItem(Material.CRYING_OBSIDIAN, 7),
                new LootPoolItem(Material.FIRE_CHARGE, 7),
                new LootPoolItem(Material.STRING, 5),
                new LootPoolItem(Material.IRON_NUGGET, 8),
                new LootPoolItem(Material.NETHER_BRICK, 10)
        );
        return pool;
    }

    @Override
    public void addLootToTable(PlayerData pD, List<ItemStack> lootList) {
        lootList.addAll(pool.getRandomItemFromList(rand, 1, 1, 3));
    }
}
