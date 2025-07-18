package me.depickcator.ascension.Timeline;

import me.depickcator.ascension.Ascension;
import me.depickcator.ascension.General.Game.GameStates;
import me.depickcator.ascension.Player.Data.PlayerData;
import me.depickcator.ascension.Player.Data.PlayerUtil;
import me.depickcator.ascension.Utility.TextUtil;
import org.bukkit.Location;
import org.bukkit.damage.DamageSource;
import org.bukkit.damage.DamageType;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

import java.util.ArrayList;
import java.util.List;

public class PeriodicChecks {
    private BukkitTask periodicTasks;
    private final Ascension plugin;
    private final int heightLimit = 75;
    public PeriodicChecks() {
        plugin = Ascension.getInstance();
    }

    public void start() {
        startPeriodChecks();
    }

    public void stop() {
        if (periodicTasks != null) periodicTasks.cancel();
    }

    private void startPeriodChecks() {
        if (periodicTasks != null) periodicTasks.cancel();
        int delay = switch (plugin.getGameState().getCurrentState()) {
            case GameStates.GAME_ASCENSION, GameStates.GAME_FINAL_ASCENSION -> 30;
            default -> 60;
        };
        periodicTasks = new BukkitRunnable() {
            @Override
            public void run() {
                TextUtil.debugText("Running Periodic Checks...");
                List<PlayerData> players = new ArrayList<>(PlayerUtil.getAllPlayingPlayers());
                new BukkitRunnable() {
                    @Override
                    public void run() {
                        if (players.isEmpty()) {
                            cancel();
                            startPeriodChecks();
                            return;
                        }

                        PlayerData pD = players.getFirst();
                        if (pD.checkState(PlayerData.STATE_ALIVE)) checkPlayerHeight(pD, false);
                        players.remove(pD);
                    }
                }.runTaskTimer(plugin, 0, 10);

            }
        }.runTaskLater(plugin,  delay * 20);
    }

    private void checkPlayerHeight(PlayerData playerData, boolean warned) {
        Player player = playerData.getPlayer();
        TextUtil.debugText("Checking Player Height " +  player.getName());
        Location loc = player.getLocation();
        int maxHeight = Integer.min(loc.getWorld().getHighestBlockAt(loc).getLocation().getBlockY() + heightLimit, 300);
        int delay = 1;
        if (player.getLocation().getY() > maxHeight && playerData.checkState(PlayerData.STATE_ALIVE)) {
            if (warned) {
                player.damage(2, DamageSource.builder(DamageType.OUT_OF_WORLD).build());
                TextUtil.sendActionBar(player,
                        TextUtil.makeText("You are over the height limit!", TextUtil.RED),
                        20);
            } else {
                player.sendMessage(TextUtil.makeText("You are over the height limit, you start taking damage in 15 seconds", TextUtil.RED));
                delay = 15;
            }
            new BukkitRunnable() {
                @Override
                public void run() {
                    checkPlayerHeight(playerData, true);
                }
            }.runTaskLater(plugin, delay * 20);
        } else if (warned) {
            player.sendMessage(TextUtil.makeText("You are now below the height limit", TextUtil.GREEN));
        }

    }

}
