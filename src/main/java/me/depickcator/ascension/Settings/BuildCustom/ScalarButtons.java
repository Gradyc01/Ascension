package me.depickcator.ascension.Settings.BuildCustom;

import me.depickcator.ascension.Settings.BuildCustom.Buttons.*;
import java.util.HashMap;
import java.util.Set;

public class ScalarButtons {
//    private final HashMap<ItemStack, Pair<Integer, CustomButton>> buttons;
    private final HashMap<ScalarButton, Integer> buttons;
    public ScalarButtons() {
        buttons = new HashMap<>();
        initSettings();
    }

    private void initSettings() {
        addScrollSetting(new GameScoreRequirement(), 19);
        addScrollSetting(new WorldBorderSize(), 20);
        addScrollSetting(new AscensionTimer(), 21);
        addScrollSetting(new EasyItems(), 22);
        addScrollSetting(new MediumItems(), 23);
        addScrollSetting(new HardItems(), 24);
        addScrollSetting(new CustomItems(), 25);
    }

    private void addScrollSetting(ScalarButton button, int index) {
        // ItemStack item = button.getItem();
//        buttons.put(item, new MutablePair<>(index, button));
        buttons.put(button, index);
    }

    public int getIndex(ScalarButton button) {
        return buttons.get(button);
    }

    public Set<ScalarButton> getKeys() {
        return buttons.keySet();
    }
}
