package me.depickcator.ascension.Items.Craftable.Vanilla;


import me.depickcator.ascension.Items.Craftable.Craft;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.Recipe;
import org.bukkit.inventory.ShapedRecipe;


public class DiamondSword extends Craft implements Vanilla {
    private static DiamondSword instance;
    private DiamondSword() {
        super("Diamond Sword", "diamond_sword");
    }

    public static DiamondSword getInstance() {
        if (instance == null) instance = new DiamondSword();
        return instance;
    }

    @Override
    protected Recipe initRecipe() {
        NamespacedKey key = NamespacedKey.minecraft(KEY);

        ShapedRecipe recipe = new ShapedRecipe(key, result);
        recipe.shape("A", "A", "B");
        recipe.setIngredient('A', Material.DIAMOND);
        recipe.setIngredient('B', Material.STICK);
        plugin.getServer().addRecipe(recipe);
        return recipe;
    }

    @Override
    protected ItemStack initResult() {
        ItemStack item = new ItemStack(Material.DIAMOND_SWORD);
        double attackDamage = 13; double attackSpeed = -2.4;
        item.setItemMeta(Vanilla.addModifiers(item.getItemMeta(), attackDamage, attackSpeed, KEY));
        return item;
    }

}
