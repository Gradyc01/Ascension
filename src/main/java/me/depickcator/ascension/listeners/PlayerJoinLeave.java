package me.depickcator.ascension.listeners;

import me.depickcator.ascension.Ascension;
import me.depickcator.ascension.General.GameStates;
import me.depickcator.ascension.General.Queue.Queue;
import me.depickcator.ascension.Utility.TextUtil;
import me.depickcator.ascension.Player.Cooldowns.Death.PlayerDeath;
import me.depickcator.ascension.Player.Data.PlayerData;
import me.depickcator.ascension.Player.Data.PlayerUtil;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.scheduler.BukkitRunnable;

public class PlayerJoinLeave implements Listener {
    private final Ascension plugin;
    public PlayerJoinLeave() {
        this.plugin = Ascension.getInstance();
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
                event.joinMessage(TextUtil.makeText(""));
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
                Player player = event.getPlayer();
                PlayerData playerData = PlayerUtil.getPlayerData(player);
//                if (playerData.checkState(PlayerData.STATE_SPECTATING)) {
//                    event.quitMessage(TextUtil.makeText(""));
//                    return; //TODO: Add this in later? and test obv
//                }
                PlayerDeath.getInstance().setPlayerSpectating(playerData);
                playerData.getPlayerTeam().getTeam().updateState();
                event.quitMessage(TextUtil.makeText(event.getPlayer().getName(), TextUtil.DARK_GRAY)
                        .append(TextUtil.makeText(" has disconnected and has been slain", TextUtil.RED)));
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
        event.joinMessage(TextUtil.makeText(event.getPlayer().getName(), TextUtil.DARK_GRAY)
                .append(TextUtil.makeText( " has joined the lobby!", TextUtil.GOLD)));
    }

    private void onPlayerJoinDuringGame(PlayerJoinEvent event) {
        PlayerData pD = PlayerUtil.getPlayerData(event.getPlayer());
        if (pD != null && !plugin.getGameState().checkState(GameStates.GAME_FINAL_ASCENSION, GameStates.GAME_LOADING)) {
            TextUtil.debugText("Player Data is not null");
            TextUtil.debugText("Same player? " + pD.getPlayer().equals(event.getPlayer()));
            TextUtil.debugText("Same player instance? " + (pD.getPlayer() == event.getPlayer()));
            pD.reInitPlayer(event.getPlayer());
            pD.getPlayerTeam().getTeam().updateState();
            return;
        }
        Player player = event.getPlayer();
        PlayerData playerData = PlayerUtil.assignNewPlayerData(player);
        PlayerUtil.clearEffects(playerData);
        Location spawn = Ascension.getSpawn();
        player.teleport(new Location(plugin.getWorld(), spawn.getX(), spawn.getY() + 102, spawn.getZ()));
        PlayerDeath.getInstance().setPlayerSpectating(playerData);
    }

    private void onPlayerLeaveLobby(PlayerQuitEvent event) {
        Player player = event.getPlayer();
        PlayerData playerData = PlayerUtil.getPlayerData(player);
        if (playerData != null) {
            playerData.getPlayerTeam().leaveTeam();
        }
        event.quitMessage(TextUtil.makeText(""));
    }
}
