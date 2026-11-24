package org.bingo4.General.Game.Load.Sequences;

import org.bingo4.Bingo4;
import org.bingo4.General.Game.GameLauncher;
import org.bingo4.General.Game.GameSequences;
import org.bingo4.Items.ItemLists.BingoItemManager;
import org.bingo4.MainMenuUI.BingoBoard.BingoData;
import org.bingo4.Utility.TextUtil;
import org.bukkit.Bukkit;
import org.bukkit.Material;

import java.util.concurrent.CompletableFuture;

public class SetBingoData extends GameSequences {

    @Override
    public void run(GameLauncher game) {
        plugin.setBingoData(new BingoData(plugin));
        CompletableFuture.supplyAsync(() -> {
            try {
                return BingoItemManager.downloadJson();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }).thenAccept(json -> {

            Bukkit.getScheduler().runTask(plugin, () -> {
                plugin.setManager(BingoItemManager.parse(
                        json,
                        BingoItemManager.loadScan(plugin.getWorld().getWorldPath().toAbsolutePath() + "/scan.json"),
                        plugin.getLogger()
                ));
                game.callback();
                TextUtil.debugText("Bingo items loaded!");
            });

        }).exceptionally(error -> {
            throw new RuntimeException(
                    "Failed to load bingo items: " + error.getMessage()
            );
        });
    }
}
