package org.bingo4.General.Game.Relaunch.Sequences;

import org.bingo4.General.Game.GameLauncher;
import org.bingo4.General.Game.GameSequences;
import org.bingo4.Utility.TextUtil;
import org.bukkit.World;

import java.io.File;
import java.util.List;
import java.util.Set;

public class DeleteWorld extends GameSequences {
    private final List<World> worlds;
    private final Set<String> whitelistedFiles;
    public DeleteWorld(List<World> worlds) {
        this.worlds = worlds;
//        this.whitelistedFiles = Set.of("raids.dat", "random_sequences.dat", "scoreboard.dat", "chunks.dat");\
        this.whitelistedFiles = Set.of();
    }

    @Override
    public void run(GameLauncher game) {
        for (World world : worlds) {
            deleteDirectory(world.getWorldFolder());
            TextUtil.debugText("[World Deletion] Deleting world: " + world.getName() + " at: " + world.getWorldPath());
        }

        game.callback(10 * 20);
    }

    private boolean deleteDirectory(File directory) {
        if (directory.exists()) {
            File[] files = directory.listFiles();
            if (files != null) {
                for (File file : files) {
                    if (whitelistedFiles.contains(file.getName())) continue;
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
//        return true;
    }
}
