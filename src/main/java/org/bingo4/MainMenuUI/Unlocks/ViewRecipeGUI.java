package org.bingo4.MainMenuUI.Unlocks;

import org.bingo4.Interfaces.ViewRecipeGUIs;
import org.bingo4.Items.Craftable.Craft;
import org.bingo4.Player.Data.PlayerData;
import org.bingo4.Utility.TextUtil;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

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
//            new UnlocksGUI(playerData, pageNumber);
        }
    }
}
