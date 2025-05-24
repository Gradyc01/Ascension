package me.depickcator.ascension.General.Game.Load.Sequences;

import me.depickcator.ascension.General.Game.GameLauncher;
import me.depickcator.ascension.General.Game.GameSequences;
import me.depickcator.ascension.General.Game.Load.LoadGame;
import me.depickcator.ascension.Lobby.Lobby;
import org.bukkit.entity.ArmorStand;

import static me.depickcator.ascension.General.BuildLobby.fillArea;

public class BuildLobby extends GameSequences {
    @Override
    public void run(GameLauncher game) {
        if (!(game instanceof LoadGame)) throw new IllegalArgumentException();
        LoadGame g = (LoadGame) game;
        ArmorStand spawnCoordsArmorStand = g.getSpawnCoordsArmorStand();
        plugin.setLobby(new Lobby(spawnCoordsArmorStand.getLocation()));
        game.callback();
    }
}
