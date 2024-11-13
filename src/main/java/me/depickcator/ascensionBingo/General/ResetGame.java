package me.depickcator.ascensionBingo.General;

import me.depickcator.ascensionBingo.AscensionBingo;
import me.depickcator.ascensionBingo.Player.PlayerData;
import me.depickcator.ascensionBingo.Player.PlayerUtil;
import org.bukkit.GameRule;
import org.bukkit.World;
import org.bukkit.WorldBorder;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitScheduler;

import java.util.Objects;

public class ResetGame implements Runnable {
    AscensionBingo plugin;
    Player player;
    public ResetGame(AscensionBingo plugin, Player player) {
        this.plugin = plugin;
        this.player = player;
        plugin.getGameState().setCurrentState(GameStates.LOBBY);
        player.sendMessage("Placeholder Message For Reset Game");
        run();
    }

    @Override
    public void run() {
        BukkitScheduler scheduler =  plugin.getScheduler();
        WorldBorder border = plugin.getWorld().getWorldBorder();
        border.setSize(59999968, 0);
        resetPlayers();
        scheduler.runTaskLater(plugin, () -> {
            loadGameRules(plugin.getWorld());
            loadGameRules(plugin.getNether());
        }, 20);
    }

    private void resetPlayers() {
        plugin.getBingoData().resetPlayers();
        PlayerUtil.assignNewPlayerData(plugin);
        for (Player p : plugin.getServer().getOnlinePlayers()) {
            PlayerData pD = PlayerUtil.getPlayerData(p);
            Objects.requireNonNull(pD).resetToLobby();
        }
    }

    private void loadGameRules(World world) {
        world.setGameRule(GameRule.SPAWN_RADIUS, 0);
        world.setGameRule(GameRule.KEEP_INVENTORY, true);
        world.setGameRule(GameRule.DO_IMMEDIATE_RESPAWN, true);
        // world.setGameRule(GameRule.DO_LIMITED_CRAFTING, true); //TODO: REMOVE WHEN DONE
        world.setGameRule(GameRule.NATURAL_REGENERATION, false);
        world.setGameRule(GameRule.DO_DAYLIGHT_CYCLE, false);
        // world.setGameRule(GameRule.COMMAND_BLOCK_OUTPUT, true);
        world.setGameRule(GameRule.DO_INSOMNIA, false);
        world.setGameRule(GameRule.DO_WEATHER_CYCLE, false);
        world.setTime(1000);
    }
}
