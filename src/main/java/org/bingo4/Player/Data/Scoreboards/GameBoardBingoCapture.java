package org.bingo4.Player.Data.Scoreboards;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextColor;
import org.bingo4.Bingo4;
import org.bingo4.Player.Data.PlayerData;
import org.bingo4.Structures.BingoTurnIn.BingoTurnInInstance;
import org.bingo4.Teams.Team;
import org.bingo4.Timeline.Timeline;
import org.bingo4.Utility.TextUtil;
import org.bukkit.scoreboard.Objective;

public class GameBoardBingoCapture extends GameBoard {
    private final Bingo4 plugin;
    private final Timeline timeline;
    private final BingoTurnInInstance instance;
    public GameBoardBingoCapture(Objective board, PlayerData playerData, BingoTurnInInstance instance) {
        super(board, playerData);
        this.plugin = Bingo4.getInstance();
        timeline = plugin.getSettingsUI().getSettings().getTimeline();
        this.instance = instance;
    }

    @Override
    public void makeBoard() {
        super.makeBoard();
        editLine(board, 10, TextUtil.makeText("  Your Progress:  ", TextUtil.GOLD));
        editLine(board, 6, TextUtil.makeText("  Enemy's Progress:  ", TextUtil.GOLD));
        updateTime();
    }

    @Override
    public void update() {
        updateStats();

        editLine(board, 9, buildCompletionProgressBar(getPlayerData().getPlayerTeam().getTeam()));
        editLine(board, 8, buildCaptureProgressBar(getPlayerData().getPlayerTeam().getTeam()));

        editLine(board, 5, buildCompletionProgressBar(getLeadingOpponentTeam()));
    }

    private Component buildCompletionProgressBar(Team team) {
        int percentage = instance.getBingoTurnInProgress().getCompletionPercentage().getOrDefault(team, 0);
        Component text = TextUtil.makeText("  [");
        for (int i = 0; i < 10; i++) {
            TextColor color = chooseColorBasedOnPercentage(percentage);
            Component box = TextUtil.makeText("█", color, true, false);
            percentage-=10;
            text = text.append(box);
        }
        return text.append(TextUtil.makeText("]  "));
    }

    private Component buildCaptureProgressBar(Team team) {
        int percentage = instance.getBingoTurnInProgress().getCapturePercentage().getOrDefault(team, 0);
        if (percentage == 100) {
            return TextUtil.makeText("  [")
                    .append(TextUtil.makeText("Altar of Songs ✔", TextUtil.DARK_GREEN))
                    .append(TextUtil.makeText("]  "));
        }
        Component text = TextUtil.makeText("  [");
        for (int i = 0; i < 10; i++) {
            TextColor color = chooseColorBasedOnPercentage(percentage);
            Component box = TextUtil.makeText("█", color, true, false);
            percentage-=10;
            text = text.append(box);
        }
        return text.append(TextUtil.makeText("]  "));
    }

    private Team getLeadingOpponentTeam() {
        Team playerTeam = getPlayerData().getPlayerTeam().getTeam();

        return instance.getBingoTurnInProgress()
                .getCompletionPercentage()
                .entrySet()
                .stream()
                .filter(entry -> !entry.getKey().equals(playerTeam))
                .max(java.util.Map.Entry.comparingByValue())
                .map(java.util.Map.Entry::getKey)
                .orElse(null);
    }

    private TextColor chooseColorBasedOnPercentage(int percentage) {
        if (percentage > 10) {
            return TextUtil.GREEN;
        } else if (percentage > 6) {
            return TextUtil.ORANGE;
        } else if (percentage > 3) {
            return TextUtil.YELLOW;
        } else if (percentage > 0){
            return TextUtil.RED;
        } else {
            return TextUtil.WHITE;
        }
    }


}
