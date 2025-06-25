package me.depickcator.ascension.listeners;


import me.depickcator.ascension.Ascension;
import me.depickcator.ascension.Interfaces.AscensionGUI;
import me.depickcator.ascension.Interfaces.AscensionMenuGUI;
import me.depickcator.ascension.Interfaces.ItemDrop;
import me.depickcator.ascension.Items.UnlockRecommender;
import me.depickcator.ascension.Player.Data.PlayerData;
import me.depickcator.ascension.Player.Data.PlayerInventoryTracker;
import me.depickcator.ascension.Player.Data.PlayerUnlocks;
import me.depickcator.ascension.Player.Data.PlayerUtil;
import me.depickcator.ascension.Utility.TextUtil;
import org.apache.commons.lang3.tuple.Pair;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityPickupItemEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.event.player.PlayerDropItemEvent;
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
            ItemDrop itemDrop = ItemDrop.findDropItem(e.getCursor());
            if (itemDrop != null && e.getCurrentItem() !=null && e.getCurrentItem().getType() != Material.AIR) {
                itemDrop.uponApply(e, e.getCurrentItem(), e.getCursor(), PlayerUtil.getPlayerData(player));
            }
        }
        if (playerGUI == null && e.getCurrentItem() != null
                && e.getCurrentItem().getType() != Material.AIR && e.getInventory().getType() == InventoryType.CHEST) {
            PlayerInventoryTracker tracker = PlayerUtil.getPlayerData(player).getPlayerInventoryTracker();
            tracker.needsUpdate();
        }
        if (playerGUI != null && inventory == playerGUI.getLeft()) {
            if (playerGUI.getRight() instanceof AscensionMenuGUI && e.getCurrentItem() == null) return;
            playerGUI.getRight().interactWithGUIButtons(e);
            if (playerGUI.getRight() instanceof AscensionMenuGUI) e.setCancelled(true);
        }
    }

    @EventHandler
    public void onInventoryClose(InventoryCloseEvent e) {
        Ascension plugin = Ascension.getInstance();
        if (!plugin.getGameState().inGame()) return;
        Player player = (Player) e.getPlayer();
        Pair<Inventory, AscensionGUI> gui = plugin.findInventory(player);
        PlayerData playerData = PlayerUtil.getPlayerData(player);
        if (playerData == null) return;
        PlayerInventoryTracker tracker = playerData.getPlayerInventoryTracker();
        if (tracker.isNeedsUpdate()) {
            TextUtil.debugText("Reparsing inventory " + player.getName());
            tracker.reparseInventory();
        }
        if (gui == null || gui.getRight().runWhenCloseGUI(e)) {
            plugin.removeGUI((Player) e.getPlayer());
        }
    }

    @EventHandler
    public void onItemPickup(EntityPickupItemEvent e) {
        if (!Ascension.getInstance().getGameState().inGame()) return;
        if (!(e.getEntity() instanceof Player)) return;
        Player player = (Player) e.getEntity();
        PlayerData playerData = PlayerUtil.getPlayerData(player);
        if (playerData == null) return;
        playerData.getPlayerInventoryTracker().addItems(e.getItem().getItemStack());
        UnlockRecommender.getInstance().checkMaterial(e.getItem().getItemStack(), player);
    }

    @EventHandler
    public void onPlayerDropItemEvent(PlayerDropItemEvent e) {
        if (!Ascension.getInstance().getGameState().inGame()) return;
        PlayerData playerData = PlayerUtil.getPlayerData(e.getPlayer());
        if (playerData == null) return;
        playerData.getPlayerInventoryTracker().removeItems(e.getItemDrop().getItemStack());
    }
}
