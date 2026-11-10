package org.bingo4.Lobby.NPCs;

import org.bingo4.Kits.KitBookGUI;
import org.bingo4.Player.Data.PlayerUtil;
import org.bingo4.Utility.TextUtil;
import org.apache.commons.lang3.tuple.Pair;
import org.bukkit.entity.EntityType;
import org.bukkit.event.player.PlayerInteractEntityEvent;

public class ViewKits extends LobbyNPCs {
    public ViewKits(double x, double y, double z, Pair<Integer, Integer> rotation) {
        super(x, y, z, rotation,
                EntityType.ZOMBIE, TextUtil.makeText("Kits", TextUtil.GOLD, true, false));
    }

    @Override
    public boolean interact(PlayerInteractEntityEvent event) {
        new KitBookGUI(PlayerUtil.getPlayerData(event.getPlayer()));
        return true;
    }
}
