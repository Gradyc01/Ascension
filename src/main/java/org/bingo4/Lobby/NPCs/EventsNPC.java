package org.bingo4.Lobby.NPCs;

import org.bingo4.Lobby.EventsBoard.EventBoardGUI;
import org.bingo4.Player.Data.PlayerUtil;
import org.bingo4.Utility.TextUtil;
import org.apache.commons.lang3.tuple.Pair;
import org.bukkit.entity.EntityType;
import org.bukkit.event.player.PlayerInteractEntityEvent;

public class EventsNPC extends LobbyNPCs {
    public EventsNPC(double x, double y, double z, Pair<Integer, Integer> rotation) {
        super(x, y, z, rotation,
                EntityType.CREEPER, TextUtil.makeText("Change Event", TextUtil.GOLD, true, false));
    }

    @Override
    public boolean interact(PlayerInteractEntityEvent event) {
        new EventBoardGUI(PlayerUtil.getPlayerData(event.getPlayer()));
        return true;
    }
}
