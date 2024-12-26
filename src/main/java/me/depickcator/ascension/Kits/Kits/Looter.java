package me.depickcator.ascension.Kits.Kits;

import me.depickcator.ascension.Utility.TextUtil;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class Looter implements Kit{
    private final static String DISPLAY_NAME = "Looter";
    private final List<ItemStack> kitItems;
    public Looter() {
        kitItems = kitItems();
        registerKit(this);
    }

    private List<ItemStack> kitItems() {
        return new ArrayList<>(Set.of(
                new ItemStack(Material.ROTTEN_FLESH, 3),
                new ItemStack(Material.BONE, 3),
                new ItemStack(Material.SLIME_BALL, 3),
                new ItemStack(Material.GUNPOWDER, 3),
                new ItemStack(Material.SPIDER_EYE, 3),
                new ItemStack(Material.INK_SAC, 3),
                new ItemStack(Material.MAGMA_CREAM, 3),
                new ItemStack(Material.ENDER_PEARL, 3)
        ));
    }

    @Override
    public List<ItemStack> getKitItems() {
        return kitItems;
    }

    @Override
    public ItemStack getMascot() {
        ItemStack item = new ItemStack(Material.BONE);
        ItemMeta meta = item.getItemMeta();
        meta.displayName(TextUtil.makeText(DISPLAY_NAME, TextUtil.AQUA));
        meta.setCustomModelData(10);
        item.setItemMeta(meta);
        return item;
    }

    @Override
    public String getDisplayName() {
        return DISPLAY_NAME;
    }
}
