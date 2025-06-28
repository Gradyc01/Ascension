package me.depickcator.ascension.testingCommands;

import me.depickcator.ascension.Utility.TextUtil;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.WorldCreator;
import org.bukkit.WorldType;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class Debugger implements CommandExecutor {

   private static boolean debugger = false;
    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {
        if (!(commandSender instanceof Player)) {
            return false;
        }
        Player p = (Player) commandSender;
        p.sendMessage(TextUtil.makeText("Changed State of the debugger", TextUtil.DARK_GREEN));
        debugger = !debugger;

        return true;
    }

//    public World createNewWorld(String worldName) {
//        // Check if the world already exists to avoid conflicts
//        if (Bukkit.getWorld(worldName) == null) {
//            WorldCreator worldCreator = new WorldCreator(worldName);
//
//            worldCreator.environment(World.Environment.NORMAL); // You can change this (e.g., to NETHER or THE_END)
//            worldCreator.type(WorldType.NORMAL); // Use different world types like FLAT, LARGE_BIOMES, etc.
//            World newWorld = worldCreator.createWorld(); // Actually creates the world
//
//            // Optionally, you can teleport a player to this world or manipulate it further
//            Bukkit.getLogger().info("New world '" + worldName + "' created successfully!");
//            return newWorld;
//        } else {
//            Bukkit.getLogger().warning("World with the name '" + worldName + "' already exists!");
//        }
//        return null;
//    }

    public static boolean getDebugger() {
        return debugger;
    }

}
