package me.depickcator.ascension.Items.Craftable.Unlocks;

import me.depickcator.ascension.General.TextUtil;
import me.depickcator.ascension.Items.Craftable.Craft;
import me.depickcator.ascension.Items.UnlockUtil;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.Recipe;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.meta.Damageable;
import org.bukkit.inventory.meta.ItemMeta;

public class VorpalSword extends Craft {
    private static VorpalSword instance;
    private VorpalSword() {
        super(1, 2, "Vorpal Sword", "vorpal_sword");
    }

    public static VorpalSword getInstance() {
        if (instance == null) instance = new VorpalSword();
        return instance;
    }

    @Override
    protected Recipe initRecipe() {
        NamespacedKey key = new NamespacedKey(plugin, KEY);

        ShapedRecipe recipe = new ShapedRecipe(key, result);
        recipe.shape("C", "A", "B");
        recipe.setIngredient('A', Material.IRON_SWORD);
        recipe.setIngredient('B', Material.ROTTEN_FLESH);
        recipe.setIngredient('C', Material.BONE);
        UnlockUtil.addUnlock(plugin, recipe, MAX_CRAFTS, DISPLAY_NAME);
        return recipe;
    }

    @Override
    protected ItemStack initResult() {
        ItemStack item =  new ItemStack(Material.IRON_SWORD);
        ItemMeta meta = item.getItemMeta();
        meta.addEnchant(Enchantment.SMITE, 2, true);
        meta.addEnchant(Enchantment.BANE_OF_ARTHROPODS, 2, true);
        meta.addEnchant(Enchantment.LOOTING, 2, true);
        Component name = Component.text(DISPLAY_NAME).color(TextUtil.AQUA).decoration(TextDecoration.ITALIC, false);
        if (meta instanceof Damageable damageMeta) {
            damageMeta.setMaxDamage(128);
        }
        meta.displayName(name);
        item.setItemMeta(meta);
        return item;
    }
}
