package org.bingo4.Lobby.EventsBoard;

import org.bingo4.Interfaces.AscensionMenuGUI;
import org.bingo4.Player.Data.PlayerData;
import org.bingo4.Utility.TextUtil;
import org.apache.commons.lang3.tuple.Pair;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;

public class EventBoardGUI extends AscensionMenuGUI {
    private final EventBoard eventBoard;
    public EventBoardGUI(PlayerData playerData) {
        super(playerData, (char) 3, TextUtil.makeText("Choose an Event", TextUtil.YELLOW), true);
        eventBoard = plugin.getLobby().getEventBoard();
        initBoardEvents();
//        makeBoardOption(Material.CAMPFIRE, "Feast", 10);
//        makeBoardOption(Material.CHEST, "Care Package", 11);
    }

    private void initBoardEvents() {
        int index = 11;
        for (Pair<Material, String> rep : eventBoard.getEventRepresentations()) {
            String name = rep.getRight();
            Material material = rep.getLeft();
            ItemStack item = initExplainerItem(material, new ArrayList<>(), TextUtil.makeText(name, TextUtil.GREEN));
            inventory.setItem(index, item);
            index++;
        }
    }

    @Override
    public void interactWithGUIButtons(InventoryClickEvent event) {
        ItemStack item = event.getCurrentItem();
        for (Pair<Material, String> rep : eventBoard.getEventRepresentations()) {
            if (item.getType() == rep.getLeft()) {
                eventBoard.showPlayerBoard(rep.getRight(), playerData);
                player.playSound(player.getLocation(), Sound.UI_BUTTON_CLICK, 100f, 2);
                player.sendMessage(TextUtil.makeText("Now Displaying: ", TextUtil.AQUA).append(TextUtil.makeText(rep.getRight(), TextUtil.GREEN)));
                event.setCancelled(true);
                player.closeInventory();
            }
        }
    }
}
