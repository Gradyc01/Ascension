package me.depickcator.ascensionBingo.Items.Unlocks;

import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.Recipe;

public interface Crafts {
    void recipe();
    String getKey();
    ItemStack getResult();
    Recipe getRecipe();
    String getDisplayName();
    int getCraftCost();
}
