package me.depickcator.ascension.MainMenu.Command;

import me.depickcator.ascension.General.TextUtil;
import me.depickcator.ascension.Interfaces.AscensionGUI;
import me.depickcator.ascension.MainMenu.Command.Commands.*;
import me.depickcator.ascension.Player.PlayerData;
import net.kyori.adventure.text.Component;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.*;

public class CommandGUI extends AscensionGUI {
    private final HashMap<ItemStack, Commands> map;
    public CommandGUI(PlayerData playerData) {
        super(playerData, (char) 6, TextUtil.makeText("Commands", TextUtil.AQUA), true);
        map = new HashMap<>();
        initializeButtons(new NightVision(), 16, true, playerData.getPlayerStats().isNightVision());
        initializeButtons(new FoodDrops(), 25, true, playerData.getPlayerStats().isFoodDrops());
        initializeButtons(new SendCoords(), 10);
        initializeButtons(new SpawnTravel(), 19);
        initializeButtons(new Surface(), 28);
        inventory.setItem(48, goBackItem());
        playerHeadButton(49);

    }

    private void initializeButtons(Commands c, int index) {
        initializeButtons(c, index, false, false);
    }
    private void initializeButtons(Commands c, int index, boolean addLore, boolean isEnabled) {
        ItemStack item = c.getButton().clone();
        item.setItemMeta(addTitleAndLore(item, addLore, isEnabled));
        map.put(item, c);
        inventory.setItem(index, item);
    }

    private ItemMeta addTitleAndLore(ItemStack item, boolean addLore, boolean isEnabled) {
        ItemMeta meta = item.getItemMeta();
        if (addLore) {
            meta.lore(addOnOffLore(isEnabled));
        }
        return meta;
    }
    private List<Component> addOnOffLore(boolean onOff) {
        List<Component> lore = new ArrayList<>();
        if (onOff) {
            lore.add(TextUtil.makeText("[Enabled]", TextUtil.GREEN));
        } else {
            lore.add(TextUtil.makeText("[Disabled]", TextUtil.RED));
        }
        return lore;
    }

    @Override
    public void interactWithGUIButtons(InventoryClickEvent event) {
        ItemStack item = event.getCurrentItem();
        if (item == null) {
            return;
        }
        if (map.containsKey(item)) {
            Commands c = map.get(item);
            c.uponEvent(event, playerData);
            new CommandGUI(playerData);
            return;
        }
        if (item.equals(goBackItem())) {
            player.performCommand("open-main-menu");
        }
    }
}
