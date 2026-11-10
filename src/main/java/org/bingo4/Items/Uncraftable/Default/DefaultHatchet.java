package org.bingo4.Items.Uncraftable.Default;

import org.bingo4.Items.CustomItem;
import org.bukkit.Material;
import org.bukkit.attribute.Attribute;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.Damageable;

public class DefaultHatchet extends CustomItem {
    private static DefaultHatchet instance;
    private DefaultHatchet() {
        super("Iron Hatchet", "default_hatchet");
    }

    public static DefaultHatchet getInstance() {
        if (instance == null) instance = new DefaultHatchet();
        return instance;
    }

    protected ItemStack initResult() {
        ItemStack item = new ItemStack(Material.IRON_AXE);
        Damageable meta = (Damageable) item.getItemMeta();
        meta.setUnbreakable(true);
        meta.removeAttributeModifier(Attribute.ATTACK_DAMAGE);
        meta.addEnchant(Enchantment.EFFICIENCY, 3, true);
        meta.addEnchant(Enchantment.UNBREAKING, 1, true);
        item.setItemMeta(meta);
        generateUniqueModelNumber(item);
        return item;
    }


}
