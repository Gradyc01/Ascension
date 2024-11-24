package me.depickcator.ascensionBingo.Items.Craftable.Unlocks;

import me.depickcator.ascensionBingo.AscensionBingo;
import me.depickcator.ascensionBingo.Items.Craftable.Crafts;
import me.depickcator.ascensionBingo.Items.UnlockUtil;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.Recipe;
import org.bukkit.inventory.ShapedRecipe;

public class IronPack implements Crafts {
    private final AscensionBingo plugin;
    private Recipe recipe;
    public static final int COST = 1;
    public static final int MAX_CRAFTS = 4;
    public static final String DISPLAY_NAME = "Iron Pack";
    public static final String KEY = "ironpack";
    public IronPack(AscensionBingo plugin) {
        this.plugin = plugin;
        recipe();
    }

    @Override
    public String getKey() {
        return KEY;
    }

    @Override
    public ItemStack getResult() {
        return IronPack.result();
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
    public void recipe() {
        NamespacedKey key = new NamespacedKey(plugin, KEY);
        ItemStack item = IronPack.result();

        ShapedRecipe recipe = new ShapedRecipe(key, item);
        recipe.shape("AAA", "ABA", "AAA");
        recipe.setIngredient('A', Material.RAW_IRON);
        recipe.setIngredient('B', Material.COAL);
        UnlockUtil.addUnlock(plugin, recipe, MAX_CRAFTS, DISPLAY_NAME);
        this.recipe = recipe;
    }

    @Override
    public int getCraftCost() {
        return COST;
    }

    public static ItemStack result() {
        return new ItemStack(Material.IRON_INGOT, 10);
    }
}
