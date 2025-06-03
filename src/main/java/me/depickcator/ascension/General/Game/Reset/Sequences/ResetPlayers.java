package me.depickcator.ascension.General.Game.Reset.Sequences;

import com.lunarclient.apollo.Apollo;
import com.lunarclient.apollo.module.waypoint.WaypointModule;
import com.lunarclient.apollo.player.ApolloPlayer;
import me.depickcator.ascension.General.Game.GameLauncher;
import me.depickcator.ascension.General.Game.GameSequences;
import me.depickcator.ascension.Player.Cooldowns.Death.PlayerDeath;
import me.depickcator.ascension.Player.Data.PlayerData;
import me.depickcator.ascension.Player.Data.PlayerUtil;
import me.depickcator.ascension.Utility.TextUtil;
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
