package me.depickcator.ascension.General.Game.Load.Sequences;

import me.depickcator.ascension.General.Game.GameLauncher;
import me.depickcator.ascension.General.Game.GameSequences;
import org.bukkit.Bukkit;

public class DeletePreviousIterations extends GameSequences {
    @Override
    public void run(GameLauncher game) {
        Bukkit.dispatchCommand(Bukkit.getConsoleSender(),"kill @e[type=minecraft:armor_stand, name=\"SpawnCoords\"]");
        Bukkit.dispatchCommand(Bukkit.getConsoleSender(),"kill @e[type=minecraft:text_display]");
        Bukkit.dispatchCommand(Bukkit.getConsoleSender(),"kill @e[type=minecraft:item_display]");
        Bukkit.dispatchCommand(Bukkit.getConsoleSender(),"kill @e[tag=lobby]");
        Bukkit.dispatchCommand(Bukkit.getConsoleSender(),"forceload remove all");
        game.callback();
    }
}
