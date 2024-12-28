package me.depickcator.ascension.Items.Craftable.Smelt;

import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.FurnaceRecipe;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.Recipe;

public class Potato extends FastSmelt {

    public Potato() {
        super("baked_potato");
    }

    @Override
    protected Recipe initRecipe() {
        NamespacedKey key = NamespacedKey.minecraft(KEY);
        Material source = Material.POTATO;
        float experience = (float) 0.35;
        int cookingTime = this.getSmeltTime();
        FurnaceRecipe furnace = new FurnaceRecipe(key, result, source, experience, cookingTime);
        return furnace;
    }

    @Override
    protected ItemStack initResult() {
        return new ItemStack(Material.BAKED_POTATO);
    }
    
}
