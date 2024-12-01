package me.depickcator.ascensionBingo.Player;

import me.depickcator.ascensionBingo.AscensionBingo;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;

public class PlayerUtil {
    public static void assignNewPlayerData(AscensionBingo ab) {
        AscensionBingo.playerDataMap.clear();
        ArrayList<Player> onlinePlayerList = new ArrayList<>(Bukkit.getOnlinePlayers());
        for (Player p : onlinePlayerList) {
            AscensionBingo.playerDataMap.put(p.getUniqueId(), new PlayerData(p, ab));
        }
    }

    public static PlayerData assignNewPlayerData(Player p, AscensionBingo ab) {
        PlayerData playerData = new PlayerData(p, ab);
        AscensionBingo.playerDataMap.put(p.getUniqueId(), playerData);
        return playerData;
    }

    public static void clearPlayerDataMap() {
        AscensionBingo.playerDataMap.clear();
    }

    public static PlayerData getPlayerData(Player p) {
        if (AscensionBingo.playerDataMap.containsKey(p.getUniqueId())) {
            return AscensionBingo.playerDataMap.get(p.getUniqueId());
        }
        return null;
    }

    public static void giveItem(Player p, ItemStack item) {
        p.getWorld().dropItem(p.getLocation(), item);
    }


}
