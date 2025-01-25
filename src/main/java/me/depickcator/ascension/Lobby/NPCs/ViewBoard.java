package me.depickcator.ascension.Lobby.NPCs;

import me.depickcator.ascension.MainMenuUI.BingoBoard.BingoBoardGUI;
import me.depickcator.ascension.Player.Data.PlayerUtil;
import me.depickcator.ascension.Utility.TextUtil;
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
