package me.depickcator.ascension.Items.Craftable.Vanilla;


import me.depickcator.ascension.Items.Craftable.Craft;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.Tag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.Recipe;
import org.bukkit.inventory.RecipeChoice;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.meta.Damageable;


public class Shield extends Craft implements Vanilla {
    private static Shield instance;
    private Shield() {
        super("Shield", "shield");
        result = initResult();
        recipe = initRecipe();
    }

    public static Shield getInstance() {
        if (instance == null) instance = new Shield();
        return instance;
    }


    @Override
    protected Recipe initRecipe() {
        NamespacedKey key = NamespacedKey.minecraft(KEY);

        ShapedRecipe recipe = new ShapedRecipe(key, result);
        recipe.shape("ABA", "AAA", " A ");
        RecipeChoice.MaterialChoice planks = new RecipeChoice.MaterialChoice(Tag.PLANKS);
        recipe.setIngredient('A', planks);
        recipe.setIngredient('B', Material.IRON_INGOT);
        plugin.getServer().addRecipe(recipe);
        return recipe;
    }

    @Override
    protected ItemStack initResult() {
        ItemStack item = new ItemStack(Material.SHIELD);
        Damageable meta = (Damageable) item.getItemMeta();
        meta.setMaxDamage(32);
        item.setItemMeta(meta);
        return item;
    }
}
