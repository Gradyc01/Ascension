package me.depickcator.ascension.Lobby.NPCs;

import me.depickcator.ascension.Lobby.EventsBoard.EventBoardGUI;
import me.depickcator.ascension.Player.Data.PlayerUtil;
import me.depickcator.ascension.Utility.TextUtil;
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
