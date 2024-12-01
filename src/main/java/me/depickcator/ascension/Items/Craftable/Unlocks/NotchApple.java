package me.depickcator.ascension.Items.Craftable.Unlocks;

import me.depickcator.ascension.Ascension;
import me.depickcator.ascension.Items.Craftable.Crafts;
import me.depickcator.ascension.Items.UnlockUtil;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.Recipe;
import org.bukkit.inventory.ShapedRecipe;

public class NotchApple implements Crafts {
    private final Ascension plugin;
    private Recipe recipe;
    public static final int COST = 1;
    public static final int MAX_CRAFTS = 2;
    public static final String DISPLAY_NAME = "Notch Apple";
    public static final String KEY = "notch_apple";
    public NotchApple() {
        this.plugin = Ascension.getInstance();
        recipe();
    }

    @Override
    public void recipe() {
        NamespacedKey key = new NamespacedKey(plugin, KEY);
        ItemStack item = NotchApple.result();

        ShapedRecipe recipe = new ShapedRecipe(key, item);
        recipe.shape("AAA", "ABA", "AAA");
        recipe.setIngredient('A', Material.GOLD_BLOCK);
        recipe.setIngredient('B', Material.APPLE);
        UnlockUtil.addUnlock(plugin, recipe, MAX_CRAFTS, DISPLAY_NAME);
        this.recipe = recipe;
    }

    public static ItemStack result() {
        return new ItemStack(Material.ENCHANTED_GOLDEN_APPLE, 1);
    }

    @Override
    public String getKey() {
        return KEY;
    }

    @Override
    public ItemStack getResult() {
        return NotchApple.result();
    }

    @Override
    public Recipe getRecipe() {
        return recipe;
    }

    @Override
    public String getDisplayName() {
        return DISPLAY_NAME;
    }

    @Override
    public int getCraftCost() {
        return COST;
    }


}
