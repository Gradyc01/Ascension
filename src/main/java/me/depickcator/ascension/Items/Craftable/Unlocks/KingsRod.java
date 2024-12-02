package me.depickcator.ascension.Items.Craftable.Unlocks;

import me.depickcator.ascension.Ascension;
import me.depickcator.ascension.General.TextUtil;
import me.depickcator.ascension.Items.Craftable.Craft;
import me.depickcator.ascension.Items.UnlockUtil;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.Recipe;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.Repairable;

public class KingsRod extends Craft {
    private static KingsRod instance;
    private KingsRod() {
        super(1, 1, "Kings Rod", "kings_rod");
    }

    public static KingsRod getInstance() {
        if (instance == null) instance = new KingsRod();
        return instance;
    }

    @Override
    protected Recipe initRecipe() {
        NamespacedKey key = new NamespacedKey(plugin, KEY);

        ShapedRecipe recipe = new ShapedRecipe(key, result);
        recipe.shape(" B ", "ACA", " D ");
        recipe.setIngredient('A', Material.LILY_PAD);
        recipe.setIngredient('B', Material.FISHING_ROD);
        recipe.setIngredient('C', Material.COMPASS);
        recipe.setIngredient('D', Material.WATER_BUCKET);
        UnlockUtil.addUnlock(plugin, recipe, MAX_CRAFTS, DISPLAY_NAME);
        return recipe;
    }

    @Override
    protected ItemStack initResult() {
        ItemStack item = new ItemStack(Material.FISHING_ROD);
        ItemMeta meta = item.getItemMeta();
        meta.displayName(TextUtil.makeText(DISPLAY_NAME, TextUtil.AQUA));
        meta.setCustomModelData(Ascension.getInstance().generateModelNumber());
        meta.addEnchant(Enchantment.LUCK_OF_THE_SEA, 10, true);
        meta.addEnchant(Enchantment.LURE, 5, true);
        meta.addEnchant(Enchantment.UNBREAKING, 10, true);
        if (meta instanceof Repairable repairable) {
            repairable.setRepairCost(999);
        }
        item.setItemMeta(meta);
        return item;
    }

}
