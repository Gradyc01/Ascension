package me.depickcator.ascension.Player.Data.Scoreboards;

import me.depickcator.ascension.Ascension;
import me.depickcator.ascension.General.Game.GameStates;
import me.depickcator.ascension.Timeline.Timeline;
import me.depickcator.ascension.Utility.TextUtil;
import me.depickcator.ascension.Player.Data.PlayerData;
import me.depickcator.ascension.Timeline.Events.Ascension.AscensionLocation;
import net.kyori.adventure.text.Component;
import org.apache.commons.lang3.tuple.Pair;
import org.bukkit.attribute.Attribute;
import org.bukkit.scoreboard.Objective;

public class GameBoard extends Boards {
    private final Ascension plugin;
    private final Timeline timeline;
    public GameBoard(Objective board, PlayerData playerData) {
        super(board, playerData);
        this.plugin = Ascension.getInstance();
        timeline = plugin.getSettingsUI().getSettings().getTimeline();
    }

    @Override
    public void makeBoard() {
        blankBoard(board, 0, 14);

        editLine(board, 4, TextUtil.makeText("  Teammates:  ", TextUtil.GOLD));
//        editLine(board, 0, TextUtil.makeText("Discord: zAhJTXbFeB", TextUtil.YELLOW));
        editLine(board, 0, TextUtil.makeText("discord.gg/zAhJTXbFeB", TextUtil.YELLOW));
        update();
        updateTime();
    }

    @Override
    public void update() {
//        if (updateTime) {
//            updateTime();
//        }
        //10 9 7 6
        PlayerData playerData = getPlayerData();
        Component itemsText = TextUtil.makeText("  Items: ", TextUtil.WHITE);
        Component itemsNum = TextUtil.makeText( playerData.getPlayerTeam().getTeam().getTeamStats().getItemsObtained() + "", TextUtil.GREEN);
        Component killText = TextUtil.makeText("  Kills: ", TextUtil.WHITE);
        Component killNum = TextUtil.makeText(playerData.getPlayerStats().getKills() + ",", TextUtil.GREEN);
        editLine(board, 6, killText.append(killNum).append(itemsText).append(itemsNum));

        editLine(board, 10, TextUtil.makeText("  Enlightenment: ", TextUtil.WHITE));
        editLine(board, 9, displayBar(playerData.getPlayerTeam().getTeam().getTeamStats().getGameScorePercentage()));

        Component soulsText = TextUtil.makeText("  Souls: ", TextUtil.WHITE);
        Component soulsNum = TextUtil.makeText(playerData.getPlayerUnlocks().getUnlockTokens() + "", TextUtil.GREEN);
        editLine(board, 7, soulsText.append(soulsNum));

        displayTeamMembers(board, 3);
    }

    public void updateTime() {
        PlayerData playerData = getPlayerData();
        switch (plugin.getGameState().getCurrentState()) {
            case GameStates.GAME_FINAL_ASCENSION -> {
                updateFinalAscensionTimer(playerData);
            }
            case GameStates.GAME_ASCENSION -> {
                updateAscensionTimer();
            }
            default -> {
                Pair<String, Integer> event = timeline.getNextBigEvent();
                editLine(board, 13, TextUtil.makeText("  " + event.getLeft() +" In:  ", TextUtil.GOLD));
                editLine(board, 12, timeline.getTime() );
            }
        }




    }

    private void updateFinalAscensionTimer(PlayerData playerData) {
        int timer = playerData.getPlayerTeam().getTeam().getTeamStats().getFinalAscensionTimer();
        String minutes = timer/60 + "";
        String seconds = timer%60 <= 9 ? "0" + timer%60 : timer%60 + "";
        editLine(board, 13, TextUtil.makeText("  Vaporized In: ", TextUtil.GOLD));
        editLine(board, 12, TextUtil.makeText("        " + minutes + ":" + seconds, TextUtil.WHITE));
    }

    private void updateAscensionTimer() {
        AscensionLocation ascensionLocation = timeline.getAscensionEvent().getAscendingLocation();
        int time = ascensionLocation.getAscendingTeam().getTeamStats().getAscensionTimer();
        int healthPercentage = (int) (ascensionLocation.getEntity().getHealth() / ascensionLocation.getEntity().getAttribute(Attribute.MAX_HEALTH).getBaseValue() * 100);
        String minutes = time/60 + "";
        String seconds = time%60 <= 9 ? "0" + time%60 : time%60 + "";
        editLine(board, 13, TextUtil.makeText("  Gatekeeper HP: ", TextUtil.GOLD)
                .append(TextUtil.makeText( + healthPercentage + "%", TextUtil.GREEN)));
        editLine(board, 12, TextUtil.makeText("   Ascension In: ", TextUtil.GOLD)
                .append(TextUtil.makeText(minutes + ":" + seconds, TextUtil.WHITE)));
    }

    private Component displayBar(double percentage) {
        Component text = TextUtil.makeText("   [", TextUtil.WHITE);
        Component endText = TextUtil.makeText("]", TextUtil.WHITE);
        Component red = TextUtil.makeText(":", TextUtil.RED);
        Component green = TextUtil.makeText(":", TextUtil.GREEN);
        int score = (int) Math.round(25 * percentage/100);
        TextUtil.debugText("Percentage: " + percentage + " Green Bars amount: " + score);
        if (score >= 25) return TextUtil.makeText(" ASCENSION READY", TextUtil.GREEN);

        for (int i = 0; i < 25; i++) {
            if (i < score) {
                text = text.append(green);
            } else {
                text = text.append(red);
            }
        }
        return text.append(endText);
    }
}
