package org.bingo4.listeners;

import org.bingo4.Bingo4;
import org.bingo4.General.Game.GameStates;
import org.bingo4.General.Queue.Queue;
import org.bingo4.Persistence.PlayerDataWriter;
import org.bingo4.Player.Cooldowns.Death.PlayerDeath;
import org.bingo4.Player.Data.PlayerData;
import org.bingo4.Player.Data.PlayerUtil;
import org.bingo4.Utility.TextUtil;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class PlayerJoinLeave implements Listener {
    private final Bingo4 plugin;
    public PlayerJoinLeave() {
        this.plugin = Bingo4.getInstance();
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        switch (plugin.getGameState().getCurrentState()) {
            case GameStates.LOBBY_NORMAL -> {
                onPlayerJoinLobby(event);
                event.joinMessage(TextUtil.makeText(event.getPlayer().getName(), TextUtil.DARK_GRAY)
                        .append(TextUtil.makeText( " has joined the lobby!", TextUtil.GOLD)));
            }
            case GameStates.LOBBY_QUEUE -> {
                onPlayerJoinLobby(event);
                Queue.getInstance().stopQueue();
            }
            case GameStates.UNLOADED -> {
                return;
            }
            default -> {
                onPlayerJoinDuringGame(event);
//                event.joinMessage(TextUtil.makeText(""));
                event.joinMessage(null);
            }
        }
        PlayerUtil.updateTabList();
    }

    @EventHandler
    public void onPlayerLeave(PlayerQuitEvent event) {
        switch (plugin.getGameState().getCurrentState()) {
            case GameStates.LOBBY_NORMAL -> {
                onPlayerLeaveLobby(event);
            }
            case GameStates.LOBBY_QUEUE -> {
                onPlayerLeaveLobby(event);
                Queue.getInstance().stopQueue();
            }
            case GameStates.UNLOADED -> {
                return;
            }
            default -> {
//                Player player = event.getPlayer();
//                PlayerData playerData = PlayerUtil.getPlayerData(player);
//                if (playerData.checkState(PlayerData.STATE_SPECTATING)) {
//                    event.quitMessage(TextUtil.makeText(""));
//                    return; //TODO: Add this in later? and test obv
//                }
//                PlayerDeath.getInstance().setPlayerSpectating(playerData);
//                playerData.getPlayerTeam().getTeam().updateState();
//                event.quitMessage(TextUtil.makeText(event.getPlayer().getName(), TextUtil.DARK_GRAY)
//                        .append(TextUtil.makeText(" has disconnected and has been slain", TextUtil.RED)));
                onPlayerLeaveDuringGame(event);
            }
        }
    }

    private void onPlayerJoinLobby(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        PlayerData playerData = PlayerUtil.assignNewPlayerData(player);
        plugin.getBingoData().resetPlayer(playerData);
        PlayerUtil.clearEffects(playerData);
//        Location spawn = Bingo4.getSpawn();
//        Location spawn = plugin.getSpawn();
        playerData.resetToLobby();
//        player.teleport(new Location(plugin.getWorld(), spawn.getX(), spawn.getY() + 102, spawn.getZ()));
        event.joinMessage(TextUtil.makeText(event.getPlayer().getName(), TextUtil.DARK_GRAY)
                .append(TextUtil.makeText( " has joined the lobby!", TextUtil.GOLD)));
    }

    private void onPlayerJoinDuringGame(PlayerJoinEvent event) {
        PlayerData pD = PlayerUtil.getPlayerData(event.getPlayer());
        if (pD != null && !plugin.getGameState().checkState(GameStates.GAME_FINAL_ASCENSION, GameStates.GAME_LOADING, GameStates.GAME_ENDING)) {
            TextUtil.debugText("Player Data is not null");
            pD.reInitPlayer(event.getPlayer());
            pD.getPlayerTeam().getTeam().updateState();
            return;
        }
        Player player = event.getPlayer();
        PlayerData playerData = PlayerUtil.assignNewPlayerData(player);
        PlayerUtil.clearEffects(playerData);
        Location spawn = Bingo4.getSpawn();
        player.teleport(new Location(plugin.getWorld(), spawn.getX(), spawn.getY() + 102, spawn.getZ()));
        PlayerDeath.getInstance().setPlayerSpectating(playerData);
    }

    private void onPlayerLeaveDuringGame(PlayerQuitEvent event) {
        Player player = event.getPlayer();
        PlayerData playerData = PlayerUtil.getPlayerData(player);
        if (playerData.checkState(PlayerData.STATE_SPECTATING)) {
//            event.quitMessage(TextUtil.makeText(""));
            event.quitMessage(null);
            return; //TODO: Add this in later? and test obv
        }
        PlayerDeath.getInstance().setPlayerSpectating(playerData);
        playerData.getPlayerTeam().getTeam().updateState();
        event.quitMessage(TextUtil.makeText(event.getPlayer().getName(), TextUtil.DARK_GRAY)
                .append(TextUtil.makeText(" has disconnected and has been slain", TextUtil.RED)));
    }

    private void onPlayerLeaveLobby(PlayerQuitEvent event) {
        Player player = event.getPlayer();
        PlayerData playerData = PlayerUtil.getPlayerData(player);
        if (playerData != null) {
            playerData.getPlayerTeam().leaveTeam();
            new PlayerDataWriter(playerData).writeNewData();
        }
        PlayerUtil.removePlayerData(player);
        event.quitMessage(null);
    }


}
