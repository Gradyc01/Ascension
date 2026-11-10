package org.bingo4.Items.Uncraftable;

import org.bingo4.Items.CustomItem;
import org.bingo4.Utility.TextUtil;
import net.kyori.adventure.text.Component;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.List;

public class HardenedSaddle extends CustomItem {
    private static HardenedSaddle instance;
    private HardenedSaddle() {
        super("Hardened Saddle", "hardened_saddle");
    }

    public static HardenedSaddle getInstance() {
        if (instance == null) {
            instance = new HardenedSaddle();
        }
        return instance;
    }

    @Override
    protected ItemStack initResult() {
        ItemStack item = new ItemStack(Material.SADDLE);
        ItemMeta meta = item.getItemMeta();
        meta.displayName(TextUtil.makeText(getDisplayName(), TextUtil.YELLOW));
        meta.setEnchantmentGlintOverride(true);
        List<Component> lore = List.of(TextUtil.makeText("Found only in Dungeons",TextUtil.DARK_PURPLE));
        meta.lore(lore);
        item.setItemMeta(meta);
        generateUniqueModelNumber(item);
        return item;
    }
}
