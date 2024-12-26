package me.depickcator.ascension.Items.Craftable.Smelt;

import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.FurnaceRecipe;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.Recipe;

public class Kelp extends FastSmelt {

    public Kelp() {
        super("dried_kelp_from_smelting_kelp", 25);
    }

    @Override
    protected Recipe initRecipe() {
        NamespacedKey key = NamespacedKey.minecraft(KEY);
        Material source = Material.KELP;
        float experience = (float) 0.1;
        int cookingTime = this.getSmeltTime();
        FurnaceRecipe furnace = new FurnaceRecipe(key, result, source, experience, cookingTime);
        return furnace;
    }

    @Override
    protected ItemStack initResult() {
        return new ItemStack(Material.DRIED_KELP);
    }
    
}
