package me.depickcator.ascension.Items.Craftable.Unlocks;

import me.depickcator.ascension.Items.Craftable.Craft;
import me.depickcator.ascension.Items.UnlockUtil;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.Recipe;
import org.bukkit.inventory.ShapedRecipe;

public class Backpack extends Craft {
    private static Backpack instance;
    private Backpack() {
        super(1, 2, "Backpack", "backpack");
    }

    @Override
    protected Recipe initRecipe() {
        NamespacedKey key = new NamespacedKey(plugin, KEY);

        ShapedRecipe recipe = new ShapedRecipe(key, result);
        recipe.shape("ABA", "ACA", "ABA");
        recipe.setIngredient('A', Material.STICK);
        recipe.setIngredient('B', Material.LEATHER);
        recipe.setIngredient('C', Material.CHEST);
        UnlockUtil.addUnlock(plugin, recipe, MAX_CRAFTS, DISPLAY_NAME);
        return recipe;
    }

    @Override
    protected ItemStack initResult() {
        return new ItemStack(Material.SHULKER_BOX, 1);
    }

    public static Backpack getInstance() {
        if (instance == null) instance = new Backpack();
        return instance;
    }
}
