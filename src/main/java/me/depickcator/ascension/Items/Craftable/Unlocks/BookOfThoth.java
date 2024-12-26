package me.depickcator.ascension.Items.Craftable.Unlocks;

import me.depickcator.ascension.Utility.TextUtil;
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

public class BookOfThoth extends Craft {
    private static BookOfThoth instance;
    private BookOfThoth() {
        super(UnlocksData.COST_250, 1, "Book Of Thoth", "book_of_thoth");
    }
    @Override
    protected Recipe initRecipe() {
        NamespacedKey key = new NamespacedKey(plugin, KEY);

        ShapedRecipe recipe = new ShapedRecipe(key, result);
        recipe.shape("A  ", " BB", " BC");
        recipe.setIngredient('A', Material.ENDER_EYE);
        recipe.setIngredient('B', Material.PAPER);
        recipe.setIngredient('C', Material.FIRE_CHARGE);
        UnlockUtil.addUnlock(plugin, recipe, MAX_CRAFTS, DISPLAY_NAME);
        return recipe;
    }

    @Override
    protected ItemStack initResult() {
        ItemStack item = new ItemStack(Material.ENCHANTED_BOOK);
        EnchantmentStorageMeta storageMeta = (EnchantmentStorageMeta) item.getItemMeta();
        storageMeta.displayName(TextUtil.makeText(getDisplayName(), TextUtil.YELLOW));
        storageMeta.setCustomModelData(plugin.generateModelNumber());
        storageMeta.addStoredEnchant(Enchantment.PROTECTION, 3, true);
        storageMeta.addStoredEnchant(Enchantment.FIRE_ASPECT, 1, true);
        storageMeta.addStoredEnchant(Enchantment.PUNCH, 1, true);
        storageMeta.addStoredEnchant(Enchantment.POWER, 2, true);
        storageMeta.addStoredEnchant(Enchantment.SHARPNESS, 2, true);
        item.setItemMeta(storageMeta);
        return item;
    }

    public static BookOfThoth getInstance() {
        if (instance == null) instance = new BookOfThoth();
        return instance;
    }
}
