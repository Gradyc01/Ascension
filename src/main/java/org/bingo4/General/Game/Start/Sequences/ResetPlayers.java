package org.bingo4.General.Game.Start.Sequences;

import org.bingo4.General.Game.GameLauncher;
import org.bingo4.General.Game.GameSequences;
import org.bingo4.Player.Data.PlayerData;
import org.bingo4.Player.Data.PlayerUtil;
import org.bingo4.Teams.TeamUtil;
import org.bingo4.Utility.TextUtil;
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
                TextUtil.sendActionBar(p, TextUtil.makeText("Starting Game Please Be Patient", TextUtil.RED, true, false), 30 * 20);
                pD.getPlayerScoreboard().makeGameBoard();
            }
        }.runTaskTimer(plugin, 10, 10);
    }
}
