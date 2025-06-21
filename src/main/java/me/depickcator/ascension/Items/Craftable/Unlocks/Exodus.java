package me.depickcator.ascension.Items.Craftable.Unlocks;

import me.depickcator.ascension.Ascension;
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
import org.bukkit.inventory.meta.ArmorMeta;
import org.bukkit.inventory.meta.Repairable;
import org.bukkit.inventory.meta.trim.ArmorTrim;
import org.bukkit.inventory.meta.trim.TrimMaterial;
import org.bukkit.inventory.meta.trim.TrimPattern;

public class Exodus extends Craft {
    private static Exodus instance;
    private Exodus() {
        super(875, 1, "Exodus" ,"exodus");
    }

    @Override
    protected Recipe initRecipe() {
        NamespacedKey key = new NamespacedKey(plugin, KEY);

        ShapedRecipe recipe = new ShapedRecipe(key, result);
        recipe.shape("CCC", "CAC", "DBD");
        recipe.setIngredient('A', Material.PLAYER_HEAD);
        recipe.setIngredient('B', Material.GOLDEN_CARROT);
        recipe.setIngredient('C', Material.DIAMOND);
        recipe.setIngredient('D', Material.EMERALD);
        UnlockUtil.addUnlock(plugin, recipe, MAX_CRAFTS, DISPLAY_NAME);
        return recipe;
    }

    @Override
    protected ItemStack initResult() {
        ItemStack item = new ItemStack(Material.DIAMOND_HELMET);
        Repairable meta = (Repairable) item.getItemMeta();
        meta.setCustomModelData(Ascension.getInstance().generateModelNumber());
        meta.displayName(TextUtil.makeText(DISPLAY_NAME, TextUtil.YELLOW));
        meta.setRepairCost(999);
        meta.addEnchant(Enchantment.UNBREAKING, 3, true);
        item.setItemMeta(meta);
        ArmorMeta meta2 = (ArmorMeta) item.getItemMeta();
        ArmorTrim armorTrim = new ArmorTrim(TrimMaterial.GOLD, TrimPattern.SPIRE);
        meta2.setTrim(armorTrim);
        item.setItemMeta(meta2);
        return item;
    }

    public static Exodus getInstance() {
        if (instance == null) instance = new Exodus();
        return instance;
    }
}
