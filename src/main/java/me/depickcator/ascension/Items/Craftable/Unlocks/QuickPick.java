package me.depickcator.ascension.Items.Craftable.Unlocks;

import me.depickcator.ascension.Ascension;
import me.depickcator.ascension.Utility.TextUtil;
import me.depickcator.ascension.Items.Craftable.Craft;
import me.depickcator.ascension.Items.UnlockUtil;
import me.depickcator.ascension.Items.UnlocksData;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.Recipe;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.meta.ItemMeta;
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
        Component name = Component.text(DISPLAY_NAME).color(TextUtil.AQUA).decoration(TextDecoration.ITALIC, false);
        meta.displayName(name);
        meta.addEnchant(Enchantment.EFFICIENCY, 1, true);
        meta.setRepairCost(999);
        meta.setCustomModelData(Ascension.getInstance().generateModelNumber());
        item.setItemMeta(meta);
        return item;
    }
}
