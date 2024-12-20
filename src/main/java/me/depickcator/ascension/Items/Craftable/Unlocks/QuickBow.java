package me.depickcator.ascension.Items.Craftable.Unlocks;

import me.depickcator.ascension.Items.Craftable.Craft;
import me.depickcator.ascension.Items.UnlockUtil;
import me.depickcator.ascension.Items.UnlocksData;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.Recipe;
import org.bukkit.inventory.ShapedRecipe;

public class QuickBow extends Craft {
    private static QuickBow instance;
    private QuickBow() {
        super(UnlocksData.COST_100, 2, "Quick Bow", "quick_bow");
    }

    public static QuickBow getInstance() {
        if (instance == null) instance = new QuickBow();
        return instance;
    }

    @Override
    protected Recipe initRecipe() {
        NamespacedKey key = new NamespacedKey(plugin, KEY);

        ShapedRecipe recipe = new ShapedRecipe(key, result);
        recipe.shape(" AC", "ABV", " AC");
        recipe.setIngredient('A', Material.STICK);
        recipe.setIngredient('B', Material.IRON_INGOT);
        recipe.setIngredient('C', Material.REDSTONE_TORCH);
        UnlockUtil.addUnlock(plugin, recipe, MAX_CRAFTS, DISPLAY_NAME);
        return recipe;
    }

    @Override
    protected ItemStack initResult() {
        return new ItemStack(Material.CROSSBOW);
    }

}
