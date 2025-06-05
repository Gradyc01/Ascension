package me.depickcator.ascension.Interfaces;

import org.bukkit.inventory.ItemStack;

import java.util.*;

public class CustomChestLootPool {
    private int totalWeight;
    private final List<LootPoolItem> lootPool;

    public CustomChestLootPool(LootPoolItem... lootPoolItems) {
        this.totalWeight = 0;
        this.lootPool = new ArrayList<>();
        addLootPoolItem(lootPoolItems);
    }

    public CustomChestLootPool() {
        this.totalWeight = 0;
        this.lootPool = new ArrayList<>();
    }

    /*Adds Loot pool items to the CustomChestLootPool*/
    public void addLootPoolItem(LootPoolItem... lootPoolItems) {
        for (LootPoolItem lootPoolItem : lootPoolItems) {
            this.lootPool.add(lootPoolItem);
            this.totalWeight+= lootPoolItem.weight();
        }
    }

    public Collection<ItemStack> getRandomItemFromList(Random r, int count) {
        return getRandomItemFromList(r, count, 1, 1, false);
    }

    public Collection<ItemStack> getRandomItemFromList(Random r, int count, int min, int max) {
        return getRandomItemFromList(r, count, min, max, true);
    }

    /* Gets a number of random items from arr and randomly sets the amount between min and max if changeItemAmount is true
     * Returns a Collection of ItemStacks*/
    private Collection<ItemStack> getRandomItemFromList(Random r, int count, int min, int max, boolean changeItemAmount) {
        int totalWeight = this.totalWeight;
        List<LootPoolItem> items = new ArrayList<>(lootPool);
        List<ItemStack> ans = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            int weight = r.nextInt(totalWeight);
            LootPoolItem lootPoolItem = getWeightedItem(items, weight);
            if (lootPoolItem == null) throw new IllegalArgumentException("Illegal argument too much weight");

            ItemStack itemStack = lootPoolItem.item();
            if (changeItemAmount) {
                itemStack.setAmount(r.nextInt(max - min + min) + 1);
            }
            ans.add(itemStack);
        }
        return ans;
    }



    /*Get the item that falls into the weight specified
    * Returns the item or null if it doesn't find one*/
    private LootPoolItem getWeightedItem(List<LootPoolItem> items, int weight) {
        int indexWeight = 0;
        for (LootPoolItem item : items) {
            indexWeight += item.weight();
            if (weight > indexWeight) {
                return item;
            }
        }
        return null;
    }




}


