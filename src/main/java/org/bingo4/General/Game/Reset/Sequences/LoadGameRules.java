package org.bingo4.General.Game.Reset.Sequences;

import org.bingo4.General.Game.GameLauncher;
import org.bingo4.General.Game.GameSequences;
import org.bukkit.GameRule;
import org.bukkit.GameRules;
import org.bukkit.World;

public class LoadGameRules extends GameSequences {
    @Override
    public void run(GameLauncher game) {
        loadGameRules(plugin.getSpawnWorld());
        plugin.getSpawnWorld().setGameRule(GameRules.SPAWN_MOBS, false);
        loadGameRules(plugin.getWorld());
        loadGameRules(plugin.getNether());
        plugin.getWorld().setGameRule(GameRules.ADVANCE_TIME, false);
        plugin.getWorld().setTime(1000);

        game.callback();
    }

    private void loadGameRules(World world) {
        world.setGameRule(GameRules.RESPAWN_RADIUS, 0);
        world.setGameRule(GameRules.KEEP_INVENTORY, true);
        world.setGameRule(GameRules.IMMEDIATE_RESPAWN, true);
        world.setGameRule(GameRules.NATURAL_HEALTH_REGENERATION, false);
        // world.setGameRule(GameRule.COMMAND_BLOCK_OUTPUT, true);
        world.setGameRule(GameRules.SPAWN_PHANTOMS, false);
        world.setGameRule(GameRules.LOCATOR_BAR,false); //TODO: False For Now
        world.setGameRule(GameRules.ADVANCE_WEATHER, false);
    }
}
