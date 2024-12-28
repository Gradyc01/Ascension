package me.depickcator.ascension.Items.Craftable.Smelt;

import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.FurnaceRecipe;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.Recipe;

public class Copper extends FastSmelt {

    public Copper() {
        super("copper_ingot_from_smelting_raw_copper");
    }

    @Override
    protected Recipe initRecipe() {
        NamespacedKey key = NamespacedKey.minecraft(KEY);
        Material source = Material.RAW_COPPER;
        float experience = (float) 0.7;
        return new FurnaceRecipe(key, result, source, experience, getSmeltTime());
    }

    @Override
    protected ItemStack initResult() {
        return new ItemStack(Material.COPPER_INGOT);
    }
    
}
