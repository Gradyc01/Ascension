package me.depickcator.ascension.General.Game.Load.Sequences;

import me.depickcator.ascension.Ascension;
import me.depickcator.ascension.General.Game.GameLauncher;
import me.depickcator.ascension.General.Game.GameSequences;
import me.depickcator.ascension.General.Game.Load.LoadGame;
import me.depickcator.ascension.Utility.DisplayUtil;
import org.bukkit.Location;
import org.bukkit.entity.ArmorStand;


public class SetSpawn extends GameSequences {
    @Override
    public void run(GameLauncher game) {
        if (!(game instanceof LoadGame)) throw new IllegalArgumentException();
        LoadGame g = (LoadGame) game;
        g.setSpawnCoordsArmorStand(setSpawnCoordsArmorStand(g.getSpawnCoordsLocation()));
        Ascension.setSpawn(g.getSpawnCoordsArmorStand().getLocation());

        game.callback();
    }

    private ArmorStand setSpawnCoordsArmorStand(Location loc) {
        return DisplayUtil.makeMarkerArmorStand(loc, plugin.getWorld(), LoadGame.spawnCoordsArmorStandName);
    }
}
