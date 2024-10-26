package me.depickcator.ascensionBingo.mainMenu.BingoBoard;

import me.depickcator.ascensionBingo.AscensionBingo;
import me.depickcator.ascensionBingo.Interfaces.AscensionGUI;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextColor;
import net.kyori.adventure.text.format.TextDecoration;
import org.apache.commons.lang3.tuple.MutablePair;
import org.apache.commons.lang3.tuple.Pair;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;

import static java.lang.foreign.MemorySegment.NULL;

public class BingoBoardGUI implements AscensionGUI {
    public final static String menuName = "BINGO-BOARD";

    private final int GUISize = 6 * 9;
    private final Inventory inventory;
    private Player player;
    private AscensionBingo ab;
    public BingoBoardGUI(AscensionBingo ab, Player p) {
        this.ab = ab;
        player = p;
        inventory = Bukkit.createInventory(p, GUISize,Component.text("Game Board").color(TextColor.color(0,255,255)));
        if (player != null) {
            p.openInventory(inventory);
        }
        setItemBackground(inventory,GUISize);
        boardItems();
        claimItemButton();
        closeGUIButton(inventory, 49);
        if (player != null) {
            Pair<Inventory, String> pair = new MutablePair<>(inventory, menuName);
            AscensionBingo.guiMap.put(p.getUniqueId(), pair);
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

    public static void interactWithGUIButtons(ItemStack item, Player p, Inventory inv) {
        switch (item.getType()) {
            case Material.EMERALD -> {
                p.sendMessage("PLACEHOLDER MESSAGE FOR WHEN SOMEONE CLAIMS AN ITEM");
            }
            case Material.BARRIER -> {
                inv.close();
            }
            default -> {

            }
        }
    }
}
