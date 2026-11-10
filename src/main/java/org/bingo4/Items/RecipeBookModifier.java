//package org.bingo4.Items;
//
//import org.bingo4.Bingo4;
//import org.bukkit.NamespacedKey;
//import org.bukkit.inventory.Recipe;
//import org.bukkit.inventory.ShapedRecipe;
//import org.bukkit.inventory.ShapelessRecipe;
//import org.bukkit.inventory.recipe.CraftingBookCategory;
//
//import java.util.HashSet;
//import java.util.Set;
//
//public class RecipeBookModifier {
//    private final Bingo4 plugin;
//    /*Modifies the recipe book to show only Unlocks*/
//    public RecipeBookModifier() {
//        plugin = Bingo4.getInstance();
//        for (String recipeID : initRecipes()) {
//            removeRecipeFromRecipeBook(recipeID);
//        }
//    }
//
//    private void removeRecipeFromRecipeBook(String recipeID) {
//        NamespacedKey key = NamespacedKey.minecraft(recipeID);
//        Recipe recipe = plugin.getServer().getRecipe(NamespacedKey.minecraft(recipeID));
//        plugin.getServer().removeRecipe(key);
//        if (recipe instanceof ShapedRecipe) {
//            ShapedRecipe shapedRecipe = (ShapedRecipe) recipe;
//            shapedRecipe.setCategory(CraftingBookCategory.MISC);
//
//        } else if (recipe instanceof ShapelessRecipe) {
//            ShapelessRecipe shapelessRecipe = (ShapelessRecipe) recipe;
//            shapelessRecipe.setCategory(CraftingBookCategory.MISC);
//        }
//        plugin.getServer().addRecipe(recipe);
//    }
//
//    private Set<String> initRecipes() {
//        Set<String> set = new HashSet<>(Set.of(
//                "bundle",
//                "saddle",
//                "turtle_helmet",
//                "wolf_armor",
//                "spyglass",
//                "spectral_arrow",
//                "shears",
//                "recovery_compass",
//                "lead",
//                "compass",
//                "clock",
//                "arrow",
//                "bow",
//                "brush",
//                "crossbow",
//                "fishing_rod",
//                "flint_and_steel",
//                "mace",
//                "leather_helmet",
//                "leather_chestplate",
//                "leather_leggings",
//                "leather_boots",
//                "diamond_helmet",
//                "diamond_chestplate",
//                "diamond_leggings",
//                "diamond_boots",
//                "diamond_hoe",
//                "diamond_shovel",
//                "diamond_pickaxe",
//                "golden_helmet",
//                "golden_chestplate",
//                "golden_leggings",
//                "golden_boots",
//                "golden_hoe",
//                "golden_shovel",
//                "golden_pickaxe",
//                "golden_sword",
//                "golden_axe",
//                "iron_helmet",
//                "iron_chestplate",
//                "iron_leggings",
//                "iron_boots",
//                "iron_hoe",
//                "iron_shovel",
//                "iron_pickaxe",
//                "stone_helmet",
//                "stone_chestplate",
//                "stone_leggings",
//                "stone_boots",
//                "stone_hoe",
//                "stone_shovel",
//                "stone_pickaxe",
//                "wooden_helmet",
//                "wooden_chestplate",
//                "wooden_leggings",
//                "wooden_boots",
//                "wooden_hoe",
//                "wooden_shovel",
//                "wooden_pickaxe"
//        ));
//        set.addAll(initColoredRecipes("bundle"));
//        set.addAll(initColoredRecipes("harness"));
//        set.addAll(initColoredRecipes("dye", "harness"));
//        return set;
//    }
//
//    private Set<String> initColoredRecipes(String recipeID) {
//        return initColoredRecipes("",  recipeID);
//    }
//
//    private Set<String> initColoredRecipes(String recipePrefix, String recipeSuffix) {
//        Set<String> colors = Set.of("white", "gray", "light_gray", "black", "brown", "red", "orange",
//                "yellow", "lime", "green", "cyan", "light_blue", "blue", "purple", "magenta", "pink");
//        Set<String> set = new HashSet<>();
//        for  (String color : colors) {
//            String str = color;
//            if (recipePrefix != null && !recipePrefix.isEmpty()) str = recipePrefix + "_" + str;
//
//            if (recipeSuffix != null && !recipeSuffix.isEmpty()) str = str + "_" + recipeSuffix;
//            set.add(str);
//        }
//        return set;
//    }
//}
//
