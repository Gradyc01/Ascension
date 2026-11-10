package org.bingo4.General.Game.Load.Sequences;

import org.bingo4.General.Game.GameLauncher;
import org.bingo4.General.Game.GameSequences;
import org.bingo4.General.Game.Load.LoadGame;
import org.bingo4.Lobby.Lobby;
import org.bukkit.Location;

public class BuildLobby extends GameSequences {

    private final Location lobbyCoords;
    public BuildLobby(Location lobbyCoords) {
        this.lobbyCoords = lobbyCoords;
    }
    @Override
    public void run(GameLauncher game) {
        if (!(game instanceof LoadGame)) throw new IllegalArgumentException();
        LoadGame g = (LoadGame) game;
//        ArmorStand spawnCoordsArmorStand = g.getSpawnCoordsArmorStand();
//        Location location = g.getSpawnCoordsLocation();
//        location.setWorld(plugin.getSpawnWorld());
        Lobby lobby = new Lobby(lobbyCoords);
        plugin.setLobby(lobby);
        lobby.build();

        game.callback();
    }
}
