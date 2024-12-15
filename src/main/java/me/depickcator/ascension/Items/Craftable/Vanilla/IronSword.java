package me.depickcator.ascension.Items.Craftable.Vanilla;


import me.depickcator.ascension.Items.Craftable.Craft;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.Recipe;
import org.bukkit.inventory.ShapedRecipe;


public class IronSword extends Craft implements Vanilla {
    private static IronSword instance;
    private IronSword() {
        super("Iron Sword", "iron_sword");
    }

    public static IronSword getInstance() {
        if (instance == null) instance = new IronSword();
        return instance;
    }

    @Override
    protected Recipe initRecipe() {
//        NamespacedKey key = NamespacedKey.minecraft(KEY);
        NamespacedKey key = new NamespacedKey(plugin, KEY);

        ShapedRecipe recipe = new ShapedRecipe(key, result);
        recipe.shape("A", "A", "B");
        recipe.setIngredient('A', Material.IRON_INGOT);
        recipe.setIngredient('B', Material.STICK);
        plugin.getServer().addRecipe(recipe);
        return recipe;
    }

    @Override
    protected ItemStack initResult() {
        ItemStack item = new ItemStack(Material.IRON_SWORD);
        double attackDamage = 10; double attackSpeed = -2.4;
        item.setItemMeta(Vanilla.addModifiers(item.getItemMeta(), attackDamage, attackSpeed, KEY));
        return item;
    }

}
