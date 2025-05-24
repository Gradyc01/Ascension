package me.depickcator.ascension.General.Game.Start.Sequences;

import me.depickcator.ascension.General.Game.GameLauncher;
import me.depickcator.ascension.General.Game.GameSequences;
import me.depickcator.ascension.Utility.TextUtil;
import org.bukkit.Difficulty;
import org.bukkit.GameRule;
import org.bukkit.World;

public class ResetWorld extends GameSequences {
    public ResetWorld() {

    }

    @Override
    public void run(GameLauncher game) {
        World world = plugin.getWorld();
        world.setGameRule(GameRule.DO_DAYLIGHT_CYCLE, true);
        world.setDifficulty(Difficulty.NORMAL);
//                world.setPVP(false);
        world.setTime(0);
        world.setWeatherDuration(0);
        TextUtil.debugText("Start World");
        game.callback();
    }
}
