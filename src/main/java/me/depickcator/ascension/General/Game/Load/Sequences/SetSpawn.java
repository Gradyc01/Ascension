package me.depickcator.ascension.General.Game.Load.Sequences;

import me.depickcator.ascension.Ascension;
import me.depickcator.ascension.General.Game.GameLauncher;
import me.depickcator.ascension.General.Game.GameSequences;
import me.depickcator.ascension.General.Game.Load.LoadGame;
import me.depickcator.ascension.Utility.DisplayUtil;
import org.bukkit.Location;
import org.bukkit.entity.ArmorStand;

import java.util.UUID;


public class SetSpawn extends GameSequences {

    private final Location spawnCoordsLocation;
    public SetSpawn(Location location) {
        this.spawnCoordsLocation = location;
    }

    @Override
    public void run(GameLauncher game) {
//        if (!(game instanceof LoadGame)) throw new IllegalArgumentException();
//        LoadGame g = (LoadGame) game;
//        g.setSpawnCoordsArmorStand(setSpawnCoordsArmorStand(g.getSpawnCoordsLocation()));
//        Ascension.setSpawn(g.getSpawnCoordsArmorStand().getLocation());
//        Ascension.setSpawn(g.getSpawnCoordsLocation());
        Location loc = spawnCoordsLocation.clone();
        loc.setWorld(Ascension.getInstance().getWorld());
//        g.setSpawnCoordsLocation(loc);
        Ascension.setSpawn(loc);
        game.callback();
    }

    private ArmorStand setSpawnCoordsArmorStand(Location loc) {
        return DisplayUtil.makeMarkerArmorStand(loc, plugin.getWorld(), LoadGame.spawnCoordsArmorStandName);
    }
}
