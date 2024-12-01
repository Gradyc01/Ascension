package me.depickcator.ascension.Player;

import me.depickcator.ascension.Ascension;
import org.bukkit.entity.Player;

public class PlayerStats {
    private final Ascension plugin;
    private final Player player;
    private final PlayerData playerData;

    //Statistics
    private int kills;
    private int deaths;

    //Settings
    private boolean nightVision;
    private boolean foodDrops;
    public PlayerStats(Ascension plugin, PlayerData playerData) {
        this.plugin = plugin;
        this.player = playerData.getPlayer();
        this.playerData = playerData;
        kills = 0;
        nightVision = false;
        foodDrops = false;
    }

    public void addKill() {
        kills++;
        playerData.getPlayerScoreboard().updateGameBoard();
    }

    public int getKills() {
        return kills;
    }

    public void nightVisionSwitch() {
        nightVision = !nightVision;
    }

    public boolean isNightVision() {
        return nightVision;
    }

    public void foodDropsSwitch() {
        foodDrops = !foodDrops;
    }

    public boolean isFoodDrops() {
        return foodDrops;
    }

    public int getDeaths() {
        return deaths;
    }

    public void setDeaths(int deaths) {
        this.deaths = deaths;
    }

    public void addDeaths(int deaths) {
        setDeaths(this.deaths + deaths);
    }
}
