package me.depickcator.ascension.Items.Uncraftable;

import me.depickcator.ascension.Ascension;
import me.depickcator.ascension.General.TextUtil;
import org.bukkit.Material;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class ShardOfTheFallen {
    public static String DISPLAY_NAME = "Shard of the Fallen";
    private static final ItemStack item = makeItem();
    private static final int modelNumber = Ascension.generateModelNumber();
    private static ItemStack makeItem() {
        ItemStack item = new ItemStack(Material.DISC_FRAGMENT_5);
        ItemMeta meta = item.getItemMeta();
        meta.setCustomModelData(modelNumber);
        meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
        meta.addItemFlags(ItemFlag.HIDE_ADDITIONAL_TOOLTIP);
        meta.displayName(TextUtil.makeText(DISPLAY_NAME, TextUtil.DARK_GRAY));
        meta.setEnchantmentGlintOverride(true);
        item.setItemMeta(meta);
        return item;
    }
    public static ItemStack result() {
        return item;
    }
}