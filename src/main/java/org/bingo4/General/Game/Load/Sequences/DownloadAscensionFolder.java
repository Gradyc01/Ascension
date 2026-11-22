package org.bingo4.General.Game.Load.Sequences;

import org.bingo4.General.Game.GameLauncher;
import org.bingo4.General.Game.GameSequences;
import org.bingo4.Persistence.AscensionCloud;
import org.bingo4.Utility.TextUtil;
import org.bukkit.Bukkit;

import java.util.logging.Level;

/**
 * Downloads the whole "Ascension" folder of the AscensionWorlds repo into ./plugins/Ascension.
 * If the download fails the locally cached copy is used and the game keeps loading.
 */
public class DownloadAscensionFolder extends GameSequences {
    @Override
    public void run(GameLauncher game) {
        Bukkit.getScheduler().runTaskAsynchronously(plugin, () -> {
            try {
                int files = new AscensionCloud().downloadFolder(AscensionCloud.REPO_FOLDER, AscensionCloud.LOCAL_ROOT);
                Bukkit.getScheduler().runTask(plugin, () -> {
                    TextUtil.debugText("Downloaded Ascension folder from GitHub (" + files + " files updated)");
                    game.callback();
                });
            } catch (Exception e) {
                Bukkit.getScheduler().runTask(plugin, () -> {
                    TextUtil.debugText("Failed to download Ascension folder, using local copy: " + e.getMessage(), Level.SEVERE);
                });
            }
        });
    }
}
