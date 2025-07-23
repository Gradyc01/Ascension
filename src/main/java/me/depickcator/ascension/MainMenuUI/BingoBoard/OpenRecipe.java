package me.depickcator.ascension.MainMenuUI.BingoBoard;

import me.depickcator.ascension.Ascension;
import me.depickcator.ascension.Player.Data.PlayerData;
import me.depickcator.ascension.Utility.ItemComparison;
import me.depickcator.ascension.Utility.TextUtil;
import org.bukkit.inventory.CraftingRecipe;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.Recipe;

import java.util.ArrayList;
import java.util.List;

public class OpenRecipe {
    private final PlayerData playerData;
    private final Ascension plugin;
    private final ItemStack item;
    public OpenRecipe(PlayerData playerData, ItemStack item) {
        this.playerData = playerData;
        this.item = item;
        this.plugin = Ascension.getInstance();
    }

    public boolean open() {
        List<Recipe> recipes = findRecipesForItem(item);
        if (recipes.isEmpty()) {
            TextUtil.errorMessage(playerData.getPlayer(), "No Crafting Recipes found for this item!");
            return false;
        }
        else {
            new ViewBingoRecipeGUI(playerData, recipes.getFirst(), recipes);
            return true;
        }
    }

    private List<Recipe> findRecipesForItem(ItemStack item) {
        List<Recipe> recipes = new ArrayList<>();
        for (Recipe recipe : plugin.getServer().getRecipesFor(item)) {
            if (recipe instanceof CraftingRecipe crafting) {
                ItemStack result = crafting.getResult();
                if (ItemComparison.equalItems(result, item)) {
                    recipes.add(crafting);
                }
            }
        }
        return recipes;
    }
}
