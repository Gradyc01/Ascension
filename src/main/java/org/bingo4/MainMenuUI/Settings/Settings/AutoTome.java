package org.bingo4.MainMenuUI.Settings.Settings;

import org.bingo4.Player.Data.PlayerData;
import org.bingo4.Player.Data.PlayerStats;
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
