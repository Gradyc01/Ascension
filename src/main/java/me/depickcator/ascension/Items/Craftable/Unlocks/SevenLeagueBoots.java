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

public class SevenLeagueBoots extends Craft {
    private static SevenLeagueBoots instance;
    private SevenLeagueBoots() {
        super(UnlocksData.COST_300, 2, "Seven League Boots", "seven_league_boots");
    }

    public static SevenLeagueBoots getInstance() {
        if (instance == null) instance = new SevenLeagueBoots();
        return instance;
    }

    @Override
    protected Recipe initRecipe() {
        NamespacedKey key = new NamespacedKey(plugin, KEY);

        ShapedRecipe recipe = new ShapedRecipe(key, result);
        recipe.shape("CAC", "CBC", "CDC");
        recipe.setIngredient('A', Material.ENDER_PEARL);
        recipe.setIngredient('B', Material.DIAMOND_BOOTS);
        recipe.setIngredient('C', Material.FEATHER);
        recipe.setIngredient('D', Material.WATER_BUCKET);
        UnlockUtil.addUnlock(plugin, recipe, MAX_CRAFTS, DISPLAY_NAME);
        return recipe;
    }

    @Override
    protected ItemStack initResult() {
        ItemStack item = new ItemStack(Material.DIAMOND_BOOTS);
        ArmorMeta meta = (ArmorMeta) item.getItemMeta();
        meta.addEnchant(Enchantment.PROTECTION, 3, true);
        meta.addEnchant(Enchantment.FEATHER_FALLING, 4, true);
        meta.setCustomModelData(Ascension.getInstance().generateModelNumber());
        Component text = TextUtil.makeText(DISPLAY_NAME, TextUtil.AQUA);
        meta.displayName(text);
        ArmorTrim armorTrim = new ArmorTrim(TrimMaterial.IRON, TrimPattern.SNOUT);
        meta.setTrim(armorTrim);
        item.setItemMeta(meta);
        return item;
    }

}
