package me.depickcator.ascensionBingo.Items.Craftable.Unlocks;

import me.depickcator.ascensionBingo.AscensionBingo;
import me.depickcator.ascensionBingo.Items.Craftable.Crafts;
import me.depickcator.ascensionBingo.Items.UnlockUtil;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.Recipe;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.meta.OminousBottleMeta;

public class Dread implements Crafts {
    private final AscensionBingo plugin;
    private Recipe recipe;
    public static final int COST = 1;
    public static final int MAX_CRAFTS = 3;
    public static final String DISPLAY_NAME = "Dread";
    public static final String KEY = "dread";
    private static final ItemStack result = Dread.makeItem();
    public Dread(AscensionBingo plugin) {
        this.plugin = plugin;
        recipe();
    }

    public void recipe() {
        NamespacedKey key = new NamespacedKey(plugin, KEY);
        ItemStack item = Dread.result();

        ShapedRecipe recipe = new ShapedRecipe(key, item);
        recipe.shape("ACA", "ABA", "ADA");
        recipe.setIngredient('A', Material.EMERALD);
        recipe.setIngredient('B', Material.NETHER_STAR);
        recipe.setIngredient('C', Material.PUMPKIN);
        recipe.setIngredient('D', Material.HAY_BLOCK);
        UnlockUtil.addUnlock(plugin, recipe, MAX_CRAFTS, DISPLAY_NAME);
        this.recipe = recipe;
    }

    @Override
    public String getKey() {
        return KEY;
    }

    @Override
    public ItemStack getResult() {
        return Dread.result();
    }

    @Override
    public Recipe getRecipe() {
        return recipe;
    }

    @Override
    public String getDisplayName() {
        return DISPLAY_NAME;
    }

    @Override
    public int getCraftCost() {
        return COST;
    }

    public static ItemStack result() {
        return result;
    }

    private static ItemStack makeItem() {
        ItemStack item = new ItemStack(Material.OMINOUS_BOTTLE);
        OminousBottleMeta potionMeta = (OminousBottleMeta) item.getItemMeta();
        potionMeta.setAmplifier(4);
        return item;
    }
}
