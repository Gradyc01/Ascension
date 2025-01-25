package me.depickcator.ascension.Items.Craftable.Unlocks;

import me.depickcator.ascension.Ascension;
import me.depickcator.ascension.Items.Craftable.Vanilla.Vanilla;
import me.depickcator.ascension.Items.Craftable.Vanilla.Weapons;
import me.depickcator.ascension.Items.UnlockUtil;
import me.depickcator.ascension.Items.UnlocksData;
import me.depickcator.ascension.Utility.TextUtil;
import net.kyori.adventure.text.Component;
import org.bukkit.*;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.CraftItemEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.Recipe;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.List;

public class DragonSword extends Weapons {
    private static DragonSword instance;
    private DragonSword() {
        super(UnlocksData.COST_250, 2, "Dragon's Sword", "dragon_sword", 14.25, -2.4);
    }

    @Override
    protected Recipe initRecipe() {
        NamespacedKey key = new NamespacedKey(plugin, KEY);

        ShapedRecipe recipe = new ShapedRecipe(key, result);
        recipe.shape(" B ", " A ", "CBC");
        recipe.setIngredient('A', Material.DIAMOND_SWORD);
        recipe.setIngredient('B', Material.BLAZE_POWDER);
        recipe.setIngredient('C', Material.OBSIDIAN);
        UnlockUtil.addUnlock(plugin, recipe, MAX_CRAFTS, DISPLAY_NAME);
        return recipe;
    }

    @Override
    protected ItemStack initResult() {
        ItemStack item = new ItemStack(Material.DIAMOND_SWORD);
        ItemMeta meta = item.getItemMeta();
        meta.displayName(TextUtil.makeText(getDisplayName(), TextUtil.RED));
        meta.setCustomModelData(Ascension.getInstance().generateModelNumber());
        item.setItemMeta(Vanilla.addModifiers(meta, getAttackDamage(), getAttackSpeed(), KEY));
        return item;
    }

    @Override
    public boolean uponCrafted(CraftItemEvent e, Player p) {
        ItemStack item = e.getCurrentItem();
        ItemMeta meta = item.getItemMeta();
        List<Component> lore = meta.lore();
        lore.addFirst(TextUtil.makeText("Crafted by ", TextUtil.YELLOW)
                .append(TextUtil.makeText(p.getName(), TextUtil.AQUA, false, true)));
        meta.lore(lore);
        item.setItemMeta(meta);
        return true;
    }

    public static DragonSword getInstance() {
        if (instance == null) {
            instance = new DragonSword();
        }
        return instance;
    }
}
