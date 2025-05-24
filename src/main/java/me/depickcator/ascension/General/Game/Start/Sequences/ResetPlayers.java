package me.depickcator.ascension.General.Game.Start.Sequences;

import me.depickcator.ascension.General.Game.GameLauncher;
import me.depickcator.ascension.General.Game.GameSequences;
import me.depickcator.ascension.Player.Data.PlayerData;
import me.depickcator.ascension.Player.Data.PlayerUtil;
import me.depickcator.ascension.Teams.TeamUtil;
import me.depickcator.ascension.Utility.TextUtil;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;

public class ResetPlayers extends GameSequences {
    public ResetPlayers() {

    }

    @Override
    public void run(GameLauncher game) {
        plugin.getBingoData().resetPlayers();
        TeamUtil.createTeamsForEveryone();
        new BukkitRunnable() {
            ArrayList<Player> players = new ArrayList<>(plugin.getServer().getOnlinePlayers());
            @Override
            public void run() {
                if (players.isEmpty()) {
                    cancel();
                    game.callback();
                    return;
                }
                Player p = players.getFirst();
                players.remove(p);

                PlayerData pD = PlayerUtil.getPlayerData(p);
                if (pD == null) {
                    throw new NullPointerException("PlayerData is null");
                }
                pD.resetBeforeStartGame();
//                TextUtil.makeTitle(TextUtil.makeText(""), 10, 10, 10);
                TextUtil.sendActionBar(p, TextUtil.makeText("Starting Game Please Be Patient", TextUtil.RED, true, false), 30 * 20, plugin);
                pD.getPlayerScoreboard().makeGameBoard();
            }
        }.runTaskTimer(plugin, 10, 10);
    }
}
