package me.depickcator.ascension.Items.Craftable.Unlocks;

import me.depickcator.ascension.Items.Craftable.Craft;
import me.depickcator.ascension.Items.Uncraftable.ShardOfTheFallen;
import me.depickcator.ascension.Items.UnlockUtil;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.Recipe;
import org.bukkit.inventory.ShapedRecipe;

public class MakeshiftStar extends Craft {
    private static MakeshiftStar instance;
    private MakeshiftStar() {
        super(1, 2,"Makeshift Star", "makeshift_star");
    }

    public static MakeshiftStar getInstance() {
        if (instance == null) instance = new MakeshiftStar();
        return instance;
    }

    @Override
    protected Recipe initRecipe() {
        NamespacedKey key = new NamespacedKey(plugin, KEY);

        ShapedRecipe recipe = new ShapedRecipe(key, result);
        recipe.shape("AAA", "AAA", "AAA");
        recipe.setIngredient('A', ShardOfTheFallen.result());
        UnlockUtil.addUnlock(plugin, recipe, MAX_CRAFTS, DISPLAY_NAME);
        return recipe;
    }

    @Override
    protected ItemStack initResult() {
        return new ItemStack(Material.NETHER_STAR);
    }



}
