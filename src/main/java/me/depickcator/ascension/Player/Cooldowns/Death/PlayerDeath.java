package me.depickcator.ascension.Player.Cooldowns.Death;

import me.depickcator.ascension.Ascension;
import me.depickcator.ascension.General.GameStates;
import me.depickcator.ascension.General.TextUtil;
import me.depickcator.ascension.Player.PlayerData;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.HashSet;
import java.util.Set;

public class PlayerDeath {
    private final DeathTimer deathTimer;
    private final Ascension plugin;
    private Set<PlayerData> players;
    private static PlayerDeath instance;
    private PlayerDeath(Ascension plugin) {
        deathTimer = new DeathTimer();
        this.plugin = plugin;
        players = new HashSet<>();
    }

    public void playerDied(PlayerData playerData) {
        GameStates gameState = plugin.getGameState();
        if (gameState.inGame()) {
            if (!gameState.checkState(GameStates.GAME_FINAL_ASCENSION)) {
                setRespawningLater(playerData);
            } else {
                setPlayerSpectating(playerData);
            }
        }
    }

    private void setRespawningLater(PlayerData playerData) {
        Player p = playerData.getPlayer();
        setPlayerDead(playerData);
        p.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, Integer.MAX_VALUE, 1, false, false));
        playerData.setPlayerState(PlayerData.STATE_DEAD);
        deathTimer.setCooldownTimer(p);
        players.add(playerData);

        if (players.size() == 1) {
            respawnTimer();
        }

    }

    private void respawnTimer() {
        new BukkitRunnable() {
            @Override
            public void run() {
                if (players.isEmpty()) {
                    cancel();
                    return;
                }
                for (PlayerData playerData : players) {
                    Player p = playerData.getPlayer();
                    if (!deathTimer.isOnCooldown(p, false) || !plugin.getGameState().inGame()) {
                        respawnPlayer(playerData);
                        continue;
                    }
                    teleportBackToDeathLocation(p);
                    Component text = TextUtil.makeText("Respawning In: " + deathTimer.getCooldownTimer(p)  + "s", TextUtil.AQUA);
                    TextUtil.sendActionBar(p, text, 24, plugin);
                }
            }

        }.runTaskTimer(plugin, 0, 20);
    }

    private void teleportBackToDeathLocation(Player p) {
        Location loc = p.getLocation();
        Location deathLoc = p.getLastDeathLocation();
        double euclidean = Math.pow(Math.pow(loc.getBlockX() - deathLoc.getBlockX(), 2) + Math.pow(loc.getY() - deathLoc.getY(), 2) + Math.pow(loc.getBlockZ() - deathLoc.getBlockZ(), 2), 0.5);
        if (euclidean > 100) {
            p.teleport(p.getLastDeathLocation());
        }
    }

    private void setPlayerDead(PlayerData playerData) {
        Player p = playerData.getPlayer();
        p.clearActivePotionEffects();
        p.setLastDeathLocation(p.getLocation());
        p.setGameMode(GameMode.SPECTATOR);
        p.sendMessage(TextUtil.makeText("You are Dead", TextUtil.DARK_RED));
        p.showTitle(TextUtil.makeTitle(TextUtil.makeText("You Died", TextUtil.DARK_RED), TextUtil.makeText(""), 0, 5, 1));
    }

    public void respawnPlayer(PlayerData playerData) {
        players.remove(playerData);
        Player p = playerData.getPlayer();
        p.setGameMode(GameMode.SURVIVAL);
        p.addPotionEffect(new PotionEffect(PotionEffectType.REGENERATION, 10 * 20, 3, false, false));
        p.removePotionEffect(PotionEffectType.BLINDNESS);
        Location spawn = Ascension.getSpawn();
        Bukkit.dispatchCommand(Bukkit.getConsoleSender(),
                "spreadplayers " + spawn.getBlockX() + " " + spawn.getBlockZ() + " 200 500 true " + p.getName());
    }

    public void setPlayerSpectating(PlayerData playerData) {
        setPlayerDead(playerData);
        playerData.setPlayerState(PlayerData.STATE_SPECTATING);
    }

    public static PlayerDeath getInstance() {
        if (instance == null) {
            instance = new PlayerDeath(Ascension.getInstance());
        }
        return instance;

    }

}
