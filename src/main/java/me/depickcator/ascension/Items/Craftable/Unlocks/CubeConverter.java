package me.depickcator.ascension.Items.Craftable.Unlocks;

import me.depickcator.ascension.Items.Craftable.Craft;
import me.depickcator.ascension.Items.UnlockUtil;
import me.depickcator.ascension.Items.UnlocksData;
import me.depickcator.ascension.Utility.TextUtil;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.Recipe;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.meta.PotionMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class CubeConverter extends Craft {
    private static CubeConverter instance;
    private CubeConverter() {
        super(UnlocksData.COST_500, 3, "Cube Converter", "cube_converter");
    }

    public void recipe() {

    }


    @Override
    protected Recipe initRecipe() {
        NamespacedKey key = new NamespacedKey(plugin, KEY);

        ShapedRecipe recipe = new ShapedRecipe(key, result);
        recipe.shape("A", "B", "C");
        recipe.setIngredient('A', Material.EMERALD);
        recipe.setIngredient('B', Material.PLAYER_HEAD);
        recipe.setIngredient('C', Material.MOSSY_COBBLESTONE);
        UnlockUtil.addUnlock(plugin, recipe, MAX_CRAFTS, DISPLAY_NAME);
        return recipe;
    }

    @Override
    protected ItemStack initResult() {
        ItemStack item = new ItemStack(Material.SPLASH_POTION);
        PotionMeta potionMeta = (PotionMeta) item.getItemMeta();
        potionMeta.displayName(TextUtil.makeText("Splash Potion of Oozing"));
        potionMeta.setColor(Color.fromRGB(0x00, 0x66, 0x00));
        potionMeta.addCustomEffect(new PotionEffect(PotionEffectType.OOZING, 20 * 60 * 20, 0), true);
        item.setItemMeta(potionMeta);
        return item;
    }

    public static CubeConverter getInstance() {
        if (instance == null) instance = new CubeConverter();
        return instance;
    }

}
