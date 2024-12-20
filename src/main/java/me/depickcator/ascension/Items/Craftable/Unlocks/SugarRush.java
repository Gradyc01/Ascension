package me.depickcator.ascension.Items.Craftable.Unlocks;

import me.depickcator.ascension.Items.Craftable.Craft;
import me.depickcator.ascension.Items.UnlockUtil;
import me.depickcator.ascension.Items.UnlocksData;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.Recipe;
import org.bukkit.inventory.ShapedRecipe;

public class SugarRush extends Craft {
    private static SugarRush instance;
    private SugarRush() {
        super(UnlocksData.COST_100, 4, "Sugar Rush", "sugar_rush");
    }

    public static SugarRush getInstance() {
        if (instance == null) instance = new SugarRush();
        return instance;
    }

    @Override
    protected Recipe initRecipe() {
        NamespacedKey key = new NamespacedKey(plugin, KEY);

        ShapedRecipe recipe = new ShapedRecipe(key, result);
        recipe.shape(" C ", "ABA");
        recipe.setIngredient('A', Material.WHEAT_SEEDS);
        recipe.setIngredient('B', Material.SUGAR);
        recipe.setIngredient('C', UnlockUtil.SAPLINGS);
        UnlockUtil.addUnlock(plugin, recipe, MAX_CRAFTS, DISPLAY_NAME);
        return recipe;
    }

    @Override
    protected ItemStack initResult() {
        return new ItemStack(Material.SUGAR_CANE, 4);
    }
}
