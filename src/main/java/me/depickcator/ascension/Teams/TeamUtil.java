package me.depickcator.ascension.Teams;

import me.depickcator.ascension.Player.Data.PlayerData;
import me.depickcator.ascension.Player.Data.PlayerUtil;
import me.depickcator.ascension.Utility.TextUtil;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class TeamUtil {
    public static void createTeam(Player p) {
        String teamName = p.getName();
        Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "team add " + teamName);

    }

    public static void joinTeam(Player team, Player p) {
        Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "team modify " + team.getName() + " friendlyFire false");
        Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "team join " + team.getName() + " " + p.getName());
    }

    public static void leaveTeam(Player p) {
        Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "team leave " + p.getName());
    }

    public static void disbandTeam(Player team) {
        Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "team remove " + team.getName());
    }

    public static void createTeamsForEveryone() {
        for (Player p : Bukkit.getOnlinePlayers()) {
            PlayerData playerData = PlayerUtil.getPlayerData(p);
            if (playerData.getPlayerTeam().getTeam() == null) {
                TextUtil.debugText("Created a brand new team for p");
                playerData.getPlayerTeam().createOrGetTeam();
            }
        }
    }

    public static List<Team> getEveryTeam() {
        return getEveryTeam(false);
    }

    public static List<Team> getEveryTeam(boolean activeOnly) {
        List<PlayerData> allPlayingPlayers = PlayerUtil.getAllPlayingPlayers();
        List<Team> allTeams = new ArrayList<>();
        for (PlayerData pD : allPlayingPlayers) {
            Team playerTeam = pD.getPlayerTeam().getTeam();
            if (playerTeam == null) continue;
            if (!allTeams.contains(playerTeam)) {
                if (playerTeam.checkSTATE(Team.STATE_ACTIVE) || !activeOnly) {
                    allTeams.add(playerTeam);
                }
            }
        }
        return allTeams;
    }




}
