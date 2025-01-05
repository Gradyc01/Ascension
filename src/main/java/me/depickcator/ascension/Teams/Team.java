package me.depickcator.ascension.Teams;

import me.depickcator.ascension.Ascension;
import me.depickcator.ascension.Utility.TextUtil;
import me.depickcator.ascension.Player.Data.PlayerData;
import me.depickcator.ascension.Player.Data.PlayerUtil;
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
    private int STATE;
    public Team(Ascension plugin, PlayerData playerData) {
        this.plugin = plugin;
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
    }

    public void addPlayer(PlayerData pD) {
//        Objects.requireNonNull(PlayerUtil.getPlayerData(p)).getPlayerTeam().setTeam(this);
        Player p = pD.getPlayer();
        pD.getPlayerTeam().setTeam(this);
        teamMembers.add(pD);
        TeamUtil.joinTeam(leader.getPlayer(), p);
        announceToAllTeamMembers(p.getName() + " has joined the party!");
        updateTeamScoreboards();
    }

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

    public void teamList(PlayerData pD) {
        Player p = pD.getPlayer();
        p.sendMessage(TextUtil.topBorder(TextUtil.BLUE));
        p.sendMessage(TextUtil.makeText("Current party members:", TextUtil.YELLOW));
        for (PlayerData playerData :teamMembers) {
            p.sendMessage(TextUtil.makeText("  -" + playerData.getPlayer().getName(), TextUtil.YELLOW));
        }
        p.sendMessage(TextUtil.bottomBorder(TextUtil.BLUE));
    }

    public void announceToAllTeamMembers(String str) {
        for (PlayerData pD : teamMembers) {
            Player p = pD.getPlayer();
            p.sendMessage(TextUtil.topBorder(TextUtil.BLUE));
            p.sendMessage(TextUtil.makeText(str, TextUtil.YELLOW));
            p.sendMessage(TextUtil.bottomBorder(TextUtil.BLUE));
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

    public ArrayList<Player> getOtherTeamMembers(Player p) {
        ArrayList<Player> otherTeamMembers = new ArrayList<>(getTeamMembers());
        otherTeamMembers.remove(p);
        return otherTeamMembers;
    }

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

//    public int getSTATE() {
//        return STATE;
//    }

    public boolean checkSTATE(int state) {
        return STATE == state;
    }

    public void setSTATE(int STATE) {
        this.STATE = STATE;
        TextUtil.debugText(leader.getPlayer().getName() + "'s team is now State: " + STATE);
    }

    public Player getLeader() {
        return leader.getPlayer();
    }

    public void updateState() {
        boolean active = false;
        for (PlayerData pD : teamMembers) {
//            PlayerData pD = PlayerUtil.getPlayerData(p);
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
