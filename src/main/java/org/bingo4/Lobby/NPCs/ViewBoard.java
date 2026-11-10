package org.bingo4.Lobby.NPCs;

import org.bingo4.MainMenuUI.BingoBoard.BingoBoardGUI;
import org.bingo4.Player.Data.PlayerUtil;
import org.bingo4.Utility.TextUtil;
import org.apache.commons.lang3.tuple.Pair;
import org.bukkit.entity.EntityType;
import org.bukkit.event.player.PlayerInteractEntityEvent;

public class ViewBoard extends LobbyNPCs {
    public ViewBoard(double x, double y, double z, Pair<Integer, Integer> rotation) {
        super(x, y, z, rotation,
                EntityType.SKELETON, TextUtil.makeText("Board", TextUtil.GOLD, true, false));
    }

    @Override
    public boolean interact(PlayerInteractEntityEvent event) {
        new BingoBoardGUI(PlayerUtil.getPlayerData(event.getPlayer()));
        return true;
    }
}
