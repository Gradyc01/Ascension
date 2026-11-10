package org.bingo4.General.Game.Reset.Sequences;

import com.lunarclient.apollo.Apollo;
import com.lunarclient.apollo.module.waypoint.WaypointModule;
import com.lunarclient.apollo.player.ApolloPlayer;
import org.bingo4.General.Game.GameLauncher;
import org.bingo4.General.Game.GameSequences;
import org.bingo4.Player.Cooldowns.Death.PlayerDeath;
import org.bingo4.Player.Data.PlayerData;
import org.bingo4.Player.Data.PlayerUtil;
import org.bingo4.Utility.TextUtil;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.Optional;

public class ResetPlayers extends GameSequences {
    @Override
    public void run(GameLauncher game) {
        resetPlayers();

        game.callback();
    }

    private void resetPlayers() {
        PlayerUtil.clearPlayerDataMap();
        new BukkitRunnable() {
            ArrayList<Player> players = new ArrayList<>(plugin.getServer().getOnlinePlayers());
            @Override
            public void run() {
                if (players.isEmpty()) {
                    cancel();
                    PlayerUtil.updateTabList();
                    return;
                }
                Player p = players.getFirst();
                PlayerData pD = PlayerUtil.assignNewPlayerData(p);
                PlayerDeath.getInstance().respawnPlayer(pD);
                pD.resetToLobby();
                players.remove(p);
                try {
                    resetLunarWaypoints(p);
                } catch (Exception ignored) {
                    TextUtil.debugText("Resetting Lunar Waypoints failed moving on");
                }
                TextUtil.debugText("Player " + p.getName() + " reset");
            }
        }.runTaskTimer(plugin, 20, 10);

    }

    private void resetLunarWaypoints(Player p) {
        WaypointModule waypointModule = Apollo.getModuleManager().getModule(WaypointModule.class);
        Optional<ApolloPlayer> apolloPlayerOpt = Apollo.getPlayerManager().getPlayer(p.getUniqueId());
        apolloPlayerOpt.ifPresent(waypointModule::resetWaypoints);
    }
}
