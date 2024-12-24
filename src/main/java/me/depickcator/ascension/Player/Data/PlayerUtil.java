package me.depickcator.ascension.Player.Data;

import me.depickcator.ascension.Ascension;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.ArrayList;
import java.util.List;

public class PlayerUtil {
    public static void assignNewPlayerData() {
        Ascension.playerDataMap.clear();
        ArrayList<Player> onlinePlayerList = new ArrayList<>(Bukkit.getOnlinePlayers());
        for (Player p : onlinePlayerList) {
            Ascension.playerDataMap.put(p.getUniqueId(), new PlayerData(p));
        }
    }

    public static PlayerData assignNewPlayerData(Player p) {
        PlayerData playerData = new PlayerData(p);
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

    //Get & returns all players who are in the game
    public static List<PlayerData> getAllPlayingPlayers() {
        ArrayList<Player> onlinePlayerList = new ArrayList<>(Bukkit.getOnlinePlayers());
        ArrayList<PlayerData> playingPlayers = new ArrayList<>();
        for (Player p : onlinePlayerList) {
            PlayerData pD = getPlayerData(p);
            if (pD == null) continue;
            playingPlayers.add(pD);
        }
        return playingPlayers;
    }

    public static void updateTabList() {
        ArrayList<Player> onlinePlayerList = new ArrayList<>(Bukkit.getOnlinePlayers());
        for (Player p : onlinePlayerList) {
            PlayerData pD = getPlayerData(p);
            pD.getPlayerScoreboard().updateTabList();
        }
    }

    public static void clearEffects(PlayerData pD) {
        Player p = pD.getPlayer();
        p.clearActivePotionEffects();
        if (pD.getPlayerStats().isNightVision()) {
            p.addPotionEffect(new PotionEffect(PotionEffectType.NIGHT_VISION, -1, 0, false, false));
        }
    }

}
