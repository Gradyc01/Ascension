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

public class EnlighteningPack extends Craft {
    private static EnlighteningPack instance;
    private EnlighteningPack() {
        super(1, 4, "Enlightening Pack", "enlightening_pack");
    }

    @Override
    protected Recipe initRecipe() {
        NamespacedKey key = new NamespacedKey(plugin, KEY);

        ShapedRecipe recipe = new ShapedRecipe(key, result);
        recipe.shape(" A ", "ABA", " A ");
        recipe.setIngredient('A', Material.REDSTONE_BLOCK);
        recipe.setIngredient('B', Material.GLASS_BOTTLE);
        UnlockUtil.addUnlock(plugin, recipe, MAX_CRAFTS, DISPLAY_NAME);
        return recipe;
    }

    @Override
    protected ItemStack initResult() {
        return new ItemStack(Material.EXPERIENCE_BOTTLE, 16);
    }

    public static EnlighteningPack getInstance() {
        if (instance == null) instance = new EnlighteningPack();
        return instance;
    }

}
