package me.depickcator.ascension.Interfaces;

import me.depickcator.ascension.Player.Data.PlayerData;
import me.depickcator.ascension.Player.Data.PlayerInventoryTracker;
import me.depickcator.ascension.Player.Data.PlayerUtil;
import me.depickcator.ascension.Utility.TextUtil;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class BackpackGUI extends AscensionGUI {

    public BackpackGUI(PlayerData playerData, int backpackLevel) {
        super(playerData,
                switch (backpackLevel) {
                    case 0 -> (char) 1;
                    case 1 -> (char) 3;
                    default -> (char) 6;
                },
                TextUtil.makeText(playerData.getPlayer().getName() + "'s Backpack", TextUtil.GREEN));
    }

    public BackpackGUI(PlayerData playerData, int backpackLevel, Inventory inventory) {
        this(playerData, backpackLevel);
        addItems(inventory);
    }

    /*Places items from the old inventory into the new inventory at the exact same inventory positions
    * Warning: This method should only be called when reinitializing this backpack*/
    private void addItems(Inventory inventory) {
//        List<ItemStack> itemsToAdd = new ArrayList<>(items);
//        for (int i = 0; i < inventory.getContents().length; i++) {
//            ItemStack item = inventory.getItem(i);
//            if (item == null && !itemsToAdd.isEmpty()) {
//                inventory.setItem(i, itemsToAdd.removeFirst());
//            }
//        }
        for (int i = 0; i < inventory.getContents().length; i++) {
            ItemStack item = inventory.getItem(i);
            if (item != null) this.inventory.setItem(i, item);
        }
    }


    @Override
    public void interactWithGUIButtons(InventoryClickEvent event) {
        ItemStack item = event.getCurrentItem();
        Player p =  (Player) event.getWhoClicked();
        PlayerInventoryTracker tracker = PlayerUtil.getPlayerData(p).getPlayerInventoryTracker();
//        if (event.getClick().equals(ClickType.NUMBER_KEY)) {
//            ItemStack hotbarItem = p.getInventory().getContents()[event.getHotbarButton()];
//            if (hotbarItem != null && hotbarItem.equals(backpackItem)) event.setCancelled(true);
//        }
        if (item == null) return;
        if (event.getCurrentItem() != null && event.getCurrentItem().getType() != Material.AIR) tracker.needsUpdate();
    }

    /*Gets all the items in the inventory*/
    public Inventory getInventory() {
        return inventory;
    }

    @Override
    public boolean runWhenCloseGUI(InventoryCloseEvent event) {
        PlayerInventoryTracker tracker = PlayerUtil.getPlayerData((Player) event.getPlayer()).getPlayerInventoryTracker();
        return true;
    }
}
