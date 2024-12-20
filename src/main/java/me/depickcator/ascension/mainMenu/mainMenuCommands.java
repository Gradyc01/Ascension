package me.depickcator.ascension.MainMenu;

import me.depickcator.ascension.MainMenu.Unlocks.NewUnlocksGUI;
import me.depickcator.ascension.Player.Cooldowns.CombatTimer;
import me.depickcator.ascension.Player.PlayerData;
import me.depickcator.ascension.Player.PlayerUtil;
import me.depickcator.ascension.MainMenu.BingoBoard.BingoBoardGUI;
import me.depickcator.ascension.MainMenu.Command.CommandGUI;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class mainMenuCommands implements CommandExecutor {

    public mainMenuCommands() {
    }

    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {
        if (!(commandSender instanceof Player)) {
            return false;
        }
        Player p = ((Player) commandSender).getPlayer();
        PlayerData pD = PlayerUtil.getPlayerData(p);
        if (strings.length != 1 || CombatTimer.getInstance().isOnCooldown(p)) {
            return false;
        }
        String guiBoardName = strings[0];
        switch (guiBoardName.toLowerCase()) {
            case "board" -> {
                new BingoBoardGUI(pD);
            }
            case "unlocks-1" -> {
//                new UnlocksGUI_1(ab, p);
                new NewUnlocksGUI(pD, (char) 1);
            }
            case "unlocks-2" -> {
                new NewUnlocksGUI(pD, (char) 2);
//                new UnlocksGUI_2(ab, p);
            }
            case "commands" -> {
                new CommandGUI(pD);
            }
            case "skills" -> {
                new SkillsGUI(pD);
            }
            default -> {
                return false;
            }
        }

        return false;
    }
}
