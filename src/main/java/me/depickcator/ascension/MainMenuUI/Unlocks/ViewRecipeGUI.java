package me.depickcator.ascension.MainMenuUI.Unlocks;

import me.depickcator.ascension.Interfaces.AscensionMenuGUI;
import me.depickcator.ascension.Interfaces.ViewRecipeGUIs;
import me.depickcator.ascension.Items.Craftable.Craft;
import me.depickcator.ascension.Player.Data.PlayerData;
import me.depickcator.ascension.Utility.TextUtil;
import org.bukkit.Material;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.*;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class ViewRecipeGUI extends ViewRecipeGUIs {
    private final char pageNumber;
    public ViewRecipeGUI(PlayerData playerData, Craft craft, char pageNumber) {
        super(playerData, (char) 6, TextUtil.makeText("Recipe: " + craft.getDisplayName(), TextUtil.AQUA), craft.getRecipe());
        this.pageNumber = pageNumber;
    }

    @Override
    public void interactWithGUIButtons(InventoryClickEvent event) {
        ItemStack item = event.getCurrentItem();
        if (item == null) return;

        if (item.equals(goBackItem())) {
            new UnlocksGUI(playerData, pageNumber);
        }
    }
}
