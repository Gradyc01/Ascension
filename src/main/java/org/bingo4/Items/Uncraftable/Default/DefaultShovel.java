package org.bingo4.Items.Uncraftable.Default;

import org.bingo4.Items.CustomItem;
import org.bukkit.Material;
import org.bukkit.attribute.Attribute;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.Damageable;

public class DefaultShovel extends CustomItem {
    private static DefaultShovel instance;
    private DefaultShovel() {
        super("Iron Shovel", "default_shovel");
    }

    public static DefaultShovel getInstance() {
        if (instance == null) instance = new DefaultShovel();
        return instance;
    }

    protected ItemStack initResult() {
        ItemStack item = new ItemStack(Material.IRON_SHOVEL);
        Damageable meta = (Damageable) item.getItemMeta();
        meta.setUnbreakable(true);
        meta.addEnchant(Enchantment.EFFICIENCY, 3, true);
        meta.addEnchant(Enchantment.UNBREAKING, 1, true);
        item.setItemMeta(meta);
        generateUniqueModelNumber(item);
        return item;
    }


}
