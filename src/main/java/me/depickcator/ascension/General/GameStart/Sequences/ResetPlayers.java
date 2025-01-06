package me.depickcator.ascension.General.GameStart.Sequences;

import me.depickcator.ascension.General.GameStart.GameStartSequence;
import me.depickcator.ascension.General.GameStart.StartGame;
import me.depickcator.ascension.Player.Data.PlayerData;
import me.depickcator.ascension.Player.Data.PlayerUtil;
import me.depickcator.ascension.Teams.TeamUtil;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;

public class ResetPlayers extends GameStartSequence {
    public ResetPlayers() {

    }

    @Override
    public void run(StartGame game) {
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
                pD.getPlayerScoreboard().makeGameBoard();
            }
        }.runTaskTimer(plugin, 10, 10);
    }
}
