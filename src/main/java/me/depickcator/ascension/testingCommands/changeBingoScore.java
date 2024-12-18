package me.depickcator.ascension.testingCommands;

import me.depickcator.ascension.Ascension;
import me.depickcator.ascension.MainMenu.BingoBoard.BingoData;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class changeBingoScore implements CommandExecutor {
    private final Ascension ab;
    public changeBingoScore() {
        this.ab = Ascension.getInstance();
    }

    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {
        if (!(commandSender instanceof Player)) {
            return false;
        }
        if (strings.length != 2) return false;

        Player p = ((Player) commandSender).getPlayer();
        assert p != null;
        if (p.hasPermission("ascensionbingo.command.changeBingoScore")) {
            p.sendMessage("You do not have permission to use this command!");
            return false;
        }
        int index = findIndex(strings[1]);
        String operation = findOperation(strings[0]);
        if (index == -1 || operation == null) {
            return false;
        }
        BingoData bingoData = ab.getBingoData();

        if (operation.equals("give")) {
            bingoData.addScore(index, p);
        } else {
            bingoData.removeScore(index, p);
        }




        return false;
    }

    private int findIndex(String str) {
        try {
            return Integer.parseInt(str);
        } catch (NumberFormatException e) {
            return -1;
        }
    }

    private String findOperation(String str) {
        if (str.equalsIgnoreCase("give")) {
            return "give";
        } else if (str.equalsIgnoreCase("take")) {
            return "take";
        } else {
            return null;
        }
    }
}
