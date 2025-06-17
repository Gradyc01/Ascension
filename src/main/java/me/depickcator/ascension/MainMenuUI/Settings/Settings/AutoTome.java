package me.depickcator.ascension.MainMenuUI.Settings.Settings;

import me.depickcator.ascension.Player.Data.PlayerData;
import me.depickcator.ascension.Player.Data.PlayerStats;
import org.bukkit.Material;

public class AutoTome extends SettingButton {
    public AutoTome() {
        super("Auto Tome",
                "Uses the XP Tome automatically",
                PlayerStats.autoTome,
                Material.BOOK);
    }

    @Override
    public boolean onClick(PlayerData pD, boolean newValue) {
        return true;
    }
}
