package me.depickcator.ascension.Interfaces;

import me.depickcator.ascension.Ascension;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Random;

public abstract class CustomChestLoot {
    protected final Ascension plugin;
    public CustomChestLoot() {
        this.plugin = Ascension.getInstance();
    }

    protected Collection<ItemStack> getRandomItemFromList(ArrayList<ItemStack> arr, Random r, int count) {
        return getRandomItemFromList(arr, r, count, 1, 1);
    }

    protected Collection<ItemStack> getRandomItemFromList(ArrayList<ItemStack> arr, Random r, int count, int min, int max) {
        ArrayList<ItemStack> items = new ArrayList<>(arr);
        ArrayList<ItemStack> ans = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            int index = r.nextInt(items.size());

            ItemStack item = items.get(index);
            item.setAmount(r.nextInt(max - min + min) + 1);

            ans.add(item);
            items.remove(index);
        }
        return ans;
    }

    public abstract Collection<ItemStack> populateLoot(Inventory inv, Random r, double luck);

}
