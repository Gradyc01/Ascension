package me.depickcator.ascension.Items.Craftable.Unlocks;

import me.depickcator.ascension.Items.Uncraftable.NetherStar.NetherStar;
import me.depickcator.ascension.Items.UnlocksData;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.Recipe;
import org.bukkit.inventory.ShapelessRecipe;
import org.bukkit.inventory.meta.ItemMeta;

import me.depickcator.ascension.Ascension;
import me.depickcator.ascension.Utility.TextUtil;
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

        ShapelessRecipe recipe = new ShapelessRecipe(key, result);
        recipe.addIngredient(Material.DIAMOND);
        recipe.addIngredient(Material.GOLD_INGOT);
        recipe.addIngredient(Material.PLAYER_HEAD);
        recipe.addIngredient(Material.EMERALD);
        recipe.addIngredient(NetherStar.getInstance().getResult());
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
        meta.setMaxStackSize(1);
        item.setItemMeta(meta);
        return item;
    }

    public static AscensionKey getInstance() {
        if (instance == null) instance = new AscensionKey();
        return instance;
    }



}
