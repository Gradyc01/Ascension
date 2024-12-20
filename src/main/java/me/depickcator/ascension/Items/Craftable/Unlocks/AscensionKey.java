package me.depickcator.ascension.Items.Craftable.Unlocks;

import me.depickcator.ascension.Items.UnlocksData;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.Recipe;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.meta.ItemMeta;

import me.depickcator.ascension.Ascension;
import me.depickcator.ascension.General.TextUtil;
import me.depickcator.ascension.Items.Craftable.Craft;
import me.depickcator.ascension.Items.UnlockUtil;

public class AscensionKey extends Craft {
    private static AscensionKey instance;
    private AscensionKey() {
        super(UnlocksData.COST_500, 999, "Ascension Key", "ascension_key");
    }

    @Override
    protected Recipe initRecipe() {
        NamespacedKey key = new NamespacedKey(plugin, KEY);
        ShapedRecipe recipe = new ShapedRecipe(key, result);
        recipe.shape("AAA", "ACB", "BBB");
        recipe.setIngredient('A', Material.EMERALD);
        recipe.setIngredient('B', Material.DIAMOND);
        recipe.setIngredient('C', Material.NETHER_STAR);
        UnlockUtil.addUnlock(plugin, recipe, MAX_CRAFTS, DISPLAY_NAME);
        return recipe;
    }

    @Override
    protected ItemStack initResult() {
        ItemStack item = new ItemStack(Material.TRIAL_KEY);
        ItemMeta meta = item.getItemMeta();
        meta.setEnchantmentGlintOverride(true);
        meta.setCustomModelData(Ascension.getInstance().generateModelNumber());
        meta.displayName(TextUtil.makeText(getDisplayName(), TextUtil.PINK));
        item.setItemMeta(meta);
        return item;
    }

    public static AscensionKey getInstance() {
        if (instance == null) instance = new AscensionKey();
        return instance;
    }



}
