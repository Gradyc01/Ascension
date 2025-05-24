package me.depickcator.ascension.General.Game.Relaunch.Sequences;

import me.depickcator.ascension.General.Game.GameLauncher;
import me.depickcator.ascension.General.Game.GameSequences;
import me.depickcator.ascension.Utility.TextUtil;
import org.bukkit.Bukkit;
import org.bukkit.World;

import java.io.File;
import java.util.List;

public class DeleteWorld extends GameSequences {
    private final List<World> worlds;
    public DeleteWorld(List<World> worlds) {
        this.worlds = worlds;
    }

    @Override
    public void run(GameLauncher game) {
        for (World world : worlds) {
            deleteDirectory(world.getWorldFolder());
            TextUtil.debugText("Deleting world: " + world.getName());
        }

        game.callback(10 * 20);
    }

    private boolean deleteDirectory(File directory) {
        if (directory.exists()) {
            File[] files = directory.listFiles();
            if (files != null) {
                for (File file : files) {
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
