package me.depickcator.ascensionBingo.General.commands;

import me.depickcator.ascensionBingo.AscensionBingo;
import me.depickcator.ascensionBingo.mainMenu.BingoBoard.BingoData;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.ScoreboardManager;
import org.jetbrains.annotations.NotNull;

public class loadPluginCommand implements CommandExecutor {
    AscensionBingo ab;

    public loadPluginCommand(AscensionBingo ab) {
        this.ab = ab;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {
        if (!(commandSender instanceof Player)) {
            return false;
        }

        Player p = ((Player) commandSender).getPlayer();
        ab.setBingoData(new BingoData(ab));
        return true;
    }
}
