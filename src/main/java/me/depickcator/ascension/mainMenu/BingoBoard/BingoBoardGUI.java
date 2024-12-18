package me.depickcator.ascension.MainMenu.BingoBoard;

import me.depickcator.ascension.General.TextUtil;
import me.depickcator.ascension.Interfaces.AscensionGUI;
import me.depickcator.ascension.Player.PlayerData;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextColor;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.Material;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;

public class BingoBoardGUI extends AscensionGUI {

    public BingoBoardGUI(PlayerData playerData) {
        super(playerData, (char) 6, TextUtil.makeText("Game Board", TextUtil.AQUA), true);
        boardItems();
        claimItemButton();
        playerHeadButton(49);
        inventory.setItem(48, goBackItem());
    }

    private void boardItems() {
        BingoData bingoData = plugin.getBingoData();
        ArrayList<ItemStack> items = bingoData.getItems();
        ArrayList<Boolean> hasItems = bingoData.getItemsCompleted(player);
        if (items.size() != 25) {
            return;
        }
        int index = 2;
        for (int i = 0; i < items.size(); i++) {
            if (!hasItems.get(i)) {
                inventory.setItem(index, items.get(i));
            } else {
                inventory.setItem(index, getClaimItem());
            }

            if (((index - 6) % 9) == 0) {
                index+=4;
            }
            index++;
        }
    }

    private ItemStack getClaimItem() {
        ItemStack CLAIMED = new ItemStack(Material.GREEN_STAINED_GLASS_PANE);
        ItemMeta meta = CLAIMED.getItemMeta();
        Component text = Component.text("CLAIMED").color(TextColor.color(185,255,185));
        text = text.decoration(TextDecoration.ITALIC,false);
        text = text.decoration(TextDecoration.BOLD,true);
        meta.displayName(text);
        CLAIMED.setItemMeta(meta);
        return CLAIMED;
    }

    private void claimItemButton() {
        ItemStack button = new ItemStack(Material.EMERALD);
        ItemMeta buttonMeta = button.getItemMeta();
        Component title = Component.text("CLAIM A ITEM").color(TextColor.color(62,92,32));
        title = title.decoration(TextDecoration.ITALIC, false);
        buttonMeta.displayName(title);
        buttonMeta.setEnchantmentGlintOverride(true);
        buttonMeta.setCustomModelData(0); // Use this when using the same item
        button.setItemMeta(buttonMeta);
        inventory.setItem( 53, button);
    }


    @Override
    public void interactWithGUIButtons(InventoryClickEvent event) {
        ItemStack item = event.getCurrentItem();
        if (item == null) {
            return;
        }
        switch (item.getType()) {
            case Material.EMERALD -> {
                plugin.getBingoData().claimItem(player);
            }
            case Material.ARROW -> {
                player.performCommand("open-main-menu");
            }
            default -> {

            }
        }
    }
}
