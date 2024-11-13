package me.depickcator.ascensionBingo.Teams;

import me.depickcator.ascensionBingo.AscensionBingo;
import me.depickcator.ascensionBingo.Player.PlayerData;
import me.depickcator.ascensionBingo.Player.PlayerUtil;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.Objects;

public class Team {
    private String name;
    private String teamKey;
    private ArrayList<Player> teamMembers;
    private Player leader;
    AscensionBingo plugin;
    public Team(AscensionBingo plugin, Player player) {
        this.plugin = plugin;
        this.teamKey = player.getUniqueId().toString();
        teamMembers = new ArrayList<>();
        teamMembers.add(player);
        leader = player;
        TeamUtil.createTeam(player);
        TeamUtil.joinTeam(player, player);
    }

    public void addPlayer(Player p) {
        Objects.requireNonNull(PlayerUtil.getPlayerData(p)).setTeam(this);
        teamMembers.add(p);
        TeamUtil.joinTeam(leader, p);
        announceToAllTeamMembers(p.getName() + " has joined the party!");
    }

    public void removePlayer(Player p) {
        announceToAllTeamMembers(p.getName() + " has left the party");
        if (teamMembers.size() == 1 || leader.equals(p)) {
            announceToAllTeamMembers("Party leader has left the party the party will now be disbanded");
            for (Player i: teamMembers) {
                PlayerData playerData = PlayerUtil.getPlayerData(i);
                assert playerData != null;
                playerData.setTeam(null);
            }
            TeamUtil.disbandTeam(leader);
        } else {
            Objects.requireNonNull(PlayerUtil.getPlayerData(p)).setTeam(null);
            teamMembers.remove(p);
            TeamUtil.leaveTeam(p);
        }
    }

    public void teamList(Player p) {
        p.sendMessage("===================================================\n\n");
        p.sendMessage("Current party members:");
        for (Player player :teamMembers) {
            p.sendMessage("  -" + player.getName());
        }
        p.sendMessage("\n\n===================================================");
    }

    public void announceToAllTeamMembers(String str) {
        for (Player p : teamMembers) {
            p.sendMessage("===================================================\n\n");
            p.sendMessage(str);
            p.sendMessage("\n\n===================================================");
        }
    }

    public ArrayList<Player> getTeamMembers() {
        return teamMembers;
    }

    public String getTeamKey() {
        return teamKey;
    }

    public ArrayList<Player> getOtherTeamMembers(Player p) {
        ArrayList<Player> otherTeamMembers = new ArrayList<>(teamMembers);
        otherTeamMembers.remove(p);
        return otherTeamMembers;
    }
}
