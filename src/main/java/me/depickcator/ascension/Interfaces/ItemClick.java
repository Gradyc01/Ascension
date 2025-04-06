package me.depickcator.ascension.Interfaces;


import me.depickcator.ascension.Player.Data.PlayerData;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.HashMap;
public interface ItemClick {
    HashMap<String, ItemClick> items = new HashMap<>();
    /* Gets the item in the ItemClick*/
    ItemStack getItem();
    /* What happens when it is clicked
    Returns True if successful
    False Otherwise*/
    boolean uponClick(PlayerInteractEvent e, PlayerData pD);
    /* Registers item into Items*/
    void registerItem();

    default void addItem(ItemStack item, ItemClick itemClick) {
        items.put(parser(item), itemClick);
    }

    static ItemClick findClickItem(ItemStack item) {
        if (item == null) return null;
        return items.get(parser(item));
    }

    default boolean isMainHandRightClick(PlayerInteractEvent e) {
        return (e.getHand() == EquipmentSlot.HAND && e.getAction().isRightClick());
    }

    private static String parser(ItemStack item) {
        int customModelNumber;
        ItemMeta meta = item.getItemMeta();
        try {
            customModelNumber = meta.getCustomModelData();
        } catch (Exception ignored) {
            customModelNumber = 0;
        }

        return item.getType().toString() + customModelNumber;
    }

    /* Compares item1 and item2 if they are the same return True
    False Otherwise */
    static boolean compareItems(ItemStack item1, ItemStack item2) {
        return parser(item1).equals(parser(item2));
    }

    /* Compares whether Player is holding item1 in their main hand */
    static boolean isHolding(Player p, ItemStack item1) {
        return compareItems(item1, p.getInventory().getItemInMainHand());
    }
}
