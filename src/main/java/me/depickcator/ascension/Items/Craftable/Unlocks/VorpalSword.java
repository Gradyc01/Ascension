package me.depickcator.ascension.Items.Craftable.Unlocks;

import me.depickcator.ascension.Ascension;
import me.depickcator.ascension.General.TextUtil;
import me.depickcator.ascension.Items.Craftable.Crafts;
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

public class VorpalSword implements Crafts {
    private final Ascension plugin;
    private Recipe recipe;
    public static final int COST = 1;
    public static final int MAX_CRAFTS = 2;
    public static final String DISPLAY_NAME = "Vorpal Sword";
    public static final String KEY = "vorpal_sword";
    public VorpalSword() {
        this.plugin = Ascension.getInstance();
        recipe();
    }

    @Override
    public void recipe() {
        NamespacedKey key = new NamespacedKey(plugin, KEY);
        ItemStack item = VorpalSword.result();

        ShapedRecipe recipe = new ShapedRecipe(key, item);
        recipe.shape("C", "A", "B");
        recipe.setIngredient('A', Material.IRON_SWORD);
        recipe.setIngredient('B', Material.ROTTEN_FLESH);
        recipe.setIngredient('C', Material.BONE);
        UnlockUtil.addUnlock(plugin, recipe, MAX_CRAFTS, DISPLAY_NAME);
        this.recipe = recipe;
    }

    @Override
    public String getKey() {
        return KEY;
    }

    @Override
    public ItemStack getResult() {
        return VorpalSword.result();
    }

    @Override
    public String getDisplayName() {
        return DISPLAY_NAME;
    }

    @Override
    public Recipe getRecipe() {
        return recipe;
    }

    @Override
    public int getCraftCost() {
        return COST;
    }

    public static ItemStack result() {
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
