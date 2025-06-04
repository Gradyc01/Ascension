package me.depickcator.ascension.General.Game.Reset.Sequences;

import me.depickcator.ascension.General.Game.GameLauncher;
import me.depickcator.ascension.General.Game.GameSequences;
import me.depickcator.ascension.Persistence.PlayerDataWriter;
import me.depickcator.ascension.Player.Data.PlayerData;
import me.depickcator.ascension.Player.Data.PlayerUtil;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.List;

public class SaveGameStatistics extends GameSequences {
    @Override
    public void run(GameLauncher game) {
        savePlayerStatistics();
        game.callback();
    }

    private void savePlayerStatistics() {
        new BukkitRunnable() {
            List<Player> players = new ArrayList<>(plugin.getServer().getOnlinePlayers());
            @Override
            public void run() {
                if (players.isEmpty()) {
                    cancel();
                    return;
                }
                Player p = players.getFirst();
                PlayerData pD = PlayerUtil.getPlayerData(p);
                if (pD != null && pD.getPlayerStats() != null && pD.getPlayerTeam().getTeam() != null) {
                    new PlayerDataWriter(pD).writeNewData();
                }

                players.remove(p);
            }
        }.runTaskTimer(plugin, 0, 5);

    }
}
