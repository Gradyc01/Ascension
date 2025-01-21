package me.depickcator.ascension.Items.Craftable.Unlocks;

import me.depickcator.ascension.Items.Craftable.Craft;
import me.depickcator.ascension.Items.UnlockUtil;
import me.depickcator.ascension.Items.UnlocksData;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.Recipe;
import org.bukkit.inventory.ShapedRecipe;

public class GoldPack extends Craft {
    private static GoldPack instance;
    private GoldPack() {
        super(UnlocksData.COST_75, 4, "Gold Pack","gold_pack");
    }


    @Override
    protected Recipe initRecipe() {
        NamespacedKey key = new NamespacedKey(plugin, KEY);

        ShapedRecipe recipe = new ShapedRecipe(key, result);
        recipe.shape("AAA", "ABA", "AAA");
        recipe.setIngredient('A', Material.RAW_GOLD);
        recipe.setIngredient('B', Material.COAL);
        UnlockUtil.addUnlock(plugin, recipe, MAX_CRAFTS, DISPLAY_NAME);
        return recipe;
    }

    @Override
    protected ItemStack initResult() {
        return new ItemStack(Material.GOLD_INGOT, 12);
    }

    public static GoldPack getInstance() {
        if (instance == null) instance = new GoldPack();
        return instance;
    }

}
