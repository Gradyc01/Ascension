package me.depickcator.ascension.Items.Craftable.Unlocks;

import me.depickcator.ascension.Ascension;
import me.depickcator.ascension.Items.Craftable.Crafts;
import me.depickcator.ascension.Items.UnlockUtil;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.Recipe;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.meta.EnchantmentStorageMeta;

public class BookOfProjectileProtection implements Crafts {
    private final Ascension plugin;
    private Recipe recipe;
    public static final int COST = 1;
    public static final int MAX_CRAFTS = 4;
    public static final String DISPLAY_NAME = "Artemis's Book";
    public static final String KEY = "book_of_projectile_protection";
    public BookOfProjectileProtection() {
        this.plugin = Ascension.getInstance();
        recipe();
    }

    @Override
    public void recipe() {
        NamespacedKey key = new NamespacedKey(plugin, KEY);
        ItemStack item = BookOfProjectileProtection.result();

        ShapedRecipe recipe = new ShapedRecipe(key, item);
        recipe.shape("A  ", " BB", " BC");
        recipe.setIngredient('A', Material.ROTTEN_FLESH);
        recipe.setIngredient('B', Material.PAPER);
        recipe.setIngredient('C', Material.ARROW);
        UnlockUtil.addUnlock(plugin, recipe, MAX_CRAFTS, DISPLAY_NAME);
        this.recipe = recipe;
    }

    public static ItemStack result() {
        ItemStack item = new ItemStack(Material.ENCHANTED_BOOK);
        EnchantmentStorageMeta storageMeta = (EnchantmentStorageMeta) item.getItemMeta();
        storageMeta.addStoredEnchant(Enchantment.PROJECTILE_PROTECTION, 1, true);
        item.setItemMeta(storageMeta);
        return item;
    }

    @Override
    public String getKey() {
        return KEY;
    }

    @Override
    public ItemStack getResult() {
        return BookOfProjectileProtection.result();
    }

    @Override
    public Recipe getRecipe() {
        return recipe;
    }

    @Override
    public String getDisplayName() {
        return DISPLAY_NAME;
    }

    @Override
    public int getCraftCost() {
        return COST;
    }


}
