package me.depickcator.ascension.Kits.Kits;

import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.Repairable;

import java.util.List;

public abstract class Kit2 {
    private final String DISPLAY_NAME;
    private List<ItemStack> items;
    public Kit2(String DISPLAY_NAME) {
        this.DISPLAY_NAME = DISPLAY_NAME;

    }
    public String getDisplayName() {
        return DISPLAY_NAME;
    }
    public abstract List<ItemStack> initKitItems();
    public abstract ItemStack getMascot();
    public List<ItemStack> getKitItems() {
        if (items == null) items = initKitItems();
        return items;
    }
    protected ItemStack setToolMeta(ItemStack item) {
        Repairable meta = (Repairable) item.getItemMeta();
        meta.setRepairCost(999);
        meta.addEnchant(Enchantment.EFFICIENCY, 3, true);
        meta.addEnchant(Enchantment.UNBREAKING, 1, true);
        item.setItemMeta(meta);
        return item;
    }
}
