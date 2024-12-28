package me.depickcator.ascension.Items.Craftable.Smelt;

import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.FurnaceRecipe;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.Recipe;

public class Brick extends FastSmelt {

    public Brick() {
        super("brick");
    }

    @Override
    protected Recipe initRecipe() {
        NamespacedKey key = NamespacedKey.minecraft(KEY);
        Material source = Material.CLAY_BALL;
        float experience = (float) 0.3;
        return new FurnaceRecipe(key, result, source, experience, getSmeltTime());
    }

    @Override
    protected ItemStack initResult() {
        return new ItemStack(Material.BRICK);
    }
    
}
