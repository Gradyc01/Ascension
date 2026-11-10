package org.bingo4.Settings.GUIs;

import org.bingo4.Bingo4;
import org.bingo4.Interfaces.AscensionMenuGUI;
import org.bingo4.Player.Data.PlayerData;
import org.bingo4.Settings.Presets.Brawl;
import org.bingo4.Settings.Presets.InstantAscension;
import org.bingo4.Settings.Presets.Quickplay;
import org.bingo4.Settings.Presets.Standard;
import org.bingo4.Settings.Settings;
import org.bingo4.Utility.SoundUtil;
import org.bingo4.Utility.TextUtil;
import net.kyori.adventure.text.Component;
import org.bukkit.Material;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class SettingsPresetGUI extends AscensionMenuGUI {
    private final HashMap<ItemStack, Settings> presets;
    public SettingsPresetGUI(PlayerData playerData) {
        super(playerData, (char) 3 , TextUtil.makeText("Presets", TextUtil.AQUA), true);
        inventory.setItem(22, getCloseButton());
        inventory.setItem(21, goBackItem());
        presets = new HashMap<>();
        initPresets();
    }

    private void initPresets() {
        addPreset(Material.GRASS_BLOCK,
                "1 Hr 30 Mins",
                "A Standard Game of Bingo4",
                new Standard(), 13);
        addPreset(Material.SUGAR_CANE,
                "1 Hr",
                "Compact Version of the Standard Game",
                new Quickplay(), 11);
        addPreset(Material.GOLDEN_SWORD,
                "20 Mins",
                "A Last Man Standing Scenario",
                new Brawl(), 15);
        addPreset(Material.ENCHANTED_BOOK,
                "1 Hr 30 Mins",
                "A No Bingo4 Game mode",
                new InstantAscension(), 10);
    }

    private void addPreset(Material mat, String estTime ,String desc, Settings settings, int index) {
        List<Component> lore = new ArrayList<>(List.of(
                TextUtil.makeText(estTime, TextUtil.GOLD),
                TextUtil.makeText(desc, TextUtil.DARK_PURPLE)
        ));
        ItemStack item = initExplainerItem(mat, lore, TextUtil.makeText(settings.getName(), TextUtil.GREEN));
        presets.put(item, settings);
        inventory.setItem(index, item);
    }

    @Override
    public void interactWithGUIButtons(InventoryClickEvent event) {
        ItemStack item = event.getCurrentItem();
        if (item.equals(getCloseButton())) {
            event.setCancelled(true);
            player.closeInventory();
        } else if (item.equals(goBackItem())) {
            new SettingsGUI(playerData);
        } else {
            Settings preset = presets.get(item);
            if (preset != null) {
                Bingo4.getInstance().getSettingsUI().setSettings(preset);
                player.sendMessage(TextUtil.makeText("You selected the preset ", TextUtil.AQUA).append(item.displayName().color(TextUtil.AQUA)));
                SoundUtil.playHighPitchPling(player);
                event.setCancelled(true);
                player.closeInventory();
            }
        }
    }
}
