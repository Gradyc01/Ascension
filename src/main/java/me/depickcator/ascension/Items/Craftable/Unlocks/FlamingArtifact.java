package me.depickcator.ascension.Items.Craftable.Unlocks;

import me.depickcator.ascension.Items.Craftable.Craft;
import me.depickcator.ascension.Items.UnlockUtil;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.Recipe;
import org.bukkit.inventory.ShapedRecipe;

public class FlamingArtifact extends Craft {
    private static FlamingArtifact instance;
    private FlamingArtifact() {
        super(1,2 ,"Flaming Artifact", "flaming_artifact");
    }

    @Override
    protected Recipe initRecipe() {
        NamespacedKey key = new NamespacedKey(plugin, KEY);

        ShapedRecipe recipe = new ShapedRecipe(key, result);
        recipe.shape("ABA", "ACA", "ABA");
        recipe.setIngredient('A', Material.ORANGE_STAINED_GLASS);
        recipe.setIngredient('B', Material.LAVA_BUCKET);
        recipe.setIngredient('C', Material.FIREWORK_ROCKET);
        UnlockUtil.addUnlock(plugin, recipe, MAX_CRAFTS, DISPLAY_NAME);
        return recipe;
    }

    @Override
    protected ItemStack initResult() {
        return new ItemStack(Material.BLAZE_ROD, 1);
    }

    public static FlamingArtifact getInstance() {
        if (instance == null) instance = new FlamingArtifact();
        return instance;
    }
}
