package me.depickcator.ascensionBingo.listeners;

import me.depickcator.ascensionBingo.AscensionBingo;
import me.depickcator.ascensionBingo.General.GameStates;
import me.depickcator.ascensionBingo.Player.PlayerData;
import me.depickcator.ascensionBingo.Player.PlayerUtil;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class PlayerJoinLeave implements Listener {
    private final AscensionBingo plugin;
    public PlayerJoinLeave(AscensionBingo plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        switch (plugin.getGameState().getCurrentState()) {
            case GameStates.LOBBY -> {
                onPlayerJoinLobby(event);
            }
            case GameStates.GAME_BEFORE_GRACE -> {

            }
            default -> {
                return;
            }
        }

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
            case GameStates.GAME_BEFORE_GRACE -> {

            }
            default -> {
                return;
            }
        }
    }

    private void onPlayerJoinLobby(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        PlayerData playerData = PlayerUtil.assignNewPlayerData(player, plugin);
        Location spawn = AscensionBingo.getSpawn();
        playerData.resetToLobby();
        player.teleport(new Location(plugin.getWorld(), spawn.getX(), spawn.getY() + 102, spawn.getZ()));
    }
}
