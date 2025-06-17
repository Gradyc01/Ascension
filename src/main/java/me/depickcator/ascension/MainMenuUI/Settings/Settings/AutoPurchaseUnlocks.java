package me.depickcator.ascension.MainMenuUI.Settings.Settings;

import me.depickcator.ascension.Player.Data.PlayerData;
import me.depickcator.ascension.Player.Data.PlayerStats;
import org.bukkit.Material;

public class AutoPurchaseUnlocks extends SettingButton {
    public AutoPurchaseUnlocks() {
        super("Auto Purchase Unlocks",
                "Purchases unlocks automatically when crafting it",
                PlayerStats.autoPurchaseUnlocks,
                Material.CRAFTER);
    }

    @Override
    public boolean onClick(PlayerData pD, boolean newValue) {
        return true;
    }
}
