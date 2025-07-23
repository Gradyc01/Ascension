package me.depickcator.ascension.MainMenuUI.BingoBoard;

import me.depickcator.ascension.Interfaces.ViewRecipeGUIs;
import me.depickcator.ascension.Player.Data.PlayerData;
import me.depickcator.ascension.Utility.TextUtil;
import org.bukkit.Material;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.*;

import java.util.List;

public class ViewBingoRecipeGUI extends ViewRecipeGUIs {
    private final int recipeIndex;
    private final Recipe recipe;
    private final List<Recipe> recipes;
    public ViewBingoRecipeGUI(PlayerData playerData, Recipe recipe, List<Recipe> recipes) {
        super(playerData, (char) 6, TextUtil.makeText("Recipe: "+ TextUtil.getItemNameString(recipe.getResult()), TextUtil.AQUA), recipe);
        this.recipes = recipes;
        this.recipe = recipe;
        recipeIndex = recipes.indexOf(recipe);
        if (recipes.size() != 1) {
            if (recipeIndex != 0) inventory.setItem(45, previousPageItem());
            if (recipeIndex != recipes.size() - 1) inventory.setItem(53, nextPageItem());
        }
    }


    @Override
    public void interactWithGUIButtons(InventoryClickEvent event) {
        ItemStack item = event.getCurrentItem();
        if (item == null) return;
        if (item.equals(goBackItem())) {
            new BingoBoardGUI(playerData);
        } else if (item.equals(previousPageItem())) {
            new ViewBingoRecipeGUI(playerData, recipes.get(recipeIndex - 1), recipes);
        } else if (item.equals(nextPageItem())) {
            new ViewBingoRecipeGUI(playerData, recipes.get(recipeIndex + 1), recipes);
        } else if (!item.equals(recipe.getResult()) &&
                !item.getType().equals(Material.GRAY_STAINED_GLASS_PANE) &&
                event.getSlot() != 49) {
            new OpenRecipe(playerData, item).open();
        }
    }
}
