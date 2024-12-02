package me.depickcator.ascension.Items.Craftable.Vanilla;


import me.depickcator.ascension.Items.Craftable.Craft;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.Tag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.Recipe;
import org.bukkit.inventory.RecipeChoice;
import org.bukkit.inventory.ShapedRecipe;


public class WoodenSword extends Craft implements Vanilla {
    private static WoodenSword instance;
    private WoodenSword() {
        super("Wood Sword", "wooden_sword");
    }

    public static WoodenSword getInstance() {
        if (instance == null) instance = new WoodenSword();
        return instance;
    }

    @Override
    protected Recipe initRecipe() {
        NamespacedKey key = NamespacedKey.minecraft(KEY);

        ShapedRecipe recipe = new ShapedRecipe(key, result);
        recipe.shape("A", "A", "B");
        RecipeChoice.MaterialChoice planks = new RecipeChoice.MaterialChoice(Tag.PLANKS);
        recipe.setIngredient('A', planks);;
        recipe.setIngredient('B', Material.STICK);
        plugin.getServer().addRecipe(recipe);
        return recipe;
    }

    @Override
    protected ItemStack initResult() {
        ItemStack item = new ItemStack(Material.WOODEN_SWORD);
        double attackDamage = 4; double attackSpeed = -2.4;
        item.setItemMeta(Vanilla.addModifiers(item.getItemMeta(), attackDamage, attackSpeed, KEY));
        return item;
    }

}
