package org.bingo4.Items.Craftable.Unlocks;

import org.bingo4.Bingo4;
import org.bingo4.Items.Craftable.Craft;
import org.bingo4.Items.UnlockUtil;
import org.bingo4.Items.UnlocksData;
import org.bingo4.Utility.TextUtil;
import net.kyori.adventure.text.Component;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.Recipe;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.meta.Repairable;

public class QuickPick extends Craft {
    private static QuickPick instance;
    private QuickPick() {
        super(UnlocksData.COST_100, 2, "Quick Pick", "quick_pick");
    }

    public static QuickPick getInstance() {
        if (instance == null) instance = new QuickPick();
        return instance;
    }

    @Override
    protected Recipe initRecipe() {
        NamespacedKey key = new NamespacedKey(plugin, KEY);

        ShapedRecipe recipe = new ShapedRecipe(key, result);
        recipe.shape("AAA", "BCB", " C ");
        recipe.setIngredient('A', Material.RAW_IRON);
        recipe.setIngredient('B', Material.COAL);
        recipe.setIngredient('C', Material.STICK);
        UnlockUtil.addUnlock(plugin, recipe, MAX_CRAFTS, DISPLAY_NAME);
        return recipe;
    }

    @Override
    protected ItemStack initResult() {
        ItemStack item = new ItemStack(Material.IRON_PICKAXE);
        Repairable meta = (Repairable) item.getItemMeta();
        Component name = TextUtil.makeText(getDisplayName(), TextUtil.AQUA);
        meta.displayName(name);
        meta.addEnchant(Enchantment.EFFICIENCY, 1, true);
        meta.setRepairCost(999);
        meta.setCustomModelData(Bingo4.getInstance().generateModelNumber());
        item.setItemMeta(meta);
        return item;
    }
}
