package me.depickcator.ascension.Items.Craftable.Unlocks;

import me.depickcator.ascension.Ascension;
import me.depickcator.ascension.Items.Craftable.Craft;
import me.depickcator.ascension.Items.UnlockUtil;
import me.depickcator.ascension.Items.UnlocksData;
import me.depickcator.ascension.Utility.TextUtil;
import net.kyori.adventure.text.Component;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.Recipe;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.meta.ArmorMeta;
import org.bukkit.inventory.meta.trim.ArmorTrim;
import org.bukkit.inventory.meta.trim.TrimMaterial;
import org.bukkit.inventory.meta.trim.TrimPattern;

public class ApprenticeHelmet extends Craft {
    private static ApprenticeHelmet instance;
    private ApprenticeHelmet() {
        super(UnlocksData.COST_100, 2, "Apprentice Helmet", "apprentice_helmet");
    }

    @Override
    protected Recipe initRecipe() {
        NamespacedKey key = new NamespacedKey(plugin, KEY);
        ShapedRecipe recipe = new ShapedRecipe(key, result);
        recipe.shape("BBB", "BAB");
        recipe.setIngredient('A', Material.REDSTONE_TORCH);
        recipe.setIngredient('B', Material.IRON_INGOT);
        UnlockUtil.addUnlock(plugin, recipe, MAX_CRAFTS, DISPLAY_NAME);
        return recipe;
    }

    @Override
    protected ItemStack initResult() {
        ItemStack item = new ItemStack(Material.IRON_HELMET);
        ArmorMeta meta = (ArmorMeta) item.getItemMeta();
        meta.addEnchant(Enchantment.PROTECTION, 1, true);
        meta.addEnchant(Enchantment.FIRE_PROTECTION, 1, true);
        meta.addEnchant(Enchantment.BLAST_PROTECTION, 1, true);
        meta.addEnchant(Enchantment.PROJECTILE_PROTECTION, 1, true);
        meta.setCustomModelData(Ascension.getInstance().generateModelNumber());
        Component text = TextUtil.makeText(DISPLAY_NAME, TextUtil.GREEN);
        meta.displayName(text);
        ArmorTrim armorTrim = new ArmorTrim(TrimMaterial.IRON, TrimPattern.SPIRE);
        meta.setTrim(armorTrim);
        item.setItemMeta(meta);
        return item;
    }

    public static ApprenticeHelmet getInstance() {
        if (instance == null) instance = new ApprenticeHelmet();
        return instance;
    }
}
