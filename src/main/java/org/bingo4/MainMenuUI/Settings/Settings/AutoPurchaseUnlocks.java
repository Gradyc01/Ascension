package org.bingo4.MainMenuUI.Settings.Settings;

import org.bingo4.Player.Data.PlayerData;
import org.bingo4.Player.Data.PlayerStats;
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
