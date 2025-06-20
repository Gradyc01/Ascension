package me.depickcator.ascension.Items;

import me.depickcator.ascension.Ascension;
import me.depickcator.ascension.Items.Craftable.Craft;
import me.depickcator.ascension.Items.Craftable.Unlocks.*;
import me.depickcator.ascension.Items.Craftable.Unlocks.EcholocatorItem.Echolocator;
import me.depickcator.ascension.Items.Craftable.Unlocks.GhostItem.Ghost;
import me.depickcator.ascension.Items.Craftable.Unlocks.MasterCompass.MasterCompass;
import me.depickcator.ascension.Items.Craftable.Unlocks.PandoraBoxItem.PandoraBox;
import me.depickcator.ascension.Items.Craftable.Unlocks.RedLedgerItem.RedLedger;
import me.depickcator.ascension.Items.Craftable.Unlocks.TeamPortalItem.TeamPortal;
import me.depickcator.ascension.Items.Craftable.Vanilla.*;
import me.depickcator.ascension.Items.Uncraftable.EnderPearl;
import me.depickcator.ascension.Items.Uncraftable.KitBook;
import me.depickcator.ascension.Items.Uncraftable.XPTome.XPTome;
import org.apache.commons.lang3.tuple.Pair;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.Recipe;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.ShapelessRecipe;
import org.bukkit.inventory.recipe.CraftingBookCategory;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class RecipeBookModifier {
    private final Ascension plugin;
    /*Modifies the recipe book to show only Unlocks*/
    public RecipeBookModifier() {
        plugin = Ascension.getInstance();
        for (String recipeID : initRecipes()) {
            removeRecipeFromRecipeBook(recipeID);
        }
    }

    private void removeRecipeFromRecipeBook(String recipeID) {
        NamespacedKey key = NamespacedKey.minecraft(recipeID);
        Recipe recipe = plugin.getServer().getRecipe(NamespacedKey.minecraft(recipeID));
        plugin.getServer().removeRecipe(key);
        if (recipe instanceof ShapedRecipe) {
            ShapedRecipe shapedRecipe = (ShapedRecipe) recipe;
            shapedRecipe.setCategory(CraftingBookCategory.MISC);

        } else if (recipe instanceof ShapelessRecipe) {
            ShapelessRecipe shapelessRecipe = (ShapelessRecipe) recipe;
            shapelessRecipe.setCategory(CraftingBookCategory.MISC);
        }
        plugin.getServer().addRecipe(recipe);
    }

    private Set<String> initRecipes() {
        Set<String> set = new HashSet<>(Set.of(
                "bundle",
                "saddle",
                "turtle_helmet",
                "wolf_armor",
                "spyglass",
                "spectral_arrow",
                "shears",
                "recovery_compass",
                "lead",
                "compass",
                "clock",
                "arrow",
                "bow",
                "brush",
                "crossbow",
                "fishing_rod",
                "flint_and_steel",
                "mace",
                "leather_helmet",
                "leather_chestplate",
                "leather_leggings",
                "leather_boots",
                "diamond_helmet",
                "diamond_chestplate",
                "diamond_leggings",
                "diamond_boots",
                "diamond_hoe",
                "diamond_shovel",
                "diamond_pickaxe",
                "golden_helmet",
                "golden_chestplate",
                "golden_leggings",
                "golden_boots",
                "golden_hoe",
                "golden_shovel",
                "golden_pickaxe",
                "golden_sword",
                "golden_axe",
                "iron_helmet",
                "iron_chestplate",
                "iron_leggings",
                "iron_boots",
                "iron_hoe",
                "iron_shovel",
                "iron_pickaxe",
                "stone_helmet",
                "stone_chestplate",
                "stone_leggings",
                "stone_boots",
                "stone_hoe",
                "stone_shovel",
                "stone_pickaxe",
                "wooden_helmet",
                "wooden_chestplate",
                "wooden_leggings",
                "wooden_boots",
                "wooden_hoe",
                "wooden_shovel",
                "wooden_pickaxe"
        ));
        set.addAll(initColoredRecipes("bundle"));
        set.addAll(initColoredRecipes("harness"));
        set.addAll(initColoredRecipes("dye", "harness"));
        return set;
    }

    private Set<String> initColoredRecipes(String recipeID) {
        return initColoredRecipes("",  recipeID);
    }

    private Set<String> initColoredRecipes(String recipePrefix, String recipeSuffix) {
        Set<String> colors = Set.of("white", "gray", "light_gray", "black", "brown", "red", "orange",
                "yellow", "lime", "green", "cyan", "light_blue", "blue", "purple", "magenta", "pink");
        Set<String> set = new HashSet<>();
        for  (String color : colors) {
            String str = color;
            if (recipePrefix != null && !recipePrefix.isEmpty()) str = recipePrefix + "_" + str;

            if (recipeSuffix != null && !recipeSuffix.isEmpty()) str = str + "_" + recipeSuffix;
            set.add(str);
        }
        return set;
    }
}

