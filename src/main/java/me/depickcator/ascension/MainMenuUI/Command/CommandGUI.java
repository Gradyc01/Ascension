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
        initializeButtons(new NightVision(), 24, true, playerData.getPlayerStats().getSetting(PlayerStats.nightVisionKey));
        initializeButtons(new FoodDrops(), 25, true, playerData.getPlayerStats().getSetting(PlayerStats.foodDropsKey));
        initializeButtons(new AutoPurchaseUnlocks(), 33, true, playerData.getPlayerStats().getSetting(PlayerStats.autoPurchaseUnlocks));
        initializeButtons(new SendCoords(), 19);
        initializeButtons(new SpawnTravel(), 21);
        initializeButtons(new Surface(), 22);
        initializeButtons(new AscensionTravel(), 30);
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
