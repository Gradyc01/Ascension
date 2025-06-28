package me.depickcator.ascension.Lobby;

import me.depickcator.ascension.Ascension;
import me.depickcator.ascension.General.BuildLobby;
import me.depickcator.ascension.Lobby.BingoBoard.BoardDisplay;
import me.depickcator.ascension.Lobby.EventsBoard.EventBoard;
import me.depickcator.ascension.Lobby.NPCs.ParkourNPC;
import me.depickcator.ascension.Player.Data.PlayerData;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.bukkit.Difficulty;
import org.bukkit.Location;

import java.util.List;

public class Lobby {
    private EventBoard eventBoard;
    private BoardDisplay boardDisplay;
    private StatsBoard statsBoard;
    private final Location spawn;
    public Lobby(Location loc) {
        Ascension.getInstance().getWorld().setDifficulty(Difficulty.NORMAL);
        spawn = loc;
    }

    public void build() {
        new BuildLobby(spawn);
        initLobbyBoards();
        initMiscNPCs();
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

    private void initMiscNPCs() {
//        Location spawn = Ascension.getInstance().get();
        new ParkourNPC(spawn.getX() + 19, spawn.getY() + 101 + 17, spawn.getZ() - 19, new ImmutablePair<>(45, 0));
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

    public Location getSpawn() {
        return spawn;
    }
}
