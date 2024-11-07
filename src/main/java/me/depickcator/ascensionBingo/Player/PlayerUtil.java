package me.depickcator.ascensionBingo.Player;

import me.depickcator.ascensionBingo.AscensionBingo;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.ArrayList;

public class PlayerUtil {
    public static void assignNewPlayerData(AscensionBingo ab) {
        AscensionBingo.playerDataMap.clear();
        ArrayList<Player> onlinePlayerList = new ArrayList<>(Bukkit.getOnlinePlayers());
        for (Player p : onlinePlayerList) {
            AscensionBingo.playerDataMap.put(p.getUniqueId(), new PlayerData(p, ab));
        }
    }

    public static PlayerData getPlayerData(Player p) {
        if (AscensionBingo.playerDataMap.containsKey(p.getUniqueId())) {
            return AscensionBingo.playerDataMap.get(p.getUniqueId());
        }
        return null;
    }


}
