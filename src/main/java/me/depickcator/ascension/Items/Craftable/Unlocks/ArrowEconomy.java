package me.depickcator.ascension.Items.Craftable.Unlocks;

import me.depickcator.ascension.Ascension;
import me.depickcator.ascension.Items.Craftable.Craft;
import me.depickcator.ascension.Items.Craftable.Crafts;
import me.depickcator.ascension.Items.UnlockUtil;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.Recipe;
import org.bukkit.inventory.ShapedRecipe;

public class ArrowEconomy extends Craft {
    private static ArrowEconomy instance;
    private ArrowEconomy() {
        super(1, 4, "Arrow Economy", "arrow_economy");
    }

    @Override
    protected Recipe initRecipe() {
        NamespacedKey key = new NamespacedKey(plugin, KEY);
        ShapedRecipe recipe = new ShapedRecipe(key, result);
        recipe.shape("AAA", "BBB", "CCC");
        recipe.setIngredient('A', Material.FLINT);
        recipe.setIngredient('B', Material.STICK);
        recipe.setIngredient('C', Material.FEATHER);
        UnlockUtil.addUnlock(plugin, recipe, MAX_CRAFTS, DISPLAY_NAME);
        return recipe;
    }

    @Override
    protected ItemStack initResult() {
        return new ItemStack(Material.ARROW, 20);
    }

    public static ArrowEconomy getInstance() {
        if (instance == null) instance = new ArrowEconomy();
        return instance;
    }
}
