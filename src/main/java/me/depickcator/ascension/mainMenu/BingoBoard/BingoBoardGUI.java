package me.depickcator.ascension.MainMenu.BingoBoard;

import me.depickcator.ascension.Ascension;
import me.depickcator.ascension.Interfaces.AscensionGUI;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextColor;
import net.kyori.adventure.text.format.TextDecoration;
import org.apache.commons.lang3.tuple.MutablePair;
import org.apache.commons.lang3.tuple.Pair;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;

public class BingoBoardGUI implements AscensionGUI {
    public final static String menuName = "BINGO-BOARD";

    private final int GUISize = 6 * 9;
    private final Inventory inventory;
    private Player player;
    private Ascension ab;
    public BingoBoardGUI(Ascension ab, Player p) {
        this.ab = ab;
        player = p;
        inventory = Bukkit.createInventory(p, GUISize,Component.text("Game Board").color(TextColor.color(0,255,255)));
        if (player != null) {
            p.openInventory(inventory);
        }
        setItemBackground(inventory,GUISize);
        boardItems();
        claimItemButton();
        playerHeadButton(inventory, 49, p);
        inventory.setItem(48, goBackItem());
//        closeGUIButton(inventory, 49);
        if (player != null) {
            Pair<Inventory, AscensionGUI> pair2 = new MutablePair<>(inventory,this);
            Ascension.guiMap.put(p.getUniqueId(), pair2);
        }
    }

    private void boardItems() {
        BingoData bingoData = this.ab.getBingoData();
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
        setGUIItems(inventory, button, buttonMeta,53);
    }

    @Override
    public String getGUIName() {
        return menuName;
    }

    @Override
    public void interactWithGUIButtons(InventoryClickEvent event, Player p) {
        ItemStack item = event.getCurrentItem();
        if (item == null) {
            return;
        }
        switch (item.getType()) {
            case Material.EMERALD -> {
                ab.getBingoData().claimItem(p);
            }
            case Material.ARROW -> {
                p.performCommand("open-main-menu");
            }
            default -> {

            }
        }
    }
}
