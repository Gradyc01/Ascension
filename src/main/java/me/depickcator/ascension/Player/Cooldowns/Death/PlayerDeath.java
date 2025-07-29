package me.depickcator.ascension.Player.Cooldowns.Death;

import me.depickcator.ascension.Ascension;
import me.depickcator.ascension.General.Game.GameStates;
import me.depickcator.ascension.Teams.Team;
import me.depickcator.ascension.Utility.TextUtil;
import me.depickcator.ascension.Player.Data.PlayerData;
import me.depickcator.ascension.Player.Data.PlayerUtil;
import net.kyori.adventure.text.Component;
import org.bukkit.*;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
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
            Team team = playerData.getPlayerTeam().getTeam();
            if (team.getTeamStats().getGameScore() <= 0 && !gameState.checkState(GameStates.GAME_BEFORE_GRACE)) {
                setPlayerDead(playerData, PlayerData.STATE_DEAD);
                Player p = playerData.getPlayer();
                p.sendMessage(TextUtil.makeText("Your team is still alive! " +
                        "If they become more enlightened you are able to be respawned", TextUtil.RED));
                playerData.getPlayerTeam().getTeam().updateState();
            } else if (gameState.checkState(GameStates.GAME_FINAL_ASCENSION)) {
                setPlayerSpectating(playerData);
            } else {
                if (!gameState.checkState(GameStates.GAME_BEFORE_GRACE)) team.getTeamStats().addGameScore(-1);
                setRespawningLater(playerData);
            }
        }
    }

    public void setPlayerVaporized(PlayerData playerData) {
        setPlayerDead(playerData, PlayerData.STATE_DEAD);
        TextUtil.broadcastMessage(TextUtil.makeText(playerData.getPlayer().getName() + " has been vaporized", TextUtil.DARK_RED));
        playerData.getPlayerTeam().getTeam().updateState();
    }

    /*Sets Player playerData to the spectating state*/
    public void setPlayerSpectating(PlayerData playerData) {
        setPlayerDead(playerData, PlayerData.STATE_SPECTATING);
        if (playerData.getPlayerTeam().getTeam() != null) {
            playerData.getPlayerTeam().getTeam().updateState();
            Player p = playerData.getPlayer();
            p.playSound(p.getLocation(), Sound.ENTITY_WITHER_DEATH, 100f, 1f);
            p.sendMessage(TextUtil.makeText("You have been eliminated, but your team is still alive!", TextUtil.RED));
        }
    }

    private void setRespawningLater(PlayerData playerData) {
        Player p = playerData.getPlayer();
        setPlayerDead(playerData, PlayerData.STATE_RESPAWNING);
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
                for (PlayerData playerData : new ArrayList<>(players)) {
                    Player p = playerData.getPlayer();
                    if (!deathTimer.isOnCooldown(p, false) || !plugin.getGameState().inGame()) {
                        respawnPlayer(playerData);
                        continue;
                    }
                    teleportBackToDeathLocation(p);
                    Component text = TextUtil.makeText("Respawning In: " + (int) deathTimer.getCooldownTimer(p)  + "s", TextUtil.AQUA);
                    TextUtil.sendActionBar(p, text, 24);
                }
            }

        }.runTaskTimer(plugin, 0, 20);
    }

    private void teleportBackToDeathLocation(Player p) {
        Location loc = p.getLocation();
        Location deathLoc = p.getLastDeathLocation();
//        double euclidean = Math.pow(Math.pow(loc.getBlockX() - deathLoc.getBlockX(), 2) +
//                Math.pow(loc.getY() - deathLoc.getY(), 2) + Math.pow(loc.getBlockZ() - deathLoc.getBlockZ(), 2), 0.5);
        if (loc.distance(deathLoc) > 30) {
            p.teleport(p.getLastDeathLocation());
        }
    }

    private void setPlayerDead(PlayerData playerData, int newPlayerState) {
        playerData.setPlayerState(newPlayerState);
        Player p = playerData.getPlayer();
        PlayerUtil.clearEffects(playerData);
        p.setLastDeathLocation(p.getLocation());
        if (!p.getGameMode().isInvulnerable()) strikeLightning(p);
        p.setGameMode(GameMode.SPECTATOR);
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
        Player p = playerData.getPlayer();
        p.setGameMode(GameMode.SURVIVAL);
        p.addPotionEffect(new PotionEffect(PotionEffectType.REGENERATION, 10 * 20, 3, false, false));
        p.removePotionEffect(PotionEffectType.DARKNESS);
        if (!playerData.checkState(PlayerData.STATE_ALIVE)) {
            Location loc = !plugin.getGameState().checkState(GameStates.GAME_BEFORE_GRACE) ? getRespawnLocation() : p.getLastDeathLocation();
            loc.getWorld().getBlockAt(loc).setType(Material.GLASS);
            p.teleport(loc.add(0.5, 1, 0.5));
        }
        playerData.setPlayerState(PlayerData.STATE_ALIVE);
        playerData.freezePlayer(plugin.getGameState().checkState(GameStates.GAME_PAUSED));
        changePlayerVisibility(playerData);
    }

    private void changePlayerVisibility(PlayerData playerData) {
        //Changed from PlayerUtil.getAllPlayingPlayers()
        for (Player p : Bukkit.getOnlinePlayers()) {
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
        String debugString = "Attempting to get respawn location...  ";
        WorldBorder border = plugin.getWorld().getWorldBorder();
        Location spawn = border.getCenter();
        Random r = new Random();
        double worldBorderDiameter = border.getSize() - 100 < 100 ? border.getSize() : border.getSize() - 100;;
        if (plugin.getGameState().checkState(GameStates.GAME_ASCENSION)) {
            spawn = plugin.getSettingsUI().getSettings().getTimeline().getAscensionEvent().getAscendingLocation().getSpawnLocation();
            worldBorderDiameter = border.getSize() - 25 < 25 ? border.getSize() : border.getSize() - 25;
        }
        int bX = r.nextBoolean() ? 1 : - 1;
        int bZ = r.nextBoolean() ? 1 : - 1;
        int x = spawn.getBlockX()  + (int) (r.nextDouble(50, worldBorderDiameter/2) * bX);
        int z = spawn.getBlockZ()  + (int) (r.nextDouble(50, worldBorderDiameter/2) * bZ);
        int y = spawn.getWorld().getHighestBlockYAt(x, z);
        Location spawnLocation = new Location(spawn.getWorld(), x, y, z);
        debugString += "trying coords (" + x + "," + y + "," + z + ")...  ";
        if (border.isInside(spawnLocation)) {
            TextUtil.debugText(debugString + " SUCCESS!");
            return spawnLocation;
        } else {
            TextUtil.debugText(debugString + " Not in border. Retrying...");
            return getRespawnLocation();
        }
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
