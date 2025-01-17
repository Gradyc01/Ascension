package me.depickcator.ascension.Settings.BuildCustom;

import me.depickcator.ascension.Utility.TextUtil;
import net.kyori.adventure.text.Component;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

public abstract class ScalarButton {
    private final Material material;
    private final String title;
    private int number;
    private final ItemStack item;
    private final int min;
    private final int max;
    public ScalarButton(Material material, String title, int initialNumber) {
        this(material, title, initialNumber, 0, 25);
    }

    public ScalarButton(Material material, String title, int initialNumber, int max) {
        this(material, title, initialNumber, 0, max);
    }

    public ScalarButton(Material material, String title, int initialNumber, int min, int max) {
        this.material = material;
        this.title = title;
        this.number = initialNumber;
        item = initItem();
        this.min = min;
        this.max = max;
        updateVisual();
    }

    public abstract boolean build(BuildCustom custom);

    public void updateVisual() {
        List<Component> lore = new ArrayList<>(List.of(
                TextUtil.makeText(getNumber() + "", TextUtil.DARK_GREEN)
        ));
        ItemMeta meta = item.getItemMeta();
        meta.lore(lore);
        item.setItemMeta(meta);
    }

    public int addNumber(int amount) {
        if (number + amount < min) {
            number = min;
            return min;
        }
        if (number + amount > max) {
            number = max;
            return max;
        }
        number += amount;
        return number;
    }

    private ItemStack initItem() {
        ItemStack item = new ItemStack(material);
        ItemMeta meta = item.getItemMeta();
        meta.displayName(TextUtil.makeText(title, TextUtil.GOLD));
        item.setItemMeta(meta);
        return item;
    }

    public List<Integer> getIncrementSpread() {
        return new ArrayList<>(List.of(
                -5,
                -3,
                -1,
                1,
                3,
                5
        ));

    }

    public int getNumber() {
        return number;
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
