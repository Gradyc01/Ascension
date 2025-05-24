package me.depickcator.ascension.Player.Data.Scoreboards;

import me.depickcator.ascension.Ascension;
import me.depickcator.ascension.General.Game.GameStates;
import me.depickcator.ascension.General.Queue.Queue;
import me.depickcator.ascension.Utility.TextUtil;
import me.depickcator.ascension.Player.Data.PlayerData;
import net.kyori.adventure.text.Component;
import org.apache.commons.lang3.tuple.Pair;
import org.bukkit.scoreboard.Objective;

public class LobbyBoard extends Boards {
    public LobbyBoard(Objective board, PlayerData playerData) {
        super(board, playerData);
    }

    @Override
    public void makeBoard() {
        blankBoard(board, 0, 14);

        editLine(board, 13, TextUtil.makeText("  Game Type:", TextUtil.GOLD, true, false));

//        editLine(board, 10, TextUtil.makeText("To add players to ", TextUtil.WHITE));
//        editLine(board, 9, TextUtil.makeText(" your party use", TextUtil.WHITE));
//        Component commandPart1 = TextUtil.makeText("/party invite ", TextUtil.WHITE);
//        Component commandPart2 = TextUtil.makeText("<name>", TextUtil.YELLOW);
//        editLine(board, 8, commandPart1.append(commandPart2));

        editLine(board, 5, TextUtil.makeText( "  Your Team", TextUtil.GREEN, true, false));

//        editLine(board, 0, TextUtil.makeText("Discord: zAhJTXbFeB", TextUtil.YELLOW));
        editLine(board, 0, TextUtil.makeText("discord.gg/zAhJTXbFeB", TextUtil.YELLOW));
        update();
    }

    @Override
    public void update() {
        String name = Ascension.getInstance().getSettingsUI().getSettings().getName();
        editLine(board, 12, TextUtil.makeText("      " + name, TextUtil.YELLOW));
        displayQueueTime();
        displayTeamMembers(board, 4);
    }

    public void displayQueueTime() {
        if (Ascension.getInstance().getGameState().checkState(GameStates.LOBBY_QUEUE)) {
            editLine(board, 10, TextUtil.makeText(""));
            int time = Queue.getInstance().getTimer();
            String minutes = time/60 + "";
            String seconds = time%60 <= 9 ? "0" + time%60 : time%60 + "";
            Component queueText = TextUtil.makeText("  Queue   ", TextUtil.BLUE, true, false);
            Component timeText = TextUtil.makeText("(" + minutes + ":" + seconds + ")");
            editLine(board, 9, queueText.append(timeText));

            Pair<Integer, Integer> playerNumbers = Queue.getInstance().getPlayerQueueNumbers();
            editLine(board, 8, TextUtil.makeText("    " + playerNumbers.getLeft() + "/" + playerNumbers.getRight() + " Ready" , TextUtil.GREEN));

        } else {
            editLine(board, 10, TextUtil.makeText("To add players to ", TextUtil.WHITE));
            editLine(board, 9, TextUtil.makeText(" your party use", TextUtil.WHITE));
            Component commandPart1 = TextUtil.makeText("/party invite ", TextUtil.WHITE);
            Component commandPart2 = TextUtil.makeText("<name>", TextUtil.YELLOW);
            editLine(board, 8, commandPart1.append(commandPart2));
        }
    }

}
