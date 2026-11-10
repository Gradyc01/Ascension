package org.bingo4.Teams;

import io.papermc.paper.command.brigadier.BasicCommand;
import io.papermc.paper.command.brigadier.CommandSourceStack;
import org.bingo4.Bingo4;
import org.bingo4.General.Game.GameStates;
import org.bingo4.Player.Data.PlayerData;
import org.bingo4.Player.Data.PlayerUtil;
import org.bingo4.Utility.TextUtil;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class TeamCommand implements CommandExecutor, TabCompleter, BasicCommand {

    private final Bingo4 plugin;

    private static final List<String> SUBCOMMANDS =
            List.of("invite", "leave", "accept", "reject", "list");

    public TeamCommand() {
        this.plugin = Bingo4.getInstance();
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

    // Paper command execution
    @Override
    public void execute(
            @NotNull CommandSourceStack commandSourceStack,
            @NotNull String[] args
    ) {
        executeCommand(commandSourceStack.getSender(), args);
    }

    private boolean executeCommand(CommandSender commandSender, String[] args) {
        if (!(commandSender instanceof Player p)) {
            return false;
        }

        if (args.length < 1 || args.length > 2) {
            return false;
        }

        if (!plugin.getGameState().checkState(GameStates.LOBBY_NORMAL)) {
            TextUtil.errorMessage(p, "You may not use this command currently!");
            return false;
        }

        PlayerData pD = PlayerUtil.getPlayerData(p);
        if (pD == null) {
            return false;
        }

        // These commands require a player argument.
        if ((args[0].equalsIgnoreCase("invite")
                || args[0].equalsIgnoreCase("accept")
                || args[0].equalsIgnoreCase("reject"))
                && args.length < 2) {
            TextUtil.errorMessage(p, "Please specify a player.");
            return false;
        }

        commandFinder(p, pD, args);
        return true;
    }

    private void commandFinder(Player p, PlayerData pD, String[] args) {
        switch (args[0].toLowerCase()) {
            case "invite" -> {
                Player invited = Bukkit.getPlayer(args[1]);

                if (invited == null || invited.equals(p)) {
                    TextUtil.errorMessage(p, "Failed to find player.");
                    return;
                }

                PlayerData invitedData = PlayerUtil.getPlayerData(invited);
                if (invitedData == null) {
                    TextUtil.errorMessage(p, "Failed to find player.");
                    return;
                }

                pD.getPlayerTeam().sendInvite(invited);
            }

            case "leave" -> pD.getPlayerTeam().leaveTeam();

            case "accept" -> {
                Player sender = Bukkit.getPlayer(args[1]);

                if (sender == null || sender.equals(p)) {
                    TextUtil.errorMessage(p, "Failed to find player.");
                    return;
                }

                PlayerData senderData = PlayerUtil.getPlayerData(sender);
                if (senderData == null) {
                    TextUtil.errorMessage(p, "Failed to find player.");
                    return;
                }

                pD.getPlayerTeam().acceptInvite(senderData);
            }

            case "reject" -> {
                Player sender = Bukkit.getPlayer(args[1]);

                if (sender == null || sender.equals(p)) {
                    TextUtil.errorMessage(p, "Failed to find player.");
                    return;
                }

                PlayerData senderData = PlayerUtil.getPlayerData(sender);
                if (senderData == null) {
                    TextUtil.errorMessage(p, "Failed to find player.");
                    return;
                }

                pD.getPlayerTeam().rejectInvite(senderData);
            }

            case "list" -> {
                try {
                    pD.getPlayerTeam().getTeam().teamList(pD);
                } catch (Exception e) {
                    TextUtil.errorMessage(p, "You are currently not in a party");
                }
            }

            default -> p.performCommand("party invite " + args[0]);
        }
    }

    // Legacy Bukkit tab completion
    @Override
    public @Nullable List<String> onTabComplete(
            @NotNull CommandSender commandSender,
            @NotNull Command command,
            @NotNull String s,
            @NotNull String[] strings
    ) {
        if (strings.length == 1) {
            return SUBCOMMANDS;
        }

        if (strings.length == 2 &&
                (strings[0].equalsIgnoreCase("invite")
                        || strings[0].equalsIgnoreCase("accept")
                        || strings[0].equalsIgnoreCase("reject"))) {
            return Bukkit.getOnlinePlayers().stream()
                    .map(Player::getName)
                    .toList();
        }

        return null;
    }

    // Paper tab completion
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

        if (args.length == 2 &&
                (args[0].equalsIgnoreCase("invite")
                        || args[0].equalsIgnoreCase("accept")
                        || args[0].equalsIgnoreCase("reject"))) {
            String input = args[1].toLowerCase();

            return Bukkit.getOnlinePlayers().stream()
                    .map(Player::getName)
                    .filter(name -> name.toLowerCase().startsWith(input))
                    .toList();
        }

        return List.of();
    }
}