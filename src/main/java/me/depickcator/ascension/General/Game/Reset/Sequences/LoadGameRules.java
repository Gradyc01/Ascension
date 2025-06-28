package me.depickcator.ascension.General.Game.Reset.Sequences;

import me.depickcator.ascension.General.Game.GameLauncher;
import me.depickcator.ascension.General.Game.GameSequences;
import org.bukkit.GameRule;
import org.bukkit.World;

public class LoadGameRules extends GameSequences {
    @Override
    public void run(GameLauncher game) {
        loadGameRules(plugin.getSpawnWorld());
        plugin.getSpawnWorld().setGameRule(GameRule.DO_MOB_SPAWNING, false);
        loadGameRules(plugin.getWorld());
        loadGameRules(plugin.getNether());

        game.callback();
    }

    private void loadGameRules(World world) {
        world.setGameRule(GameRule.SPAWN_RADIUS, 0);
        world.setGameRule(GameRule.KEEP_INVENTORY, true);
        world.setGameRule(GameRule.DO_IMMEDIATE_RESPAWN, true);
        world.setGameRule(GameRule.NATURAL_REGENERATION, false);
        world.setGameRule(GameRule.DO_DAYLIGHT_CYCLE, false);
        // world.setGameRule(GameRule.COMMAND_BLOCK_OUTPUT, true);
        world.setGameRule(GameRule.DO_INSOMNIA, false);
        world.setGameRule(GameRule.LOCATOR_BAR,false); //TODO: False For Now
        world.setGameRule(GameRule.DO_WEATHER_CYCLE, false);
        world.setTime(1000);
    }
}
