package me.depickcator.ascension.Player;

import me.depickcator.ascension.Ascension;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;

public class PlayerUtil {
    public static void assignNewPlayerData(Ascension ab) {
        Ascension.playerDataMap.clear();
        ArrayList<Player> onlinePlayerList = new ArrayList<>(Bukkit.getOnlinePlayers());
        for (Player p : onlinePlayerList) {
            Ascension.playerDataMap.put(p.getUniqueId(), new PlayerData(p, ab));
        }
    }

    public static PlayerData assignNewPlayerData(Player p, Ascension ab) {
        PlayerData playerData = new PlayerData(p, ab);
        Ascension.playerDataMap.put(p.getUniqueId(), playerData);
        return playerData;
    }

    public static void clearPlayerDataMap() {
        Ascension.playerDataMap.clear();
    }

    public static PlayerData getPlayerData(Player p) {
        if (Ascension.playerDataMap.containsKey(p.getUniqueId())) {
            return Ascension.playerDataMap.get(p.getUniqueId());
        }
        return null;
    }

    public static void giveItem(Player p, ItemStack item) {
        p.getWorld().dropItem(p.getLocation(), item);
    }


}
