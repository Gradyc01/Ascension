package me.depickcator.ascension.Settings.BuildCustom;

import me.depickcator.ascension.Utility.TextUtil;
import net.kyori.adventure.text.Component;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

public abstract class ScalarButton extends Button {
    private int number;
    private final int min;
    private final int max;
    public ScalarButton(Material material, String title, int initialNumber) {
        this(material, title, initialNumber, 0, 25);
    }

    public ScalarButton(Material material, String title, int initialNumber, int max) {
        this(material, title, initialNumber, 0, max);
    }

    public ScalarButton(Material material, String title, int initialNumber, int min, int max) {
        super(material, title);
        this.number = initialNumber;
        this.min = min;
        this.max = max;
        updateVisual();
    }

    public abstract boolean build(BuildCustom custom);

    public void updateVisual() {
        List<Component> lore = new ArrayList<>(List.of(
                TextUtil.makeText(getNumber() + "", TextUtil.DARK_GREEN)
        ));
        ItemMeta meta = getItem().getItemMeta();
        meta.lore(lore);
        getItem().setItemMeta(meta);
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

}
