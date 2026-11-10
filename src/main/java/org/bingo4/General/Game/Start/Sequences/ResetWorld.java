package org.bingo4.General.Game.Start.Sequences;

import org.bingo4.General.Game.GameLauncher;
import org.bingo4.General.Game.GameSequences;
import org.bingo4.Utility.TextUtil;
import org.bukkit.Difficulty;
import org.bukkit.GameRule;
import org.bukkit.GameRules;
import org.bukkit.World;

public class ResetWorld extends GameSequences {
    public ResetWorld() {

    }

    @Override
    public void run(GameLauncher game) {
        World world = plugin.getWorld();
        world.setGameRule(GameRules.ADVANCE_TIME, true);
        world.setDifficulty(Difficulty.NORMAL);
//                world.setPVP(false);
        world.setTime(0);
        world.setWeatherDuration(0);
        TextUtil.debugText("Start World");
        game.callback();
    }
}
