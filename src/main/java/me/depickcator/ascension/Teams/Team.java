package me.depickcator.ascension.Teams;

import me.depickcator.ascension.Ascension;
import me.depickcator.ascension.Utility.TextUtil;
import me.depickcator.ascension.Player.Data.PlayerData;
import me.depickcator.ascension.Player.Data.PlayerUtil;
import net.kyori.adventure.text.Component;
import org.bukkit.entity.Player;

import java.util.ArrayList;
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
    }

    public Player getLeader() {
        return leader.getPlayer();
    }

    /*Changes the State of the team*/
    public void updateState() {
        boolean active = false;
        for (PlayerData pD : teamMembers) {
            if (!pD.checkState(PlayerData.STATE_SPECTATING)) {
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
}
