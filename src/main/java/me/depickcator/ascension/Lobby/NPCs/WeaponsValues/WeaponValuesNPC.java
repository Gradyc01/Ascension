package me.depickcator.ascension.Lobby.NPCs.WeaponsValues;

import me.depickcator.ascension.Lobby.NPCs.LobbyNPCs;
import me.depickcator.ascension.Player.Data.PlayerUtil;
import me.depickcator.ascension.Utility.TextUtil;
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
