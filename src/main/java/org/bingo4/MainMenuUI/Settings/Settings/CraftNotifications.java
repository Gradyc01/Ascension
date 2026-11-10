package org.bingo4.MainMenuUI.Settings.Settings;

import org.bingo4.Player.Data.PlayerData;
import org.bingo4.Player.Data.PlayerStats;
import org.bukkit.Material;

public class CraftNotifications extends SettingButton {
    public CraftNotifications() {
        super("Craft Notifications",
                "Notifies when you can craft an Unlock",
                PlayerStats.craftNotifications,
                Material.REDSTONE_LAMP);
    }

    @Override
    public boolean onClick(PlayerData pD, boolean newValue) {
        return true;
    }
}
