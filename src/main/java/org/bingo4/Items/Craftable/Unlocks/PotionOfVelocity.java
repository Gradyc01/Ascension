package org.bingo4.Items.Craftable.Unlocks;

import org.bingo4.Bingo4;
import org.bingo4.Items.Craftable.Craft;
import org.bingo4.Items.UnlockUtil;
import org.bingo4.Items.UnlocksData;
import org.bingo4.Utility.TextUtil;
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
    public PotionOfVelocity() {
        super(UnlocksData.COST_225, 3, "Potion of Velocity", "potion_of_velocity");
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
        potionMeta.setCustomModelData(Bingo4.getInstance().generateModelNumber());
        item.setItemMeta(potionMeta);
        return item;
    }



}
