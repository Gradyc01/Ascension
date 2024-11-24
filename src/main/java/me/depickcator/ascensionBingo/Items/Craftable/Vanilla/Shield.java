package me.depickcator.ascensionBingo.Items.Craftable.Vanilla;


import me.depickcator.ascensionBingo.AscensionBingo;
import me.depickcator.ascensionBingo.Items.Craftable.Crafts;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.Tag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.Recipe;
import org.bukkit.inventory.RecipeChoice;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.meta.Damageable;


public class Shield implements Crafts, Vanilla {
    private Recipe recipe;
    private final AscensionBingo plugin;
    public static final String DISPLAY_NAME = "Shield";
    public static final String KEY = "shield";
    private static final ItemStack result = Shield.makeResult();
    public Shield(AscensionBingo plugin) {
        this.plugin = plugin;
        removeVanillaRecipe();
        recipe();
    }

    @Override
    public void recipe() {
        NamespacedKey key = NamespacedKey.minecraft(KEY);
        ItemStack item = getResult();

        ShapedRecipe recipe = new ShapedRecipe(key, item);
        recipe.shape("ABA", "AAA", " A ");
        RecipeChoice.MaterialChoice planks = new RecipeChoice.MaterialChoice(Tag.PLANKS);
        recipe.setIngredient('A', planks);
        recipe.setIngredient('B', Material.IRON_INGOT);
        this.recipe = recipe;
        plugin.getServer().addRecipe(recipe);
    }

    @Override
    public String getKey() {
        return KEY;
    }

    @Override
    public ItemStack getResult() {
        return result;
    }

    public static ItemStack item() {
        return result;
    }

    private static ItemStack makeResult() {
        ItemStack item = new ItemStack(Material.SHIELD);
        Damageable meta = (Damageable) item.getItemMeta();
        meta.setMaxDamage(32);
        item.setItemMeta(meta);
        return item;
    }

    @Override
    public Recipe getRecipe() {
        return recipe;
    }

    @Override
    public String getDisplayName() {
        return DISPLAY_NAME;
    }

    @Override
    public int getCraftCost() {
        return 0;
    }

    @Override
    public void removeVanillaRecipe() {
        plugin.getServer().removeRecipe(NamespacedKey.minecraft(KEY));
    }
}
