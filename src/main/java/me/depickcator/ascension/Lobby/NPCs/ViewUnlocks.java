package me.depickcator.ascension.Lobby.NPCs;

import me.depickcator.ascension.MainMenuUI.Unlocks.UnlocksGUI;
import me.depickcator.ascension.Player.Data.PlayerUtil;
import me.depickcator.ascension.Utility.TextUtil;
import org.apache.commons.lang3.tuple.Pair;
import org.bukkit.entity.EntityType;
import org.bukkit.event.player.PlayerInteractEntityEvent;

public class ViewUnlocks extends LobbyNPCs {
    public ViewUnlocks(double x, double y, double z, Pair<Integer, Integer> rotation) {
        super(x, y, z, rotation,
                EntityType.BOGGED, TextUtil.makeText("Unlocks", TextUtil.GOLD, true, false));
    }

    @Override
    public boolean interact(PlayerInteractEntityEvent event) {
        new UnlocksGUI(PlayerUtil.getPlayerData(event.getPlayer()), (char) 1);
        return true;
    }
}
