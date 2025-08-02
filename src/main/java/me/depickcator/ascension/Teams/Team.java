package me.depickcator.ascension.Teams;

import me.depickcator.ascension.Ascension;
import me.depickcator.ascension.General.Game.GameStates;
import me.depickcator.ascension.Player.Cooldowns.Death.PlayerDeath;
import me.depickcator.ascension.Timeline.Events.Winner.Winner;
import me.depickcator.ascension.Utility.TextUtil;
import me.depickcator.ascension.Player.Data.PlayerData;
import me.depickcator.ascension.Player.Data.PlayerUtil;
import net.kyori.adventure.audience.Audience;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.title.Title;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class Team {
    // private String name; //TODO: maybe add later
    public static int STATE_ACTIVE = 1;
    public static int STATE_DEPRECATED = 2;
    private String teamKey;
    private final List<PlayerData> teamMembers;
    private final PlayerData leader;
    private final Ascension plugin;
    private final TeamStats teamStats;
    private final TeamBackpack teamBackpack;
    private final TeamAscension teamAscension;
    private int STATE;
    /*Initializes a team with PlayerData playerData as the leader*/
    public Team(PlayerData playerData) {
        this.plugin = Ascension.getInstance();
        this.teamKey = playerData.getPlayer().getUniqueId().toString();
        teamMembers = new ArrayList<>();
        teamMembers.add(playerData);
        leader = playerData;
        Player p = playerData.getPlayer();
        TeamUtil.createTeam(p);
        TeamUtil.joinTeam(p, p);
        STATE = STATE_ACTIVE;
        teamStats = new TeamStats(this);
        teamBackpack = new TeamBackpack(this);
        teamAscension = new TeamAscension(this);
    }

    /*Adds PlayerData pD to this Team*/
    public boolean addPlayer(PlayerData pD) {
        Player p = pD.getPlayer();
        if (teamMembers.size() < plugin.getSettingsUI().getSettings().getTeamSize()) {
            pD.getPlayerTeam().setTeam(this);
            teamMembers.add(pD);
            TeamUtil.joinTeam(leader.getPlayer(), p);
            announceToAllTeamMembers(p.getName() + " has joined the party!");
            updateTeamScoreboards();
            return true;
        }
        return false;
    }

    /*Removes PlayerData pD from this Team*/
    public void removePlayer(PlayerData pD) {
        if (teamMembers.size() == 1 || leader.equals(pD)) {
            for (PlayerData i: teamMembers) {
//                PlayerData playerData = PlayerUtil.getPlayerData(i);
//                assert playerData != null;
                i.getPlayerTeam().setTeam(null);
            }
            TeamUtil.disbandTeam(leader.getPlayer());
            for (PlayerData pl : teamMembers) {
                Player p = pl.getPlayer();
                p.sendMessage(TextUtil.topBorder(TextUtil.BLUE));
                p.sendMessage(TextUtil.makeText("Party leader has left the party. The party will now be disbanded", TextUtil.YELLOW));
                p.sendMessage(TextUtil.bottomBorder(TextUtil.BLUE));
            }
            updateTeamScoreboards();
        } else {
//            Objects.requireNonNull(PlayerUtil.getPlayerData(p)).getPlayerTeam().setTeam(null);
            pD.getPlayerTeam().setTeam(null);
            teamMembers.remove(pD);
            Player p = pD.getPlayer();
            TeamUtil.leaveTeam(p);
            announceToAllTeamMembers(p.getName() + " has left the party");
            updateTeamScoreboards();
        }
    }

    /*Prints in the chat the members in this Team to Player pD*/
    public void teamList(PlayerData pD) {
        Player p = pD.getPlayer();
        p.sendMessage(TextUtil.topBorder(TextUtil.BLUE));
        p.sendMessage(TextUtil.makeText("Current party members:", TextUtil.YELLOW));
        for (PlayerData playerData :teamMembers) {
            p.sendMessage(TextUtil.makeText("  -" + playerData.getPlayer().getName(), TextUtil.YELLOW));
        }
        p.sendMessage(TextUtil.bottomBorder(TextUtil.BLUE));
    }

    /*Announces to all team members message str*/
    public void announceToAllTeamMembers(String str) {
        Component text = TextUtil.topBorder(TextUtil.BLUE)
                .append(TextUtil.makeText("\n" + str + "\n", TextUtil.YELLOW))
                .append(TextUtil.bottomBorder(TextUtil.BLUE));
        for (PlayerData pD : teamMembers) {
            Player p = pD.getPlayer();
            p.sendMessage(text);
            teamList(pD);
        }
    }

    public List<Player> getTeamMembers() {
        List<Player> members = new ArrayList<>();
        for (PlayerData pD : teamMembers) {
            members.add(pD.getPlayer());
        }
        return members;
    }

    public String getTeamKey() {
        return teamKey;
    }

    /*Returns a list of teamMembers other than Player p*/
    public List<Player> getOtherTeamMembers(Player p) {
        ArrayList<Player> otherTeamMembers = new ArrayList<>(getTeamMembers());
        otherTeamMembers.remove(p);
        return otherTeamMembers;
    }

    public void attemptToRespawnTeamMembers() {
        if (!canRespawn()) return;
        for (PlayerData pD : teamMembers) {
            if (pD.checkState(PlayerData.STATE_DEAD)) {
                PlayerDeath.getInstance().playerDied(pD);
                attemptToRespawnTeamMembers();
            }
        }
    }

    /*Returns true if this team still can respawn or false if they can't*/
    public boolean canRespawn() {
        return getTeamStats().getGameScore() > plugin.getSettingsUI().getSettings().getTimeline().getGameScoreThreshold();
    }

    /*Returns a list of teamMembers other than Player p*/
    public void updateTeamScoreboards() {
        for (PlayerData pD : teamMembers) {
//            PlayerData playerData = PlayerUtil.getPlayerData(p);
            if (pD == null) {
                plugin.getServer().broadcast(TextUtil.makeText("ERROR updateTeamScoreboards", TextUtil.RED));
                continue;
            }
            pD.getPlayerScoreboard().update();
            PlayerUtil.updateTabList();
        }
    }

    public TeamStats getTeamStats() {
        return teamStats;
    }

    public TeamBackpack getTeamBackpack() {
        return teamBackpack;
    }

    public TeamAscension getTeamAscension() {
        return teamAscension;
    }

    public boolean checkSTATE(int state) {
        return STATE == state;
    }

    private void setSTATE(int STATE) {
        this.STATE = STATE;
        TextUtil.debugText(leader.getPlayer().getName() + "'s team is now State: " + STATE);
        if (STATE == STATE_DEPRECATED) {
            teamEliminatedMessage();
            List<Team> teams = TeamUtil.getEveryTeam(true);
            if (teams.size() <= 1) {
                new Winner(teams);
            }
        }
    }

    public Player getLeader() {
        return leader.getPlayer();
    }

    /*Changes the State of the team*/
    public void updateState() {
        if (checkSTATE(STATE_DEPRECATED)) return;
        boolean active = false;
        for (PlayerData pD : teamMembers) {
            if (!pD.checkState(PlayerData.STATE_SPECTATING, PlayerData.STATE_DEAD)) {
                active = true;
                break;
            }
        }
        if (active) {
            setSTATE(STATE_ACTIVE);
        } else {
            setSTATE(STATE_DEPRECATED);
        }
    }

    private void teamEliminatedMessage() {
        Component lost = TextUtil.makeText("\n            YOU LOST   \n\n", TextUtil.RED, true, true);
        Component description = TextUtil.makeText(
                "      Your team has failed to ascend.      \n",TextUtil.AQUA);
        Component finalText = TextUtil.topBorder(TextUtil.YELLOW).append(lost).append(description).append(TextUtil.bottomBorder(TextUtil.YELLOW));
        Audience team = Audience.audience(getTeamMembers());
        team.sendMessage(finalText);
        Title title = TextUtil.makeTitle(TextUtil.makeText("GAME OVER!", TextUtil.RED, true, false)
                , 0, 9, 1);
        team.showTitle(title);

//        team.showTitle(TextUtil.makeTitle());
        TextUtil.broadcastMessage(TextUtil.makeText(leader.getPlayer().getName() + "'s Team has failed to complete Ascension", TextUtil.DARK_RED));

    }

    /*Shows the team the no respawn left message*/
    public void showNoRespawnLeftsMessage() {
        Title title = TextUtil.makeTitle(TextUtil.makeText("No more Respawns", TextUtil.RED),
                TextUtil.makeText("Be careful!", TextUtil.DARK_RED),
                1, 5, 1);
        Component text = TextUtil.topBorder(TextUtil.DARK_GRAY)
                .append(TextUtil.makeText("            NO RESPAWNS REMAINING!", TextUtil.RED, true, false))
                .append(TextUtil.makeText("\n\n Become more enlightened in order to respawn again"))
                .append(TextUtil.bottomBorder(TextUtil.DARK_GRAY));
        Audience teamMembers = Audience.audience(getTeamMembers());
        teamMembers.sendMessage(text);
        teamMembers.showTitle(title);
    }
}
