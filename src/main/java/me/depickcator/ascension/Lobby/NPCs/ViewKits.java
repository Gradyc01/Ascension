package me.depickcator.ascension.Lobby.NPCs;

import me.depickcator.ascension.Kits.KitBookGUI;
import me.depickcator.ascension.Player.Data.PlayerUtil;
import me.depickcator.ascension.Utility.TextUtil;
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
