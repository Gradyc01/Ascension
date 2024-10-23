package me.depickcator.ascensionBingo.General.commands;

import me.depickcator.ascensionBingo.AscensionBingo;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.PluginManager;
import org.bukkit.scoreboard.*;
import org.jetbrains.annotations.NotNull;

public class resetGameCommand implements CommandExecutor {
    PluginManager pm;
    AscensionBingo plugin;
    ScoreboardManager scoreboardManager;
    Scoreboard bingoScoreboard;
    Objective bingodata;
    public resetGameCommand(PluginManager manager, AscensionBingo plugin) {
        pm = manager;
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {
        if (!(commandSender instanceof Player)) {
            return false;
        }
        Player p = ((Player) commandSender).getPlayer();
        try {
            assert p != null;
        } catch (Exception e) {
            return false;
        }
        plugin.getBingoData().resetPlayers();
        p.sendMessage("Placeholder Message For Reset Game");
        return true;

    }
}
