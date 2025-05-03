package me.depickcator.ascension.Player.Data;

import me.depickcator.ascension.Ascension;
import me.depickcator.ascension.Teams.TeamUtil;
import me.depickcator.ascension.Utility.TextUtil;
import me.depickcator.ascension.Teams.Team;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.event.ClickEvent;
import net.kyori.adventure.text.event.HoverEvent;
import net.kyori.adventure.text.format.TextColor;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class PlayerTeam implements PlayerDataObservers {
    private final Ascension plugin;
    private final PlayerData playerData;
    private Player player;

    //TEAMS
//    private Boolean pendingTeamInvite;
    private List<PlayerData> inviteFromWho;
    private Team team;
    public PlayerTeam(Ascension plugin, PlayerData PlayerData) {
        this.plugin = plugin;
        this.playerData = PlayerData;
        this.player = playerData.getPlayer();
//        pendingTeamInvite = false;
        inviteFromWho = new ArrayList<>();
        team = null;
    }

    /*Sends a party invite to Player p from this Player*/
    public void sendInvite(Player p) {
        team = createOrGetTeam();
        PlayerTeam invitedPlayerTeamData = Objects.requireNonNull(PlayerUtil.getPlayerData(p)).getPlayerTeam();
        if (invitedPlayerTeamData == null) {
            throw new NullPointerException("PlayerData is null!");
        }
//        if (invitedPlayerTeamData.getPendingTeamInvite()) {
//            errorMessage(p.getName() + " already has a team invite from somewhere else");
//            return;
//        }
        invitedPlayerTeamData.addInviteFromWho(playerData);
//        invitedPlayerTeamData.setPendingTeamInvite(true);
        sendingInviteText(p);
        receivingInviteText(p);
    }

    /*Player accepts the current incoming invite*/
    public void acceptInvite(PlayerData sender) {
        if (team != null) {
            errorMessage("Already on a team must leave before joining another team!");
            return;
        }
        if (hasInviteFrom(sender)) {
            try {
                if (!sender.getPlayerTeam().getTeam().addPlayer(playerData)) {
                    errorMessage("Unable to accept the invite, Party may be full");
                }
                removeInviteFromWho(sender);
            } catch (NullPointerException e) {
                errorMessage("Party no longer exists");
                removeInviteFromWho(sender);
            }
        }
    }


    /*Player rejects the incoming invite*/
    public void rejectInvite(PlayerData sender) {
        if (hasInviteFrom(sender)) {
            sender.getPlayerTeam().getTeam().announceToAllTeamMembers(player.getName() + " has rejected your party invite");
            removeInviteFromWho(sender);
        }
    }

    /*Player leaves the current party*/
    public void leaveTeam() {
        if (team == null) {
            errorMessage("You must be on a team to be able to leave the team");
            return;
        }
        leaveTeamMessage();
        team.removePlayer(playerData);
        playerData.getPlayerScoreboard().update();
    }

    /*Creates or gets the current team for this Player */
    public Team createOrGetTeam() {
        if (team != null) {
            return team;
        }
        team = new Team(playerData);
        return team;
    }

    public Team getTeam() {
        return team;
    }

    public void setTeam(Team t) {
        team = t;
    }

//    public Boolean getPendingTeamInvite() {
//        return pendingTeamInvite;
//    }

//    public void setPendingTeamInvite(Boolean teamInvite) {
//        pendingTeamInvite = teamInvite;
//    }

    public boolean hasInviteFrom(PlayerData teamLeader) {
        if (this.inviteFromWho.contains(teamLeader)) {
            return true;
        }
        errorMessage("You don't have an invite to this Party");
        return false;
    }

    /*Adds a invite from inviteFromWho for this player*/
    public void addInviteFromWho(PlayerData inviteFromWho) {
        this.inviteFromWho.add(inviteFromWho);
    }

    /*Removes an invite from inviteFromWho for this player*/
    public void removeInviteFromWho(PlayerData inviteFromWho) {
        this.inviteFromWho.remove(inviteFromWho);
    }

    private void sendingInviteText(Player invitedPlayer) {
        Component text = TextUtil.topBorder(TextUtil.BLUE)
                .append(TextUtil.makeText("\n" + player.getName() + " has invited " + invitedPlayer.getName() + " to the party!\n", TextUtil.YELLOW))
                .append(TextUtil.bottomBorder(TextUtil.BLUE));
        TextUtil.broadcastMessage(text, getTeam().getTeamMembers());
    }

    private void receivingInviteText(Player invitedPlayer) {
        invitedPlayer.sendMessage(TextUtil.topBorder(TextUtil.BLUE));
        invitedPlayer.sendMessage(TextUtil.makeText(player.getName() + " has invited you to their party!", TextUtil.YELLOW));

        invitedPlayer.sendMessage(inviteHyperlink("accept", player.getName()));

        invitedPlayer.sendMessage(TextUtil.bottomBorder(TextUtil.BLUE));
    }

    private Component inviteHyperlink(String str, String senderName) {
        Component beginning = TextUtil.makeText("If you would like to " + str + " this invite ", TextUtil.YELLOW);
        Component hyperlink = TextUtil.makeText("[Click Here]", TextUtil.GOLD)
                .hoverEvent(HoverEvent.showText(TextUtil.makeText("Click here to " + str + " the invite", TextUtil.DARK_PURPLE)
                        .append(TextUtil.makeText("\n /party " + str + " " + senderName, TextUtil.YELLOW))))
                .clickEvent(ClickEvent.runCommand("/party " + str + " " + senderName));
        return beginning.append(hyperlink);
    }

    private void leaveTeamMessage() {
        player.sendMessage(makeTeamMessage("You have left the party", TextUtil.BLUE));
    }

    private void errorMessage(String message) {
        player.sendMessage(makeTeamMessage(message, TextUtil.RED));
    }

    private Component makeTeamMessage(String msg, TextColor color) {
        return TextUtil.topBorder(TextUtil.BLUE)
                .append(TextUtil.makeText("\n" + msg + "\n", color))
                .append(TextUtil.bottomBorder(TextUtil.BLUE));
    }

    @Override
    public void updatePlayer() {
        player = playerData.getPlayer();
    }
}
