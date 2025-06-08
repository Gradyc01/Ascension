package me.depickcator.ascension.listeners;


import me.depickcator.ascension.Ascension;
import me.depickcator.ascension.Interfaces.AscensionGUI;
import me.depickcator.ascension.Interfaces.AscensionMenuGUI;
import org.apache.commons.lang3.tuple.Pair;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.Inventory;


public class InventoryListener implements Listener {
    public InventoryListener() {


    }
    @EventHandler
    public void onInventoryClick(InventoryClickEvent e) {
        Player player = (Player) e.getWhoClicked();
        Inventory inventory = e.getInventory();
        Pair<Inventory, AscensionGUI> playerGUI = Ascension.getInstance().findInventory(player);
        if (playerGUI == null) {
            return;
        }
        if (inventory == playerGUI.getLeft()) {
            if (playerGUI.getRight() instanceof AscensionMenuGUI && e.getCurrentItem() == null) return;
            playerGUI.getRight().interactWithGUIButtons(e);
            if (playerGUI.getRight() instanceof AscensionMenuGUI) e.setCancelled(true);
        }
    }

    @EventHandler
    public void onInventoryClose(InventoryCloseEvent e) {
        Ascension plugin = Ascension.getInstance();
        Pair<Inventory, AscensionGUI> gui = plugin.findInventory((Player) e.getPlayer());
        if (gui == null || gui.getRight().runWhenCloseGUI(e)) {
            plugin.removeGUI((Player) e.getPlayer());
        }
    }

//    @EventHandler
//    public void onPlayerDropItemEvent(PlayerDropItemEvent e) {
//        if (Ascension.getInstance().getGameState().canNotDropItems()) {
//            e.setCancelled(true);
//        }
//    }
}
