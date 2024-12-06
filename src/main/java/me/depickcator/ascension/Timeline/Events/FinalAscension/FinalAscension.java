package me.depickcator.ascension.Timeline.Events.FinalAscension;

import me.depickcator.ascension.Ascension;
import me.depickcator.ascension.General.GameStates;
import me.depickcator.ascension.General.TextUtil;
import me.depickcator.ascension.Player.Cooldowns.Death.PlayerDeath;
import me.depickcator.ascension.Player.PlayerData;
import me.depickcator.ascension.Player.PlayerUtil;
import me.depickcator.ascension.Teams.Team;
import me.depickcator.ascension.Teams.TeamStats;
import me.depickcator.ascension.Teams.TeamUtil;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.List;

public class FinalAscension {
    private final Ascension plugin;
    public FinalAscension(){
        this.plugin = Ascension.getInstance();
        setGameState();
        calculateTeamTimers();
    }

    private void setGameState() {
        GameStates gameState = plugin.getGameState();
        gameState.setCurrentState(GameStates.GAME_FINAL_ASCENSION);
    }

    private void calculateTeamTimers() {
        List<Team> allTeams = TeamUtil.getEveryTeam();
        for (Team t : allTeams) {
            TeamStats teamStats = t.getTeamStats();
            int time = calculateTimer(teamStats.getGameScore());
            teamStats.setFinalAscensionTimer(time);
            TextUtil.debugText("Team Score: " + teamStats.getFinalAscensionTimer());
        }
    }

    private void timer() {
        new BukkitRunnable() {
            final List<Team> activeTeams = TeamUtil.getEveryTeam(true);
            @Override
            public void run() {
                for (Team t : activeTeams) {
                    if (activeTeams.size() <= 1) {
                        cancel();
                        //DECLARE WINNER?
                        return;
                    }
                    if (t.checkSTATE(Team.STATE_DEPRECATED)) {
                        activeTeams.remove(t);
                        continue;
                    }
                    TeamStats teamStats = t.getTeamStats();
                    if (teamStats.getFinalAscensionTimer() <= 0) {
                        timerExpired(t);
                        continue;
                    }
                    teamStats.addFinalAscensionTimer(-1);
                }
            }

        }.runTaskTimer(plugin, 0, 20);
    }

    //Effects after a teams timer expired
    private void timerExpired(Team team) {
        List<Player> players = team.getTeamMembers();
        for (Player p : players) {
            PlayerData pD = PlayerUtil.getPlayerData(p);
            PlayerDeath.getInstance().playerDied(pD);
        }
    }

    //Calculates the ascension timer
    private int calculateTimer(int gameScore) {
        int defaultTimer = 300;
        int multiplier = 30;
        return defaultTimer + gameScore * multiplier;
    }

    //updates the scoreboard
    private void updateScoreboard(Team team) {
    }

}

