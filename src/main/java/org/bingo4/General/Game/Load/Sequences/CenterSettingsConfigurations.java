package org.bingo4.General.Game.Load.Sequences;

import org.bingo4.Bingo4;
import org.bingo4.General.Game.GameLauncher;
import org.bingo4.General.Game.GameSequences;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.WorldBorder;

public class CenterSettingsConfigurations extends GameSequences {
    @Override
    public void run(GameLauncher game) {
//        if (!(game instanceof LoadGame)) throw new IllegalArgumentException();
//        LoadGame g = (LoadGame) game;
//        Location spawnCoordsArmorStandLoc = g.getSpawnCoordsLocation();
        forceload(plugin.getWorld(),  Bingo4.getSpawn());
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
