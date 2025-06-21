package me.depickcator.ascension.Items.Craftable.Unlocks;

import me.depickcator.ascension.Items.Craftable.Craft;
import me.depickcator.ascension.Items.UnlockUtil;
import me.depickcator.ascension.Items.UnlocksData;
import me.depickcator.ascension.Utility.TextUtil;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.Recipe;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.meta.EnchantmentStorageMeta;

public class TabletsOfDestiny extends Craft {
    private static TabletsOfDestiny instance;
    private TabletsOfDestiny() {
        super(800, 1, "Tablets of Destiny", "tablets_of_destiny");
    }
    @Override
    protected Recipe initRecipe() {
        NamespacedKey key = new NamespacedKey(plugin, KEY);

        ShapedRecipe recipe = new ShapedRecipe(key, result);
        recipe.shape(" D ", "CAB", "EEE");
        recipe.setIngredient('A', Material.WRITABLE_BOOK);
        recipe.setIngredient('B', Material.BOW);
        recipe.setIngredient('C', Material.DIAMOND_SWORD);
        recipe.setIngredient('D', Material.MAGMA_CREAM);
        recipe.setIngredient('E', Material.EXPERIENCE_BOTTLE);
        UnlockUtil.addUnlock(plugin, recipe, MAX_CRAFTS, DISPLAY_NAME);
        return recipe;
    }

    @Override
    protected ItemStack initResult() {
        ItemStack item = new ItemStack(Material.ENCHANTED_BOOK);
        EnchantmentStorageMeta storageMeta = (EnchantmentStorageMeta) item.getItemMeta();
        storageMeta.displayName(TextUtil.makeText(getDisplayName(), TextUtil.YELLOW));
        storageMeta.setCustomModelData(plugin.generateModelNumber());
        storageMeta.addStoredEnchant(Enchantment.PROTECTION, 4, true);
        storageMeta.addStoredEnchant(Enchantment.FIRE_ASPECT, 1, true);
        storageMeta.addStoredEnchant(Enchantment.POWER, 3, true);
        storageMeta.addStoredEnchant(Enchantment.SHARPNESS, 3, true);
        item.setItemMeta(storageMeta);
        return item;
    }

    public static TabletsOfDestiny getInstance() {
        if (instance == null) instance = new TabletsOfDestiny();
        return instance;
    }
}
