package me.depickcator.ascension.Items.Craftable;

import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.CraftItemEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.Recipe;

import me.depickcator.ascension.Ascension;

public abstract class Craft {
    protected final Ascension plugin;
    protected Recipe recipe;
    protected final int COST;
    protected final int MAX_CRAFTS;
    protected final String DISPLAY_NAME;
    protected final String KEY;
    protected ItemStack result;

    protected Craft(int cost, int maxCrafts, String displayName, String key) {
        COST = cost; MAX_CRAFTS = maxCrafts; DISPLAY_NAME = displayName; KEY = key;
        this.plugin = Ascension.getInstance();
        result = initResult();
        recipe = initRecipe();
    }

    protected Craft(int cost, int maxCrafts, String displayName, String key, boolean isWeapon) {
        COST = cost; MAX_CRAFTS = maxCrafts; DISPLAY_NAME = displayName; KEY = key;
        this.plugin = Ascension.getInstance();
    }
    
    protected Craft(String displayName, String key) {
        COST = -1; MAX_CRAFTS = -1; DISPLAY_NAME = displayName; KEY = key;
        this.plugin = Ascension.getInstance();
        removeVanillaRecipe();
    }
    
    protected Craft(String key) {
        COST = -1; MAX_CRAFTS = -1; DISPLAY_NAME = key; KEY = key;
        this.plugin = Ascension.getInstance();
        removeVanillaRecipe();
        result = initResult();
        recipe = initRecipe();
        plugin.getServer().addRecipe(recipe);
    }

    protected Craft(String displayName, String key, boolean instantInstantiation) {
        COST = -1; MAX_CRAFTS = -1; DISPLAY_NAME = displayName; KEY = key;
        this.plugin = Ascension.getInstance();
        removeVanillaRecipe();
        if (instantInstantiation) {
            result = initResult();
            recipe = initRecipe();
        }
    }

    public boolean uponCrafted(CraftItemEvent e, Player p) {
        //Empty on purpose
        return true;
    }

    protected abstract Recipe initRecipe();
    protected abstract ItemStack initResult();

    private void removeVanillaRecipe() {
        plugin.getServer().removeRecipe(NamespacedKey.minecraft(KEY));
    }

    public int getCraftCost() {
        return COST;
    }

    public int getMaxCrafts() {
        return MAX_CRAFTS;
    }

    public String getDisplayName() {
        return DISPLAY_NAME;
    }

    public String getKey() {
        return KEY;
    }

    public Recipe getRecipe() {
        return recipe;
    }

    public ItemStack getResult() {
        return result;
    }
}
