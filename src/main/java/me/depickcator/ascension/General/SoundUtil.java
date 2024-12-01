package me.depickcator.ascension.General;

import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.entity.Player;

public class SoundUtil {
    public static void broadcastSound(Sound sound, float volume, double pitch) {
        for (Player player : Bukkit.getOnlinePlayers()) {
            player.playSound(player.getLocation(), sound, volume, (float)pitch);
        }
    }

    public static void playErrorSoundEffect(Player p) {
        p.playSound(p.getLocation(), Sound.ENTITY_PLAYER_TELEPORT, 1.0f, 0f);
    }

    public static void playHighPitchPling(Player p) {
        p.playSound(p.getLocation(), Sound.BLOCK_NOTE_BLOCK_PLING, 1.0f, 2.0f);
    }


}
