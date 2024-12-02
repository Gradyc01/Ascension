package me.depickcator.ascension.Items.Craftable.Unlocks;

import me.depickcator.ascension.Items.Craftable.Craft;
import me.depickcator.ascension.Items.UnlockUtil;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.Recipe;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.meta.EnchantmentStorageMeta;

public class BookOfProtection extends Craft {
    private static BookOfProtection instance;
    private BookOfProtection() {
        super(1, 4, "Book of Protection", "book_of_protection");
    }


    @Override
    protected Recipe initRecipe() {
        NamespacedKey key = new NamespacedKey(plugin, KEY);
        ShapedRecipe recipe = new ShapedRecipe(key, result);
        recipe.shape("A  ", " BB", " BC");
        recipe.setIngredient('A', Material.ROTTEN_FLESH);
        recipe.setIngredient('B', Material.PAPER);
        recipe.setIngredient('C', Material.IRON_INGOT);
        UnlockUtil.addUnlock(plugin, recipe, MAX_CRAFTS, DISPLAY_NAME);
        return recipe;
    }

    @Override
    protected ItemStack initResult() {
        ItemStack item = new ItemStack(Material.ENCHANTED_BOOK);
        EnchantmentStorageMeta storageMeta = (EnchantmentStorageMeta) item.getItemMeta();
        storageMeta.addStoredEnchant(Enchantment.PROTECTION, 1, true);
        item.setItemMeta(storageMeta);
        return item;
    }

    public static BookOfProtection getInstance() {
        if (instance == null) instance = new BookOfProtection();
        return instance;
    }


}
