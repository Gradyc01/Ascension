package me.depickcator.ascension.Items.Craftable.Vanilla;

import me.depickcator.ascension.Items.Craftable.Craft;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.Recipe;
import org.bukkit.inventory.RecipeChoice;
import org.bukkit.inventory.ShapedRecipe;

public class StoneAxe extends Craft implements Vanilla {
    private static StoneAxe instance;
    private StoneAxe() {
        super("Stone Axe", "stone_axe");
    }

    public static StoneAxe getInstance() {
        if (instance == null) instance = new StoneAxe();
        return instance;
    }

    @Override
    protected Recipe initRecipe() {
        NamespacedKey key = NamespacedKey.minecraft(KEY);

        ShapedRecipe recipe = new ShapedRecipe(key, result);
        recipe.shape("AA", "AB", " B");
        RecipeChoice.MaterialChoice stone = new RecipeChoice.MaterialChoice(Material.COBBLESTONE, Material.COBBLED_DEEPSLATE);
        recipe.setIngredient('A', stone);
        recipe.setIngredient('B', Material.STICK);
        plugin.getServer().addRecipe(recipe);
        return recipe;
    }

    @Override
    protected ItemStack initResult() {
        ItemStack item = new ItemStack(Material.STONE_AXE);
        double attackDamage = 10; double attackSpeed = -3.1;
        item.setItemMeta(Vanilla.addModifiers(item.getItemMeta(), attackDamage, attackSpeed, KEY));
        return item;
    }
}