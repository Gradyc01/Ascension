package me.depickcator.ascension.Items.Craftable.Smelt;

import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.FurnaceRecipe;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.Recipe;

public class GreenDye extends FastSmelt {

    public GreenDye() {
        super("green_dye");
    }

    @Override
    protected Recipe initRecipe() {
        NamespacedKey key = NamespacedKey.minecraft(KEY);
        Material source = Material.CACTUS;
        float experience = (float) 1;
        int cookingTime = this.getSmeltTime();
        return new FurnaceRecipe(key, result, source, experience, cookingTime);
    }

    @Override
    protected ItemStack initResult() {
        return new ItemStack(Material.GREEN_DYE);
    }
    
}
