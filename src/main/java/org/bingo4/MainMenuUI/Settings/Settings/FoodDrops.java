package org.bingo4.MainMenuUI.Settings.Settings;

import org.bingo4.Player.Data.PlayerData;
import org.bingo4.Player.Data.PlayerStats;
import org.bukkit.Material;

public class FoodDrops extends SettingButton {
    public FoodDrops() {
        super("Food Drops",
                "Whether animals should drop food",
                PlayerStats.foodDropsKey,
                Material.COOKED_BEEF);
    }

    @Override
    public boolean onClick(PlayerData pD, boolean newValue) {
        return true;
    }
}
