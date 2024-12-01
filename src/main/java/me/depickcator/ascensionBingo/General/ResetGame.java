package me.depickcator.ascensionBingo.General;

import me.depickcator.ascensionBingo.AscensionBingo;
import me.depickcator.ascensionBingo.Player.Cooldowns.Death.PlayerDeath;
import me.depickcator.ascensionBingo.Player.PlayerData;
import me.depickcator.ascensionBingo.Player.PlayerUtil;
import org.bukkit.GameRule;
import org.bukkit.World;
import org.bukkit.WorldBorder;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitScheduler;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public class ResetGame implements Runnable {
    AscensionBingo plugin;
    Player player;
    public ResetGame(AscensionBingo plugin, Player player) {
        this.plugin = plugin;
        this.player = player;
        plugin.getGameState().setCurrentState(GameStates.LOBBY);
        run();
    }

    @Override
    public void run() {
        BukkitScheduler scheduler =  plugin.getScheduler();
        WorldBorder border = plugin.getWorld().getWorldBorder();
        border.setSize(59999968, 0);
        resetPlayers();
        plugin.getTimeline().resetTimeline();
        scheduler.runTaskLater(plugin, () -> {
            loadGameRules(plugin.getWorld());
            loadGameRules(plugin.getNether());
        }, 20);
    }

    private void resetPlayers() {
        plugin.getBingoData().resetPlayers();
        PlayerUtil.clearPlayerDataMap();
//        PlayerUtil.assignNewPlayerData(plugin);
//        for (Player p : plugin.getServer().getOnlinePlayers()) {
//
//        }

        new BukkitRunnable() {
            ArrayList<Player> players = new ArrayList<>(plugin.getServer().getOnlinePlayers());
            @Override
            public void run() {
                if (players.isEmpty()) {
                    cancel();
                    return;
                }
                Player p = players.getFirst();
                PlayerData pD = PlayerUtil.assignNewPlayerData(p, plugin);
//                PlayerDeath.getInstance(plugin).respawnPlayer(pD);
                pD.resetToLobby();
                players.remove(p);
                plugin.getServer().broadcast(TextUtil.makeText("[Debug] Player " + p.getName() + " reset"));
            }
        }.runTaskTimer(plugin, 20, 10);

    }

    private void loadGameRules(World world) {
        world.setGameRule(GameRule.SPAWN_RADIUS, 0);
        world.setGameRule(GameRule.KEEP_INVENTORY, true);
        world.setGameRule(GameRule.DO_IMMEDIATE_RESPAWN, true);
        world.setGameRule(GameRule.NATURAL_REGENERATION, false);
        world.setGameRule(GameRule.DO_DAYLIGHT_CYCLE, false);
        // world.setGameRule(GameRule.COMMAND_BLOCK_OUTPUT, true);
        world.setGameRule(GameRule.DO_INSOMNIA, false);
        world.setGameRule(GameRule.DO_WEATHER_CYCLE, false);
        world.setTime(1000);
    }
}
