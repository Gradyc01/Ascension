package org.bingo4.testingCommands;

import io.papermc.paper.command.brigadier.BasicCommand;
import io.papermc.paper.command.brigadier.CommandSourceStack;
import org.bingo4.Bingo4;
import org.bingo4.Structures.CampManager;
import org.bingo4.Structures.Structures;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

import static org.bingo4.Structures.Structures.*;

public class placeCamp implements CommandExecutor, TabCompleter, BasicCommand {

    private final Bingo4 plugin = Bingo4.getInstance();

    private static final List<Structures> CAMP_TYPES = List.of(
            BINGO_TURN_IN,
            MINING_CAMP_1,
            MINING_CAMP_2,
            MINING_CAMP_2_GEODE_VARIANT,
            MINING_CAMP_3,
            MINING_CAMP_3_LUSH_VARIANT,
            MINING_CAMP_3_SULFUR_VARIANT,
            NETHER_KOTH,
            MIDBOSS
    );

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

        CampManager campManager = plugin.getSettingsUI()
                .getSettings()
                .getTimeline()
                .getCampManager();

        if (args.length == 1) {
            try {
                campManager.generateCamps(
                        campManager.generateLocations(
                                1,
                                p.getLocation(),
                                1,
                                campManager.getCampType(args[0])
                        ),
                        campManager.getCampType(args[0])
                );
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }

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
        return CAMP_TYPES.stream().map(Structures::getId).toList();
    }

    // Paper tab completion
    @Override
    public @NotNull List<String> suggest(
            @NotNull CommandSourceStack commandSourceStack,
            @NotNull String[] args
    ) {
        return CAMP_TYPES.stream().map(Structures::getId).toList();
    }
}