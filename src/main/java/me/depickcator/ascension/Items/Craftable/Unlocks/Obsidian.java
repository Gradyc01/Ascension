package me.depickcator.ascension.Items.Craftable.Unlocks;

import me.depickcator.ascension.Items.Craftable.Craft;
import me.depickcator.ascension.Items.UnlockUtil;
import me.depickcator.ascension.Items.UnlocksData;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.Recipe;
import org.bukkit.inventory.ShapelessRecipe;

public class Obsidian extends Craft {
    private static Obsidian instance;
    private Obsidian() {
        super(UnlocksData.COST_125, 4, "Obsidian", "obsidian");
    }


    @Override
    protected Recipe initRecipe() {
        NamespacedKey key = new NamespacedKey(plugin, KEY);

        ShapelessRecipe recipe = new ShapelessRecipe(key, result);
        recipe.addIngredient(Material.WATER_BUCKET);
        recipe.addIngredient(Material.LAVA_BUCKET);
        UnlockUtil.addUnlock(plugin, recipe, MAX_CRAFTS, DISPLAY_NAME);
        return recipe;
    }

    @Override
    protected ItemStack initResult() {
        return new ItemStack(Material.OBSIDIAN, 2);
    }

    public static Obsidian getInstance() {
        if (instance == null) instance = new Obsidian();
        return instance;
    }
}
