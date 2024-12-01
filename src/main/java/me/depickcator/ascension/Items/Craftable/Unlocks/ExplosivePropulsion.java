package me.depickcator.ascension.Items.Craftable.Unlocks;

import me.depickcator.ascension.Ascension;
import me.depickcator.ascension.Items.Craftable.Craft;
import me.depickcator.ascension.Items.Craftable.Crafts;
import me.depickcator.ascension.Items.UnlockUtil;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.Recipe;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.meta.FireworkMeta;

public class ExplosivePropulsion extends Craft {
    private static ExplosivePropulsion instance;
    private ExplosivePropulsion() {
        super(1, 2, "Explosive Propulsion", "explosive_propulsion");
    }

    @Override
    protected Recipe initRecipe() {
        NamespacedKey key = new NamespacedKey(plugin, KEY);
        ShapedRecipe recipe = new ShapedRecipe(key, result);
        recipe.shape("ABA", "ACA", "ABA");
        recipe.setIngredient('A', Material.PAPER);
        recipe.setIngredient('B', Material.GUNPOWDER);
        recipe.setIngredient('C', Material.NETHER_STAR);
        UnlockUtil.addUnlock(plugin, recipe, MAX_CRAFTS, DISPLAY_NAME);
        return recipe;
    }

    @Override
    protected ItemStack initResult() {
        ItemStack item = new ItemStack(Material.FIREWORK_ROCKET, 16);
        FireworkMeta itemMeta = (FireworkMeta) item.getItemMeta();
        itemMeta.setPower(3);
        item.setItemMeta(itemMeta);
        return item;
    }

    public static ExplosivePropulsion getInstance() {
        if (instance == null) instance = new ExplosivePropulsion();
        return instance;
    }

}
