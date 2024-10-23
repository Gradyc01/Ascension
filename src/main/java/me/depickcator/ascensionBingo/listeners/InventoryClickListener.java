package me.depickcator.ascensionBingo.listeners;


import me.depickcator.ascensionBingo.AscensionBingo;
import me.depickcator.ascensionBingo.commands.OpenMainMenuCommand;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;

import java.util.UUID;



public class InventoryClickListener implements Listener {
    @EventHandler
    public void onInventoryClick(InventoryClickEvent e) {
        Player player = (Player) e.getWhoClicked();
        UUID playerUUID = e.getWhoClicked().getUniqueId();
        Inventory inventory = e.getClickedInventory();
        try {
            AscensionBingo.guiMap.get(playerUUID).getLeft();
        } catch (Exception ex) {
            return;
        }
        if (inventory == AscensionBingo.guiMap.get(playerUUID).getLeft()) {
            if (e.getCurrentItem() == null) {
                return;
            }
            switch (AscensionBingo.guiMap.get(playerUUID).getRight()) {
                case OpenMainMenuCommand.menuName -> {
                    OpenMainMenuCommand.interactWithGUIButtons(e.getCurrentItem(), player, inventory);
                }
                case "TEMP SO NO ERROR" -> {
                    System.out.println("TEMP SO NO ERROR");
                }
                default -> {

                }
            }
            e.setCancelled(true);
        }

    }
}
