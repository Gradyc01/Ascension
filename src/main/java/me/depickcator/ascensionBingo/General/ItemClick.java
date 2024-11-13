package me.depickcator.ascensionBingo.General;

import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;
import java.util.Objects;

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
//        Bukkit.getServer().broadcast(Component.text(item.toString()));
//        for (String i : items.keySet()) {
//            Bukkit.getServer().broadcast(Component.text("\n\n" + i));
//        }
//        Bukkit.getServer().broadcast(Component.text(items.get(item).toString()).color(TextUtil.YELLOW));
        return items.get(item.toString().toLowerCase());
    }

    default boolean isMainHandRightClick(PlayerInteractEvent e) {
        return (e.getHand() == EquipmentSlot.HAND && e.getAction().isRightClick());
    }
}
