package me.depickcator.ascension.MainMenuUI.Settings.Settings;

import me.depickcator.ascension.Player.Data.PlayerData;
import me.depickcator.ascension.Player.Data.PlayerStats;
import me.depickcator.ascension.Settings.Settings;
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
