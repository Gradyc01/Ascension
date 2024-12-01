package me.depickcator.ascension.Items.Craftable.Unlocks;

import me.depickcator.ascension.Ascension;
import me.depickcator.ascension.Items.Craftable.Craft;
import me.depickcator.ascension.Items.Craftable.Crafts;
import me.depickcator.ascension.Items.UnlockUtil;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.Recipe;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.meta.EnchantmentStorageMeta;

public class BookOfProjectileProtection extends Craft {
    private static BookOfProjectileProtection instance;
    private BookOfProjectileProtection() {
        super(1, 4, "Artemis's Book", "book_of_projectile_protection");
    }

    @Override
    protected Recipe initRecipe() {
        NamespacedKey key = new NamespacedKey(plugin, KEY);
        ShapedRecipe recipe = new ShapedRecipe(key, result);
        recipe.shape("A  ", " BB", " BC");
        recipe.setIngredient('A', Material.ROTTEN_FLESH);
        recipe.setIngredient('B', Material.PAPER);
        recipe.setIngredient('C', Material.ARROW);
        UnlockUtil.addUnlock(plugin, recipe, MAX_CRAFTS, DISPLAY_NAME);
        return recipe;
    }

    @Override
    protected ItemStack initResult() {
        ItemStack item = new ItemStack(Material.ENCHANTED_BOOK);
        EnchantmentStorageMeta storageMeta = (EnchantmentStorageMeta) item.getItemMeta();
        storageMeta.addStoredEnchant(Enchantment.PROJECTILE_PROTECTION, 1, true);
        item.setItemMeta(storageMeta);
        return item;
    }

    public static BookOfProjectileProtection getInstance() {
        if (instance == null) instance = new BookOfProjectileProtection();
        return instance;
    }

}
