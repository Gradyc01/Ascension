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
    ItemStack getItem();
    boolean uponClick(PlayerInteractEvent e, PlayerData pD);
    void registerItem();

    default void addItem(ItemStack item, ItemClick itemClick) {
//        items.put(item.toString().toLowerCase(), itemClick);
        items.put(parser(item), itemClick);
    }

    static ItemClick findClickItem(ItemStack item) {
//        Bukkit.getServer().broadcast(TextUtil.makeText(item.toString(), TextUtil.WHITE));
        if (item == null) return null;
//        for (String itemStr: items.keySet()) {
//            Ascension.getInstance().getServer().broadcast(TextUtil.makeText(itemStr + "      ", TextUtil.BLUE).append(items.get(itemStr).getItem().displayName()));
//        }
//        return items.get(item.toString().toLowerCase());
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

    static boolean compareItems(ItemStack item1, ItemStack item2) {
        return parser(item1).equals(parser(item2));
    }

    static boolean isHolding(Player p, ItemStack item1) {
        return compareItems(item1, p.getInventory().getItemInMainHand());
    }
}
