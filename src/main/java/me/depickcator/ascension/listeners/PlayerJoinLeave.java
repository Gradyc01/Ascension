package me.depickcator.ascension.listeners;

import me.depickcator.ascension.Ascension;
import me.depickcator.ascension.General.GameStates;
import me.depickcator.ascension.General.TextUtil;
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
                event.joinMessage(TextUtil.makeText(event.getPlayer().getName(), TextUtil.DARK_GRAY)
                        .append(TextUtil.makeText( " has joined the lobby!", TextUtil.GOLD)));
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
            case GameStates.LOBBY -> {
                Player player = event.getPlayer();
                PlayerData playerData = PlayerUtil.getPlayerData(player);
                if (playerData != null) {
                    playerData.getPlayerTeam().leaveTeam();
                }
                event.quitMessage(TextUtil.makeText(""));
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
                        .append(TextUtil.makeText(" has disconnected and has been eliminated", TextUtil.RED)));
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
