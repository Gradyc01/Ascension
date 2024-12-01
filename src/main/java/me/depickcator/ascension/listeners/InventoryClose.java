package me.depickcator.ascension.listeners;

import me.depickcator.ascension.Ascension;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryCloseEvent;


import java.util.UUID;

public class InventoryClose implements Listener {
    @EventHandler
    public void onInventoryClose(InventoryCloseEvent e) {
//        if (e.getInventory().getHolder() instanceof AscensionBingo)
        UUID playerUUID = e.getPlayer().getUniqueId();
        if (Ascension.guiMap.containsKey(playerUUID)) {
            Ascension.guiMap.remove(playerUUID);
            System.out.println("Map Closed");
        }
    }
}
