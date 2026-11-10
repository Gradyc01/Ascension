package org.bingo4.General.Game;

import io.papermc.paper.command.brigadier.BasicCommand;
import io.papermc.paper.command.brigadier.CommandSourceStack;
import org.bingo4.Bingo4;
import org.bingo4.General.Game.Load.LoadGame;
import org.bingo4.General.Game.Pause.PauseGame;
import org.bingo4.General.Game.ReSeed.ReSeed;
import org.bingo4.General.Game.Reset.ResetGame;
import org.bingo4.General.Game.Start.StartGame;
import org.bingo4.Utility.TextUtil;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class GameCommand implements CommandExecutor, TabCompleter, BasicCommand {

    private final Bingo4 ab;

    private static final List<String> SUBCOMMANDS = List.of(
            "start",
            "load",
            "reset",
            "pause",
            "relaunch",
            "reseed"
    );

    public GameCommand() {
        this.ab = Bingo4.getInstance();
    }

    /* Legacy Bukkit command implementation */
    @Override
    public boolean onCommand(
            @NotNull CommandSender commandSender,
            @NotNull Command command,
            @NotNull String s,
            @NotNull String[] strings
    ) {
        return executeCommand(commandSender, strings);
    }

    /* New Paper command implementation */
    @Override
    public void execute(
            @NotNull CommandSourceStack commandSourceStack,
            @NotNull String[] args
    ) {
        executeCommand(commandSourceStack.getSender(), args);
    }

    private boolean executeCommand(CommandSender commandSender, String[] strings) {
        if (strings.length == 0 || strings.length == 2 || strings.length > 4) {
            sendMisuseMessage(commandSender);
            return false;
        }

        Player p = commandSender instanceof Player player ? player : null;

        switch (strings[0].toLowerCase()) {
            case "start" -> forceStartGame();

            case "reset" -> resetGame();

            case "reset-" -> new ResetGame(true);

            case "relaunch" -> new ResetGame(
                    false,
                    Bingo4.getSpawn(),
                    Bingo4.getInstance().getWorld().getSeed()
            );

            case "pause" -> new PauseGame();

            case "reseed" -> new ReSeed();

            case "load" -> {
                if (strings.length == 4) {
                    try {
                        loadGame(
                                Integer.parseInt(strings[1]),
                                Integer.parseInt(strings[2]),
                                Integer.parseInt(strings[3])
                        );
                    } catch (NumberFormatException e) {
                        sendMisuseMessage(commandSender);
                        return false;
                    }

                } else if (strings.length == 3) {
                    try {
                        int x = Integer.parseInt(strings[1]);
                        int z = Integer.parseInt(strings[2]);

                        loadGame(
                                x,
                                Bingo4.getInstance().getWorld().getHighestBlockYAt(x, z),
                                z
                        );
                    } catch (NumberFormatException e) {
                        sendMisuseMessage(commandSender);
                        return false;
                    }

                } else if (p != null) {
                    loadGame(p.getLocation());
                } else {
                    sendMisuseMessage(commandSender);
                    return false;
                }
            }

            default -> {
                sendMisuseMessage(commandSender);
                return false;
            }
        }

        return true;
    }

    private void sendMisuseMessage(CommandSender sender) {
        if (sender instanceof Player p) {
            TextUtil.errorMessage(p, "The command is misused");
        } else {
            TextUtil.debugText("The command is misused");
        }
    }

    private void resetGame() {
        new ResetGame(false);
    }

    private void loadGame(int x, int y, int z) {
        Location loc = new Location(
                Bingo4.getInstance().getWorld(),
                x + 0.5,
                y,
                z + 0.5
        );

        loadGame(loc);
    }

    private void loadGame(Location loc) {
        new LoadGame(loc, loc, loc.getWorld().getSeed());

        String text = "Successfully loaded game at ("
                + loc.getBlockX() + ", "
                + loc.getBlockY() + ", "
                + loc.getBlockZ() + ")";

        TextUtil.debugText(text);
    }

    private void forceStartGame() {
        if (ab.getGameState().checkState(GameStates.LOBBY_NORMAL)) {
            TextUtil.debugText("Attempting to force start game...");
            new StartGame();
        }
    }

    /* Legacy Bukkit tab completion */
    @Override
    public @Nullable List<String> onTabComplete(
            @NotNull CommandSender commandSender,
            @NotNull Command command,
            @NotNull String s,
            @NotNull String[] strings
    ) {
        return SUBCOMMANDS;
    }

    /* Paper BasicCommand tab completion */
    @Override
    public @NotNull List<String> suggest(
            @NotNull CommandSourceStack commandSourceStack,
            @NotNull String[] args
    ) {
        if (args.length == 1) {
            return SUBCOMMANDS.stream()
                    .filter(option -> option.startsWith(args[0].toLowerCase()))
                    .toList();
        }

        return List.of();
    }
}