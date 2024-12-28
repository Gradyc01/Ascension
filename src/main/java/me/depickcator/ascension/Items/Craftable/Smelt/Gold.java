package me.depickcator.ascension.Items.Craftable.Smelt;

import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.FurnaceRecipe;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.Recipe;

public class Gold extends FastSmelt {

    public Gold() {
        super("gold_ingot_from_smelting_raw_gold");
    }

    @Override
    protected Recipe initRecipe() {
        NamespacedKey key = NamespacedKey.minecraft(KEY);
        Material source = Material.GOLD_ORE;
        float experience = (float) 1;
        return new FurnaceRecipe(key, result, source, experience, getSmeltTime());
    }

    @Override
    protected ItemStack initResult() {
        return new ItemStack(Material.GOLD_INGOT);
    }
    
}
