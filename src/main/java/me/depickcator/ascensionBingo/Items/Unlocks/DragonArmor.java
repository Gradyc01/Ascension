package me.depickcator.ascensionBingo.Items.Unlocks;

import me.depickcator.ascensionBingo.AscensionBingo;
import me.depickcator.ascensionBingo.General.TextUtil;
import me.depickcator.ascensionBingo.Items.UnlockUtil;
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

public class DragonArmor implements Crafts{
    private final AscensionBingo plugin;
    private Recipe recipe;
    public static final int COST = 1;
    public static final int MAX_CRAFTS = 2;
    public static final String DISPLAY_NAME = "Dragon Armor";
    public static final String KEY = "dragonarmor";
    public DragonArmor(AscensionBingo plugin) {
        this.plugin = plugin;
        recipe();
    }

    public void recipe() {
        NamespacedKey key = new NamespacedKey(plugin, KEY);
        ItemStack item = DragonArmor.result();

        RecipeChoice.MaterialChoice anvilChoice = new RecipeChoice.MaterialChoice(
                Material.ANVIL,
                Material.CHIPPED_ANVIL,
                Material.DAMAGED_ANVIL);

        ShapedRecipe recipe = new ShapedRecipe(key, item);
        recipe.shape(" A ", " B ", "CDC");
        recipe.setIngredient('A', Material.MAGMA_CREAM);
        recipe.setIngredient('B', Material.DIAMOND_CHESTPLATE);
        recipe.setIngredient('C', Material.OBSIDIAN);
        recipe.setIngredient('D', anvilChoice);
        UnlockUtil.addUnlock(plugin, recipe, MAX_CRAFTS, DISPLAY_NAME);
        this.recipe = recipe;
    }

    @Override
    public String getKey() {
        return KEY;
    }

    @Override
    public ItemStack getResult() {
        return DragonArmor.result();
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

    public static ItemStack result() {
        ItemStack item = new ItemStack(Material.DIAMOND_CHESTPLATE);
        ArmorMeta meta = (ArmorMeta) item.getItemMeta();
        meta.addEnchant(Enchantment.PROTECTION, 4, true);
        meta.setCustomModelData(20);
        Component text = Component.text("Dragon Armor").color(TextUtil.AQUA).decoration(TextDecoration.ITALIC, false);
        meta.displayName(text);
        ArmorTrim armorTrim = new ArmorTrim(TrimMaterial.NETHERITE, TrimPattern.DUNE);
        meta.setTrim(armorTrim);
        item.setItemMeta(meta);
        return item;
    }
}
