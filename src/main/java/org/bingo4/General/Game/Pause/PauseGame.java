package org.bingo4.General.Game.Pause;

import org.bingo4.Bingo4;
import org.bingo4.General.Game.GameStates;
import org.bingo4.Player.Data.PlayerData;
import org.bingo4.Player.Data.PlayerUtil;
import org.bingo4.Utility.SoundUtil;
import org.bingo4.Utility.TextUtil;
import org.bukkit.GameRule;
import org.bukkit.GameRules;
import org.bukkit.World;
import org.bukkit.entity.Player;

import java.util.List;

public class PauseGame {
    private final Bingo4 plugin;
    private final GameStates gameState;
    public PauseGame() {
        plugin = Bingo4.getInstance();
        gameState = plugin.getGameState();
        if (gameState.inGame()) {
            if (gameState.checkState(GameStates.GAME_PAUSED)) {
                unPauseGame();
            } else {
                pauseGame();
            }
        }
    }

    private void pauseGame() {
        gameState.setCurrentState(GameStates.GAME_PAUSED);
        plugin.getSettingsUI().getSettings().getTimeline().pauseTimeline();
        for (PlayerData playerData : PlayerUtil.getAllPlayingPlayers()) {
            Player player = playerData.getPlayer();
            playerData.freezePlayer(true);
            SoundUtil.playHighPitchPling(player);
            player.showTitle(TextUtil.makeTitle(
                    TextUtil.makeText("GAME PAUSED", TextUtil.RED, true, false),
                    TextUtil.makeText("Do not log out during this time", TextUtil.YELLOW),
                    0,
                    10,
                    0));
            player.sendMessage(TextUtil.makeText("The Game is temporarily Paused. Do not log out during this time", TextUtil.RED));

        }
        for (World world : List.of(plugin.getWorld())) {
            world.setGameRule(GameRules.ADVANCE_TIME, false);
//            world.setDifficulty(Difficulty.PEACEFUL);
        }


    }

    private void unPauseGame() {
        gameState.setCurrentState(gameState.getPreviousState());
        plugin.getSettingsUI().getSettings().getTimeline().startTimeline();
        for (PlayerData playerData : PlayerUtil.getAllPlayingPlayers()) {
            Player player = playerData.getPlayer();
            playerData.freezePlayer(false);
            SoundUtil.playHighPitchPling(player);
            player.sendMessage(TextUtil.makeText("The game has been resumed!", TextUtil.GREEN));
        }
        for (World world : List.of(plugin.getWorld())) {
            world.setGameRule(GameRules.ADVANCE_TIME, true);
//            world.setDifficulty(Difficulty.NORMAL);
        }
    }


}
