package me.depickcator.ascension.General.Game.Load.Sequences;

import me.depickcator.ascension.Ascension;
import me.depickcator.ascension.General.Game.GameLauncher;
import me.depickcator.ascension.General.Game.GameSequences;
import me.depickcator.ascension.General.Game.Load.LoadGame;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.WorldBorder;

public class CenterSettingsConfigurations extends GameSequences {
    @Override
    public void run(GameLauncher game) {
//        if (!(game instanceof LoadGame)) throw new IllegalArgumentException();
//        LoadGame g = (LoadGame) game;
//        Location spawnCoordsArmorStandLoc = g.getSpawnCoordsLocation();
        forceload(plugin.getWorld(),  Ascension.getSpawn());
//        forceload(plugin.getSpawnWorld(),  spawnCoordsArmorStandLoc);
        game.callback();
    }

    private void forceload(World world, Location loc) {
//        World world = plugin.getWorld();
        WorldBorder border = world.getWorldBorder();

        int x = loc.getBlockX();
        int z = loc.getBlockZ();
        border.setCenter(x, z);
        world.setChunkForceLoaded((int) Math.floor((double) x /16), (int) Math.floor((double) z /16), true);

    }
}
