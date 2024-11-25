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

public class SevenLeagueBoots implements Crafts {
    private final AscensionBingo plugin;
    private Recipe recipe;
    public static final int COST = 1;
    public static final int MAX_CRAFTS = 2;
    public static final String DISPLAY_NAME = "Seven League Boots";
    public static final String KEY = "seven_league_boots";
    private static final ItemStack result = SevenLeagueBoots.makeItem();
    public SevenLeagueBoots(AscensionBingo plugin) {
        this.plugin = plugin;
        recipe();
    }

    public void recipe() {
        NamespacedKey key = new NamespacedKey(plugin, KEY);
        ItemStack item = SevenLeagueBoots.result();

        ShapedRecipe recipe = new ShapedRecipe(key, item);
        recipe.shape("CAC", "CBC", "CDC");
        recipe.setIngredient('A', Material.ENDER_PEARL);
        recipe.setIngredient('B', Material.DIAMOND_BOOTS);
        recipe.setIngredient('C', Material.FEATHER);
        recipe.setIngredient('D', Material.WATER_BUCKET);
        UnlockUtil.addUnlock(plugin, recipe, MAX_CRAFTS, DISPLAY_NAME);
        this.recipe = recipe;
    }

    @Override
    public String getKey() {
        return KEY;
    }

    @Override
    public ItemStack getResult() {
        return SevenLeagueBoots.result();
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
        ItemStack item = new ItemStack(Material.DIAMOND_BOOTS);
        ArmorMeta meta = (ArmorMeta) item.getItemMeta();
        meta.addEnchant(Enchantment.PROTECTION, 3, true);
        meta.addEnchant(Enchantment.FEATHER_FALLING, 4, true);
        meta.setCustomModelData(22);
        Component text = TextUtil.makeText(DISPLAY_NAME, TextUtil.AQUA);
        meta.displayName(text);
        ArmorTrim armorTrim = new ArmorTrim(TrimMaterial.IRON, TrimPattern.SNOUT);
        meta.setTrim(armorTrim);
        item.setItemMeta(meta);
        return item;
    }
}
