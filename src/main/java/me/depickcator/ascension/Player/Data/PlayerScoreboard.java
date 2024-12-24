package me.depickcator.ascension.Player.Data;

import io.papermc.paper.scoreboard.numbers.NumberFormat;
import me.depickcator.ascension.Ascension;
import me.depickcator.ascension.General.TextUtil;
import me.depickcator.ascension.Player.Data.Scoreboards.Boards;
import me.depickcator.ascension.Player.Data.Scoreboards.GameBoard;
import me.depickcator.ascension.Player.Data.Scoreboards.LobbyBoard;
import net.kyori.adventure.audience.Audience;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.*;

import java.util.List;


public class PlayerScoreboard {
    private final Scoreboard scoreboard;
    private final ScoreboardManager manager;
    private final Player player;
    private final PlayerData playerData;
    private Boards boards;
    private Team teammates;
    private Team enemy;
    private Objective board;
    private Objective health;
    public PlayerScoreboard(PlayerData playerData) {
        this.player = playerData.getPlayer();
        this.playerData = playerData;
        this.manager = Ascension.getInstance().getServer().getScoreboardManager();
        this.scoreboard = manager.getNewScoreboard();
        board = scoreboard.registerNewObjective(
                "board",
                Criteria.DUMMY,
                TextUtil.makeText("ASCENSION", TextUtil.YELLOW, true, false));
        board.numberFormat(NumberFormat.blank());
        initTabList();
        makeLobbyBoard();
        board.setDisplaySlot(DisplaySlot.SIDEBAR);
        makeHealthScoreboardToTab();
        player.setScoreboard(scoreboard);
    }

    public void makeLobbyBoard() {
        boards = new LobbyBoard(board, playerData);
        boards.makeBoard();
    }

    public void makeGameBoard() {
        boards = new GameBoard(board, playerData);
        boards.makeBoard();
    }

    public void update() {
        boards.update();
    }

    public Boards getBoards() {
        return boards;
    }

    private void makeHealthScoreboardToTab() {
        health = scoreboard.registerNewObjective(
                "health",
                Criteria.HEALTH,
                TextUtil.makeText("health", TextUtil.YELLOW));
        health.setRenderType(RenderType.INTEGER);
        health.setDisplaySlot(DisplaySlot.PLAYER_LIST);
    }

    public void updateTabList() {
        List<PlayerData> allPlayers = PlayerUtil.getAllPlayingPlayers();
        teammates.addPlayer(player);
        allPlayers.remove(playerData);
        if (playerData.getPlayerTeam().getTeam() != null) {
            for (Player p : playerData.getPlayerTeam().getTeam().getTeamMembers()) {
                teammates.addPlayer(p);
                allPlayers.remove(PlayerUtil.getPlayerData(p));
            }
        }
        for (PlayerData pD : allPlayers) {
            enemy.addPlayer(pD.getPlayer());
        }
    }

    private void initTabList() {
        teammates = registerTeam("aFriend");
        enemy = registerTeam("enemy");
        teammates.color((NamedTextColor) TextUtil.GREEN);
        enemy.color((NamedTextColor) TextUtil.RED);
        updateTabList();

//        Audience.(player).sendMessage(TextUtil.makeText("T"));/
        Component text = TextUtil.makeText("You are currently playing ", TextUtil.AQUA);
        text = text.append(TextUtil.makeText("ASCENSION", TextUtil.YELLOW, true, false));
        Audience.audience(player).sendPlayerListFooter(text);
    }



    private Team registerTeam(String teamName) {
        Team team = scoreboard.getTeam(teamName);
        if (team == null) {
            team = scoreboard.registerNewTeam(teamName);
        }
        return team;
    }



//    public void makeLobbyBoard() {
//        blankBoard(board, 0, 14);
//
//        editLine(board, 13, TextUtil.makeText("  Game Type:", TextUtil.GOLD, true, false));
//
//        editLine(board, 10, TextUtil.makeText("To add players to ", TextUtil.WHITE));
//        editLine(board, 9, TextUtil.makeText(" your party use", TextUtil.WHITE));
//        Component commandPart1 = TextUtil.makeText("/party invite ", TextUtil.WHITE);
//        Component commandPart2 = TextUtil.makeText("<name>", TextUtil.YELLOW);
//        editLine(board, 8, commandPart1.append(commandPart2));
//
//        editLine(board, 5, TextUtil.makeText( "  Your Team", TextUtil.GREEN, true, false));
//
////        editLine(board, 0, TextUtil.makeText("discord.gg/not_real", TextUtil.YELLOW));
//        editLine(board, 0, TextUtil.makeText("Discord: zAhJTXbFeB", TextUtil.YELLOW));
//        updateLobbyBoard();
//    }
//    public void updateLobbyBoard() {
//        editLine(board, 12, TextUtil.makeText("      Standard", TextUtil.YELLOW));
//
//        displayTeamMembers(board, 4);
//    }
//    public void makeGameBoard() {
//        blankBoard(board, 0, 14);
//
//        editLine(board, 4, TextUtil.makeText("  Teammates:  ", TextUtil.GOLD));
//
//        editLine(board, 0, TextUtil.makeText("Discord: zAhJTXbFeB", TextUtil.YELLOW));
//        updateGameBoard(true);
//    }

//    public void updateGameBoard(boolean updateTime) {
//        if (updateTime) {
//            updateTime();
//        }
//        //10 9 7 6
////        Component itemsText = TextUtil.makeText("  Items Obtained: ", TextUtil.WHITE);
////        Component itemsNum = TextUtil.makeText( playerData.getPlayerTeam().getTeam().getTeamStats().getItemsObtained() +  "", TextUtil.GREEN);
////        editLine(board, 10, itemsText.append(itemsNum));
//        Component itemsText = TextUtil.makeText("  Items: ", TextUtil.WHITE);
//        Component itemsNum = TextUtil.makeText( playerData.getPlayerTeam().getTeam().getTeamStats().getItemsObtained() + "", TextUtil.GREEN);
//        Component killText = TextUtil.makeText("  Kills: ", TextUtil.WHITE);
//        Component killNum = TextUtil.makeText(playerData.getPlayerStats().getKills() + ",", TextUtil.GREEN);
//        editLine(board, 6, killText.append(killNum).append(itemsText).append(itemsNum));
//
////        Component scoreText = TextUtil.makeText("  Score: ", TextUtil.WHITE);
////        Component scoreNum = TextUtil.makeText( playerData.getPlayerTeam().getTeam().getTeamStats().getGameScore() + "", TextUtil.GREEN);
////        editLine(board, 7, scoreText.append(scoreNum));
//        editLine(board, 10, TextUtil.makeText("  Enlightenment: ", TextUtil.WHITE));
//        editLine(board, 9, displayBar(playerData.getPlayerTeam().getTeam().getTeamStats().getGameScore()));
//
//        Component soulsText = TextUtil.makeText("  Souls: ", TextUtil.WHITE);
//        Component soulsNum = TextUtil.makeText(playerData.getPlayerUnlocks().getUnlockTokens() + "", TextUtil.GREEN);
//        editLine(board, 7, soulsText.append(soulsNum));
//
//        displayTeamMembers(board, 3);
//    }
//
//    private Component displayBar(int score) {
//        Component text = TextUtil.makeText("    [", TextUtil.WHITE);
//        Component endText = TextUtil.makeText("]", TextUtil.WHITE);
//        Component red = TextUtil.makeText(":", TextUtil.RED);
//        Component green = TextUtil.makeText(":", TextUtil.GREEN);
//        for (int i = 0; i < 25; i++) {
//            if (i < score) {
//                text = text.append(green);
//            } else {
//                text = text.append(red);
//            }
//        }
//        return text.append(endText);
//    }
//
//    private void updateTime() {
//        if (plugin.getGameState().checkState(GameStates.GAME_FINAL_ASCENSION)) {
//            int timer = playerData.getPlayerTeam().getTeam().getTeamStats().getFinalAscensionTimer();
//            String minutes = timer/60 + "";
//            String seconds = timer%60 <= 9 ? "0" + timer%60 : timer%60 + "";
//            editLine(board, 13, TextUtil.makeText("  Vaporized In: ", TextUtil.GOLD));
//            editLine(board, 12, TextUtil.makeText("        " + minutes + ":" + seconds, TextUtil.WHITE));
//
//            return;
//        } else if (plugin.getGameState().checkState(GameStates.GAME_ASCENSION)) {
//            AscensionLocation ascensionLocation = plugin.getTimeline().getAscensionEvent().getAscendingLocation();
//            int time = ascensionLocation.getAscendingTeam().getTeamStats().getAscensionTimer();
//            int healthPercentage = (int) (ascensionLocation.getEntity().getHealth() / ascensionLocation.getEntity().getAttribute(Attribute.MAX_HEALTH).getBaseValue() * 100);
//
//
//            String minutes = time/60 + "";
//            String seconds = time%60 <= 9 ? "0" + time%60 : time%60 + "";
////            editLine(board, 13, TextUtil.makeText("  Ascend In: ", TextUtil.GOLD));
////            editLine(board, 12, TextUtil.makeText("        " + minutes + ":" + seconds, TextUtil.WHITE));
//            editLine(board, 13, TextUtil.makeText("  Gatekeeper HP: ", TextUtil.GOLD)
//                    .append(TextUtil.makeText( + healthPercentage + "%", TextUtil.GREEN)));
//            editLine(board, 12, TextUtil.makeText("   Ascension In: ", TextUtil.GOLD)
//                    .append(TextUtil.makeText(minutes + ":" + seconds, TextUtil.WHITE)));
//            return;
//
//        }
//
//        editLine(board, 13, TextUtil.makeText("  Deathmatch In:  ", TextUtil.GOLD));
//        editLine(board, 12, plugin.getTimeline().getTime());
//    }



//    public void updateGameBoard() {
//        updateGameBoard(false);
//    }

//    private void displayTeamMembers(Objective board, int startingLine) {
//        Team team = playerData.getPlayerTeam().getTeam();
//        if (team == null) {
//            undefinedTeamMemberLine(board, startingLine);
//            undefinedTeamMemberLine(board, startingLine - 1);
//            return;
//        };
//        ArrayList<Player> teamMembers = team.getOtherTeamMembers(player);
//        int memberIndex = 0;
//        for (int i = startingLine; i > startingLine - 2; i--) {
//            try {
//                Player p = teamMembers.get(memberIndex);
//                editLine(board, i, TextUtil.makeText("     " + p.getName(), TextUtil.GREEN));
//            } catch (Exception ignored) {
//                undefinedTeamMemberLine(board, i);
//            }
//            memberIndex++;
//        }
//    }

//    private void undefinedTeamMemberLine(Objective board, int line) {
//        editLine(board, line, TextUtil.makeText("     None", TextUtil.GRAY));
//    }

//    private void blankBoard(Objective board, int start, int end) {
//        for (int i = start; i <= end; i++) {
//            Score score = board.getScore("" + i);
//            score.setScore(i);
//            score.customName(TextUtil.makeText("                 ", TextUtil.WHITE));
//        }
//    }
//
//    private void editLine(Objective board, int line, Component text) {
//        Score score = board.getScore("" + line);
//        score.customName(text);
//    }

//    private void clearLine(Objective board, int line) {
//        editLine(board, line, TextUtil.makeText(" ", TextUtil.WHITE));
//    }



}
