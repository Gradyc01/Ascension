package me.depickcator.ascension.Player.Data;

import me.depickcator.ascension.Ascension;
import me.depickcator.ascension.General.TextUtil;
import me.depickcator.ascension.Teams.Team;
import org.bukkit.entity.Player;

import java.util.Objects;

public class PlayerTeam {
    private final Ascension plugin;
    private final PlayerData playerData;
    private final Player player;

    //TEAMS
    private Boolean pendingTeamInvite;
    private PlayerData inviteFromWho;
    private Team team;
    public PlayerTeam(Ascension plugin, PlayerData PlayerData) {
        this.plugin = plugin;
        this.playerData = PlayerData;
        this.player = playerData.getPlayer();
        pendingTeamInvite = false;
        team = null;
    }

    public void sendInvite(Player p) {

        team = createOrGetTeam();
        PlayerTeam invitedPlayerTeamData = Objects.requireNonNull(PlayerUtil.getPlayerData(p)).getPlayerTeam();
        if (invitedPlayerTeamData == null) {
            throw new NullPointerException("PlayerData is null!");
        }
        if (invitedPlayerTeamData.getPendingTeamInvite()) {
            errorMessage(p.getName() + " already has a team invite from somewhere else");
            return;
        }
        invitedPlayerTeamData.setInviteFromWho(playerData);
        invitedPlayerTeamData.setPendingTeamInvite(true);
        sendingInviteText(p);
        receivingInviteText(p);
    }

    public void acceptInvite() {
        if (team != null) {
            errorMessage("Already on a team must leave before joining another team!");
            return;
        }
        if (getPendingTeamInvite()) {
            PlayerData sender = getInviteFromWho();
            pendingTeamInvite = false;
            inviteFromWho = null;
            try {
                sender.getPlayerTeam().getTeam().addPlayer(player);
            } catch (NullPointerException e) {
                errorMessage("Party no longer exists");
                pendingTeamInvite = false;
                inviteFromWho = null;
            }
        }
    }

    public void rejectInvite() {
        if (getPendingTeamInvite()) {
            PlayerData sender = getInviteFromWho();
            sender.getPlayerTeam().getTeam().announceToAllTeamMembers(player.getName() + " has rejected your party invite");
            pendingTeamInvite = false;
            inviteFromWho = null;
        } else {
            errorMessage("You do not have an invite from anyone");
        }
    }

    public void leaveTeam() {
        if (team == null) {
            errorMessage("You must be on a team to be able to leave the team");
            return;
        }
        leaveTeamMessage();
        team.removePlayer(player);
        playerData.getPlayerScoreboard().update();
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

    private void sendingInviteText(Player invitedPlayer) {
        for (Player p : getTeam().getTeamMembers()) {
            p.sendMessage(TextUtil.topBorder(TextUtil.BLUE));
            p.sendMessage(TextUtil.makeText(player.getName() + " has invited " + invitedPlayer.getName() + " to the party!", TextUtil.YELLOW));
            p.sendMessage(TextUtil.bottomBorder(TextUtil.BLUE));
        }
    }

    private void receivingInviteText(Player invitedPlayer) {
        invitedPlayer.sendMessage(TextUtil.topBorder(TextUtil.BLUE));
        invitedPlayer.sendMessage(TextUtil.makeText(player.getName() + " has invited you to their party!", TextUtil.YELLOW));
        invitedPlayer.sendMessage(TextUtil.makeText("Accept by typing /party accept or party reject", TextUtil.YELLOW));
        invitedPlayer.sendMessage(TextUtil.bottomBorder(TextUtil.BLUE));
    }

    private void leaveTeamMessage() {
        player.sendMessage(TextUtil.topBorder(TextUtil.BLUE));
        player.sendMessage(TextUtil.makeText("You have left the party", TextUtil.YELLOW));
        player.sendMessage(TextUtil.bottomBorder(TextUtil.BLUE));
    }

    private void errorMessage(String message) {
        player.sendMessage(TextUtil.topBorder(TextUtil.BLUE));
        player.sendMessage(TextUtil.makeText(message, TextUtil.RED));
        player.sendMessage(TextUtil.bottomBorder(TextUtil.BLUE));
    }
}
