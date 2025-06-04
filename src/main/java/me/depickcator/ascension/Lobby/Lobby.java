package me.depickcator.ascension.Lobby;

import me.depickcator.ascension.Ascension;
import me.depickcator.ascension.General.BuildLobby;
import me.depickcator.ascension.Lobby.BingoBoard.BoardDisplay;
import me.depickcator.ascension.Lobby.EventsBoard.EventBoard;
import me.depickcator.ascension.Player.Data.PlayerData;
import org.bukkit.Difficulty;
import org.bukkit.Location;

public class Lobby {
    private final EventBoard eventBoard;
    private final BoardDisplay boardDisplay;
    private final StatsBoard statsBoard;
    public Lobby(Location loc) {
        Ascension.getInstance().getWorld().setDifficulty(Difficulty.NORMAL);
        new BuildLobby(loc);
        initLobbyBoards();
        eventBoard = new EventBoard();
        boardDisplay = new BoardDisplay();
        statsBoard = new StatsBoard();
    }

    private void initLobbyBoards() {
        new WelcomeBoard();
        new SpecialItems();
        new NPCBoard();
        new NPCBoard2();
        new MiscBoard();
//        new BoardDisplay();
    }

    public EventBoard getEventBoard() {
        return eventBoard;
    }

    public BoardDisplay getBoardDisplay() {
        return boardDisplay;
    }

    public void updateLobby() {
        boardDisplay.initBoardItemsDisplay();
        statsBoard.initOtherDisplays();
    }

    public void resetToLobby(PlayerData playerData) {
        eventBoard.showDefaultBoard(playerData);
        boardDisplay.displayBoard(playerData.getPlayer(), playerData.getPlayer());
        statsBoard.setupLoginPlayer(playerData.getPlayer());
    }
}
