package me.depickcator.ascensionBingo.Interfaces;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextColor;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;

public interface AscensionGUI {

    default void setGUIItems(Inventory inventory, ItemStack button, ItemMeta buttonMeta, int index) {
        button.setItemMeta(buttonMeta); //Sets the Meta to Button Meta
        button.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
        inventory.setItem(index, button);
    }

    default void setItemBackground(Inventory inventory, int GUISize) {
        ItemStack button = new ItemStack(Material.GRAY_STAINED_GLASS_PANE);
        ItemMeta buttonMeta = button.getItemMeta();
        buttonMeta.displayName(Component.text(" ").color(TextColor.color(185, 185, 185)));
        buttonMeta.setCustomModelData(0);
        button.setItemMeta(buttonMeta); //Sets the Meta to Button Meta
        for (int i = 0; i < GUISize; i++) {
            inventory.setItem(i, button);
        }
    }

    default void closeGUIButton(Inventory inventory, int index) {
        ItemStack button = getCloseButton();
        button.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
        inventory.setItem(index, button);
    }

    default void playerHeadButton(Inventory inventory, int index, Player player) {
        ItemStack button = new ItemStack(Material.PLAYER_HEAD);
        SkullMeta headMeta = (SkullMeta) button.getItemMeta();
        headMeta.setPlayerProfile(player.getPlayerProfile());
        Component title = Component.text("Your Stat's").color(TextColor.color(255, 170, 0));
        title = title.decoration(TextDecoration.ITALIC, false);
        headMeta.displayName(title);
        headMeta.setCustomModelData(0);
        setGUIItems(inventory, button, headMeta, index);

    }

    static ItemStack getCloseButton() {
        ItemStack button = new ItemStack(Material.BARRIER);
        ItemMeta buttonMeta = button.getItemMeta();
        Component title = Component.text("Close").color(TextColor.color(255,0,0));
        title = title.decoration(TextDecoration.ITALIC, false);
        buttonMeta.displayName(title);
        buttonMeta.setCustomModelData(2);
        button.setItemMeta(buttonMeta);
        return button;
    }
}
