package me.depickcator.ascension.General.Game.Reset.Sequences;

import me.depickcator.ascension.General.Game.GameLauncher;
import me.depickcator.ascension.General.Game.GameSequences;
import me.depickcator.ascension.Utility.TextUtil;
import org.bukkit.World;
import org.bukkit.scheduler.BukkitRunnable;

import java.io.File;
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
                worldNames.add(plugin.getWorld().getName());
                worldNames.add(plugin.getNether().getName());
//                TextUtil.debugText("added worldNames: " + plugin.getWorld().getName());
//                TextUtil.debugText("added worldNames: " + plugin.getNether().getName());
                File file = new File("./worlds");
                if (file.exists() && file.isDirectory()) {
                    for (File f : file.listFiles()) {
                        TextUtil.debugText("Attempting to delete world: " + f.getName());
                        if (worldNames.contains("./worlds/" + f.getName())) continue;
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
