package me.depickcator.ascensionBingo.listeners;

import me.depickcator.ascensionBingo.AscensionBingo;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryCloseEvent;


import java.util.UUID;

public class InventoryClose implements Listener {
    @EventHandler
    public void onInventoryClose(InventoryCloseEvent e) {
//        if (e.getInventory().getHolder() instanceof AscensionBingo)
        UUID playerUUID = e.getPlayer().getUniqueId();
        if (AscensionBingo.guiMap.containsKey(playerUUID)) {
            AscensionBingo.guiMap.remove(playerUUID);
            System.out.println("Map Closed");
        }
    }
}
