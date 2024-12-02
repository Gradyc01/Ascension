package me.depickcator.ascension.Player;

import me.depickcator.ascension.Ascension;
import me.depickcator.ascension.General.TextUtil;
import me.depickcator.ascension.Teams.Team;
import net.kyori.adventure.text.Component;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.*;

import java.util.ArrayList;


public class PlayerScoreboard {
    private final Scoreboard scoreboard;
    private final ScoreboardManager manager;
    private final Ascension plugin;
    private final Player player;
    private final PlayerData playerData;
    private Objective board;
    private Objective health;
    public PlayerScoreboard(Ascension plugin, PlayerData playerData) {
        this.plugin = plugin;
        this.player = playerData.getPlayer();
        this.playerData = playerData;
        this.manager = plugin.getServer().getScoreboardManager();
        this.scoreboard = manager.getNewScoreboard();
        board = scoreboard.registerNewObjective(
                "board",
                Criteria.DUMMY,
                TextUtil.makeText("ASCENSION", TextUtil.YELLOW, true, false));

        makeLobbyBoard();
        board.setDisplaySlot(DisplaySlot.SIDEBAR);
        makeHealthScoreboardToTab();
        player.setScoreboard(scoreboard);
    }

    private void makeHealthScoreboardToTab() {
        health = scoreboard.registerNewObjective(
                "health",
                Criteria.HEALTH,
                TextUtil.makeText("health", TextUtil.YELLOW));
        health.setRenderType(RenderType.INTEGER);
        health.setDisplaySlot(DisplaySlot.PLAYER_LIST);
    }

    public void makeLobbyBoard() {
        blankBoard(board, 0, 14);

        editLine(board, 13, TextUtil.makeText("  Game Type:", TextUtil.GOLD, true, false));

        editLine(board, 10, TextUtil.makeText("To add players to ", TextUtil.WHITE));
        editLine(board, 9, TextUtil.makeText(" your party use", TextUtil.WHITE));
        Component commandPart1 = TextUtil.makeText("/party invite ", TextUtil.WHITE);
        Component commandPart2 = TextUtil.makeText("<name>", TextUtil.YELLOW);
        editLine(board, 8, commandPart1.append(commandPart2));

        editLine(board, 5, TextUtil.makeText( "  Your Team", TextUtil.GREEN, true, false));

        editLine(board, 0, TextUtil.makeText("discord.gg/not_real", TextUtil.YELLOW));
        updateLobbyBoard();
    }

    public void updateLobbyBoard() {
        editLine(board, 12, TextUtil.makeText("      Standard", TextUtil.YELLOW));

        displayTeamMembers(board, 4);
    }

    public void makeGameBoard() {
        blankBoard(board, 0, 14);

        editLine(board, 4, TextUtil.makeText("  Teammates:  ", TextUtil.GOLD));

        editLine(board, 0, TextUtil.makeText("discord.gg/not_real", TextUtil.YELLOW));
        updateGameBoard(true);
    }

    public void updateGameBoard(boolean updateTime) {
        if (updateTime) {
            editLine(board, 13, TextUtil.makeText("  Deathmatch In:  ", TextUtil.GOLD));
            editLine(board, 12, plugin.getTimeline().getTime());
        }
        Component itemsText = TextUtil.makeText("  Items Obtained: ", TextUtil.WHITE);
        Component itemsNum = TextUtil.makeText( playerData.getPlayerTeam().getTeam().getTeamStats().getItemsObtained() +  "", TextUtil.GREEN);
        editLine(board, 10, itemsText.append(itemsNum));

        Component killText = TextUtil.makeText("  Kills: ", TextUtil.WHITE);
        Component killNum = TextUtil.makeText(playerData.getPlayerStats().getKills() + "", TextUtil.GREEN);
        editLine(board, 9, killText.append(killNum));

        Component scoreText = TextUtil.makeText("  Score: ", TextUtil.WHITE);
        Component scoreNum = TextUtil.makeText( playerData.getPlayerTeam().getTeam().getTeamStats().getGameScore() + "", TextUtil.GREEN);
        editLine(board, 7, scoreText.append(scoreNum));

        Component soulsText = TextUtil.makeText("  Souls: ", TextUtil.WHITE);
        Component soulsNum = TextUtil.makeText(playerData.getPlayerUnlocks().getUnlockTokens() + "", TextUtil.GREEN);
        editLine(board, 6, soulsText.append(soulsNum));

        displayTeamMembers(board, 3);
    }

    public void updateGameBoard() {
        updateGameBoard(false);
    }

    private void displayTeamMembers(Objective board, int startingLine) {
        Team team = playerData.getPlayerTeam().getTeam();
        if (team == null) {
            undefinedTeamMemberLine(board, startingLine);
            undefinedTeamMemberLine(board, startingLine - 1);
            return;
        };
        ArrayList<Player> teamMembers = team.getOtherTeamMembers(player);
        int memberIndex = 0;
        for (int i = startingLine; i > startingLine - 2; i--) {
            try {
                Player p = teamMembers.get(memberIndex);
                editLine(board, i, TextUtil.makeText("     " + p.getName(), TextUtil.GREEN));
            } catch (Exception ignored) {
                undefinedTeamMemberLine(board, i);
            }
            memberIndex++;
        }
    }

    private void undefinedTeamMemberLine(Objective board, int line) {
        editLine(board, line, TextUtil.makeText("     None", TextUtil.GRAY));
    }

    private void blankBoard(Objective board, int start, int end) {
        for (int i = start; i <= end; i++) {
            Score score = board.getScore("" + i);
            score.setScore(i);
            score.customName(TextUtil.makeText("                 ", TextUtil.WHITE));
        }
    }

    private void editLine(Objective board, int line, Component text) {
        Score score = board.getScore("" + line);
        score.customName(text);
    }

    private void clearLine(Objective board, int line) {
        editLine(board, line, TextUtil.makeText(" ", TextUtil.WHITE));
    }



}
