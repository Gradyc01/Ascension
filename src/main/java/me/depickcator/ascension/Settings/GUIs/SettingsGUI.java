package me.depickcator.ascension.Settings.GUIs;

import me.depickcator.ascension.Interfaces.AscensionGUI;
import me.depickcator.ascension.Player.Data.PlayerData;
import me.depickcator.ascension.Settings.BuildCustom.AllButtons;
import me.depickcator.ascension.Utility.TextUtil;
import org.bukkit.Material;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;

public class SettingsGUI extends AscensionGUI {
    private final ItemStack presetsButton;
    private final ItemStack customButton;
    public SettingsGUI(PlayerData playerData) {
        super(playerData, (char) 3 , TextUtil.makeText("Settings", TextUtil.AQUA), true);
        inventory.setItem(22, getCloseButton());
        presetsButton = initExplainerItem(Material.EMERALD, new ArrayList<>(), TextUtil.makeText("Presets", TextUtil.GREEN));
        customButton = initExplainerItem(Material.COMMAND_BLOCK, new ArrayList<>(), TextUtil.makeText("Custom", TextUtil.BLUE));
        inventory.setItem(11, presetsButton);
        inventory.setItem(15, customButton);
    }

    @Override
    public void interactWithGUIButtons(InventoryClickEvent event) {
        ItemStack item = event.getCurrentItem();
        if (item.equals(presetsButton)) {
            new SettingsPresetGUI(playerData);
        } else if (item.equals(customButton)) {
            new SettingsCustomGUI(playerData, new AllButtons());
        } else if (item.equals(getCloseButton())) {
            event.setCancelled(true);
            player.closeInventory();
        }
    }
}
