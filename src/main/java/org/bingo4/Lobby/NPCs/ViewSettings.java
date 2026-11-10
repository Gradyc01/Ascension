package org.bingo4.Lobby.NPCs;

import org.bingo4.MainMenuUI.Command.CommandGUI;
import org.bingo4.Player.Data.PlayerUtil;
import org.bingo4.Utility.TextUtil;
import org.apache.commons.lang3.tuple.Pair;
import org.bukkit.entity.EntityType;
import org.bukkit.event.player.PlayerInteractEntityEvent;

public class ViewSettings extends LobbyNPCs {
    public ViewSettings(double x, double y, double z, Pair<Integer, Integer> rotation) {
        super(x, y, z, rotation,
                EntityType.HUSK, TextUtil.makeText("Commands & Settings", TextUtil.GOLD, true, false));
    }

    @Override
    public boolean interact(PlayerInteractEntityEvent event) {
        new CommandGUI(PlayerUtil.getPlayerData(event.getPlayer()));
        return true;
    }
}
