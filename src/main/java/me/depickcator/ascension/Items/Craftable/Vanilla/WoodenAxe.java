package me.depickcator.ascension.Items.Craftable.Vanilla;

import me.depickcator.ascension.Items.Craftable.Craft;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.Tag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.Recipe;
import org.bukkit.inventory.RecipeChoice;
import org.bukkit.inventory.ShapedRecipe;

public class WoodenAxe extends Craft implements Vanilla {
    private static WoodenAxe instance;
    private WoodenAxe() {
        super("Wood Axe", "wooden_axe");
    }

    public static WoodenAxe getInstance() {
        if (instance == null) instance = new WoodenAxe();
        return instance;
    }

    @Override
    protected Recipe initRecipe() {
        NamespacedKey key = NamespacedKey.minecraft(KEY);

        ShapedRecipe recipe = new ShapedRecipe(key, result);
        recipe.shape("AA", "AB", " B");
        RecipeChoice.MaterialChoice planks = new RecipeChoice.MaterialChoice(Tag.PLANKS);
        recipe.setIngredient('A', planks);
        recipe.setIngredient('B', Material.STICK);
        plugin.getServer().addRecipe(recipe);
        return recipe;
    }

    @Override
    protected ItemStack initResult() {
        ItemStack item = new ItemStack(Material.WOODEN_AXE);
        double attackDamage = 8; double attackSpeed = -3.1;
        item.setItemMeta(Vanilla.addModifiers(item.getItemMeta(), attackDamage, attackSpeed, KEY));
        return item;
    }
}