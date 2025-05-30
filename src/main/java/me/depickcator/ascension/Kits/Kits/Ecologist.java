package me.depickcator.ascension.Kits.Kits;

import me.depickcator.ascension.Utility.TextUtil;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

public class Ecologist extends Kit {
    public Ecologist() {
        super("Ecologist");
    }

    @Override
    public List<ItemStack> initKitItems() {
        return new ArrayList<>(List.of(
                setToolMeta(new ItemStack(Material.STONE_PICKAXE)),
                new ItemStack(Material.SUGAR_CANE, 12),
                new ItemStack(Material.LILY_PAD, 64),
                new ItemStack(Material.VINE, 32),
                new ItemStack(Material.COW_SPAWN_EGG, 3)
        ));
    }

    @Override
    public ItemStack getMascot() {
        ItemStack item = new ItemStack(Material.OAK_LOG);
        ItemMeta meta = item.getItemMeta();
        meta.displayName(TextUtil.makeText(getDisplayName(), TextUtil.AQUA));
        item.setItemMeta(meta);
        return item;
    }
}
