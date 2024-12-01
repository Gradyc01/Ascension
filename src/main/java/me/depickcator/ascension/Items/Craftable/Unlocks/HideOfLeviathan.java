package me.depickcator.ascension.Items.Craftable.Unlocks;

import me.depickcator.ascension.Ascension;
import me.depickcator.ascension.General.TextUtil;
import me.depickcator.ascension.Items.Craftable.Crafts;
import me.depickcator.ascension.Items.UnlockUtil;
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

public class HideOfLeviathan implements Crafts {
    private final Ascension plugin;
    private Recipe recipe;
    public static final int COST = 1;
    public static final int MAX_CRAFTS = 2;
    public static final String DISPLAY_NAME = "Hide of Leviathan";
    public static final String KEY = "hide_of_leviathan";
    private static final ItemStack result = HideOfLeviathan.makeItem();
    private static final int modelNumber = Ascension.generateModelNumber();
    public HideOfLeviathan() {
        this.plugin = Ascension.getInstance();
        recipe();
    }

    public void recipe() {
        NamespacedKey key = new NamespacedKey(plugin, KEY);
        ItemStack item = HideOfLeviathan.result();

        ShapedRecipe recipe = new ShapedRecipe(key, item);
        recipe.shape("ADA", "CBC", "E E");
        recipe.setIngredient('A', Material.LAPIS_BLOCK);
        recipe.setIngredient('B', Material.DIAMOND_LEGGINGS);
        recipe.setIngredient('C', Material.DIAMOND);
        recipe.setIngredient('D', Material.WATER_BUCKET);
        recipe.setIngredient('E', Material.LILY_PAD);
        UnlockUtil.addUnlock(plugin, recipe, MAX_CRAFTS, DISPLAY_NAME);
        this.recipe = recipe;
    }

    @Override
    public String getKey() {
        return KEY;
    }

    @Override
    public ItemStack getResult() {
        return HideOfLeviathan.result();
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
        return result;
    }

    private static ItemStack makeItem() {
        ItemStack item = new ItemStack(Material.DIAMOND_LEGGINGS);
        ArmorMeta meta = (ArmorMeta) item.getItemMeta();
        meta.addEnchant(Enchantment.PROTECTION, 4, true);
        meta.addEnchant(Enchantment.RESPIRATION, 3, true);
        meta.addEnchant(Enchantment.AQUA_AFFINITY, 1, true);
        meta.setCustomModelData(modelNumber);
        Component text = TextUtil.makeText(DISPLAY_NAME, TextUtil.AQUA);
        meta.displayName(text);
        ArmorTrim armorTrim = new ArmorTrim(TrimMaterial.EMERALD, TrimPattern.TIDE);
        meta.setTrim(armorTrim);
        item.setItemMeta(meta);
        return item;
    }
}
