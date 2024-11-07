package me.depickcator.ascensionBingo.Teams;

import me.depickcator.ascensionBingo.AscensionBingo;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class TeamUtil {
    public static void createTeam(Player p) {
        String teamName = p.getName();
        Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "team add " + teamName);
    }

    public static void joinTeam(Player team, Player p) {
        Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "team join " + team.getName() + " " + p.getName());
    }

    public static void leaveTeam(Player p) {
        Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "team leave " + p.getName());
    }

    public static void disbandTeam(Player team) {
        Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "team remove " + team.getName());
    }
}
