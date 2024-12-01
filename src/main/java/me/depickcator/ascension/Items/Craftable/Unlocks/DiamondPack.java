package me.depickcator.ascension.Items.Craftable.Unlocks;

import me.depickcator.ascension.Ascension;
import me.depickcator.ascension.Items.Craftable.Crafts;
import me.depickcator.ascension.Items.UnlockUtil;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.Recipe;
import org.bukkit.inventory.ShapedRecipe;

public class DiamondPack implements Crafts {
    private final Ascension plugin;
    private Recipe recipe;
    public static final int COST = 1;
    public static final int MAX_CRAFTS = 4;
    public static final String DISPLAY_NAME = "Diamond Pack";
    public static final String KEY = "diamond_pack";
    public DiamondPack() {
        this.plugin = Ascension.getInstance();
        recipe();
    }

    @Override
    public void recipe() {
        NamespacedKey key = new NamespacedKey(plugin, KEY);
        ItemStack item = DiamondPack.result();

        ShapedRecipe recipe = new ShapedRecipe(key, item);
        recipe.shape("AAA", "ABA", "ACA");
        recipe.setIngredient('A', Material.DIAMOND);
        recipe.setIngredient('B', Material.NETHER_STAR);
        recipe.setIngredient('C', Material.IRON_PICKAXE);
        UnlockUtil.addUnlock(plugin, recipe, MAX_CRAFTS, DISPLAY_NAME);
        this.recipe = recipe;
    }

    public static ItemStack result() {
        return new ItemStack(Material.DIAMOND, 12);
    }

    @Override
    public int getCraftCost() {
        return COST;
    }

    @Override
    public String getKey() {
        return KEY;
    }

    @Override
    public ItemStack getResult() {
        return DiamondPack.result();
    }

    @Override
    public Recipe getRecipe() {
        return recipe;
    }

    @Override
    public String getDisplayName() {
        return DISPLAY_NAME;
    }
}
