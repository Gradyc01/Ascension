package me.depickcator.ascension.Interfaces;

import me.depickcator.ascension.Ascension;
import me.depickcator.ascension.Player.Data.PlayerData;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextColor;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;


public abstract class AscensionGUI extends ItemComparison{
    protected final int GUISize;
    protected final Inventory inventory;
    protected final Ascension plugin;

    /* Creates a GUI for playerData that is GUI lines tall */
    public AscensionGUI(PlayerData playerData, char GUILines, Component name) {
        plugin = Ascension.getInstance();
        GUISize = GUILines * 9;
        inventory = Bukkit.createInventory(playerData.getPlayer(), GUISize, name);
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
        return equalItems(held, item);
    }

    /* Triggers when a player interacts with an item in the GUI */
    public abstract void interactWithGUIButtons(InventoryClickEvent event);

    /* Triggers when a player closes the GUI */
    public boolean runWhenCloseGUI(InventoryCloseEvent event) {
        return true; //Stub on purpose
    }


}
