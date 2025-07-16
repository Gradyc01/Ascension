package me.depickcator.ascension.MainMenuUI.Command;

import me.depickcator.ascension.Player.Data.PlayerStats;
import me.depickcator.ascension.Utility.TextUtil;
import me.depickcator.ascension.Interfaces.AscensionMenuGUI;
import me.depickcator.ascension.MainMenuUI.Command.Commands.*;
import me.depickcator.ascension.Player.Data.PlayerData;
import net.kyori.adventure.text.Component;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.*;

public class CommandGUI extends AscensionMenuGUI {
    private final HashMap<ItemStack, Commands> map;
    public CommandGUI(PlayerData playerData) {
        super(playerData, (char) 6, TextUtil.makeText("Commands", TextUtil.AQUA), true);
        map = new HashMap<>();
        initializeButtons(new SendCoords(), 45);
        initializeButtons(new SpawnTravel(), 11);
        initializeButtons(new Surface(), 13);
        initializeButtons(new AscensionTravel(), 15);
        initializeButtons(new AnchorTravel(), 31);
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
