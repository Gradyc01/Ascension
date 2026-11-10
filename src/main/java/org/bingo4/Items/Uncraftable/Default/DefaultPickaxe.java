package org.bingo4.Items.Uncraftable.Default;

import org.bingo4.Items.CustomItem;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.Damageable;

public class DefaultPickaxe extends CustomItem {
    private static DefaultPickaxe instance;
    private DefaultPickaxe() {
        super("Iron Pickaxe", "default_pickaxe");
    }

    public static DefaultPickaxe getInstance() {
        if (instance == null) instance = new DefaultPickaxe();
        return instance;
    }

    protected ItemStack initResult() {
        ItemStack item = new ItemStack(Material.IRON_PICKAXE);
        Damageable meta = (Damageable) item.getItemMeta();
        meta.setUnbreakable(true);
        meta.addEnchant(Enchantment.EFFICIENCY, 3, true);
        meta.addEnchant(Enchantment.UNBREAKING, 1, true);
        item.setItemMeta(meta);
        generateUniqueModelNumber(item);
        return item;
    }


}
