package me.depickcator.ascension.Items.Craftable.Unlocks;

import me.depickcator.ascension.Ascension;
import me.depickcator.ascension.General.TextUtil;
import me.depickcator.ascension.Items.Craftable.Craft;
import me.depickcator.ascension.Items.UnlockUtil;
import me.depickcator.ascension.Items.UnlocksData;
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

public class HideOfLeviathan extends Craft {
    private static HideOfLeviathan instance;
    private HideOfLeviathan() {
        super(UnlocksData.COST_300, 2, "Hide of Leviathan", "hide_of_leviathan");
    }

    public static HideOfLeviathan getInstance() {
        if (instance == null) instance = new HideOfLeviathan();
        return instance;
    }

    @Override
    protected Recipe initRecipe() {
        NamespacedKey key = new NamespacedKey(plugin, KEY);

        ShapedRecipe recipe = new ShapedRecipe(key, result);
        recipe.shape("ADA", "CBC", "E E");
        recipe.setIngredient('A', Material.LAPIS_BLOCK);
        recipe.setIngredient('B', Material.DIAMOND_LEGGINGS);
        recipe.setIngredient('C', Material.DIAMOND);
        recipe.setIngredient('D', Material.WATER_BUCKET);
        recipe.setIngredient('E', Material.LILY_PAD);
        UnlockUtil.addUnlock(plugin, recipe, MAX_CRAFTS, DISPLAY_NAME);
        return recipe;
    }

    @Override
    protected ItemStack initResult() {
        ItemStack item = new ItemStack(Material.DIAMOND_LEGGINGS);
        ArmorMeta meta = (ArmorMeta) item.getItemMeta();
        meta.addEnchant(Enchantment.PROTECTION, 4, true);
        meta.addEnchant(Enchantment.RESPIRATION, 3, true);
        meta.addEnchant(Enchantment.AQUA_AFFINITY, 1, true);
        meta.setCustomModelData(Ascension.getInstance().generateModelNumber());
        Component text = TextUtil.makeText(DISPLAY_NAME, TextUtil.AQUA);
        meta.displayName(text);
        ArmorTrim armorTrim = new ArmorTrim(TrimMaterial.EMERALD, TrimPattern.TIDE);
        meta.setTrim(armorTrim);
        item.setItemMeta(meta);
        return item;
    }


}
