package me.depickcator.ascensionBingo.General.commands;

import me.depickcator.ascensionBingo.AscensionBingo;
import me.depickcator.ascensionBingo.mainMenu.BingoBoard.BingoData;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.PluginManager;
import org.bukkit.scoreboard.*;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

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

        switch (strings[0].toLowerCase()) {
            case "start" -> {
                p.sendMessage("PLACEHOLDER MESSAGE FOR WHEN SOMEONE STARTS THE GAME");
            }
            case "reset" -> {
                resetGame(p);
            }
            case "forcestart" -> {
                BingoData bingoData = ab.getBingoData();
                bingoData.setItems(bingoData.getItemList().get25());
                p.sendMessage("PLACEHOLDER MESSAGE FOR WHEN SOMEONE FORCESTARTS THE GAME");
                ArrayList<ItemStack> item = bingoData.getItems();
                for (ItemStack i : item) {
                    p.sendMessage(i.toString());
                }
            }
            case "load" -> {
                ab.setBingoData(new BingoData(ab));
                p.sendMessage("PLACEHOLDER MESSAGE FOR WHEN SOMEONE LOADS THE GAME");
            }
            default -> {
                p.sendMessage("ERRORED");
                return false;
            }
        }


        return true;

    }

    private void resetGame(Player p) {
        ab.getBingoData().resetPlayers();
        p.sendMessage("Placeholder Message For Reset Game");
    }
}
