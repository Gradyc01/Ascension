package org.bingo4.Items.Craftable.Unlocks;

import org.bingo4.Items.Craftable.Craft;
import org.bingo4.Items.UnlockUtil;
import org.bingo4.Items.UnlocksData;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.Recipe;
import org.bukkit.inventory.ShapedRecipe;

public class LightEnchantingTable extends Craft {
    private static LightEnchantingTable instance;
    private LightEnchantingTable() {
        super(UnlocksData.COST_175, 2,"Light Enchanting Table", "light_enchanting_table");
    }

    public static LightEnchantingTable getInstance() {
        if (instance == null) instance = new LightEnchantingTable();
        return instance;
    }

    @Override
    protected Recipe initRecipe() {
        NamespacedKey key = new NamespacedKey(plugin, KEY);

        ShapedRecipe recipe = new ShapedRecipe(key, result);
        recipe.shape(" A ", "BCB", "DDD");
        recipe.setIngredient('A', Material.BOOK);
        recipe.setIngredient('B', Material.OBSIDIAN);
        recipe.setIngredient('C', Material.DIAMOND);
        recipe.setIngredient('D', Material.EXPERIENCE_BOTTLE);
        UnlockUtil.addUnlock(plugin, recipe, MAX_CRAFTS, DISPLAY_NAME);
        return recipe;
    }

    @Override
    protected ItemStack initResult() {
        return new ItemStack(Material.ENCHANTING_TABLE);
    }



}
