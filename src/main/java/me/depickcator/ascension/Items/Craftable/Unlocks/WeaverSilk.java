package me.depickcator.ascension.Items.Craftable.Unlocks;

import me.depickcator.ascension.Items.Craftable.Craft;
import me.depickcator.ascension.Items.UnlockUtil;
import me.depickcator.ascension.Items.UnlocksData;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.Recipe;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.meta.EnchantmentStorageMeta;

public class WeaverSilk extends Craft {
    private static WeaverSilk instance;
    private WeaverSilk() {
        super(UnlocksData.COST_275, 1, "A Weaver's Silk", "weaver_silk");
    }

    public static WeaverSilk getInstance() {
        if (instance == null) instance = new WeaverSilk();
        return instance;
    }

    @Override
    protected Recipe initRecipe() {
        NamespacedKey key = new NamespacedKey(plugin, KEY);
        ShapedRecipe recipe = new ShapedRecipe(key, result);
        recipe.shape("BAA", "ADA", "AAC");
        recipe.setIngredient('A', Material.COBWEB);
        recipe.setIngredient('B', Material.FERMENTED_SPIDER_EYE);
        recipe.setIngredient('C', Material.NETHER_STAR);
        recipe.setIngredient('D', Material.BOOK);
        UnlockUtil.addUnlock(plugin, recipe, MAX_CRAFTS, DISPLAY_NAME);
        return recipe;
    }

    @Override
    protected ItemStack initResult() {
        ItemStack item = new ItemStack(Material.ENCHANTED_BOOK);
        EnchantmentStorageMeta itemMeta = (EnchantmentStorageMeta) item.getItemMeta();
        itemMeta.addStoredEnchant(Enchantment.SILK_TOUCH, 1, true);
        item.setItemMeta(itemMeta);
        return item;
    }
}
