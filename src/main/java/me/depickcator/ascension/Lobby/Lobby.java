package me.depickcator.ascension.Lobby;

import me.depickcator.ascension.General.BuildLobby;
import me.depickcator.ascension.Lobby.BingoBoard.BoardDisplay;
import me.depickcator.ascension.Lobby.EventsBoard.EventBoard;
import org.bukkit.Location;

public class Lobby {
    private final EventBoard eventBoard;
    private final BoardDisplay boardDisplay;
    public Lobby(Location loc) {
        new BuildLobby(loc);
        initLobbyBoards();
        eventBoard = new EventBoard();
        boardDisplay = new BoardDisplay();
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
    }
}
