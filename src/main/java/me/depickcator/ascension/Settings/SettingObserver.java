package me.depickcator.ascension.Settings;

import me.depickcator.ascension.Ascension;
import me.depickcator.ascension.General.LocationChecker.LocationCheck;
import me.depickcator.ascension.General.ResetGame;
import me.depickcator.ascension.Settings.Presets.Standard;

public class SettingObserver {
    private Settings settings;
    public SettingObserver() {
        settings = new Standard();
    }

    private void updateSettings() {
//        for (PlayerData pD: PlayerUtil.getAllPlayingPlayers()) {
//            pD.getPlayerScoreboard().update();
//        }
//        for (Team team : TeamUtil.getEveryTeam()) {
//            team.getTeamStats().updateGameScoreRequirement();
//        }
//        Ascension.getInstance().setLocationCheck(new LocationCheck(Ascension.getSpawn()));
        new ResetGame();
        Ascension.getInstance().setLocationCheck(new LocationCheck(Ascension.getSpawn()));
    }

    public Settings getSettings() {
        return settings;
    }

    public void setSettings(Settings settings) {
        this.settings = settings;
        updateSettings();
    }
}
