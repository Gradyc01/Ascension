package org.bingo4.Items.Craftable.Unlocks.MasterCompass.GUIs;

import org.bingo4.Interfaces.AscensionMenuGUI;
import org.bingo4.Items.Craftable.Unlocks.MasterCompass.CompassAbilities;
import org.bingo4.Player.Data.PlayerData;
//import org.bingo4.Player.Data.PlayerUnlocks;
import org.bingo4.Utility.TextUtil;
import net.kyori.adventure.text.Component;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public abstract class MasterCompassGUIs extends AscensionMenuGUI {
    private final CompassAbilities masterCompassAbilities;
    protected final int trackSpecificPlayerCost = 1000;
    protected final int trackNearestPlayerCost = 300;
    public MasterCompassGUIs(PlayerData playerData, char GUILines, Component name, ItemStack compass) {
        super(playerData, GUILines, name, true);
        this.masterCompassAbilities = new CompassAbilities(compass);
    }

    protected void trackPlayer(Player p) {
//        PlayerUnlocks pU = playerData.getPlayerUnlocks();
//        if (hasEnoughSouls(pU, trackSpecificPlayerCost)) {
//            masterCompassAbilities.trackPlayer(player ,p);
//            pU.addUnlockTokens(-trackSpecificPlayerCost);
//        }
    }

    protected void trackNearest(PlayerData pD) {
//        PlayerUnlocks pU = playerData.getPlayerUnlocks();
//        if (hasEnoughSouls(pU, trackNearestPlayerCost)) {
//            if (masterCompassAbilities.trackNearestPlayer(pD)) pU.addUnlockTokens(-trackNearestPlayerCost);
//        }
    }

    private boolean hasEnoughSouls(PlayerData pD, int amount) {
//        if (pD.getUnlockTokens() >= amount) {
//            return true;
//        };
//        TextUtil.errorMessage(player, "You do not have enough Souls to do this!");
        return false;
    }



}
