package me.depickcator.ascension.Lobby.NPCs.Bingo;

import me.depickcator.ascension.Lobby.NPCs.LobbyNPCs;
import me.depickcator.ascension.Player.Data.PlayerUtil;
import me.depickcator.ascension.Utility.TextUtil;
import org.apache.commons.lang3.tuple.Pair;
import org.bukkit.entity.EntityType;
import org.bukkit.event.player.PlayerInteractEntityEvent;

public class BingoNPC extends LobbyNPCs {
    public BingoNPC(double x, double y, double z, Pair<Integer, Integer> rotation) {
        super(x, y, z, rotation,
                EntityType.BLAZE, TextUtil.makeText("Change Player", TextUtil.GOLD, true, false));
    }

    @Override
    public boolean interact(PlayerInteractEntityEvent event) {
        new BingoNPCGUI(PlayerUtil.getPlayerData(event.getPlayer()), 0);
        return true;
    }
}
