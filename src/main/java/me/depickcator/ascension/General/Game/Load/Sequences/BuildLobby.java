package me.depickcator.ascension.General.Game.Load.Sequences;

import me.depickcator.ascension.Ascension;
import me.depickcator.ascension.General.Game.GameLauncher;
import me.depickcator.ascension.General.Game.GameSequences;
import me.depickcator.ascension.General.Game.Load.LoadGame;
import me.depickcator.ascension.Lobby.Lobby;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.ArmorStand;

import static me.depickcator.ascension.General.BuildLobby.fillArea;

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
