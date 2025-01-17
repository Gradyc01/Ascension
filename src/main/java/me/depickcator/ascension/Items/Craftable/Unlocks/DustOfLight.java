package me.depickcator.ascension.Items.Craftable.Unlocks;

import me.depickcator.ascension.Items.Craftable.Craft;
import me.depickcator.ascension.Items.UnlockUtil;
import me.depickcator.ascension.Items.UnlocksData;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.Recipe;
import org.bukkit.inventory.ShapedRecipe;

public class DustOfLight extends Craft {
    private static DustOfLight instance;
    private DustOfLight() {
        super(UnlocksData.COST_100, 3, "Dust of Light", "dust_of_light");
    }

    @Override
    protected Recipe initRecipe() {
        NamespacedKey key = new NamespacedKey(plugin, KEY);
        ShapedRecipe recipe = new ShapedRecipe(key, result);
        recipe.shape("BBB", "BAB", "BBB");
        recipe.setIngredient('A', Material.FLINT_AND_STEEL);
        recipe.setIngredient('B', Material.REDSTONE);
        UnlockUtil.addUnlock(plugin, recipe, MAX_CRAFTS, DISPLAY_NAME);
        return recipe;
    }

    @Override
    protected ItemStack initResult() {
        return new ItemStack(Material.GLOWSTONE_DUST, 8);
    }

    public static DustOfLight getInstance() {
        if (instance == null) instance = new DustOfLight();
        return instance;
    }
}
