package me.depickcator.ascension.Interfaces;

import me.depickcator.ascension.Ascension;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.Collection;
import java.util.List;
import java.util.Random;

public abstract class CustomChestLoot {
    protected final Ascension plugin;
    public CustomChestLoot() {
        this.plugin = Ascension.getInstance();
    }
    protected Collection<ItemStack> placeInInventory(Inventory inv, Random r, List<ItemStack> items) {
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

    /* Populates an inventory with an Itemstack returns what was populated with*/
    public abstract Collection<ItemStack> populateLoot(Inventory inv, Random r, double luck);

}
