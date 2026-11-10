package org.bingo4.General.Game.Load.Sequences;

import org.bingo4.Bingo4;
import org.bingo4.General.Game.GameLauncher;
import org.bingo4.General.Game.GameSequences;
import org.bingo4.General.Game.Load.LoadGame;
import org.bingo4.Utility.DisplayUtil;
import org.bukkit.Location;
import org.bukkit.entity.ArmorStand;


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
//        Bingo4.setSpawn(g.getSpawnCoordsArmorStand().getLocation());
//        Bingo4.setSpawn(g.getSpawnCoordsLocation());
        Location loc = spawnCoordsLocation.clone();
        loc.setWorld(Bingo4.getInstance().getWorld());
//        g.setSpawnCoordsLocation(loc);
        Bingo4.setSpawn(loc);
        game.callback();
    }

    private ArmorStand setSpawnCoordsArmorStand(Location loc) {
        return DisplayUtil.makeMarkerArmorStand(loc, plugin.getWorld(), LoadGame.spawnCoordsArmorStandName);
    }
}
