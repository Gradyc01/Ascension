package me.depickcator.ascension.Items.Craftable.Smelt;

import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.FurnaceRecipe;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.Recipe;

public class NetheriteScrap extends FastSmelt {

    public NetheriteScrap() {
        super("netherite_scrap", 25);
    }

    @Override
    protected Recipe initRecipe() {
        NamespacedKey key = NamespacedKey.minecraft(KEY);
        Material source = Material.ANCIENT_DEBRIS;
        float experience = (float) 2;
        int cookingTime = this.getSmeltTime();
        FurnaceRecipe furnace = new FurnaceRecipe(key, result, source, experience, cookingTime);
        return furnace;
    }

    @Override
    protected ItemStack initResult() {
        return new ItemStack(Material.NETHERITE_SCRAP);
    }
    
}
