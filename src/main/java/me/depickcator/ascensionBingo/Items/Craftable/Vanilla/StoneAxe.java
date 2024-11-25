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


public class StoneAxe implements Crafts, Vanilla {
    private Recipe recipe;
    private final AscensionBingo plugin;
    public static final String DISPLAY_NAME = "Stone Axe";
    public static final String KEY = "stone_axe";
    private static final ItemStack result = StoneAxe.makeResult();
    public StoneAxe(AscensionBingo plugin) {
        this.plugin = plugin;
        removeVanillaRecipe();
        recipe();
    }

    @Override
    public void recipe() {
        NamespacedKey key = NamespacedKey.minecraft(KEY);
        ItemStack item = getResult();

        ShapedRecipe recipe = new ShapedRecipe(key, item);
        RecipeChoice.MaterialChoice cobble = new RecipeChoice.MaterialChoice(Tag.ITEMS_STONE_TOOL_MATERIALS);
        recipe.shape("AA", "AB", " B");
        recipe.setIngredient('A', cobble);
        recipe.setIngredient('B', Material.STICK);
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
        ItemStack item = new ItemStack(Material.STONE_AXE);
        double attackDamage = 10; double attackSpeed = -3.1;
        item.setItemMeta(Vanilla.addModifiers(item.getItemMeta(), attackDamage, attackSpeed, KEY));
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
