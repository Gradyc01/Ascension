package me.depickcator.ascension.Items.Uncraftable;

import me.depickcator.ascension.Ascension;
import me.depickcator.ascension.Items.CustomItem;
import me.depickcator.ascension.Utility.TextUtil;
import org.bukkit.Material;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class ShardOfTheFallen extends CustomItem {
    private static ShardOfTheFallen instance;

    private ShardOfTheFallen() {
        super("Shard of the Fallen", "shard_of_the_fallen");
    }

    public static ShardOfTheFallen getInstance() {
        if (instance == null) {
            instance = new ShardOfTheFallen();
        }
        return instance;
    }

    @Override
    protected ItemStack initResult() {
        ItemStack item = new ItemStack(Material.DISC_FRAGMENT_5, 1);
        ItemMeta meta = item.getItemMeta();
        meta.setCustomModelData(Ascension.getInstance().generateModelNumber());
        meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
        meta.addItemFlags(ItemFlag.HIDE_ADDITIONAL_TOOLTIP);
        meta.displayName(TextUtil.makeText(DISPLAY_NAME, TextUtil.DARK_GRAY));
        meta.setEnchantmentGlintOverride(true);
        item.setItemMeta(meta);
        item.setAmount(1);
        return item;
    }
}
