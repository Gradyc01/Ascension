package org.bingo4.Lobby.NPCs.WeaponsValues;

import org.bingo4.Lobby.NPCs.LobbyNPCs;
import org.bingo4.Player.Data.PlayerUtil;
import org.bingo4.Utility.TextUtil;
import org.apache.commons.lang3.tuple.Pair;
import org.bukkit.entity.EntityType;
import org.bukkit.event.player.PlayerInteractEntityEvent;

public class WeaponValuesNPC extends LobbyNPCs {
    public WeaponValuesNPC(double x, double y, double z, Pair<Integer, Integer> rotation) {
        super(x, y, z, rotation,
                EntityType.ZOMBIFIED_PIGLIN, TextUtil.makeText("Weapons", TextUtil.GOLD, true, false));
    }

    @Override
    public boolean interact(PlayerInteractEntityEvent event) {
        new WeaponValuesGUI(PlayerUtil.getPlayerData(event.getPlayer()));
        return true;
    }
}
