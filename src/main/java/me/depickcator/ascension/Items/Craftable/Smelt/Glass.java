package me.depickcator.ascension.Items.Craftable.Smelt;

import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.FurnaceRecipe;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.Recipe;

public class Glass extends FastSmelt {

    public Glass() {
        super("glass");
    }

    @Override
    protected Recipe initRecipe() {
        NamespacedKey key = NamespacedKey.minecraft(KEY);
        Material source = Material.SAND;
        float experience = (float) 0.1;
        return new FurnaceRecipe(key, result, source, experience, getSmeltTime());
    }

    @Override
    protected ItemStack initResult() {
        return new ItemStack(Material.GLASS);
    }
    
}
