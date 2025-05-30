package me.depickcator.ascension.listeners;

import me.depickcator.ascension.Ascension;
import me.depickcator.ascension.Interfaces.AscensionGUI;
import org.apache.commons.lang3.tuple.Pair;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.Inventory;


public class InventoryClose implements Listener {
    @EventHandler
    public void onInventoryClose(InventoryCloseEvent e) {
        Ascension plugin = Ascension.getInstance();
//        if (e.getInventory().getHolder() instanceof AscensionBingo)
        // UUID playerUUID = e.getPlayer().getUniqueId();
//        if (Ascension.guiMap.containsKey(playerUUID)) {
//            Ascension.guiMap.remove(playerUUID);
////            TextUtil.debugText(e.getPlayer().getName() + " has closed inventory");
//        }
        Pair<Inventory, AscensionGUI> gui = plugin.findInventory((Player) e.getPlayer());
        if (gui == null || gui.getRight().runWhenCloseGUI(e)) {
            plugin.removeGUI((Player) e.getPlayer());
        }
    }
}
