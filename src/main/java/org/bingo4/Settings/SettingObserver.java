package org.bingo4.Settings;

import org.bingo4.Bingo4;
import org.bingo4.General.Game.Reset.ResetGame;
import org.bingo4.General.LocationChecker.LocationCheck;
import org.bingo4.Settings.Presets.Standard;

public class SettingObserver {
    private Settings settings;
    public SettingObserver() {
        settings = new Standard();
    }

    private void updateSettings() {
        new ResetGame();
        Bingo4.getInstance().setLocationCheck(new LocationCheck(Bingo4.getSpawn()));
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
