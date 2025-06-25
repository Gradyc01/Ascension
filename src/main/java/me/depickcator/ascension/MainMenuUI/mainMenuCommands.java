
package me.depickcator.ascension.MainMenuUI;

import me.depickcator.ascension.Timeline.Events.SoulShop.SoulShopGUI;
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
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class mainMenuCommands implements CommandExecutor, TabCompleter {

    public mainMenuCommands() {
    }

    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {
        if (!(commandSender instanceof Player)) {
            return false;
        }
        Player p = ((Player) commandSender).getPlayer();
        PlayerData pD = PlayerUtil.getPlayerData(p);
        if (strings.length < 1 || strings.length > 2 || CombatTimer.getInstance().isOnCooldown(p)) {
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
            case "unlocks" -> {
                int num;
                if (strings.length == 1) {
                    num = 1;
                } else {
                    int page = Integer.parseInt(strings[1]);
                    if (page > 5 || page < 1) {
                        num = 1;
                    } else {
                        num = page;
                    }
                }
                new UnlocksGUI(pD, (char) num);
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
            case "shop" -> {
                new SoulShopGUI(pD);
            }
            default -> {
                return false;
            }
        }

        return false;
    }

    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {
        return List.of("board", "unlocks", "commands", "skills", "events", "shop");
    }
}
