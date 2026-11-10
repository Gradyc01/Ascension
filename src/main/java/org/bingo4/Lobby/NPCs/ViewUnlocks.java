package org.bingo4.Lobby.NPCs;

//import org.bingo4.MainMenuUI.Unlocks.UnlocksGUI;
import org.bingo4.Player.Data.PlayerUtil;
import org.bingo4.Utility.TextUtil;
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
//        new UnlocksGUI(PlayerUtil.getPlayerData(event.getPlayer()), (char) 1);
        return true;
    }
}
