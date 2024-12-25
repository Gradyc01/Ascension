package me.depickcator.ascension.Items.Craftable.Vanilla;

import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.Recipe;
import org.bukkit.inventory.RecipeChoice;
import org.bukkit.inventory.ShapedRecipe;

public class StoneAxe extends Weapons implements Vanilla {
    private static StoneAxe instance;
    private StoneAxe() {
        super("Stone Axe", "stone_axe", 6, -3.1);
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
        item.setItemMeta(Vanilla.addModifiers(item.getItemMeta(), getAttackDamage(), getAttackSpeed(), KEY));
        return item;
    }
}