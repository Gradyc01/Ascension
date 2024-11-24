package me.depickcator.ascensionBingo.Items.Craftable.Unlocks;

import me.depickcator.ascensionBingo.AscensionBingo;
import me.depickcator.ascensionBingo.General.TextUtil;
import me.depickcator.ascensionBingo.Items.Craftable.Crafts;
import me.depickcator.ascensionBingo.Items.UnlockUtil;
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

public class Tarnhelm implements Crafts {
    private final AscensionBingo plugin;
    private Recipe recipe;
    public static final int COST = 1;
    public static final int MAX_CRAFTS = 2;
    public static final String DISPLAY_NAME = "Tarnhelm";
    public static final String KEY = "tarnhelm";
    public Tarnhelm(AscensionBingo plugin) {
        this.plugin = plugin;
        recipe();
    }

    public void recipe() {
        NamespacedKey key = new NamespacedKey(plugin, KEY);
        ItemStack item = Tarnhelm.result();
        ShapedRecipe recipe = new ShapedRecipe(key, item);
        recipe.shape("ACA", "ABA");
        recipe.setIngredient('A', Material.DIAMOND);
        recipe.setIngredient('B', Material.REDSTONE_BLOCK);
        recipe.setIngredient('C', Material.IRON_INGOT);
        UnlockUtil.addUnlock(plugin, recipe, MAX_CRAFTS, DISPLAY_NAME);
        this.recipe = recipe;
    }

    public static ItemStack result() {
        ItemStack item = new ItemStack(Material.DIAMOND_HELMET);
        ArmorMeta meta = (ArmorMeta) item.getItemMeta();
        meta.addEnchant(Enchantment.PROTECTION, 1, true);
        meta.addEnchant(Enchantment.FIRE_PROTECTION, 1, true);
        meta.addEnchant(Enchantment.AQUA_AFFINITY, 3, true);
        meta.setCustomModelData(21);
        Component text = TextUtil.makeText(DISPLAY_NAME, TextUtil.AQUA);
        meta.displayName(text);
        ArmorTrim armorTrim = new ArmorTrim(TrimMaterial.REDSTONE, TrimPattern.SPIRE);
        meta.setTrim(armorTrim);
        item.setItemMeta(meta);
        return item;
    }

    @Override
    public String getKey() {
        return KEY;
    }

    @Override
    public ItemStack getResult() {
        return Tarnhelm.result();
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
