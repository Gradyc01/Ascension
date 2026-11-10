package org.bingo4.Player.Data.Scoreboards;

import org.bingo4.Bingo4;
import org.bingo4.General.Game.GameStates;
import org.bingo4.MainMenuUI.BingoBoard.BingoSlot;
import org.bingo4.Player.Data.PlayerData;
import org.bingo4.Teams.Team;
import org.bingo4.Timeline.Timeline;
import org.bingo4.Utility.TextUtil;
import net.kyori.adventure.text.Component;
import org.apache.commons.lang3.tuple.Pair;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scoreboard.Objective;

import java.util.ArrayList;
import java.util.List;

public class GameBoard extends Boards {
    private final Bingo4 plugin;
    private final Timeline timeline;
    public GameBoard(Objective board, PlayerData playerData) {
        super(board, playerData);
        this.plugin = Bingo4.getInstance();
        timeline = plugin.getSettingsUI().getSettings().getTimeline();
    }

    @Override
    public void makeBoard() {
        blankBoard(board, 0, 14);

        editLine(board, 10, TextUtil.makeText("  Bingo Board:  ", TextUtil.GOLD));
        editLine(board, 0, TextUtil.makeText("discord.gg/zAhJTXbFeB", TextUtil.YELLOW));
        update();
        updateTime();
    }

    @Override
    public void update() {
        updateStats();

        List<Component> bingoBoardTextLines = buildBingoBoard();
        editLine(board, 9, bingoBoardTextLines.get(0));
        editLine(board, 8, bingoBoardTextLines.get(1));
        editLine(board, 7, bingoBoardTextLines.get(2));
        editLine(board, 6, bingoBoardTextLines.get(3));
        editLine(board, 5, bingoBoardTextLines.get(4));
    }

    protected void updateStats() {
        PlayerData playerData = getPlayerData();
        Component itemsText = TextUtil.makeText("  Items: ", TextUtil.WHITE);
        Component itemsNum = TextUtil.makeText( playerData.getPlayerTeam().getTeam().getTeamStats().getItemsObtained() + "", TextUtil.GREEN);
        Component killText = TextUtil.makeText("  Kills: ", TextUtil.WHITE);
        Component killNum = TextUtil.makeText(playerData.getPlayerStats().getKills() + ",", TextUtil.GREEN);
        editLine(board, 3, killText.append(killNum).append(itemsText).append(itemsNum));
    }

    public void updateTime() {
        PlayerData playerData = getPlayerData();
        switch (plugin.getGameState().getCurrentState()) {
//            case GameStates.GAME_FINAL_ASCENSION -> {
//                updateFinalAscensionTimer(playerData);
//            }
            case GameStates.GAME_ASCENSION -> {
                updateAscensionTimer();
            }
            default -> {
                Pair<String, Integer> event = timeline.getNextBigEvent(playerData);
                editLine(board, 13, TextUtil.makeText("  " + event.getLeft() +" In:  ", TextUtil.GOLD));
                editLine(board, 12, timeline.getTime(playerData) );
            }
        }
    }

    private List<Component> buildBingoBoard() {
        List<PlayerData> teamData = getPlayerData().getPlayerTeam().getTeam().getTeamMembersData();
        List<Component> texts = new ArrayList<>();//■█▮
        List<BingoSlot> slots = plugin.getBingoData().getBingoSlots();
        int index = 0;
        for (int i = 0; i < 5; i++) {
            Component text = TextUtil.makeText("     ");
            Component box = TextUtil.makeText("█", TextUtil.WHITE, true, false);
            for (int j = 0; j < 5; j++) {
                BingoSlot slot = slots.size() == 25 ? slots.get(index++) : new BingoSlot(new ItemStack(Material.AIR));
                if (!slot.isClaimed()) {
                    box = box.color(TextUtil.WHITE);
                } else if (teamData.contains(slot.getClaimer())) {
                    box = box.color(TextUtil.GREEN);
                } else {
                    box = box.color(TextUtil.RED);
                }
                text = text.append(box);
            }
            texts.add(text);
        }
        return texts;
    }


    private void updateAscensionTimer() {
        Team team = timeline.getBingoLockdown().getTeam();
        int time = team.getTeamBingoLockdown().getAscensionTimer();
        String minutes = time/60 + "";
        String seconds = time%60 <= 9 ? "0" + time%60 : time%60 + "";

        String teamText = team.getTeamMembersData().contains(getPlayerData()) ? "Your" : "Enemy";
        editLine(board, 13, TextUtil.makeText("  "  + teamText + " Team Wins In: ", TextUtil.GOLD));
        editLine(board, 12, TextUtil.makeText("        " + minutes + ":" + seconds, TextUtil.WHITE));
    }

    private Component displayBar(double percentage) {
        Component text = TextUtil.makeText("   [", TextUtil.WHITE);
        Component endText = TextUtil.makeText("]", TextUtil.WHITE);
        Component red = TextUtil.makeText(":", TextUtil.RED);
        Component green = TextUtil.makeText(":", TextUtil.GREEN);
        int score = (int) Math.round(25 * percentage/100);
        if (score >= 25) return TextUtil.makeText(" ASCENSION READY", TextUtil.GREEN);

        for (int i = 0; i < 25; i++) {
            if (i < score) {
                text = text.append(green);
            } else {
                text = text.append(red);
            }
        }
        return text.append(endText).append(TextUtil.makeText(" " + (int) percentage + "%", TextUtil.WHITE));
    }
}
