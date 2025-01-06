package me.depickcator.ascension.Player.Cooldowns.Death;

import me.depickcator.ascension.Ascension;
import me.depickcator.ascension.General.GameStates;
import me.depickcator.ascension.Utility.TextUtil;
import me.depickcator.ascension.Player.Data.PlayerData;
import me.depickcator.ascension.Player.Data.PlayerUtil;
import net.kyori.adventure.text.Component;
import org.bukkit.*;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.HashSet;
import java.util.Random;
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
//                playerData.setPlayerState(PlayerData.STATE_DEAD);
                setRespawningLater(playerData);

            } else {
                setPlayerSpectating(playerData);
//                playerData.setPlayerState(PlayerData.STATE_SPECTATING);
                playerData.getPlayerTeam().getTeam().updateState();
                Player p = playerData.getPlayer();
                p.playSound(p.getLocation(), Sound.ENTITY_WITHER_DEATH, 100f, 1f);
                p.sendMessage(TextUtil.makeText("You have been eliminated, but your team is still alive!", TextUtil.RED));
            }
        }
    }

    public void setRespawningLater(PlayerData playerData) {
        Player p = playerData.getPlayer();
        setPlayerDead(playerData, PlayerData.STATE_DEAD);
        p.addPotionEffect(new PotionEffect(PotionEffectType.DARKNESS, PotionEffect.INFINITE_DURATION, 1, false, false));
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
        if (euclidean > 30) {
            p.teleport(p.getLastDeathLocation());
        }
    }

    private void setPlayerDead(PlayerData playerData, int newPlayerState) {
        playerData.setPlayerState(newPlayerState);
        Player p = playerData.getPlayer();
        PlayerUtil.clearEffects(playerData);
        p.setLastDeathLocation(p.getLocation());
        p.setGameMode(GameMode.SPECTATOR);
        strikeLightning(p);
        p.sendMessage(TextUtil.makeText("You are Dead", TextUtil.DARK_RED));
        p.showTitle(TextUtil.makeTitle(TextUtil.makeText("You Died", TextUtil.DARK_RED), TextUtil.makeText(""), 0, 5, 1));
        changePlayerVisibility(playerData);
    }

    private void strikeLightning(Player player) {
        Location loc = player.getLocation();
        loc.getWorld().strikeLightningEffect(loc);
        loc.getWorld().playSound(loc, Sound.ENTITY_LIGHTNING_BOLT_THUNDER, 1.0F, 1.0F);
        loc.getWorld().playSound(loc, Sound.ENTITY_LIGHTNING_BOLT_IMPACT, 1.0F, 0.0F);
    }

    public void respawnPlayer(PlayerData playerData) {
        players.remove(playerData);
        playerData.setPlayerState(PlayerData.STATE_ALIVE);
        Player p = playerData.getPlayer();
        p.setGameMode(GameMode.SURVIVAL);
        p.addPotionEffect(new PotionEffect(PotionEffectType.REGENERATION, 10 * 20, 3, false, false));
        p.removePotionEffect(PotionEffectType.DARKNESS);
//        Location spawn = Ascension.getSpawn();
//        Random r = new Random();
//        double worldBorderDiameter = spawn.getWorld().getWorldBorder().getSize();
//        int x = spawn.getBlockX() + (int) (r.nextDouble(50, worldBorderDiameter) - (worldBorderDiameter/2));
//        int z = spawn.getBlockZ() + (int) (r.nextDouble(50, worldBorderDiameter) - (worldBorderDiameter/2));
//        int y = spawn.getWorld().getHighestBlockYAt(x, z);
//        p.teleport(new Location(spawn.getWorld(), x, y + 1, z));
//        spawn.getWorld().getBl
        Location loc = getRespawnLocation();
        loc.getWorld().getBlockAt(loc).setType(Material.GLASS);
        p.teleport(loc.add(0.5, 1, 0.5));


        changePlayerVisibility(playerData);
    }

    private void changePlayerVisibility(PlayerData playerData) {
        for (PlayerData pD : PlayerUtil.getAllPlayingPlayers()) {
            Player p = pD.getPlayer();
            if (playerData.checkState(PlayerData.STATE_ALIVE)) {
                p.showPlayer(plugin, playerData.getPlayer());
                TextUtil.debugText(playerData.getPlayer().getName() + " is now shown to " + p.getName());
            } else {
                p.hidePlayer(plugin, playerData.getPlayer());
                TextUtil.debugText(playerData.getPlayer().getName() + " is no longer now shown to " + p.getName());
            }
        }
    }

    private Location getRespawnLocation() {
        Location spawn = Ascension.getSpawn();
//        Bukkit.dispatchCommand(Bukkit.getConsoleSender(),
//                "spreadplayers " + spawn.getBlockX() + " " + spawn.getBlockZ() + " 200 500 true " + p.getName());
        Random r = new Random();
        double worldBorderDiameter = spawn.getWorld().getWorldBorder().getSize();
        int x = spawn.getBlockX() + (int) (r.nextDouble(50, worldBorderDiameter) - (worldBorderDiameter/2));
        int z = spawn.getBlockZ() + (int) (r.nextDouble(50, worldBorderDiameter) - (worldBorderDiameter/2));
        int y = spawn.getWorld().getHighestBlockYAt(x, z);
        return new Location(spawn.getWorld(), x, y, z);
    }

    public void setPlayerSpectating(PlayerData playerData) {
//        playerData.setPlayerState(PlayerData.STATE_SPECTATING);
        setPlayerDead(playerData, PlayerData.STATE_SPECTATING);
    }

    public void respawnEveryone() {
        for (PlayerData playerData : players) {
            respawnPlayer(playerData);
        }
    }

    public static PlayerDeath getInstance() {
        if (instance == null) {
            instance = new PlayerDeath(Ascension.getInstance());
        }
        return instance;

    }


}
