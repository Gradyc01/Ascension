package me.depickcator.ascension.Items.Craftable.Vanilla;


import me.depickcator.ascension.Items.Craftable.Craft;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.Recipe;
import org.bukkit.inventory.RecipeChoice;
import org.bukkit.inventory.ShapedRecipe;


public class StoneSword extends Craft implements Vanilla {
    private static StoneSword instance;
    private StoneSword() {
        super("Stone Sword", "stone_sword");
    }

    public static StoneSword getInstance() {
        if (instance == null) instance = new StoneSword();
        return instance;
    }

    @Override
    protected Recipe initRecipe() {
        NamespacedKey key = NamespacedKey.minecraft(KEY);

        ShapedRecipe recipe = new ShapedRecipe(key, result);
        recipe.shape("A", "A", "B");
        RecipeChoice.MaterialChoice stone = new RecipeChoice.MaterialChoice(Material.COBBLESTONE, Material.COBBLED_DEEPSLATE);
        recipe.setIngredient('A', stone);;
        recipe.setIngredient('B', Material.STICK);
        plugin.getServer().addRecipe(recipe);
        return recipe;
    }

    @Override
    protected ItemStack initResult() {
        ItemStack item = new ItemStack(Material.STONE_SWORD);
        double attackDamage = 7; double attackSpeed = -2.4;
        item.setItemMeta(Vanilla.addModifiers(item.getItemMeta(), attackDamage, attackSpeed, KEY));
        return item;
    }

}
