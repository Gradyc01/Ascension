package me.depickcator.ascension.mainMenu;

import me.depickcator.ascension.Ascension;
import me.depickcator.ascension.Player.Cooldowns.CombatTimer;
import me.depickcator.ascension.Player.PlayerUtil;
import me.depickcator.ascension.mainMenu.BingoBoard.BingoBoardGUI;
import me.depickcator.ascension.mainMenu.Command.CommandGUI;
import me.depickcator.ascension.mainMenu.Unlocks.UnlocksGUI_1;
import me.depickcator.ascension.mainMenu.Unlocks.UnlocksGUI_2;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class mainMenuCommands implements CommandExecutor {
    private final Ascension ab;

    public mainMenuCommands() {
        this.ab = Ascension.getInstance();
    }

    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {
        if (!(commandSender instanceof Player)) {
            return false;
        }
        Player p = ((Player) commandSender).getPlayer();
        if (strings.length != 1 || CombatTimer.getInstance().isOnCooldown(p)) {
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
            case "commands" -> {
                new CommandGUI(ab, PlayerUtil.getPlayerData(p));
            }
            default -> {
                return false;
            }
        }

        return false;
    }
}
