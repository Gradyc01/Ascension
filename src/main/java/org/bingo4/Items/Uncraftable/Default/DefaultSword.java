package org.bingo4.Items.Uncraftable.Default;

import org.bingo4.Items.CustomItem;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.Damageable;

public class DefaultSword extends CustomItem {
    private static DefaultSword instance;
    private DefaultSword() {
        super("Iron Sword", "default_sword");
    }

    public static DefaultSword getInstance() {
        if (instance == null) instance = new DefaultSword();
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
