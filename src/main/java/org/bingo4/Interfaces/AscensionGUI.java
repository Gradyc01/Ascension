package org.bingo4.Interfaces;

import org.bingo4.Bingo4;
import org.bingo4.Player.Data.PlayerData;
import org.bingo4.Utility.ItemComparison;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;


public abstract class AscensionGUI{
    protected final int GUISize;
    protected final Inventory inventory;
    protected final Bingo4 plugin;

    /* Creates a GUI for playerData that is GUI lines tall */
    public AscensionGUI(InventoryHolder holder, int GUILines, Component name) {
        plugin = Bingo4.getInstance();
        GUISize = GUILines * 9;
        inventory = Bukkit.createInventory(holder, GUISize, name);
    }

    /*Opens the GUI for Player player*/
    public void open(Player p) {
        p.openInventory(inventory);
        plugin.registerGUI(p, inventory, this);
    }

    /* Checks whether a player is holding item
    * Returns true if Yes
    * False Otherwise */
    protected boolean isHolding(Player p, ItemStack item) {
        ItemStack held = p.getInventory().getItemInMainHand();
//        return held.equals(item);
        return ItemComparison.equalItems(held, item);
    }

    /* Triggers when a player interacts with an item in the GUI */
    public abstract void interactWithGUIButtons(InventoryClickEvent event);

    /* Triggers when a player closes the GUI */
    public boolean runWhenCloseGUI(InventoryCloseEvent event) {
        return true; //Stub on purpose
    }


}
