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

public class BookOfPower extends Craft {
    private static BookOfPower instance;
    private BookOfPower() {
        super(UnlocksData.COST_225, 4, "Book of Power", "book_of_power");
    }

    @Override
    protected Recipe initRecipe() {
        NamespacedKey key = new NamespacedKey(plugin, KEY);
        ShapedRecipe recipe = new ShapedRecipe(key, result);
        recipe.shape("A  ", " BB", " BC");
        recipe.setIngredient('A', Material.FLINT);
        recipe.setIngredient('B', Material.PAPER);
        recipe.setIngredient('C', Material.BONE);
        UnlockUtil.addUnlock(plugin, recipe, MAX_CRAFTS, DISPLAY_NAME);
        return recipe;
    }

    @Override
    protected ItemStack initResult() {
        ItemStack item = new ItemStack(Material.ENCHANTED_BOOK);
        EnchantmentStorageMeta storageMeta = (EnchantmentStorageMeta) item.getItemMeta();
        storageMeta.addStoredEnchant(Enchantment.POWER, 1, true);
        item.setItemMeta(storageMeta);
        return item;
    }

    public static BookOfPower getInstance() {
        if (instance == null) instance = new BookOfPower();
        return instance;
    }

}
