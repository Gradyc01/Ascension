package me.depickcator.ascensionBingo.General;

import me.depickcator.ascensionBingo.AscensionBingo;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.PluginManager;
import org.bukkit.scoreboard.*;
import org.jetbrains.annotations.NotNull;


public class GameCommand implements CommandExecutor {
    PluginManager pm;
    AscensionBingo ab;
    ScoreboardManager scoreboardManager;
    Scoreboard bingoScoreboard;
    Objective bingodata;
    public GameCommand(PluginManager manager, AscensionBingo plugin) {
        pm = manager;
        this.ab = plugin;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {
        if (!(commandSender instanceof Player)) {
            return false;
        }
        if (strings.length != 1) return false;

        Player p = ((Player) commandSender).getPlayer();
        try {
            assert p != null;
        } catch (Exception e) {
            return false;
        }
//        if (p.hasPermission("ascensionbingo.command.changeBingoScore")) {
//            p.sendMessage("You do not have permission to use this command!");
//            return false;
//        }

        switch (strings[0].toLowerCase()) {
            case "start" -> {
                p.sendMessage("PLACEHOLDER MESSAGE FOR WHEN SOMEONE STARTS THE GAME");
            }
            case "reset" -> {
                resetGame(p);
            }
            case "forcestart" -> {
                forceStartGame(p);
            }
            case "load" -> {
                loadGame(p);
            }
            default -> {
                p.sendMessage("ERRORED");
                return false;
            }
        }


        return true;

    }

    private void resetGame(Player p) {
        new ResetGame(ab, p);
    }

    private void loadGame(Player p) {
        // ab.setBingoData(new BingoData(ab));
        new LoadGame(ab, p);
//        resetGame(p);
        // ab.getGameState().setCurrentState(GameStates.LOBBY);
        p.sendMessage("Successfully loaded game");
    }

    private void forceStartGame(Player p) {
        if (!ab.getGameState().checkState(GameStates.LOBBY)) return;

        p.sendMessage("Successfully force started game!");
        new StartGame(ab, p);
    }


}
