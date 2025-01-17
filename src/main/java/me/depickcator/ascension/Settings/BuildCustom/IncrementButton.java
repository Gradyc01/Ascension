package me.depickcator.ascension.Settings.BuildCustom;

import me.depickcator.ascension.Utility.TextUtil;
import net.kyori.adventure.text.format.TextColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class IncrementButton {
    private final ScalarButton button;
    private final ItemStack item;
    private final int incrementAmount;
    public IncrementButton(ScalarButton button, int incrementAmount) {
        this.button = button;
        this.incrementAmount = incrementAmount;
        item = initItem();
    }

    private ItemStack initItem() {
        ItemStack item = incrementAmount < 0 ? new ItemStack(Material.RED_DYE) : new ItemStack(Material.LIME_DYE);
        String sign = incrementAmount < 0 ? " - " : " + ";
        TextColor color = incrementAmount < 0 ? TextUtil.RED : TextUtil.GREEN;
        ItemMeta meta = item.getItemMeta();
        meta.displayName(TextUtil.makeText(sign + Math.abs(incrementAmount), color));
        item.setItemMeta(meta);
        return item;
    }

    public ItemStack getItem() {
        return item;
    }

    public int increment() {
        return button.addNumber(incrementAmount);
    }


}
