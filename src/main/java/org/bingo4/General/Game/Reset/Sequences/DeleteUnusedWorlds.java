package org.bingo4.General.Game.Reset.Sequences;

import org.bingo4.Bingo4;
import org.bingo4.General.Game.GameLauncher;
import org.bingo4.General.Game.GameSequences;
import org.bingo4.Utility.TextUtil;
import org.bukkit.Bukkit;
import org.bukkit.scheduler.BukkitRunnable;

import java.io.File;
import java.nio.file.Path;
import java.util.HashSet;
import java.util.Set;

public class DeleteUnusedWorlds extends GameSequences {

    @Override
    public void run(GameLauncher game) {
        TextUtil.debugText("Deleting all unused worlds in 60 seconds...");
        deleteAllUnusedWorlds();
        game.callback();
    }

    private void deleteAllUnusedWorlds() {
        new BukkitRunnable() {
            @Override
            public void run() {
                Set<String> worldNames = new HashSet<>();
                worldNames.add(plugin.getWorld().getKey().getKey());
                Path worldContainer = Bukkit.getServer()
                        .getLevelDirectory()
                        .resolve("dimensions")
                        .resolve(Bingo4.getInstance().getName())
                        .toAbsolutePath()
                        .normalize();

                File file = worldContainer.toFile();
                if (file.exists() && file.isDirectory()) {
                    for (File f : file.listFiles()) {
                        TextUtil.debugText("Attempting to delete world: " + f.getName() + " | " + plugin.getWorld().getKey().getKey());
                        if (worldNames.contains(f.getName())) continue;
                        TextUtil.debugText("Deleting world: " + f.getName());
                        deleteDirectory(f);
                    }
                }
            }
        }.runTaskLaterAsynchronously(plugin, 60 * 20);
    }



    private boolean deleteDirectory(File directory) {
        if (directory.exists()) {
            File[] files = directory.listFiles();
            if (files != null) {
                for (File file : files) {
                    TextUtil.debugText("Deleting file: " + file.getName());
                    if (file.isDirectory()) {
                        deleteDirectory(file);  // Recursively delete subdirectories
                    } else {
                        file.delete();
                    }
                }
            }
        }
        return directory.delete();  // Finally delete the directory itself
    }
}
