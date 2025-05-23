package me.depickcator.ascension.General;

import me.depickcator.ascension.Ascension;
import me.depickcator.ascension.Player.Data.PlayerData;
import me.depickcator.ascension.Player.Data.PlayerUtil;
import me.depickcator.ascension.Utility.SoundUtil;
import me.depickcator.ascension.Utility.TextUtil;
import org.bukkit.GameRule;
import org.bukkit.World;
import org.bukkit.entity.Player;

import java.util.List;

public class PauseGame {
    private final Ascension plugin;
    private final GameStates gameState;
    public PauseGame() {
        plugin = Ascension.getInstance();
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
        for (World world : List.of(plugin.getWorld(), plugin.getNether())) {
            world.setGameRule(GameRule.DO_DAYLIGHT_CYCLE, false);
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
        for (World world : List.of(plugin.getWorld(), plugin.getNether())) {
            world.setGameRule(GameRule.DO_DAYLIGHT_CYCLE, true);
//            world.setDifficulty(Difficulty.NORMAL);
        }
    }


}
