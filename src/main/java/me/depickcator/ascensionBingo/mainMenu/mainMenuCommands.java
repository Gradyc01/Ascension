package me.depickcator.ascensionBingo.mainMenu;

import me.depickcator.ascensionBingo.AscensionBingo;
import me.depickcator.ascensionBingo.mainMenu.BingoBoard.BingoBoardGUI;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class mainMenuCommands implements CommandExecutor {
    private Object gui;
    private AscensionBingo ab;

    public mainMenuCommands(AscensionBingo ab) {
        this.ab = ab;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {
        if (!(commandSender instanceof Player)) {
            return false;
        }
        Player p = ((Player) commandSender).getPlayer();
        if (strings.length != 1) {
            return false;
        }
        String guiBoardName = strings[0];
        if (guiBoardName.equalsIgnoreCase("board")) {
            gui = new BingoBoardGUI(ab, p);
        }

        return false;
    }
}
