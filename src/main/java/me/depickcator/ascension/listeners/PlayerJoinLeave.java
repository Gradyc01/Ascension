package me.depickcator.ascension.listeners;

import me.depickcator.ascension.Ascension;
import me.depickcator.ascension.General.GameStates;
import me.depickcator.ascension.Player.Cooldowns.Death.PlayerDeath;
import me.depickcator.ascension.Player.Data.PlayerData;
import me.depickcator.ascension.Player.Data.PlayerUtil;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class PlayerJoinLeave implements Listener {
    private final Ascension plugin;
    public PlayerJoinLeave() {
        this.plugin = Ascension.getInstance();
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        switch (plugin.getGameState().getCurrentState()) {
            case GameStates.LOBBY -> {
                onPlayerJoinLobby(event);
            }
            case GameStates.UNLOADED -> {
                return;
            }
            default -> {
                onPlayerJoinDuringGame(event);
            }
        }
        PlayerUtil.updateTabList();
    }

    @EventHandler
    public void onPlayerLeave(PlayerQuitEvent event) {
        switch (plugin.getGameState().getCurrentState()) {
            case GameStates.LOBBY -> {
                Player player = event.getPlayer();
                PlayerData playerData = PlayerUtil.getPlayerData(player);
                if (playerData != null) {
                    playerData.getPlayerTeam().leaveTeam();
                }
            }
            case GameStates.UNLOADED -> {
                return;
            }
            default -> {
                Player player = event.getPlayer();
                PlayerData playerData = PlayerUtil.getPlayerData(player);
                PlayerDeath.getInstance().setPlayerSpectating(playerData);
                playerData.getPlayerTeam().getTeam().updateState();
            }
        }
    }

    private void onPlayerJoinLobby(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        PlayerData playerData = PlayerUtil.assignNewPlayerData(player);
        PlayerUtil.clearEffects(playerData);
        Location spawn = Ascension.getSpawn();
        playerData.resetToLobby();
        player.teleport(new Location(plugin.getWorld(), spawn.getX(), spawn.getY() + 102, spawn.getZ()));
    }

    private void onPlayerJoinDuringGame(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        PlayerData playerData = PlayerUtil.assignNewPlayerData(player);
        PlayerUtil.clearEffects(playerData);
        Location spawn = Ascension.getSpawn();
        player.teleport(new Location(plugin.getWorld(), spawn.getX(), spawn.getY() + 102, spawn.getZ()));
        PlayerDeath.getInstance().setPlayerSpectating(playerData);
    }

//    private void setNightVision(PlayerData playerData) {
//        if(playerData.getPlayerStats().isNightVision()) {
//            playerData.getPlayer().addPotionEffect(new PotionEffect(PotionEffectType.NIGHT_VISION, -1, 0, false, false));
//        }
//    }
}
