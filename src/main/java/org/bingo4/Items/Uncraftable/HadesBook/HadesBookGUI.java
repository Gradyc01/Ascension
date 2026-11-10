package org.bingo4.Items.Uncraftable.HadesBook;


import org.bingo4.Interfaces.AscensionMenuGUI;
import org.bingo4.MainMenuUI.BingoBoard.BingoSlot;
import org.bingo4.Player.Data.PlayerData;
import org.bingo4.Player.Data.PlayerUtil;
import org.bingo4.Utility.SoundUtil;
import org.bingo4.Utility.TextUtil;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class HadesBookGUI extends AscensionMenuGUI {
    private final List<BingoSlot> bingoSlots;
    private final List<ItemStack> items;
    public HadesBookGUI(PlayerData playerData) {
        super(playerData, (char) 6, TextUtil.makeText("Hades' Book", TextUtil.GOLD), true);
        bingoSlots = plugin.getBingoData().getBingoSlots();
        items = new ArrayList<>();
        loadBingoBoard();
    }

    private void loadBingoBoard() {
        char index = 2;
        for (BingoSlot slot : bingoSlots) {
            inventory.setItem(index, slot.getItem());
            items.add(slot.getItem());
            if (((index - 6) % 9) == 0) {
                index+=4;
            }
            index++;
        }
    }

    @Override
    public void interactWithGUIButtons(InventoryClickEvent event) {
        if (event.getCurrentItem() != null && event.isLeftClick() && items.contains(event.getCurrentItem())) {
            if (player.getInventory().getItemInMainHand().equals(HadesBook.getInstance().getItem())) {
//                player.getWorld().dropItem(player.getLocation(), event.getCurrentItem());
                successfulPurchase(event);
                PlayerUtil.giveItem(player, event.getCurrentItem());
            }
        }
    }

    private void successfulPurchase(InventoryClickEvent event) {
        SoundUtil.playHighPitchPling(player);
        event.setCancelled(true);
        player.closeInventory();
        player.getInventory().getItemInMainHand().setAmount(0);
        player.sendMessage(TextUtil.makeText("You have selected the item: ", TextUtil.GREEN).append(event.getCurrentItem().displayName()));
    }
}

