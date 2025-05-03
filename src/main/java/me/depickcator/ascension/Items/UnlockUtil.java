package me.depickcator.ascension.Items;


import me.depickcator.ascension.Ascension;
import org.apache.commons.lang3.tuple.MutablePair;
import org.apache.commons.lang3.tuple.Pair;
import org.bukkit.Material;
import org.bukkit.inventory.Recipe;
import org.bukkit.inventory.RecipeChoice;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.ShapelessRecipe;
import org.bukkit.inventory.recipe.CraftingBookCategory;


import java.util.HashMap;

public class UnlockUtil {
    public static RecipeChoice.MaterialChoice SAPLINGS = new RecipeChoice.MaterialChoice(
            Material.OAK_SAPLING,
            Material.BIRCH_SAPLING,
            Material.ACACIA_SAPLING,
            Material.SPRUCE_SAPLING,
            Material.CHERRY_SAPLING,
            Material.JUNGLE_SAPLING,
            Material.DARK_OAK_SAPLING);
    private static HashMap<String, Pair<Integer, String>> UNLOCKS = new HashMap<>();

    /*Registers the Unlock into the HashMap Unlock
    * It also Registers the Recipe, displayName, and the Max number of Crafts*/
    public static void addUnlock(Ascension plugin, Recipe recipe, Integer maxCrafts, String displayName) {
//        plugin.getServer().addRecipe(recipe);
        if (recipe instanceof ShapedRecipe) {
            ShapedRecipe shaped = (ShapedRecipe) recipe;
            shaped.setCategory(CraftingBookCategory.EQUIPMENT);
            UNLOCKS.put(shaped.getKey().getKey(), new MutablePair<>(maxCrafts, displayName));
        }
        if (recipe instanceof ShapelessRecipe) {
            ShapelessRecipe shapeless = (ShapelessRecipe) recipe;
            shapeless.setCategory(CraftingBookCategory.EQUIPMENT);
            UNLOCKS.put(shapeless.getKey().getKey(), new MutablePair<>(maxCrafts, displayName));
        }
        plugin.getServer().addRecipe(recipe);
    }

    public static boolean isAUnlock(String unlock) {
        return UNLOCKS.containsKey(unlock);
    }

    public static int getMaxCrafts(String unlock) {
        return UNLOCKS.get(unlock).getLeft();
    }

    public static String getDisplayName(String unlock) {
        return UNLOCKS.get(unlock).getRight();
    }


}
