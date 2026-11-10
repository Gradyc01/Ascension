package org.bingo4.Items.Uncraftable.Default;

import org.bingo4.Items.CustomItem;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class DefaultBow extends CustomItem {
    private static DefaultBow instance;
    private DefaultBow() {
        super("Bow", "default_bow");
    }

    public static DefaultBow getInstance() {
        if (instance == null) instance = new DefaultBow();
        return instance;
    }

    protected ItemStack initResult() {
        ItemStack item = new ItemStack(Material.BOW);
        ItemMeta meta = item.getItemMeta();
        meta.addEnchant(Enchantment.INFINITY, 1, true);
        meta.setUnbreakable(true);
        item.setItemMeta(meta);
        generateUniqueModelNumber(item);
        return item;
    }


}
