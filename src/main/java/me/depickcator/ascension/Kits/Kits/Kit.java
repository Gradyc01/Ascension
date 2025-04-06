package me.depickcator.ascension.Kits.Kits;

import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.Repairable;

import java.util.List;

public abstract class Kit {
    private final String DISPLAY_NAME;
    private List<ItemStack> items;
    public Kit(String DISPLAY_NAME) {
        this.DISPLAY_NAME = DISPLAY_NAME;

    }
    public String getDisplayName() {
        return DISPLAY_NAME;
    }
    /*Initializes and Returns a List of ItemStacks that are contained in the Kit*/
    public abstract List<ItemStack> initKitItems();
    /*Get ItemStack that represents the Kit (The Icon of the Kit)*/
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
