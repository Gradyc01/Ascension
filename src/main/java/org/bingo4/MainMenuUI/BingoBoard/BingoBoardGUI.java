package org.bingo4.MainMenuUI.BingoBoard;

import org.bingo4.Interfaces.AscensionMenuGUI;
import org.bingo4.MainMenuUI.MainMenuGUI;
import org.bingo4.Player.Cooldowns.ScanBoardCooldown;
import org.bingo4.Player.Data.PlayerData;
import org.bingo4.Utility.TextUtil;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextDecoration;
import org.apache.commons.lang3.tuple.Pair;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

public class BingoBoardGUI extends AscensionMenuGUI {
    private final int[] boardSlots = {
             2,  3,  4,  5,  6,
            11, 12, 13, 14, 15,
            20, 21, 22, 23, 24,
            29, 30 ,31, 32, 33,
            38, 39, 40, 41 ,42};
    private List<ItemStack> bingoItems;
    public static final int rightClickClaim = 250;
    public BingoBoardGUI(PlayerData playerData) {
        super(playerData, (char) 6, TextUtil.makeText("Game Board", TextUtil.AQUA), true);
        bingoItems = new ArrayList<>();
        boardItems();
//        inventory.setItem( 53, initClaimItem());
        inventory.setItem( /*44*/53, initScanItem());
        inventory.setItem( 50, initExplainerItem());
        playerHeadButton(49);
        inventory.setItem(48, goBackItem());
    }

    private void boardItems() {
        BingoData bingoData = plugin.getBingoData();
        List<BingoSlot> slots = bingoData.getBingoSlots();
        List<Boolean> hasItems = bingoData.getItemsCompleted(playerData);
        if (slots.size() != 25) {
            slots.clear();
            ItemStack item = initUnsetItem("UNSET");
            for (int i = 0; i < 25; i++) {
                if (!hasItems.get(i)) {
                    inventory.setItem(boardSlots[i], item);
                } else {
                    inventory.setItem(boardSlots[i], makeClaimedItem(item));
                }
            }
            return;
        }
        for (int i = 0; i < 25; i++) {
            ItemStack item = slots.get(i).getItem();
            if (!hasItems.get(i)) {
                inventory.setItem(boardSlots[i], item);
                bingoItems.add(item);
            } else {
                inventory.setItem(boardSlots[i], makeClaimedItem(item));
            }
        }
    }

    private ItemStack initUnsetItem(String name) {
        ItemStack item = new ItemStack(Material.BEDROCK);
        ItemMeta meta = item.getItemMeta();
        meta.displayName(TextUtil.makeText(name, TextUtil.GRAY, true, true));
        meta.setEnchantmentGlintOverride(true);
        item.setItemMeta(meta);
        return item;
    }

    private ItemStack makeClaimedItem(ItemStack item) {
        ItemStack CLAIMED = new ItemStack(Material.GREEN_STAINED_GLASS_PANE);
        ItemMeta meta = CLAIMED.getItemMeta();
        meta.displayName(item.displayName().color(TextUtil.AQUA).decoration(TextDecoration.ITALIC, false));
        CLAIMED.setItemMeta(meta);
        return CLAIMED;
    }

    private ItemStack initClaimItem() {
        Component title =TextUtil.makeText("CLAIM AN ITEM", TextUtil.GREEN, true, false);
        List<Component> lore = new ArrayList<>(List.of(
                TextUtil.makeText(" Claims a item (if any)", TextUtil.DARK_PURPLE),
                TextUtil.makeText("from the board in order", TextUtil.DARK_PURPLE)
        ));
        return initExplainerItem(Material.EMERALD, lore, title);
    }

    private ItemStack initExplainerItem() {
        Component title = TextUtil.makeText("Information", TextUtil.DARK_GREEN);
        List<Component> lore = new ArrayList<>(List.of(
                TextUtil.makeText("[Left Click]", TextUtil.GOLD).append(TextUtil.makeText(" to Claim items by ", TextUtil.DARK_PURPLE)),
                TextUtil.makeText("clicking on the item directly. ", TextUtil.DARK_PURPLE),
                TextUtil.makeText(""),
                TextUtil.makeText("[Right Click]", TextUtil.GOLD).append(TextUtil.makeText(" to view the item's ", TextUtil.DARK_PURPLE)),
                TextUtil.makeText("recipes if there are any", TextUtil.DARK_PURPLE),
                TextUtil.makeText("", TextUtil.AQUA),
                TextUtil.makeText("   Warning: This will claim items", TextUtil.RED),
                TextUtil.makeText("that are currently been worn too.", TextUtil.RED)
        ));
        return initExplainerItem(Material.REDSTONE_TORCH, lore, title);
    }

    private ItemStack initScanItem() {
        Component title = TextUtil.makeText("SCAN ITEMS", TextUtil.GREEN, true, false);
        List<Component> lore = new ArrayList<>(List.of(
                TextUtil.makeText(" Scans through the items on the", TextUtil.DARK_PURPLE),
                TextUtil.makeText("board to find which items you have ", TextUtil.DARK_PURPLE),
                TextUtil.makeText("in your inventory that went unnoticed", TextUtil.DARK_PURPLE),
                TextUtil.makeText("", TextUtil.AQUA),
                TextUtil.makeText("       Cooldown: 60 Seconds", TextUtil.GOLD)
        ));
        return initExplainerItem(Material.REINFORCED_DEEPSLATE, lore, title);
    }

    private void checkClaimable() {
        BingoData bingoData = plugin.getBingoData();
        ItemStack item = new ItemStack(Material.BLACK_STAINED_GLASS_PANE);
        ItemMeta meta = item.getItemMeta();
        meta.displayName(TextUtil.makeText(""));
        item.setItemMeta(meta);
        for (BingoSlot slot : bingoData.getBingoSlots()) {
            if (!bingoData.canUnlockItem(playerData, slot.getItem())) {
                int index = boardSlots[bingoData.getBingoSlots().indexOf(slot)];
                inventory.setItem(index, item);
            }
        }
    }

    @Override
    public void interactWithGUIButtons(InventoryClickEvent event) {
        ItemStack item = event.getCurrentItem();
        if (item == null || event.getClickedInventory() instanceof PlayerInventory) return;
        BingoData bingoData = plugin.getBingoData();
        if (item.equals(initClaimItem())) {
//            bingoData.claimItem(playerData);
        } else if (item.equals(goBackItem())) {
            new MainMenuGUI(playerData);
        } else if (item.equals(initScanItem()) && !ScanBoardCooldown.getInstance().isOnCooldown(player)) {
            if (!plugin.getGameState().inGame()) {
                TextUtil.errorMessage(player, "This feature is currently not available");
                return;
            }
            checkClaimable();
            player.playSound(player.getLocation(), Sound.ENTITY_ENDERMAN_HURT, 10, 0);
            ScanBoardCooldown.getInstance().setCooldownTimer(player);
        } else if (bingoItems.contains(item)) {
            switch (event.getClick()) {
//                case LEFT -> {
//                    bingoData.claimItem(playerData, item, true);
//                }
                case RIGHT -> {
                    new OpenRecipe(playerData, item).open();
                }
            };
        }
    }
}
