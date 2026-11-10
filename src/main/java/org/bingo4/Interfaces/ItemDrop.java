package org.bingo4.Interfaces;


import org.bingo4.Player.Data.PlayerData;
import org.bingo4.Utility.ItemComparison;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;

public interface ItemDrop {
    HashMap<String, ItemDrop> items = new HashMap<>();
    /* What happens when ItemStack applying is applied on to ItemStack appliedOn
    Returns True if successful
    False Otherwise*/
    boolean uponApply(InventoryClickEvent e, ItemStack appliedOn, ItemStack applying, PlayerData pD);
    /* Registers item into Items*/
    void registerItem();

    default void addItem(ItemStack item, ItemDrop itemClick) {
        items.put(ItemComparison.itemParser(item), itemClick);
    }

    static ItemDrop findDropItem(ItemStack item) {
        if (item == null) return null;
        return items.get(ItemComparison.itemParser(item));
    }

}
