package me.depickcator.ascension.Timeline.Events.Feast;

import me.depickcator.ascension.Interfaces.CustomChestLoot;
import org.bukkit.Material;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Random;

public class FeastResourceChestLoot extends CustomChestLoot {
    private static FeastResourceChestLoot instance;
    private final List<ItemStack> farmItems;
    private final List<ItemStack> mineralItems;
    private final List<ItemStack> mobItems;
    private FeastResourceChestLoot() {
        farmItems = initFarmItems();
        mineralItems = initMineralItems();
        mobItems = initMobItems();
    }
    @Override
    public Collection<ItemStack> populateLoot(Inventory inv, Random r, double luck) {
        ArrayList<ItemStack> items = new ArrayList<>();

        items.addAll(getRandomItemFromList(farmItems, r, 2));
        items.addAll(getRandomItemFromList(mineralItems, r, 2));
        items.addAll(getRandomItemFromList(mobItems, r, 2));

        return placeInInventory(inv, r, items);
    }

    private List<ItemStack> initFarmItems() {
        List<ItemStack> list = new ArrayList<>(List.of(
                new ItemStack(Material.EGG),
                new ItemStack(Material.RABBIT_FOOT),
                new ItemStack(Material.POISONOUS_POTATO),
                new ItemStack(Material.HONEY_BOTTLE)
        ));
        list.addAll(generateItems(Material.BOOK, 1, 4, 1));
        list.addAll(generateItems(Material.SUGAR_CANE, 2, 4, 2));
        list.addAll(generateItems(Material.LEATHER, 1, 4, 1));
        list.addAll(generateItems(Material.FEATHER, 1, 4, 1));
        list.addAll(generateItems(Material.CARROT, 1, 4, 1));
        list.addAll(generateItems(Material.WHEAT, 2, 4, 2));
        return list;
    }

    private List<ItemStack> initMineralItems() {
        List<ItemStack> list = new ArrayList<>(List.of(
                new ItemStack(Material.DIAMOND_BLOCK)
        ));
        list.addAll(generateItems(Material.DIAMOND, 1, 5, 1));
        list.addAll(generateItems(Material.IRON_INGOT, 2, 5, 2));
        list.addAll(generateItems(Material.GOLD_INGOT, 2, 4, 1));
        list.addAll(generateItems(Material.LAPIS_LAZULI, 8, 5, 4));
        list.addAll(generateItems(Material.REDSTONE, 8, 5, 4));
        list.addAll(generateItems(Material.EMERALD, 1, 4, 1));
        list.addAll(generateItems(Material.LAPIS_BLOCK, 1, 2, 1));
        list.addAll(generateItems(Material.REDSTONE_BLOCK, 1, 2, 1));
        list.addAll(generateItems(Material.IRON_BLOCK, 1, 2, 1));
        list.addAll(generateItems(Material.GOLD_BLOCK, 1, 2, 1));
        return list;
    }

    private List<ItemStack> initMobItems() {
        List<ItemStack> list = new ArrayList<>(List.of(
        ));
        list.addAll(generateItems(Material.ENDER_PEARL, 1, 2, 1));
        list.addAll(generateItems(Material.SLIME_BALL, 1, 3, 1));
        list.addAll(generateItems(Material.GUNPOWDER, 1, 3, 1));
        list.addAll(generateItems(Material.ROTTEN_FLESH, 1, 3, 2));
        list.addAll(generateItems(Material.BONE, 1, 3, 2));
        list.addAll(generateItems(Material.STRING, 1, 3, 2));
        list.addAll(generateItems(Material.SPIDER_EYE, 1, 2, 1));
        return list;
    }


    public static FeastResourceChestLoot getInstance() {
        if (instance == null) instance = new FeastResourceChestLoot();
        return instance;
    }
}
