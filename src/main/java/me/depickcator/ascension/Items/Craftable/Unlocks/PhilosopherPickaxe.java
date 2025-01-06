package me.depickcator.ascension.Items.Craftable.Unlocks;

import me.depickcator.ascension.Ascension;
import me.depickcator.ascension.Utility.TextUtil;
import me.depickcator.ascension.Items.Craftable.Craft;
import me.depickcator.ascension.Items.UnlockUtil;
import me.depickcator.ascension.Items.UnlocksData;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.Recipe;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.meta.Damageable;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.Repairable;

public class PhilosopherPickaxe extends Craft {
    private static PhilosopherPickaxe instance;
    private PhilosopherPickaxe() {
        super(UnlocksData.COST_250, 2, "Philosopher’s Pickaxe", "philosopher_pickaxe");
    }

    public static PhilosopherPickaxe getInstance() {
        if (instance == null) instance = new PhilosopherPickaxe();
        return instance;
    }

    @Override
    protected Recipe initRecipe() {
        NamespacedKey key = new NamespacedKey(plugin, KEY);

        ShapedRecipe recipe = new ShapedRecipe(key, result);
        recipe.shape("ABA", "CDC", " D ");
        recipe.setIngredient('A', Material.IRON_INGOT);
        recipe.setIngredient('B', Material.GOLD_INGOT);
        recipe.setIngredient('C', Material.LAPIS_BLOCK);
        recipe.setIngredient('D', Material.STICK);
        UnlockUtil.addUnlock(plugin, recipe, MAX_CRAFTS, DISPLAY_NAME);
        return recipe;
    }

    @Override
    protected ItemStack initResult() {
        ItemStack item = new ItemStack(Material.DIAMOND_PICKAXE);
        ItemMeta meta = item.getItemMeta();
        meta.displayName(TextUtil.makeText(DISPLAY_NAME, TextUtil.PINK));
        meta.setCustomModelData(Ascension.getInstance().generateModelNumber());
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



}
