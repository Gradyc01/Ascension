package me.depickcator.ascension.Settings.BuildCustom;

import me.depickcator.ascension.Utility.TextUtil;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public abstract class Button {
    private final Material material;
    private final String title;
    private final ItemStack item;
    public Button(Material material, String title) {
        this.material = material;
        this.title = title;
        this.item = initItem();
    }

    public abstract boolean build(BuildCustom custom);
    private ItemStack initItem() {
        ItemStack item = new ItemStack(material);
        ItemMeta meta = item.getItemMeta();
        meta.displayName(TextUtil.makeText(title, TextUtil.GOLD));
        item.setItemMeta(meta);
        return item;
    }

    public Material getMaterial() {
        return material;
    }

    public String getTitle() {
        return title;
    }

    public ItemStack getItem() {
        return item;
    }
}
