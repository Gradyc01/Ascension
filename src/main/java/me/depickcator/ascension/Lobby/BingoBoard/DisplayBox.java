package me.depickcator.ascension.Lobby.BingoBoard;

import me.depickcator.ascension.Ascension;
import me.depickcator.ascension.Utility.DisplayUtil;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.ItemDisplay;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class DisplayBox {
    private final ItemDisplay notComplete;
    private final ItemDisplay complete;
    private final Ascension plugin;
    public DisplayBox(Location loc) {
        notComplete = DisplayUtil.makeItemDisplay(loc, new ItemStack(Material.GLASS), 0, 0, 1.8);
        complete = DisplayUtil.makeItemDisplay(loc, new ItemStack(Material.LIME_STAINED_GLASS), 0, 90, 1.8);
        DisplayUtil.makeItemDisplay(loc.add(0.9, 0, 0), new ItemStack(Material.OAK_PLANKS), 0, 90, 1.8);
        plugin = Ascension.getInstance();
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
