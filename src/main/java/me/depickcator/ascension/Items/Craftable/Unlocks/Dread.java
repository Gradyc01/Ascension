package me.depickcator.ascension.Items.Craftable.Unlocks;

import me.depickcator.ascension.Items.Craftable.Craft;
import me.depickcator.ascension.Items.UnlockUtil;
import me.depickcator.ascension.Items.UnlocksData;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.Recipe;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.meta.OminousBottleMeta;

public class Dread extends Craft {
    private static Dread instance;
    private Dread() {
        super(UnlocksData.COST_250, 3, "Dread", "dread");
    }


    @Override
    protected Recipe initRecipe() {
        NamespacedKey key = new NamespacedKey(plugin, KEY);

        ShapedRecipe recipe = new ShapedRecipe(key, result);
        recipe.shape("ACA", "ABA", "ADA");
        recipe.setIngredient('A', Material.EMERALD);
        recipe.setIngredient('B', Material.NETHER_STAR);
        recipe.setIngredient('C', Material.PUMPKIN);
        recipe.setIngredient('D', Material.HAY_BLOCK);
        UnlockUtil.addUnlock(plugin, recipe, MAX_CRAFTS, DISPLAY_NAME);
        return recipe;
    }

    @Override
    protected ItemStack initResult() {
        ItemStack item = new ItemStack(Material.OMINOUS_BOTTLE);
        OminousBottleMeta potionMeta = (OminousBottleMeta) item.getItemMeta();
        potionMeta.setAmplifier(4);
        return item;
    }


    public static Dread getInstance() {
        if (instance == null) instance = new Dread();
        return instance;
    }


}
