package me.depickcator.ascensionBingo.Player;

import me.depickcator.ascensionBingo.AscensionBingo;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.ScoreboardManager;
import me.depickcator.ascensionBingo.Teams.Team;

public class PlayerData {
    private Player player;
    private AscensionBingo plugin;

    //TEAMS
    private Boolean pendingTeamInvite;

    private PlayerData inviteFromWho;
    private Team team;
    public PlayerData(Player player, AscensionBingo plugin) {
        this.player = player;
        this.plugin = plugin;
        pendingTeamInvite = false;
        team = null;
    }

    public void sendInvite(Player p) {

        team = createOrGetTeam();
        PlayerData playerData = PlayerUtil.getPlayerData(p);
        if (playerData == null) {
            throw new NullPointerException("PlayerData is null!");
        }
        if (playerData.getPendingTeamInvite()) {
            player.sendMessage(p.getName() + " already has a team invite from somewhere else");
            return;
        }
        playerData.setInviteFromWho(this);
        playerData.setPendingTeamInvite(true);
        p.sendMessage("You have an invite!");
    }

    public void acceptInvite() {
        if (team != null) {
            player.sendMessage("Already on a team must leave before joining another team!");
            return;
        }
        if (getPendingTeamInvite()) {
            PlayerData sender = getInviteFromWho();
            pendingTeamInvite = false;
            inviteFromWho = null;
            try {
                sender.getTeam().addPlayer(player);
            } catch (NullPointerException e) {
                player.sendMessage("Party no longer exists");
                pendingTeamInvite = false;
                inviteFromWho = null;
            }

        }
    }

    public void rejectInvite() {
        if (getPendingTeamInvite()) {
            PlayerData sender = getInviteFromWho();
            sender.getTeam().announceToAllTeamMembers(player.getName() + " has rejected your party invite");
            pendingTeamInvite = false;
            inviteFromWho = null;
        } else {
            player.sendMessage("You do not have an invite from anyone");
        }
    }

    public void leaveTeam() {
        if (team == null) {
            player.sendMessage("You must be on a team to be able to leave the team");
            return;
        }
        team.removePlayer(player);
    }

    public Team createOrGetTeam() {
        if (team != null) {
            return team;
        }
        team = new Team(plugin, player);
        return team;
    }

    public Team getTeam() {
        return team;
    }

    public void setTeam(Team t) {
        team = t;
    }

    public Boolean getPendingTeamInvite() {
        return pendingTeamInvite;
    }

    public void setPendingTeamInvite(Boolean teamInvite) {
        pendingTeamInvite = teamInvite;
    }

    public PlayerData getInviteFromWho() {
        return inviteFromWho;
    }

    public void setInviteFromWho(PlayerData inviteFromWho) {
        this.inviteFromWho = inviteFromWho;
    }

    public Player getPlayer() {
        return player;
    }
}
