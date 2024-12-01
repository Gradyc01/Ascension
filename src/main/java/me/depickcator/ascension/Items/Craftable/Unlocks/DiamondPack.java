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

public class DiamondPack extends Craft {
    private static DiamondPack instance;
    private DiamondPack() {
        super(1, 4, "Diamond Pack", "diamond_pack");
    }

    @Override
    protected Recipe initRecipe() {
        NamespacedKey key = new NamespacedKey(plugin, KEY);

        ShapedRecipe recipe = new ShapedRecipe(key, result);
        recipe.shape("AAA", "ABA", "ACA");
        recipe.setIngredient('A', Material.DIAMOND);
        recipe.setIngredient('B', Material.NETHER_STAR);
        recipe.setIngredient('C', Material.IRON_PICKAXE);
        UnlockUtil.addUnlock(plugin, recipe, MAX_CRAFTS, DISPLAY_NAME);
        return recipe;
    }

    @Override
    protected ItemStack initResult() {
        return new ItemStack(Material.DIAMOND, 12);
    }

    public static DiamondPack getInstance() {
        if (instance == null) instance = new DiamondPack();
        return instance;
    }
}
