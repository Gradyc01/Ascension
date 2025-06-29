package me.depickcator.ascension.General.Game.Load.Sequences;

import me.depickcator.ascension.General.Game.GameLauncher;
import me.depickcator.ascension.General.Game.GameSequences;
import me.depickcator.ascension.General.Game.Load.LoadGame;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.WorldBorder;
import org.bukkit.scheduler.BukkitRunnable;

public class DeletePreviousIterations extends GameSequences {
    @Override
    public void run(GameLauncher game) {
        if (game instanceof LoadGame g) {
            World w = plugin.getSpawnWorld();
            Location l = g.getLobbyCoordsLocation();
            w.setChunkForceLoaded((int) Math.floor(l.getX() /16), (int) Math.floor(l.getZ() /16), true);

            new BukkitRunnable() {
                @Override
                public void run() {
                    Bukkit.dispatchCommand(Bukkit.getConsoleSender(),"kill @e[type=minecraft:armor_stand, name=\"SpawnCoords\"]");
                    Bukkit.dispatchCommand(Bukkit.getConsoleSender(),"kill @e[type=minecraft:text_display]");
                    Bukkit.dispatchCommand(Bukkit.getConsoleSender(),"kill @e[type=minecraft:item_display]");
                    Bukkit.dispatchCommand(Bukkit.getConsoleSender(),"kill @e[tag=lobby]");
//                    Bukkit.dispatchCommand(Bukkit.getConsoleSender(),"forceload remove all");

                    game.callback();
                }
            }.runTaskLater(plugin, 20L);

        }

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
