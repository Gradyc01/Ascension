package me.depickcator.ascension.Interfaces;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.PotionMeta;

public class ItemComparison {
    protected boolean equalItems(ItemStack inv, ItemStack board) {
        return itemParser(inv).equals(itemParser(board));
    }

    private String itemParser(ItemStack item) {
        int customModelNumber = getItemModelNumber(item);

        if (item.getType().equals(Material.ENCHANTED_BOOK) && customModelNumber == 0) {
            return item.getType() + item.getItemMeta().getEnchants().toString();
        }

        if (item.getType().equals(Material.POTION) && customModelNumber == 0) {
            PotionMeta meta = (PotionMeta) item.getItemMeta();
            return item.getType() + meta.getCustomEffects().toString();
        }

        return item.getType().toString() + customModelNumber;
    }

    private int getItemModelNumber(ItemStack item) {
        int customModelNumber;
        ItemMeta meta = item.getItemMeta();
        try {
            customModelNumber = meta.getCustomModelData();
        } catch (Exception ignored) {
            customModelNumber = 0;
        }
        return customModelNumber;
    }
}
