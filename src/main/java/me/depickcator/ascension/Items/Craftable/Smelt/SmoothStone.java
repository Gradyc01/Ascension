package me.depickcator.ascension.Items.Craftable.Smelt;

import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.FurnaceRecipe;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.Recipe;

public class SmoothStone extends FastSmelt {

    public SmoothStone() {
        super("smooth_stone_from_smelting_stone", 25);
    }

    @Override
    protected Recipe initRecipe() {
        NamespacedKey key = NamespacedKey.minecraft(KEY);
        Material source = Material.STONE;
        float experience = (float) 0.3;
        int cookingTime = this.getSmeltTime();
        FurnaceRecipe furnace = new FurnaceRecipe(key, result, source, experience, cookingTime);
        return furnace;
    }

    @Override
    protected ItemStack initResult() {
        return new ItemStack(Material.SMOOTH_STONE);
    }
    
}
