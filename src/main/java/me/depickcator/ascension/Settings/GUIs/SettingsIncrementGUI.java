package me.depickcator.ascension.Settings.GUIs;

import me.depickcator.ascension.Interfaces.AscensionGUI;
import me.depickcator.ascension.Player.Data.PlayerData;
import me.depickcator.ascension.Settings.BuildCustom.ScalarButton;
import me.depickcator.ascension.Settings.BuildCustom.IncrementButton;
import me.depickcator.ascension.Settings.BuildCustom.AllButtons;
import me.depickcator.ascension.Utility.SoundUtil;
import me.depickcator.ascension.Utility.TextUtil;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import java.util.HashMap;
import java.util.List;

public class SettingsIncrementGUI extends AscensionGUI {
    private final HashMap<ItemStack, IncrementButton> incrementButtons;
    private final ScalarButton button;
    private final AllButtons allButtons;
    public SettingsIncrementGUI(PlayerData playerData, ScalarButton button, AllButtons allButtons) {
        super(playerData, (char) 1, TextUtil.makeText("Adjust " + button.getTitle(), TextUtil.AQUA), true);
        inventory.setItem(0, goBackItem());
        incrementButtons = new HashMap<>();
        this.button = button;
        this.allButtons = allButtons;
        inventory.setItem(4, button.getItem());
//        updateVisual();
        initIncrementButtons();
    }

    private void initIncrementButtons() {
        List<Integer> inc = button.getIncrementSpread();
        int[] arr = {1, 2, 3, 5, 6, 7};
        for (int i = 0; i < arr.length; i++) {
            IncrementButton incrementButton = new IncrementButton(button, inc.get(i));
            inventory.setItem(arr[i], incrementButton.getItem());
            incrementButtons.put(incrementButton.getItem(), incrementButton);
        }
    }

    @Override
    public void interactWithGUIButtons(InventoryClickEvent event) {
        ItemStack item = event.getCurrentItem();
        if (item.equals(goBackItem())) {
            new SettingsCustomGUI(playerData, allButtons);
        }

        IncrementButton incrementButton = incrementButtons.get(item);
        if (incrementButton != null) {
            SoundUtil.playHighPitchPling(player);
            incrementButton.increment();
            button.updateVisual();
            inventory.setItem(4, button.getItem());
        }
    }
}
