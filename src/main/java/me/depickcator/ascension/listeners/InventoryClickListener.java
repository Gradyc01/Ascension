package me.depickcator.ascension.listeners;


import me.depickcator.ascension.Ascension;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;

import java.util.UUID;



public class InventoryClickListener implements Listener {
    public InventoryClickListener() {

    }
    @EventHandler
    public void onInventoryClick(InventoryClickEvent e) {
        Player player = (Player) e.getWhoClicked();
        UUID playerUUID = e.getWhoClicked().getUniqueId();
        Inventory inventory = e.getClickedInventory();
        try {
            Ascension.guiMap.get(playerUUID).getLeft();
        } catch (Exception ex) {
            return;
        }
        if (inventory == Ascension.guiMap.get(playerUUID).getLeft()) {
            if (e.getCurrentItem() == null) {
                return;
            }
            Ascension.guiMap.get(playerUUID).getRight().interactWithGUIButtons(e, player);


            e.setCancelled(true);
        }

    }
}
