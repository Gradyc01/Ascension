package me.depickcator.ascension.Items.Craftable.Unlocks;

import me.depickcator.ascension.Ascension;
import me.depickcator.ascension.General.TextUtil;
import me.depickcator.ascension.Items.Craftable.Craft;
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

public class PotionOfVelocity extends Craft {
    private static PotionOfVelocity instance;
    private final int modelNumber = Ascension.generateModelNumber();
    public PotionOfVelocity() {
        super(1, 3, "Potion of Velocity", "potion_of_velocity");
    }

    public static PotionOfVelocity getInstance() {
        if (instance == null) instance = new PotionOfVelocity();
        return instance;
    }

    @Override
    protected Recipe initRecipe() {
        NamespacedKey key = new NamespacedKey(plugin, KEY);

        ShapedRecipe recipe = new ShapedRecipe(key, result);
        recipe.shape("A", "B", "C");
        recipe.setIngredient('A', Material.COCOA_BEANS);
        recipe.setIngredient('B', Material.SUGAR);
        recipe.setIngredient('C', Material.GLASS_BOTTLE);
        UnlockUtil.addUnlock(plugin, recipe, MAX_CRAFTS, DISPLAY_NAME);
        return recipe;
    }

    @Override
    protected ItemStack initResult() {
        ItemStack item = new ItemStack(Material.SPLASH_POTION);
        PotionMeta potionMeta = (PotionMeta) item.getItemMeta();
        potionMeta.displayName(TextUtil.makeText(DISPLAY_NAME, TextUtil.YELLOW));
        potionMeta.setColor(Color.fromRGB(0x00, 0x00, 0x88));
        potionMeta.addCustomEffect(new PotionEffect(PotionEffectType.SPEED, 15 * 20, 2), true);
        potionMeta.setCustomModelData(modelNumber);
        item.setItemMeta(potionMeta);
        return item;
    }



}