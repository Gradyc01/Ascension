package me.depickcator.ascension.General.Game.Reset.Sequences;

import me.depickcator.ascension.General.Game.GameLauncher;
import me.depickcator.ascension.General.Game.GameSequences;
import me.depickcator.ascension.Utility.TextUtil;
import org.bukkit.scheduler.BukkitRunnable;

import java.io.File;
import java.util.HashSet;
import java.util.Set;

public class UnloadUnusedWorlds extends GameSequences {

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
