package me.depickcator.ascension.Items.Craftable.Vanilla;


import me.depickcator.ascension.Ascension;
import me.depickcator.ascension.Items.Craftable.Crafts;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.Recipe;
import org.bukkit.inventory.ShapedRecipe;


public class IronSword implements Crafts, Vanilla {
    private Recipe recipe;
    private final Ascension plugin;
    public static final String DISPLAY_NAME = "Iron Sword";
    public static final String KEY = "iron_sword";
    private static final ItemStack result = IronSword.makeResult();
    public IronSword() {
        this.plugin = Ascension.getInstance();
        removeVanillaRecipe();
        recipe();
    }

    @Override
    public void recipe() {
        NamespacedKey key = NamespacedKey.minecraft(KEY);
        ItemStack item = getResult();

        ShapedRecipe recipe = new ShapedRecipe(key, item);
        recipe.shape("A", "A", "B");
        recipe.setIngredient('A', Material.IRON_INGOT);
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
        ItemStack item = new ItemStack(Material.IRON_SWORD);
        double attackDamage = 10; double attackSpeed = -2.4;
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
