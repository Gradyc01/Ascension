package me.depickcator.ascension.Interfaces;

import me.depickcator.ascension.Ascension;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Random;

public abstract class CustomChestLoot {
    protected final Ascension plugin;
    public CustomChestLoot() {
        this.plugin = Ascension.getInstance();
    }

    protected Collection<ItemStack> getRandomItemFromList(List<ItemStack> arr, Random r, int count) {
        return getRandomItemFromList(arr, r, count, 1, 1, false);
    }

    protected Collection<ItemStack> getRandomItemFromList(List<ItemStack> arr, Random r, int count, int min, int max) {
        return getRandomItemFromList(arr, r, count, min, max, true);
    }

    protected Collection<ItemStack> placeInInventory(Inventory inv, Random r, ArrayList<ItemStack> items) {
        for (ItemStack item : items) {
            boolean placed = false;
            while (!placed) {
                int slot = r.nextInt(inv.getSize());
                if (inv.getItem(slot) == null) {
                    inv.setItem(slot, item);
                    placed = true;
                }
            }
        }
        return items;
    }

    private Collection<ItemStack> getRandomItemFromList(List<ItemStack> arr, Random r, int count, int min, int max, boolean changeItemAmount) {
        ArrayList<ItemStack> items = new ArrayList<>(arr);
        ArrayList<ItemStack> ans = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            int index = r.nextInt(items.size());

            ItemStack item = items.get(index);
            if (changeItemAmount) {
                item.setAmount(r.nextInt(max - min + min) + 1);
            }

            ans.add(item);
            items.remove(index);
        }
        return ans;
    }

    public abstract Collection<ItemStack> populateLoot(Inventory inv, Random r, double luck);

}
