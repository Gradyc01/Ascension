package me.depickcator.ascension.General.Queue;

import me.depickcator.ascension.Player.Data.PlayerUtil;
import me.depickcator.ascension.Utility.TextUtil;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class QueueCommand implements CommandExecutor {

    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {
        if (!(commandSender instanceof Player)) {
            return false;
        }
        if (strings.length < 1) return false;
        Player p = ((Player) commandSender).getPlayer();

        if (strings[0].equalsIgnoreCase("accept")) {

        }

        switch (strings[0].toLowerCase()) {
            case "start" -> {
            }
            case "accept" -> {
                if (Queue.getInstance().playerReadied(PlayerUtil.getPlayerData(p))) {
                    p.sendMessage(TextUtil.makeText("Successfully readied up!", TextUtil.GREEN));
                } else {
                    TextUtil.errorMessage(p, "There is no queue currently happening");
                }
            }
            default -> {
                TextUtil.errorMessage(p, "Incorrect command syntax");
                return false;
            }
        }
        return true;
    }
}
