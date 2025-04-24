package me.depickcator.ascension.Settings.GUIs;

import me.depickcator.ascension.Ascension;
import me.depickcator.ascension.Interfaces.AscensionGUI;
import me.depickcator.ascension.Player.Data.PlayerData;
import me.depickcator.ascension.Settings.BuildCustom.*;
import me.depickcator.ascension.Utility.SoundUtil;
import me.depickcator.ascension.Utility.TextUtil;
import net.kyori.adventure.text.Component;
import org.bukkit.Material;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class SettingsCustomGUI extends AscensionGUI {
    private final AllButtons buttons;
    private final HashMap<ItemStack, Button> customButtons;
    private final ItemStack buildSettingsItem;
    public SettingsCustomGUI(PlayerData playerData, AllButtons buttons) {
        super(playerData, (char) 6 , TextUtil.makeText("Build Custom Game", TextUtil.AQUA), true);
        inventory.setItem(49, getCloseButton());
        inventory.setItem(48, goBackItem());
        this.buttons = buttons;
        customButtons = new HashMap<>();
        buildSettingsItem = initBuildSettingsButton();
        inventory.setItem(53, buildSettingsItem);
        initSettings();
    }

    private ItemStack initBuildSettingsButton() {
        List<Component> lore = new ArrayList<>(List.of(
                TextUtil.makeText("Build this layout of settings", TextUtil.DARK_PURPLE)
        ));
        Component title = TextUtil.makeText("Build Settings", TextUtil.DARK_GREEN);
        return initExplainerItem(Material.GREEN_CONCRETE, lore, title);
    }

    private void initSettings() {
        for (Button button : buttons.getKeys()) {
            ItemStack item = button.getItem();
            customButtons.put(item, button);
            inventory.setItem(buttons.getIndex(button), item);
        }
    }


    @Override
    public void interactWithGUIButtons(InventoryClickEvent event) {
        ItemStack item = event.getCurrentItem();
        if (item.equals(getCloseButton())) {
            event.setCancelled(true);
            player.closeInventory();
        } else if (item.equals(goBackItem())) {
            new SettingsGUI(playerData);
        } else if (item.equals(buildSettingsItem)) {
            buildSetting();
            event.setCancelled(true);
            player.closeInventory();
        } else {
            Button button = customButtons.get(item);
            if (button != null) {
                if (button instanceof ScalarButton) {
                    new SettingsIncrementGUI(playerData, (ScalarButton) button, buttons);
                }
                if (button instanceof ChoiceButton) {
                    new SettingsChoiceGUI(playerData, (ChoiceButton) button, buttons);
                }

            }
        }
    }

    private void buildSetting() {
        BuildCustom buildCustom = new BuildCustom(buttons);
        if (buildCustom.parse()) {
            Ascension.getInstance().getSettingsUI().setSettings(buildCustom.build());
            SoundUtil.playHighPitchPling(player);
            player.sendMessage(TextUtil.makeText("Settings Built Successfully!", TextUtil.GREEN));
        } else {
            TextUtil.errorMessage(player, "Failed to parse syntactically incorrect settings");
        }

    }
}
