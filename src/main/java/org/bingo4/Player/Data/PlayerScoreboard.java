package org.bingo4.Player.Data;

import io.papermc.paper.scoreboard.numbers.NumberFormat;
import org.bingo4.Bingo4;
import org.bingo4.Player.Data.Scoreboards.Boards;
import org.bingo4.Player.Data.Scoreboards.GameBoard;
import org.bingo4.Player.Data.Scoreboards.GameBoardBingoCapture;
import org.bingo4.Player.Data.Scoreboards.LobbyBoard;
import org.bingo4.Structures.BingoTurnIn.BingoTurnInInstance;
import org.bingo4.Utility.TextUtil;
import net.kyori.adventure.audience.Audience;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.*;

import java.util.List;


public class PlayerScoreboard implements PlayerDataObservers {
    private final Bingo4 plugin;
    private final Scoreboard scoreboard;
    private final ScoreboardManager manager;
    private Player player;
    private final PlayerData playerData;
    private Boards boards;
    private Team teammates;
    private Team enemy;
    private Objective board;
    private Objective health;
    public PlayerScoreboard(PlayerData playerData) {
        this.plugin = Bingo4.getInstance();
        this.player = playerData.getPlayer();
        this.playerData = playerData;
        this.manager = plugin.getServer().getScoreboardManager();
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

    /*Creates the LobbyBoard for this player*/
    public void makeLobbyBoard() {
        boards = new LobbyBoard(board, playerData);
        boards.makeBoard();
    }

    /*Creates the GameBoard for this player*/
    public void makeGameBoard() {
        boards = new GameBoard(board, playerData);
        boards.makeBoard();
    }

    /*Creates the GameBoard for this player*/
    public void makeBingoCaptureBoard(BingoTurnInInstance instance) {
        boards = new GameBoardBingoCapture(board, playerData, instance);
        boards.makeBoard();
    }

    /*Updates the board that is associated with this player*/
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

    /*Updates the Tab to represent which players in the game are friend and foe*/
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
//        text = text.append(TextUtil.makeText("\nGame Type: ", TextUtil.BLUE));
//        text = text.append(TextUtil.makeText(plugin.getSettingsUI().getSettings().getName(), TextUtil.GOLD));
        Audience.audience(player).sendPlayerListFooter(text);
    }

    private Team registerTeam(String teamName) {
        Team team = scoreboard.getTeam(teamName);
        if (team == null) {
            team = scoreboard.registerNewTeam(teamName);
        }
        return team;
    }

    @Override
    public void updatePlayer() {
        player = playerData.getPlayer();
        player.setScoreboard(scoreboard);
    }
}
