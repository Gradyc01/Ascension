package me.depickcator.ascension.Lobby.NPCs;

import me.depickcator.ascension.MainMenuUI.Map.MapGUI;
import me.depickcator.ascension.Player.Data.PlayerUtil;
import me.depickcator.ascension.Utility.TextUtil;
import org.apache.commons.lang3.tuple.Pair;
import org.bukkit.entity.EntityType;
import org.bukkit.event.player.PlayerInteractEntityEvent;

public class ViewMap extends LobbyNPCs {
    public ViewMap(double x, double y, double z, Pair<Integer, Integer> rotation) {
        super(x, y, z, rotation,
                EntityType.STRAY, TextUtil.makeText("Map", TextUtil.GOLD, true, false));
    }

    @Override
    public boolean interact(PlayerInteractEntityEvent event) {
        new MapGUI(PlayerUtil.getPlayerData(event.getPlayer()));
        return true;
    }
}
