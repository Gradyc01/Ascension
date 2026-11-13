package org.bingo4.MainMenuUI.Command;

import org.bingo4.Interfaces.AscensionMenuGUI;
import org.bingo4.MainMenuUI.Command.Commands.*;
import org.bingo4.Player.Data.PlayerData;
import org.bingo4.Utility.TextUtil;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;

public class CommandGUI extends AscensionMenuGUI {
    private final HashMap<ItemStack, Commands> map;
    public CommandGUI(PlayerData playerData) {
        super(playerData, (char) 6, TextUtil.makeText("Commands", TextUtil.AQUA), true);
        map = new HashMap<>();
        initializeButtons(new SendCoords(), 45);
        initializeButtons(new SpawnTravel(), 11);
        initializeButtons(new Surface(), 13);
//        initializeButtons(new AscensionTravel(), 15);
//        initializeButtons(new AnchorTravel(), 31);
        inventory.setItem(48, goBackItem());
        playerHeadButton(49);

    }

    private void initializeButtons(Commands c, int index) {
        ItemStack item = c.getButton().clone();
        map.put(item, c);
        inventory.setItem(index, item);
    }

    @Override
    public void interactWithGUIButtons(InventoryClickEvent event) {
        ItemStack item = event.getCurrentItem();
        if (item == null) {
            return;
        }
        if (map.containsKey(item)) {
            Commands c = map.get(item);
            if (c.uponEvent(event, playerData)) {
                new CommandGUI(playerData);
            }
            return;
        }
        if (item.equals(goBackItem())) {
            player.performCommand("open-main-menu");
        }
    }
}
