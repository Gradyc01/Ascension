package me.depickcator.ascension.Items.Craftable.Unlocks;

import me.depickcator.ascension.Ascension;
import me.depickcator.ascension.General.TextUtil;
import me.depickcator.ascension.Items.Craftable.Crafts;
import me.depickcator.ascension.Items.UnlockUtil;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.Recipe;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.meta.PotionMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class Panacea implements Crafts {
    private final Ascension plugin;
    private Recipe recipe;
    public static final int COST = 1;
    public static final int MAX_CRAFTS = 1;
    public static final String DISPLAY_NAME = "Panacea";
    public static final String KEY = "panacea";
    private static final ItemStack result = Panacea.makeItem();
    private static final int modelNumber = Ascension.generateModelNumber();
    public Panacea() {
        this.plugin = Ascension.getInstance();
        recipe();
    }

    public void recipe() {
        NamespacedKey key = new NamespacedKey(plugin, KEY);
        ItemStack item = Panacea.result();

        ShapedRecipe recipe = new ShapedRecipe(key, item);
        recipe.shape("BAB", " C ");
        recipe.setIngredient('A', Material.GLISTERING_MELON_SLICE);
        recipe.setIngredient('B', Material.PLAYER_HEAD);
        recipe.setIngredient('C', Material.GLASS_BOTTLE);
        UnlockUtil.addUnlock(plugin, recipe, MAX_CRAFTS, DISPLAY_NAME);
        this.recipe = recipe;
    }

    @Override
    public String getKey() {
        return KEY;
    }

    @Override
    public ItemStack getResult() {
        return Panacea.result();
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
        ItemStack item = new ItemStack(Material.POTION);
        PotionMeta potionMeta = (PotionMeta) item.getItemMeta();
        potionMeta.displayName(TextUtil.makeText(DISPLAY_NAME, TextUtil.PINK));
        potionMeta.setColor(Color.fromRGB(0xFF, 0x55, 0xFF));
        potionMeta.addCustomEffect(new PotionEffect(PotionEffectType.INSTANT_HEALTH, 20, 4), true);
        potionMeta.setCustomModelData(modelNumber);
        item.setItemMeta(potionMeta);
        return item;
    }
}
