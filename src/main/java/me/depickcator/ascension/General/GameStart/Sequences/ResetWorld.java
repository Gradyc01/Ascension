package me.depickcator.ascension.General.GameStart.Sequences;

import me.depickcator.ascension.General.GameStart.GameStartSequence;
import me.depickcator.ascension.General.GameStart.StartGame;
import me.depickcator.ascension.Utility.TextUtil;
import org.bukkit.Difficulty;
import org.bukkit.GameRule;
import org.bukkit.World;
import org.bukkit.WorldBorder;

public class ResetWorld extends GameStartSequence {
    public ResetWorld() {

    }

    @Override
    public void run(StartGame game) {
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
