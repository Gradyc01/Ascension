package me.depickcator.ascension.Kits.Kits;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;
import java.util.List;

public interface Kit {
    HashMap<ItemStack, Kit> KITS = new HashMap<>();
    ItemStack getMascot();
    List<ItemStack> getKitItems();
    String getDisplayName();
    default void registerKit(Kit kit) {
        KITS.put(getMascot(), kit);
    }
    default void giveKitItems(Player player) {
        for (ItemStack item : getKitItems()) {
            player.getInventory().addItem(item);
        }
    }
    static HashMap<ItemStack, Kit> getKits() {
        return KITS;
    }
    static Kit getKit(ItemStack item) {
        return KITS.get(item);
    }
}
