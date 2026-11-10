package org.bingo4.testingCommands;

import io.papermc.paper.command.brigadier.BasicCommand;
import io.papermc.paper.command.brigadier.CommandSourceStack;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public class TravelWorlds implements CommandExecutor, TabCompleter, BasicCommand {

    @Override
    public boolean onCommand(
            @NotNull CommandSender sender,
            @NotNull Command command,
            @NotNull String label,
            @NotNull String @NotNull [] args
    ) {
        return executeCommand(sender, args);
    }

    @Override
    public void execute(
            @NotNull CommandSourceStack commandSourceStack,
            @NotNull String[] args
    ) {
        executeCommand(commandSourceStack.getSender(), args);
    }

    private boolean executeCommand(CommandSender sender, String[] args) {
        if (!(sender instanceof Player p)) {
            return false;
        }

        if (args.length == 0) {
            return false;
        }

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

    // Legacy Bukkit tab completion
    @Override
    public @Nullable List<String> onTabComplete(
            @NotNull CommandSender sender,
            @NotNull Command command,
            @NotNull String label,
            @NotNull String @NotNull [] args
    ) {
        return getWorldSuggestions();
    }

    // Paper tab completion
    @Override
    public @NotNull List<String> suggest(
            @NotNull CommandSourceStack commandSourceStack,
            @NotNull String[] args
    ) {
        return getWorldSuggestions();
    }

    private List<String> getWorldSuggestions() {
        List<String> completions = new ArrayList<>();

        for (World world : Bukkit.getWorlds()) {
            completions.add(world.getName());
        }

        return completions;
    }
}