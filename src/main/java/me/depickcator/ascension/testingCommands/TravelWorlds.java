package me.depickcator.ascension.testingCommands;

import me.depickcator.ascension.Ascension;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.bukkit.generator.WorldInfo;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public class TravelWorlds implements CommandExecutor, TabCompleter {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String @NotNull [] args) {
        if (!(sender instanceof Player)) {
            return false;
        }
        Ascension plugin = Ascension.getInstance();
        Player p =  (Player) sender;
//        World world = switch(args[0]) {
//            case "nether" -> plugin.getNether();
//            case "spawn" -> plugin.getSpawnWorld();
//            default -> plugin.getWorld();
//        };
        World world = p.getWorld();
        for (World w : Bukkit.getWorlds()) {
            if (w.getName().equals(args[0])) {
                world = w;
                break;
            }
        }
        Location loc = p.getLocation().clone();
        loc.setWorld(world);
        p.teleport(loc);
        return true;
    }

    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String @NotNull [] args) {
        List<String> completions = new ArrayList<>();
        for (World world : Bukkit.getWorlds()) {
            completions.add(world.getName());
        }
        return completions;
    }
}
