package me.depickcator.ascension.Items.Craftable.Unlocks;

import me.depickcator.ascension.Ascension;
import me.depickcator.ascension.General.TextUtil;
import me.depickcator.ascension.Items.Craftable.Craft;
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

public class Nectar extends Craft {
    private static Nectar instance;
    public Nectar() {
        super(1, 3,  "Nectar", "nectar");
    }

    @Override
    protected Recipe initRecipe() {
        NamespacedKey key = new NamespacedKey(plugin, KEY);

        ShapedRecipe recipe = new ShapedRecipe(key, result);
        recipe.shape(" A ", "BCB", " D ");
        recipe.setIngredient('A', Material.EMERALD);
        recipe.setIngredient('B', Material.GOLD_INGOT);
        recipe.setIngredient('C', Material.MELON_SLICE);
        recipe.setIngredient('D', Material.GLASS_BOTTLE);
        UnlockUtil.addUnlock(plugin, recipe, MAX_CRAFTS, DISPLAY_NAME);
        return recipe;
    }

    @Override
    protected ItemStack initResult() {
        ItemStack item = new ItemStack(Material.POTION);
        PotionMeta potionMeta = (PotionMeta) item.getItemMeta();
        potionMeta.displayName(TextUtil.makeText(DISPLAY_NAME, TextUtil.YELLOW));
        potionMeta.setColor(Color.fromRGB(0xFF, 0x55, 0xFF));
        potionMeta.addCustomEffect(new PotionEffect(PotionEffectType.REGENERATION, 12 * 20, 2), true);
        potionMeta.setCustomModelData(Ascension.getInstance().generateModelNumber());
        item.setItemMeta(potionMeta);
        return item;
    }

    public static Nectar getInstance() {
        if (instance == null) instance = new Nectar();
        return instance;
    }



}
