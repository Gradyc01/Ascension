package me.depickcator.ascension.Items.Craftable.Unlocks;

import me.depickcator.ascension.Items.Craftable.Craft;
import me.depickcator.ascension.Items.UnlockUtil;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.Tag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.Recipe;
import org.bukkit.inventory.RecipeChoice;
import org.bukkit.inventory.ShapedRecipe;

public class Resurrection extends Craft {
    private static Resurrection instance;
    private Resurrection() {
        super(1, 1, "Resurrection", "resurrection");
    }

    public static Resurrection getInstance() {
        if (instance == null) instance = new Resurrection();
        return instance;
    }

    @Override
    protected Recipe initRecipe() {
        NamespacedKey key = new NamespacedKey(plugin, KEY);
        ShapedRecipe recipe = new ShapedRecipe(key, result);
        recipe.shape("DAD", "DCD", "DBD");
        recipe.setIngredient('A', Material.DIAMOND_AXE);
        recipe.setIngredient('B', Material.OMINOUS_BOTTLE);
        recipe.setIngredient('C', Material.NETHER_STAR);
        recipe.setIngredient('D', new RecipeChoice.MaterialChoice(Tag.LOGS));
        UnlockUtil.addUnlock(plugin, recipe, MAX_CRAFTS, DISPLAY_NAME);
        return recipe;
    }

    @Override
    protected ItemStack initResult() {
        return new ItemStack(Material.TOTEM_OF_UNDYING);
    }

}
