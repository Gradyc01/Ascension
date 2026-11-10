package org.bingo4.MainMenuUI;

import io.papermc.paper.command.brigadier.BasicCommand;
import io.papermc.paper.command.brigadier.CommandSourceStack;
import org.bingo4.MainMenuUI.BingoBoard.BingoBoardGUI;
import org.bingo4.MainMenuUI.Command.CommandGUI;
import org.bingo4.MainMenuUI.Map.MapGUI;
import org.bingo4.Player.Cooldowns.CombatTimer;
import org.bingo4.Player.Data.PlayerData;
import org.bingo4.Player.Data.PlayerUtil;
import org.bingo4.Utility.TextUtil;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class mainMenuCommands implements CommandExecutor, TabCompleter, BasicCommand {

    private static final List<String> SUBCOMMANDS =
            List.of("board", "unlocks", "commands", "skills", "events", "shop");

    public mainMenuCommands() {
    }

    // Legacy Bukkit command execution
    @Override
    public boolean onCommand(
            @NotNull CommandSender commandSender,
            @NotNull Command command,
            @NotNull String s,
            @NotNull String[] strings
    ) {
        return executeCommand(commandSender, strings);
    }

    // New Paper command execution
    @Override
    public void execute(
            @NotNull CommandSourceStack commandSourceStack,
            @NotNull String[] args
    ) {
        executeCommand(commandSourceStack.getSender(), args);
    }

    private boolean executeCommand(CommandSender commandSender, String[] strings) {
        if (!(commandSender instanceof Player p)) {
            return false;
        }

        if (strings.length < 1 || strings.length > 2
                || CombatTimer.getInstance().isOnCooldown(p)) {
            return false;
        }

        PlayerData pD = PlayerUtil.getPlayerData(p);

        if (pD == null) {
            return false;
        }

        if (!pD.checkState(PlayerData.STATE_ALIVE)) {
            TextUtil.errorMessage(p, "You can't use this command in your state!");
            return false;
        }

        String guiBoardName = strings[0].toLowerCase();

        switch (guiBoardName) {
            case "board" -> new BingoBoardGUI(pD);

            case "commands" -> new CommandGUI(pD);

            case "events" -> new MapGUI(pD);

            // case "unlocks" -> {
            //     int page = 1;
            //
            //     if (strings.length == 2) {
            //         try {
            //             page = Integer.parseInt(strings[1]);
            //         } catch (NumberFormatException e) {
            //             return false;
            //         }
            //     }
            //
            //     if (page > 5 || page < 1) {
            //         page = 1;
            //     }
            //
            //     new UnlocksGUI(pD, (char) page);
            // }

            default -> {
                return false;
            }
        }

        return true;
    }

    // Legacy Bukkit tab completion
    @Override
    public @Nullable List<String> onTabComplete(
            @NotNull CommandSender commandSender,
            @NotNull Command command,
            @NotNull String s,
            @NotNull String[] strings
    ) {
        return SUBCOMMANDS;
    }

    // Paper BasicCommand tab completion
    @Override
    public @NotNull List<String> suggest(
            @NotNull CommandSourceStack commandSourceStack,
            @NotNull String[] args
    ) {
        if (args.length == 1) {
            String input = args[0].toLowerCase();

            return SUBCOMMANDS.stream()
                    .filter(option -> option.startsWith(input))
                    .toList();
        }

        return List.of();
    }
}