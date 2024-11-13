package me.depickcator.ascensionBingo.Items.Unlocks;

import me.depickcator.ascensionBingo.AscensionBingo;
import me.depickcator.ascensionBingo.Items.UnlockUtil;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.Recipe;
import org.bukkit.inventory.ShapedRecipe;

public class AncientArtifact implements Crafts{
    private final AscensionBingo plugin;
    private Recipe recipe;
    public static final int COST = 1;
    public static final int MAX_CRAFTS = 2;
    public static final String DISPLAY_NAME = "Ancient Artifact";
    public static final String KEY = "ancient_artifact";
    public AncientArtifact(AscensionBingo plugin) {
        this.plugin = plugin;
        recipe();
    }

    @Override
    public void recipe() {
        NamespacedKey key = new NamespacedKey(plugin, KEY);
        ItemStack item = AncientArtifact.result();

        ShapedRecipe recipe = new ShapedRecipe(key, item);
        recipe.shape(" A ", "BCB", " D ");
        recipe.setIngredient('A', Material.MAGMA_CREAM);
        recipe.setIngredient('B', Material.BLAZE_POWDER);
        recipe.setIngredient('C', Material.NETHER_STAR);
        recipe.setIngredient('D', Material.GOLD_BLOCK);
        UnlockUtil.addUnlock(plugin, recipe, MAX_CRAFTS, DISPLAY_NAME);
        this.recipe = recipe;
    }

    public static ItemStack result() {
        return new ItemStack(Material.ANCIENT_DEBRIS, 3);
    }

    @Override
    public String getKey() {
        return KEY;
    }

    @Override
    public ItemStack getResult() {
        return AncientArtifact.result();
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
        return COST;
    }


}
