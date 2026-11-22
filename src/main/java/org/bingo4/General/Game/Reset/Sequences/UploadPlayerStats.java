package org.bingo4.General.Game.Reset.Sequences;

import org.bingo4.General.Game.GameLauncher;
import org.bingo4.General.Game.GameSequences;
import org.bingo4.Persistence.AscensionCloud;
import org.bingo4.Utility.TextUtil;
import org.bukkit.Bukkit;

import java.time.Instant;

/**
 * Commits the PlayerData files that changed (written by SaveGameStatistics) back to GitHub
 * as a single commit. Must run AFTER SaveGameStatistics.
 */
public class UploadPlayerStats extends GameSequences {
    @Override
    public void run(GameLauncher game) {
        AscensionCloud cloud = new AscensionCloud();
        if (!cloud.hasToken()) {
            plugin.getLogger().warning("No GITHUB_TOKEN found in plugins/Ascension/env.properties, PlayerData was NOT committed to GitHub.");
            game.callback();
            return;
        }
        Bukkit.getScheduler().runTaskAsynchronously(plugin, () -> {
            try {
                int files = cloud.commitChangedFiles(
                        AscensionCloud.LOCAL_ROOT.resolve("PlayerData"),
                        AscensionCloud.REPO_FOLDER + "/PlayerData",
                        "Update player stats " + Instant.now());
                Bukkit.getScheduler().runTask(plugin, () -> {
                    TextUtil.debugText("Committed " + files + " PlayerData file(s) to GitHub");
                    game.callback();
                });
            } catch (Exception e) {
                Bukkit.getScheduler().runTask(plugin, () -> {
                    plugin.getLogger().severe("Failed to commit PlayerData to GitHub: " + e.getMessage());
                    game.callback();
                });
            }
        });
    }
}
