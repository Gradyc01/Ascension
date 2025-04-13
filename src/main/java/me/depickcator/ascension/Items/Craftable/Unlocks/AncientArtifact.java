package me.depickcator.ascension.Items.Craftable.Unlocks;

import me.depickcator.ascension.Items.Craftable.Craft;
import me.depickcator.ascension.Items.UnlockUtil;
import me.depickcator.ascension.Items.UnlocksData;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.Recipe;
import org.bukkit.inventory.ShapedRecipe;

public class AncientArtifact extends Craft {
    private static AncientArtifact instance;
    private AncientArtifact() {
        super(UnlocksData.COST_175, 2, "Ancient Artifact", "ancient_artifact");
    }

    @Override
    protected Recipe initRecipe() {
        NamespacedKey key = new NamespacedKey(plugin, KEY);
        ShapedRecipe recipe = new ShapedRecipe(key, result);
        recipe.shape(" A ", "BCB", " D ");
        recipe.setIngredient('A', Material.MAGMA_CREAM);
        recipe.setIngredient('B', Material.BLAZE_POWDER);
        recipe.setIngredient('C', Material.PLAYER_HEAD);
        recipe.setIngredient('D', Material.GOLD_BLOCK);
        UnlockUtil.addUnlock(plugin, recipe, MAX_CRAFTS, DISPLAY_NAME);
        return recipe;
    }

    @Override
    protected ItemStack initResult() {
        return new ItemStack(Material.ANCIENT_DEBRIS, 3);
    }

    public static AncientArtifact getInstance() {
        if (instance == null) instance = new AncientArtifact();
        return instance;
    }



}
