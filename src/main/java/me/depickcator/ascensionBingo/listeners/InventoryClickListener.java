package me.depickcator.ascensionBingo.listeners;


import me.depickcator.ascensionBingo.AscensionBingo;
import me.depickcator.ascensionBingo.Interfaces.AscensionGUI;
import me.depickcator.ascensionBingo.mainMenu.OpenMainMenuCommand;
import me.depickcator.ascensionBingo.mainMenu.BingoBoard.BingoBoardGUI;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;

import java.util.UUID;



public class InventoryClickListener implements Listener {
    AscensionBingo ab;
    public InventoryClickListener(AscensionBingo ab) {
        this.ab = ab;
    }
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
                case BingoBoardGUI.menuName  -> {
                    BingoBoardGUI.interactWithGUIButtons(e.getCurrentItem(), player, inventory, ab);
                }
                default -> {

                }
            }


            e.setCancelled(true);
        }

    }
}
