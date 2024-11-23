package me.depickcator.ascensionBingo.Player;

import me.depickcator.ascensionBingo.AscensionBingo;
import org.bukkit.entity.Player;

public class PlayerStats {
    private final AscensionBingo plugin;
    private final Player player;
    private final PlayerData playerData;

    //Statistics
    private int Kills;
    public PlayerStats(AscensionBingo plugin, PlayerData playerData) {
        this.plugin = plugin;
        this.player = playerData.getPlayer();
        this.playerData = playerData;
        Kills = 0;
    }

    public void addKill() {
        Kills++;
        playerData.getPlayerScoreboard().updateGameBoard();
    }

    public int getKills() {
        return Kills;
    }

//    public void setKills(int kills) {
//        Kills = kills;
//    }
}
