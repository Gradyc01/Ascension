package me.depickcator.ascension.Settings.BuildCustom;

import me.depickcator.ascension.Settings.BuildCustom.Buttons.*;
import java.util.HashMap;
import java.util.Set;

public class AllButtons {
    private final HashMap<Button, Integer> buttons;
    public AllButtons() {
        buttons = new HashMap<>();
        initSettings();
    }

    private void initSettings() {
        addSetting(new GameScoreRequirement(), 19);
        addSetting(new WorldBorderSize(), 20);
        addSetting(new AscensionTimer(), 21);
        addSetting(new EasyItems(), 22);
        addSetting(new MediumItems(), 23);
        addSetting(new HardItems(), 24);
        addSetting(new CustomItems(), 25);
        addSetting(new TeamSize(), 28);
        addSetting(new Timelines(), 31);
    }

    private void addSetting(Button button, int index) {
        buttons.put(button, index);
    }

    public int getIndex(Button button) {
        return buttons.get(button);
    }

    public Set<Button> getKeys() {
        return buttons.keySet();
    }
}
