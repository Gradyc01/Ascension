package me.depickcator.ascension.Items.Craftable.Vanilla;


import me.depickcator.ascension.Items.Craftable.Craft;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.Recipe;
import org.bukkit.inventory.ShapedRecipe;


public class DiamondAxe extends Craft implements Vanilla {
    private static DiamondAxe instance;
    private DiamondAxe() {
        super("Diamond Axe", "diamond_axe");
    }

    public static DiamondAxe getInstance() {
        if (instance == null) instance = new DiamondAxe();
        return instance;
    }

    @Override
    protected Recipe initRecipe() {
        NamespacedKey key = NamespacedKey.minecraft(KEY);

        ShapedRecipe recipe = new ShapedRecipe(key, result);
        recipe.shape("AA", "AB", " B");
        recipe.setIngredient('A', Material.DIAMOND);
        recipe.setIngredient('B', Material.STICK);
        plugin.getServer().addRecipe(recipe);
        return recipe;
    }

    @Override
    protected ItemStack initResult() {
        ItemStack item = new ItemStack(Material.DIAMOND_AXE);
        double attackDamage = 15;
        double attackSpeed = -3;
        item.setItemMeta(Vanilla.addModifiers(item.getItemMeta(), attackDamage, attackSpeed, KEY));
        return item;
    }
}
