package org.bingo4.General.Game.Reset.Sequences;

import org.bingo4.General.Game.GameLauncher;
import org.bingo4.General.Game.GameSequences;
import org.bingo4.Persistence.AscensionCloud;
import org.bingo4.Utility.TextUtil;
import org.bukkit.Bukkit;

/**
 * Re-downloads Ascension/PlayerData from GitHub so the stats written at the end of the game
 * are added on top of the most up-to-date cloud copy. Must run BEFORE SaveGameStatistics.
 */
public class DownloadPlayerStats extends GameSequences {
    @Override
    public void run(GameLauncher game) {
        Bukkit.getScheduler().runTaskAsynchronously(plugin, () -> {
            try {
                int files = new AscensionCloud().downloadFolder(
                        AscensionCloud.REPO_FOLDER + "/PlayerData",
                        AscensionCloud.LOCAL_ROOT.resolve("PlayerData"));
                Bukkit.getScheduler().runTask(plugin, () -> {
                    TextUtil.debugText("Refreshed PlayerData from GitHub (" + files + " files updated)");
                    game.callback();
                });
            } catch (Exception e) {
                Bukkit.getScheduler().runTask(plugin, () -> {
                    plugin.getLogger().severe("Failed to refresh PlayerData from GitHub, using local copy: " + e.getMessage());
                    game.callback();
                });
            }
        });
    }
}
