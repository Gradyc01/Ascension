package me.depickcator.ascension.Items.Craftable.Vanilla;

import me.depickcator.ascension.Items.Craftable.Craft;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.Recipe;
import org.bukkit.inventory.ShapedRecipe;

public class IronAxe extends Craft implements Vanilla {
    private static IronAxe instance;
    private IronAxe() {
        super("Iron Axe", "iron_axe");
    }

    public static IronAxe getInstance() {
        if (instance == null) instance = new IronAxe();
        return instance;
    }

    @Override
    protected Recipe initRecipe() {
        NamespacedKey key = NamespacedKey.minecraft(KEY);

        ShapedRecipe recipe = new ShapedRecipe(key, result);
        recipe.shape("AA", "AB", " B");
        recipe.setIngredient('A', Material.IRON_INGOT);
        recipe.setIngredient('B', Material.STICK);
        plugin.getServer().addRecipe(recipe);
        return recipe;
    }

    @Override
    protected ItemStack initResult() {
        ItemStack item = new ItemStack(Material.IRON_AXE);
        double attackDamage = 12;
        double attackSpeed = -3;
        item.setItemMeta(Vanilla.addModifiers(item.getItemMeta(), attackDamage, attackSpeed, KEY));
        return item;
    }
}