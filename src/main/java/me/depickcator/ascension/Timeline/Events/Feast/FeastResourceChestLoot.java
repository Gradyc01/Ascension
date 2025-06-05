package me.depickcator.ascension.Timeline.Events.Feast;

import me.depickcator.ascension.Interfaces.CustomChestLoot;
import me.depickcator.ascension.Interfaces.CustomChestLootPool;
import me.depickcator.ascension.Interfaces.LootPoolItem;
import org.bukkit.Material;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Random;

public class FeastResourceChestLoot extends CustomChestLoot {
    private static FeastResourceChestLoot instance;
    private final CustomChestLootPool farmItems;
    private final CustomChestLootPool mineralItems;
    private final CustomChestLootPool mobItems;
    private FeastResourceChestLoot() {
        farmItems = initFarmItems();
        mineralItems = initMineralItems();
        mobItems = initMobItems();
    }
    @Override
    public Collection<ItemStack> populateLoot(Inventory inv, Random r, double luck) {
        ArrayList<ItemStack> items = new ArrayList<>();

        items.addAll(farmItems.getRandomItemFromList(r, 2));
        items.addAll(mineralItems.getRandomItemFromList(r, 2));
        items.addAll(mobItems.getRandomItemFromList(r, 2));

        return placeInInventory(inv, r, items);
    }

    private CustomChestLootPool initFarmItems() {
        CustomChestLootPool customChestLootPool = new CustomChestLootPool(
                new LootPoolItem(Material.EGG),
                new LootPoolItem(Material.RABBIT_FOOT),
                new LootPoolItem(Material.POISONOUS_POTATO),
                new LootPoolItem(Material.HONEY_BOTTLE)
        );
        customChestLootPool.generateItems(Material.BOOK, 1, 4, 1);
        customChestLootPool.generateItems(Material.SUGAR_CANE, 2, 4, 2);
        customChestLootPool.generateItems(Material.LEATHER, 1, 4, 1);
        customChestLootPool.generateItems(Material.FEATHER, 1, 4, 1);
        customChestLootPool.generateItems(Material.CARROT, 1, 4, 1);
        customChestLootPool.generateItems(Material.WHEAT, 2, 4, 2);
        return customChestLootPool;
    }

    private CustomChestLootPool initMineralItems() {
        CustomChestLootPool customChestLootPool = new CustomChestLootPool(
                new LootPoolItem(Material.DIAMOND_BLOCK)
        );
        customChestLootPool.generateItems(Material.DIAMOND, 1, 5, 1);
        customChestLootPool.generateItems(Material.IRON_INGOT, 2, 5, 2);
        customChestLootPool.generateItems(Material.GOLD_INGOT, 2, 4, 1);
        customChestLootPool.generateItems(Material.LAPIS_LAZULI, 8, 5, 4);
        customChestLootPool.generateItems(Material.REDSTONE, 8, 5, 4);
        customChestLootPool.generateItems(Material.EMERALD, 1, 4, 1);
        customChestLootPool.generateItems(Material.LAPIS_BLOCK, 1, 2, 1);
        customChestLootPool.generateItems(Material.REDSTONE_BLOCK, 1, 2, 1);
        customChestLootPool.generateItems(Material.IRON_BLOCK, 1, 2, 1);
        customChestLootPool.generateItems(Material.GOLD_BLOCK, 1, 2, 1);
        return customChestLootPool;
    }

    private CustomChestLootPool initMobItems() {
//        List<ItemStack> list = new ArrayList<>(List.of(
//        ));
//        list.addAll(generateItems(Material.ENDER_PEARL, 1, 2, 1));
//        list.addAll(generateItems(Material.SLIME_BALL, 1, 3, 1));
//        list.addAll(generateItems(Material.GUNPOWDER, 1, 3, 1));
//        list.addAll(generateItems(Material.ROTTEN_FLESH, 1, 3, 2));
//        list.addAll(generateItems(Material.BONE, 1, 3, 2));
//        list.addAll(generateItems(Material.STRING, 1, 3, 2));
//        list.addAll(generateItems(Material.SPIDER_EYE, 1, 2, 1));
//        return list;
        CustomChestLootPool customChestLootPool = new CustomChestLootPool();
        customChestLootPool.generateItems(Material.ENDER_PEARL, 1, 2, 1);
        customChestLootPool.generateItems(Material.SLIME_BALL, 1, 3, 1);
        customChestLootPool.generateItems(Material.GUNPOWDER, 1, 3, 1);
        customChestLootPool.generateItems(Material.ROTTEN_FLESH, 1, 3, 2);
        customChestLootPool.generateItems(Material.BONE, 1, 3, 2);
        customChestLootPool.generateItems(Material.STRING, 1, 3, 2);
        customChestLootPool.generateItems(Material.SPIDER_EYE, 1, 2, 1);
        return customChestLootPool;
    }


    public static FeastResourceChestLoot getInstance() {
        if (instance == null) instance = new FeastResourceChestLoot();
        return instance;
    }
}
