package me.depickcator.ascensionBingo.Items.Craftable.Unlocks;

import me.depickcator.ascensionBingo.AscensionBingo;
import me.depickcator.ascensionBingo.Items.Craftable.Crafts;
import me.depickcator.ascensionBingo.Items.Uncraftable.ShardOfTheFallen;
import me.depickcator.ascensionBingo.Items.UnlockUtil;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.Tag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.Recipe;
import org.bukkit.inventory.RecipeChoice;
import org.bukkit.inventory.ShapedRecipe;

public class CrimsonArtifact implements Crafts {
    private final AscensionBingo plugin;
    private Recipe recipe;
    public static final int COST = 1;
    public static final int MAX_CRAFTS = 1;
    public static final String DISPLAY_NAME = "Crimson Artifact";
    public static final String KEY = "crimson_artifact";
    private static final ItemStack result = CrimsonArtifact.makeItem();
    public CrimsonArtifact(AscensionBingo plugin) {
        this.plugin = plugin;
        recipe();
    }

    public void recipe() {
        NamespacedKey key = new NamespacedKey(plugin, KEY);
        ShapedRecipe recipe = new ShapedRecipe(key, CrimsonArtifact.result);
        recipe.shape("DAD", "DBD", "CCC");
        recipe.setIngredient('A', Material.NETHER_STAR);
        recipe.setIngredient('B', new RecipeChoice.MaterialChoice(Tag.BANNERS));
        recipe.setIngredient('C', ShardOfTheFallen.result());
        recipe.setIngredient('D', Material.DIAMOND);
        UnlockUtil.addUnlock(plugin, recipe, MAX_CRAFTS, DISPLAY_NAME);
        this.recipe = recipe;
    }

    @Override
    public String getKey() {
        return KEY;
    }

    @Override
    public ItemStack getResult() {
        return CrimsonArtifact.result();
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

    public static ItemStack result() {
        return result;
    }

    private static ItemStack makeItem() {
        return new ItemStack(Material.NETHERITE_UPGRADE_SMITHING_TEMPLATE);
    }
}
