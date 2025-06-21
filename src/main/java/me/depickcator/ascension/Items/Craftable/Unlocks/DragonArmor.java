package me.depickcator.ascension.Items.Craftable.Unlocks;

import me.depickcator.ascension.Ascension;
import me.depickcator.ascension.Utility.TextUtil;
import me.depickcator.ascension.Items.Craftable.Craft;
import me.depickcator.ascension.Items.UnlockUtil;
import me.depickcator.ascension.Items.UnlocksData;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.Recipe;
import org.bukkit.inventory.RecipeChoice;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.meta.ArmorMeta;
import org.bukkit.inventory.meta.trim.ArmorTrim;
import org.bukkit.inventory.meta.trim.TrimMaterial;
import org.bukkit.inventory.meta.trim.TrimPattern;

public class DragonArmor extends Craft {
    private static DragonArmor instance;

    private DragonArmor() {
        super(600, 2, "Dragon Armor", "dragon_armor");
    }


    @Override
    protected Recipe initRecipe() {
        NamespacedKey key = new NamespacedKey(plugin, KEY);

        RecipeChoice.MaterialChoice anvilChoice = new RecipeChoice.MaterialChoice(
                Material.ANVIL,
                Material.CHIPPED_ANVIL,
                Material.DAMAGED_ANVIL);

        ShapedRecipe recipe = new ShapedRecipe(key, result);
        recipe.shape(" A ", " B ", "CDC");
        recipe.setIngredient('A', Material.MAGMA_CREAM);
        recipe.setIngredient('B', Material.DIAMOND_CHESTPLATE);
        recipe.setIngredient('C', Material.OBSIDIAN);
        recipe.setIngredient('D', anvilChoice);
        UnlockUtil.addUnlock(plugin, recipe, MAX_CRAFTS, DISPLAY_NAME);
        return recipe;
    }

    @Override
    protected ItemStack initResult() {
        ItemStack item = new ItemStack(Material.DIAMOND_CHESTPLATE);
        ArmorMeta meta = (ArmorMeta) item.getItemMeta();
        meta.addEnchant(Enchantment.PROTECTION, 4, true);
        meta.setCustomModelData(Ascension.getInstance().generateModelNumber());
        Component text = Component.text("Dragon Armor").color(TextUtil.AQUA).decoration(TextDecoration.ITALIC, false);
        meta.displayName(text);
        ArmorTrim armorTrim = new ArmorTrim(TrimMaterial.NETHERITE, TrimPattern.DUNE);
        meta.setTrim(armorTrim);
        item.setItemMeta(meta);
        return item;
    }

    public static DragonArmor getInstance() {
        if (instance == null) instance = new DragonArmor();
        return instance;
    }
}
