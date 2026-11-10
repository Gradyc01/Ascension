package org.bingo4.Player.Data.Scoreboards;

import org.bingo4.Bingo4;
import org.bingo4.Player.Data.PlayerData;
import org.bingo4.Teams.Team;
import org.bingo4.Utility.TextUtil;
import net.kyori.adventure.text.Component;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Score;

import java.util.List;

public abstract class Boards {
    protected final Objective board;
    private final PlayerData playerData;
    private final Player player;
    public Boards(Objective board, PlayerData playerData) {
        this.board = board;
        this.playerData = playerData;
        this.player = playerData.getPlayer();
    }

    public abstract void makeBoard();
    public abstract void update();

    protected void blankBoard(Objective board, int start, int end) {
        for (int i = start; i <= end; i++) {
            Score score = board.getScore("" + i);
            score.setScore(i);
            score.customName(TextUtil.makeText("                 ", TextUtil.WHITE));
        }
    }

    protected void editLine(Objective board, int line, Component text) {
        Score score = board.getScore("" + line);
        score.customName(text);
    }

    private void undefinedTeamMemberLine(Objective board, int line) {
        editLine(board, line, TextUtil.makeText("     None", TextUtil.GRAY));
    }

    protected void displayTeamMembers(Objective board, int startingLine) {
        Team team = playerData.getPlayerTeam().getTeam();
        int teamSize = Bingo4.getInstance().getSettingsUI().getSettings().getTeamSize();
        if (team == null) {
            for (int i = startingLine; i > Math.max(startingLine - teamSize + 1, startingLine - 2); i--) {
                undefinedTeamMemberLine(board, i);
            }
            return;
        };
        List<Player> teamMembers = team.getOtherTeamMembers(player);
        int memberIndex = 0;
        for (int i = startingLine; i > startingLine - 2; i--) {
            if (memberIndex + 1 >= teamSize) {
                editLine(board, i, TextUtil.makeText("     "));
                continue;
            }
            try {
                Player p = teamMembers.get(memberIndex);
                editLine(board, i, TextUtil.makeText("     " + p.getName(), TextUtil.GREEN));
            } catch (Exception ignored) {
                undefinedTeamMemberLine(board, i);
            }
            memberIndex++;
        }
    }

    public Objective getBoard() {
        return board;
    }

    public PlayerData getPlayerData() {
        return playerData;
    }

    public Player getPlayer() {
        return player;
    }
}
