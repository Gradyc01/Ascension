package me.depickcator.ascensionBingo.Items.Craftable.Unlocks;

import me.depickcator.ascensionBingo.AscensionBingo;
import me.depickcator.ascensionBingo.Items.Craftable.Crafts;
import me.depickcator.ascensionBingo.Items.UnlockUtil;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.Recipe;
import org.bukkit.inventory.ShapedRecipe;

public class SugarRush implements Crafts {
    private final AscensionBingo plugin;
    private Recipe recipe;
    public static final int COST = 1;
    public static final int MAX_CRAFTS = 4;
    public static final String DISPLAY_NAME = "Sugar Rush";
    public static final String KEY = "sugar_rush";
    public SugarRush(AscensionBingo plugin) {
        this.plugin = plugin;
        recipe();
    }

    @Override
    public void recipe() {
        NamespacedKey key = new NamespacedKey(plugin, KEY);
        ItemStack item = SugarRush.result();

        ShapedRecipe recipe = new ShapedRecipe(key, item);
        recipe.shape(" C ", "ABA", "   ");
        recipe.setIngredient('A', Material.WHEAT_SEEDS);
        recipe.setIngredient('B', Material.SUGAR);
        recipe.setIngredient('C', UnlockUtil.SAPLINGS);
        UnlockUtil.addUnlock(plugin, recipe, MAX_CRAFTS, DISPLAY_NAME);
        this.recipe = recipe;
    }

    @Override
    public String getKey() {
        return KEY;
    }

    @Override
    public ItemStack getResult() {
        return SugarRush.result();
    }

    @Override
    public String getDisplayName() {
        return DISPLAY_NAME;
    }

    @Override
    public Recipe getRecipe() {
        return recipe;
    }

    @Override
    public int getCraftCost() {
        return COST;
    }

    public static ItemStack result() {
        return new ItemStack(Material.SUGAR_CANE, 4);
    }
}
