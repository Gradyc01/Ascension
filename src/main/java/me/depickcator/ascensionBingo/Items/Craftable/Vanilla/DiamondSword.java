package me.depickcator.ascensionBingo.Items.Craftable.Vanilla;


import me.depickcator.ascensionBingo.AscensionBingo;
import me.depickcator.ascensionBingo.Items.Craftable.Crafts;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.Recipe;
import org.bukkit.inventory.ShapedRecipe;


public class DiamondSword implements Crafts, Vanilla {
    private Recipe recipe;
    private final AscensionBingo plugin;
    public static final String DISPLAY_NAME = "Diamond Sword";
    public static final String KEY = "diamond_sword";
    private static final ItemStack result = DiamondSword.makeResult();
    public DiamondSword(AscensionBingo plugin) {
        this.plugin = plugin;
        removeVanillaRecipe();
        recipe();
    }

    @Override
    public void recipe() {
        NamespacedKey key = NamespacedKey.minecraft(KEY);
        ItemStack item = getResult();

        ShapedRecipe recipe = new ShapedRecipe(key, item);
        recipe.shape("A", "A", "B");
        recipe.setIngredient('A', Material.DIAMOND);
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
        ItemStack item = new ItemStack(Material.DIAMOND_SWORD);
        double attackDamage = 13; double attackSpeed = -2.4;
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
