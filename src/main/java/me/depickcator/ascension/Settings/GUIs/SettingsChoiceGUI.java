package me.depickcator.ascension.Settings.GUIs;

import me.depickcator.ascension.Interfaces.AscensionGUI;
import me.depickcator.ascension.Player.Data.PlayerData;
import me.depickcator.ascension.Settings.BuildCustom.AllButtons;
import me.depickcator.ascension.Settings.BuildCustom.ChoiceButton;
import me.depickcator.ascension.Settings.BuildCustom.IncrementButton;
import me.depickcator.ascension.Utility.SoundUtil;
import me.depickcator.ascension.Utility.TextUtil;
import org.bukkit.Material;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SettingsChoiceGUI extends AscensionGUI {
    private final HashMap<ItemStack, String> choiceButtons;
    private final ChoiceButton button;
    private final AllButtons allButtons;
    private final int[] choiceIndex = {4, 3, 5, 2, 6, 1, 7, 0, 8};
    public SettingsChoiceGUI(PlayerData playerData, ChoiceButton button, AllButtons allButtons) {
        super(playerData, (char) 2, TextUtil.makeText("Select a choice for: " + button.getTitle(), TextUtil.AQUA), true);
        inventory.setItem(13, goBackItem());
        choiceButtons = new HashMap<>();
        this.button = button;
        this.allButtons = allButtons;
        inventory.setItem(4, button.getItem());
        initChoices();
    }

    private void initChoices() {
        HashMap<String, Material> choices = button.getChoiceTitles();
        int i = 0;
        for (String title : choices.keySet()) {
            Material material = choices.get(title);
            ItemStack item = makeChoiceButton(title, material);
            choiceButtons.put(item, title);
            inventory.setItem(choiceIndex[i], item);
            if (i >= choiceIndex.length - 1) break;
            i++;
        }
    }

    private ItemStack makeChoiceButton(String title, Material material) {
        ItemStack item = new ItemStack(material);
        ItemMeta meta = item.getItemMeta();
        meta.displayName(TextUtil.makeText(title, TextUtil.GOLD));
        item.setItemMeta(meta);
        return item;
    }

    @Override
    public void interactWithGUIButtons(InventoryClickEvent event) {
        ItemStack item = event.getCurrentItem();
        if (item.equals(goBackItem())) {
            new SettingsCustomGUI(playerData, allButtons);
        }

        String title = choiceButtons.get(item);
        if (title != null) {
            button.setCurrentSelection(title);
            SoundUtil.playHighPitchPling(player);
            new SettingsCustomGUI(playerData, allButtons);
        }
    }
}
