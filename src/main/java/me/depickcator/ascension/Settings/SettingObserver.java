package me.depickcator.ascension.Settings;

import me.depickcator.ascension.Ascension;
import me.depickcator.ascension.General.LocationChecker.LocationCheck;
import me.depickcator.ascension.General.Game.Reset.ResetGame;
import me.depickcator.ascension.Settings.Presets.Standard;

public class SettingObserver {
    private Settings settings;
    public SettingObserver() {
        settings = new Standard();
    }

    private void updateSettings() {
        new ResetGame();
        Ascension.getInstance().setLocationCheck(new LocationCheck(Ascension.getSpawn()));
    }

    public Settings getSettings() {
        return settings;
    }

    /*Sets the games settings to the new Setting settings
    * if update == true then updates the game and resets it to fit the new settings*/
    public void setSettings(Settings settings, boolean update) {
        this.settings = settings;
        if (update) updateSettings();
    }

    public void setSettings(Settings settings) {
        setSettings(settings, true);
    }
}
