package me.depickcator.ascensionBingo.mainMenu;

import me.depickcator.ascensionBingo.AscensionBingo;
import me.depickcator.ascensionBingo.mainMenu.BingoBoard.BingoBoardGUI;
import me.depickcator.ascensionBingo.mainMenu.Unlocks.UnlocksGUI_1;
import me.depickcator.ascensionBingo.mainMenu.Unlocks.UnlocksGUI_2;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class mainMenuCommands implements CommandExecutor {
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
        switch (guiBoardName.toLowerCase()) {
            case "board" -> {
                new BingoBoardGUI(ab, p);
            }
            case "unlocks-1" -> {
                new UnlocksGUI_1(ab, p);
            }
            case "unlocks-2" -> {
                new UnlocksGUI_2(ab, p);
            }
            default -> {
                return false;
            }
        }

        return false;
    }
}
