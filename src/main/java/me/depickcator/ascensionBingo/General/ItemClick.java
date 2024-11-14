package me.depickcator.ascensionBingo.General;


import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;
public interface ItemClick {
    HashMap<String, ItemClick> items = new HashMap<>();
    ItemStack getItem();
    boolean uponClick(PlayerInteractEvent e, Player p);
    void registerItem();

    default void addItem(ItemStack item, ItemClick itemClick) {
        items.put(item.toString().toLowerCase(), itemClick);
    }

    static ItemClick findClickItem(ItemStack item) {
        if (item == null) return null;
        return items.get(item.toString().toLowerCase());
    }

    default boolean isMainHandRightClick(PlayerInteractEvent e) {
        return (e.getHand() == EquipmentSlot.HAND && e.getAction().isRightClick());
    }
}
