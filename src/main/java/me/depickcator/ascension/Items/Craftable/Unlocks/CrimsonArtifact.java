package me.depickcator.ascension.Items.Craftable.Unlocks;

import me.depickcator.ascension.Items.Craftable.Craft;
import me.depickcator.ascension.Items.Uncraftable.ShardOfTheFallen;
import me.depickcator.ascension.Items.UnlockUtil;
import me.depickcator.ascension.Items.UnlocksData;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.Recipe;
import org.bukkit.inventory.ShapedRecipe;

public class CrimsonArtifact extends Craft {
    private static CrimsonArtifact instance;
    private CrimsonArtifact() {
        super(UnlocksData.COST_275, 1, "Crimson Artifact", "crimson_artifact");
    }

    @Override
    protected Recipe initRecipe() {
        NamespacedKey key = new NamespacedKey(plugin, KEY);
        ShapedRecipe recipe = new ShapedRecipe(key, result);
        recipe.shape(" A ", "DBD", "CCC");
        recipe.setIngredient('A', Material.NETHER_STAR);
        recipe.setIngredient('B', Material.BLACK_WOOL);
        recipe.setIngredient('C', ShardOfTheFallen.result());
        recipe.setIngredient('D', Material.DIAMOND);
        UnlockUtil.addUnlock(plugin, recipe, MAX_CRAFTS, DISPLAY_NAME);
        return recipe;
    }

    @Override
    protected ItemStack initResult() {
        return new ItemStack(Material.NETHERITE_UPGRADE_SMITHING_TEMPLATE);
    }

    public static CrimsonArtifact getInstance() {
        if (instance == null) instance = new CrimsonArtifact();
        return instance;
    }
}
