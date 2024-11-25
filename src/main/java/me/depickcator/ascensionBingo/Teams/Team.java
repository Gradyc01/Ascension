package me.depickcator.ascensionBingo.Teams;

import me.depickcator.ascensionBingo.AscensionBingo;
import me.depickcator.ascensionBingo.General.TextUtil;
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
    private final AscensionBingo plugin;
    private final TeamStats teamStats;
    public Team(AscensionBingo plugin, Player player) {
        this.plugin = plugin;
        this.teamKey = player.getUniqueId().toString();
        teamMembers = new ArrayList<>();
        teamMembers.add(player);
        leader = player;
        TeamUtil.createTeam(player);
        TeamUtil.joinTeam(player, player);
        teamStats = new TeamStats(plugin, this);
    }

    public void addPlayer(Player p) {
        Objects.requireNonNull(PlayerUtil.getPlayerData(p)).getPlayerTeam().setTeam(this);
        teamMembers.add(p);
        TeamUtil.joinTeam(leader, p);
        announceToAllTeamMembers(p.getName() + " has joined the party!");
        updateTeamScoreboards();
    }

    public void removePlayer(Player p) {
        if (teamMembers.size() == 1 || leader.equals(p)) {
            for (Player i: teamMembers) {
                PlayerData playerData = PlayerUtil.getPlayerData(i);
                assert playerData != null;
                playerData.getPlayerTeam().setTeam(null);
            }
            TeamUtil.disbandTeam(leader);
            for (Player pl : teamMembers) {
                pl.sendMessage(TextUtil.topBorder(TextUtil.BLUE));
                pl.sendMessage(TextUtil.makeText("Party leader has left the party the party will now be disbanded", TextUtil.YELLOW));
                pl.sendMessage(TextUtil.bottomBorder(TextUtil.BLUE));
            }
            updateTeamScoreboards();
        } else {
            Objects.requireNonNull(PlayerUtil.getPlayerData(p)).getPlayerTeam().setTeam(null);
            teamMembers.remove(p);
            TeamUtil.leaveTeam(p);
            announceToAllTeamMembers(p.getName() + " has left the party");
            updateTeamScoreboards();
        }
    }

    public void teamList(Player p) {
        p.sendMessage(TextUtil.topBorder(TextUtil.BLUE));
        p.sendMessage(TextUtil.makeText("Current party members:", TextUtil.YELLOW));
        for (Player player :teamMembers) {
            p.sendMessage(TextUtil.makeText("  -" + player.getName(), TextUtil.YELLOW));
        }
        p.sendMessage(TextUtil.bottomBorder(TextUtil.BLUE));
    }

    public void announceToAllTeamMembers(String str) {
        for (Player p : teamMembers) {
            p.sendMessage(TextUtil.topBorder(TextUtil.BLUE));
            p.sendMessage(TextUtil.makeText(str, TextUtil.YELLOW));
            p.sendMessage(TextUtil.bottomBorder(TextUtil.BLUE));
            teamList(p);
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

    private void updateTeamScoreboards() {
        for (Player p : teamMembers) {
            PlayerData playerData = PlayerUtil.getPlayerData(p);
            if (playerData == null) {
                plugin.getServer().broadcast(TextUtil.makeText("ERROR updateTeamScoreboards", TextUtil.RED));
                continue;
            }
            playerData.getPlayerScoreboard().updateLobbyBoard();
        }
    }

    public TeamStats getTeamStats() {
        return teamStats;
    }
}
