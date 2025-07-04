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

public class SoulArtifact extends Craft {
    private static SoulArtifact instance;
    private SoulArtifact() {
        super(UnlocksData.COST_325, 4, "Soul Artifact", "soul_artifact");
    }

    public static SoulArtifact getInstance() {
        if (instance == null) instance = new SoulArtifact();
        return instance;
    }

    @Override
    protected Recipe initRecipe() {
        NamespacedKey key = new NamespacedKey(plugin, KEY);

        ShapedRecipe recipe = new ShapedRecipe(key, result);
        recipe.shape(" C ", "B B", "AAA");
        recipe.setIngredient('A', Material.BONE);
        recipe.setIngredient('B', ShardOfTheFallen.getInstance().getResult());
        recipe.setIngredient('C', Material.BOW);
        UnlockUtil.addUnlock(plugin, recipe, MAX_CRAFTS, DISPLAY_NAME);
        return recipe;
    }

    @Override
    protected ItemStack initResult() {
        return new ItemStack(Material.GHAST_TEAR, 4);
    }

}
