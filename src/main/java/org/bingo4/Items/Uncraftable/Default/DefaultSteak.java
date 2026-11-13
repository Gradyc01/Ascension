package org.bingo4.Items.Uncraftable.Default;

import org.bingo4.Items.CustomItem;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.Damageable;

public class DefaultSteak extends CustomItem {
    private static DefaultSteak instance;
    private DefaultSteak() {
        super("Steak", "default_sword");
    }

    public static DefaultSteak getInstance() {
        if (instance == null) instance = new DefaultSteak();
        return instance;
    }

    protected ItemStack initResult() {
        ItemStack item = new ItemStack(Material.IRON_SWORD);
        Damageable meta = (Damageable) item.getItemMeta();
        meta.setUnbreakable(true);
        item.setItemMeta(meta);
        generateUniqueModelNumber(item);

        return item;
    }


}
