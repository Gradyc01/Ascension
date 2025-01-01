package me.depickcator.ascension.MainMenuUI;

import me.depickcator.ascension.Utility.TextUtil;
import me.depickcator.ascension.MainMenuUI.Map.MapGUI;
import me.depickcator.ascension.MainMenuUI.Skills.SkillsGUI;
import me.depickcator.ascension.MainMenuUI.Unlocks.UnlocksGUI;
import me.depickcator.ascension.Player.Cooldowns.CombatTimer;
import me.depickcator.ascension.Player.Data.PlayerData;
import me.depickcator.ascension.Player.Data.PlayerUtil;
import me.depickcator.ascension.MainMenuUI.BingoBoard.BingoBoardGUI;
import me.depickcator.ascension.MainMenuUI.Command.CommandGUI;
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
        if (!pD.checkState(PlayerData.STATE_ALIVE)) {
            TextUtil.errorMessage(p, "You can't use this command in your state!");
            return false;
        }
        String guiBoardName = strings[0];
        switch (guiBoardName.toLowerCase()) {
            case "board" -> {
                new BingoBoardGUI(pD);
            }
            case "unlocks-1" -> {
//                new UnlocksGUI_1(ab, p);
                new UnlocksGUI(pD, (char) 1);
            }
            case "unlocks-2" -> {
                new UnlocksGUI(pD, (char) 2);
//                new UnlocksGUI_2(ab, p);
            }
            case "commands" -> {
                new CommandGUI(pD);
            }
            case "skills" -> {
                new SkillsGUI(pD);
            }
            case "events" -> {
                new MapGUI(pD);
            }
            default -> {
                return false;
            }
        }

        return false;
    }
}
