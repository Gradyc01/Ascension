package me.depickcator.ascension.General.GameStart.Sequences;

import me.depickcator.ascension.General.GameStart.GameStartSequence;
import me.depickcator.ascension.General.GameStart.StartGame;
import me.depickcator.ascension.Teams.Team;
import me.depickcator.ascension.Teams.TeamUtil;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.List;

public class AdjustStartingGameScore extends GameStartSequence {
    private final int gameScoreNum;
    public AdjustStartingGameScore(int gameScoreNum) {
        this.gameScoreNum = gameScoreNum;
    }

    @Override
    public void run(StartGame game) {
        new BukkitRunnable() {
            List<Team> teams = TeamUtil.getEveryTeam();


            @Override
            public void run() {
                if (teams.isEmpty()) {
                    cancel();
                    game.callback();
                    return;
                }

                Team team = teams.getFirst();
                team.getTeamStats().addGameScore(gameScoreNum);


                teams.removeFirst();


            }
        }.runTaskTimer(plugin, 10, 10);
    }
}
