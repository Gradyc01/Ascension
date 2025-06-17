package me.depickcator.ascension.MainMenuUI.Settings;

import me.depickcator.ascension.Interfaces.AscensionMenuGUI;
import me.depickcator.ascension.MainMenuUI.MainMenuGUI;
import me.depickcator.ascension.MainMenuUI.Settings.Settings.*;
import me.depickcator.ascension.Player.Data.PlayerData;
import me.depickcator.ascension.Player.Data.PlayerStats;
import me.depickcator.ascension.Utility.SoundUtil;
import me.depickcator.ascension.Utility.TextUtil;
import net.kyori.adventure.text.Component;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SettingGUI extends AscensionMenuGUI {
    private final Map<ItemStack, SettingButton> settings;
    private final PlayerStats playerStats;
    public SettingGUI(PlayerData playerData) {
        super(playerData, (char) 6, TextUtil.makeText("Settings", TextUtil.AQUA), true);
        playerStats = playerData.getPlayerStats();
        settings = new HashMap<>();
        inventory.setItem(48, goBackItem());
        playerHeadButton(49);
        initSettings();
    }

    private void initSettings() {
        settings.clear();
        createSettingButton(new NightVision(), 20);
        createSettingButton(new FoodDrops(), 21);
        createSettingButton(new AutoPurchaseUnlocks(), 22);
        createSettingButton(new CraftNotifications(), 23);
        createSettingButton(new AutoTome(), 24);
    }

    private void createSettingButton(SettingButton settingButton, int index) {
        List<Component> lore = new ArrayList<>(List.of(
                TextUtil.makeText(settingButton.getDescription(), TextUtil.DARK_PURPLE)
        ));
        Component booleanText = playerStats.getSetting(settingButton.getKey()) ?
                TextUtil.makeText("[Enabled]", TextUtil.GREEN) :
                TextUtil.makeText("[Disabled]", TextUtil.RED);
        lore.add(booleanText);
        ItemStack item = initExplainerItem(settingButton.getMaterial(),
                lore,
                TextUtil.makeText(settingButton.getName(), TextUtil.AQUA));
        inventory.setItem(index, item);
        settings.put(item, settingButton);
    }



    @Override
    public void interactWithGUIButtons(InventoryClickEvent event) {
        ItemStack item = event.getCurrentItem();
        if (item == null) return;
        if (item.equals(goBackItem())) {
            new MainMenuGUI(playerData);
            return;
        }
        SettingButton button = settings.get(item);
        if (button == null) return;
        boolean newValue = !playerStats.getSetting(button.getKey());
        if (button.onClick(playerData, newValue)) {
            playerStats.setSetting(button.getKey(), newValue);
            Component booleanText = newValue ?
                    TextUtil.makeText("Enabled", TextUtil.GREEN) :
                    TextUtil.makeText("Disabled", TextUtil.RED);
            player.sendMessage(TextUtil.makeText("[Settings] ", TextUtil.BLUE)
                    .append(TextUtil.makeText(button.getName(), TextUtil.GOLD))
                    .append(TextUtil.makeText(" is now ", TextUtil.AQUA))
                    .append(booleanText));
            SoundUtil.playHighPitchPling(player);
            new SettingGUI(playerData);
        }
    }
}
