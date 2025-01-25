package me.depickcator.ascension.Lobby;

import me.depickcator.ascension.General.BuildLobby;
import me.depickcator.ascension.Lobby.EventsBoard.EventBoard;
import org.bukkit.Location;

public class Lobby {
    private final EventBoard eventBoard;
    public Lobby(Location loc) {
        new BuildLobby(loc);
        initLobbyBoards();
        eventBoard = new EventBoard();
    }

    private void initLobbyBoards() {
        new WelcomeBoard();
        new SpecialItems();
        new NPCBoard();
        new NPCBoard2();
        new MiscBoard();
    }

    public EventBoard getEventBoard() {
        return eventBoard;
    }
}
