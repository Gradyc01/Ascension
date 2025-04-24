package me.depickcator.ascension.Items.Uncraftable;

import me.depickcator.ascension.Items.CustomItem;
import me.depickcator.ascension.Utility.TextUtil;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.EnchantmentStorageMeta;

public class RejuvenationBook extends CustomItem {
    private static RejuvenationBook instance;
    private RejuvenationBook() {
        super("Rejuvenation Book", "rejuvenation_book");
    }


    public static RejuvenationBook getInstance() {
        if (instance == null) {
            instance = new RejuvenationBook();
        }
        return instance;
    }

    @Override
    protected ItemStack initResult() {
        ItemStack item = new ItemStack(Material.ENCHANTED_BOOK);
        EnchantmentStorageMeta meta = (EnchantmentStorageMeta) item.getItemMeta();
        meta.displayName(TextUtil.makeText(getDisplayName(), TextUtil.GOLD));
        meta.setMaxStackSize(1);
        meta.addStoredEnchant(Enchantment.PROTECTION, 1, true);
        meta.addStoredEnchant(Enchantment.PROJECTILE_PROTECTION, 1, true);
        meta.addStoredEnchant(Enchantment.SHARPNESS, 1, true);
        meta.addStoredEnchant(Enchantment.POWER, 1, true);
        meta.setCustomModelDataComponent(generateUniqueModelNumber(meta.getCustomModelDataComponent()));
        meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
        item.setItemMeta(meta);
        return item;
    }
}
