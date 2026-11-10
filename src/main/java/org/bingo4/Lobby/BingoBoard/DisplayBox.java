package org.bingo4.Lobby.BingoBoard;

import org.bingo4.Bingo4;
import org.bingo4.Utility.DisplayUtil;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.ItemDisplay;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class DisplayBox {
    private final ItemDisplay notComplete;
    private final ItemDisplay complete;
    private final Bingo4 plugin;
    public DisplayBox(Location loc) {
        notComplete = DisplayUtil.makeItemDisplay(loc, new ItemStack(Material.GLASS), 0, 0, 1.8);
        complete = DisplayUtil.makeItemDisplay(loc, new ItemStack(Material.LIME_STAINED_GLASS), 0, 90, 1.8);
        DisplayUtil.makeItemDisplay(loc.add(0.9, 0, 0), new ItemStack(Material.OAK_PLANKS), 0, 90, 1.8);
        plugin = Bingo4.getInstance();
    }

    public void showCompleted(Player p) {
        p.showEntity(plugin, getComplete());
        p.hideEntity(plugin, getNotComplete());
    }

    public void showNotCompleted(Player p) {
        p.showEntity(plugin, notComplete);
        p.hideEntity(plugin, complete);
    }

    public ItemDisplay getNotComplete() {
        return notComplete;
    }

    public ItemDisplay getComplete() {
        return complete;
    }
}
