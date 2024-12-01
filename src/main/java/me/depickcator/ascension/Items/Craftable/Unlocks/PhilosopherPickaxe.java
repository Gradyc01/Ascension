package me.depickcator.ascension.Items.Craftable.Unlocks;

import me.depickcator.ascension.Ascension;
import me.depickcator.ascension.General.TextUtil;
import me.depickcator.ascension.Items.Craftable.Crafts;
import me.depickcator.ascension.Items.UnlockUtil;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.Recipe;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.meta.Damageable;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.Repairable;

public class PhilosopherPickaxe implements Crafts {
    private final Ascension plugin;
    private Recipe recipe;
    public static final int COST = 2;
    public static final int MAX_CRAFTS = 4;
    public static final String DISPLAY_NAME = "Philosopher’s Pickaxe";
    public static final String KEY = "philosopher_pickaxe";
    private static final int modelNumber = Ascension.generateModelNumber();
    public PhilosopherPickaxe(Ascension plugin) {
        this.plugin = plugin;
        recipe();
    }

    @Override
    public void recipe() {
        NamespacedKey key = new NamespacedKey(plugin, KEY);
        ItemStack item = PhilosopherPickaxe.result();

        ShapedRecipe recipe = new ShapedRecipe(key, item);
        recipe.shape("ABA", "CDC", " D ");
        recipe.setIngredient('A', Material.IRON_INGOT);
        recipe.setIngredient('B', Material.GOLD_INGOT);
        recipe.setIngredient('C', Material.LAPIS_BLOCK);
        recipe.setIngredient('D', Material.STICK);
        UnlockUtil.addUnlock(plugin, recipe, MAX_CRAFTS, DISPLAY_NAME);
        this.recipe = recipe;
    }

    public static ItemStack result() {
        ItemStack item = new ItemStack(Material.DIAMOND_PICKAXE);
        ItemMeta meta = item.getItemMeta();
        meta.displayName(TextUtil.makeText(DISPLAY_NAME, TextUtil.PINK));
        meta.setCustomModelData(modelNumber);
        meta.addEnchant(Enchantment.FORTUNE, 2, true);
        meta.addEnchant(Enchantment.EFFICIENCY, 3, true);
        if (meta instanceof Damageable damageMeta) {
            damageMeta.setMaxDamage(4);
        }
        if (meta instanceof Repairable repairable) {
            repairable.setRepairCost(999);
        }
        item.setItemMeta(meta);
        return item;
    }

    @Override
    public String getKey() {
        return KEY;
    }

    @Override
    public ItemStack getResult() {
        return PhilosopherPickaxe.result();
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


}
