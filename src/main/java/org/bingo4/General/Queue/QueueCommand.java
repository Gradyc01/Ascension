package org.bingo4.General.Queue;

import com.mojang.brigadier.exceptions.CommandSyntaxException;
import io.papermc.paper.command.brigadier.BasicCommand;
import io.papermc.paper.command.brigadier.CommandSourceStack;
import org.bingo4.Player.Data.PlayerUtil;
import org.bingo4.Utility.SoundUtil;
import org.bingo4.Utility.TextUtil;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class QueueCommand implements CommandExecutor, BasicCommand {

    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {
        if (!(commandSender instanceof Player)) {
            return false;
        }
        if (strings.length < 1) return false;
        Player p = ((Player) commandSender).getPlayer();

        switch (strings[0].toLowerCase()) {
            case "start" -> {
                if (!Queue.getInstance().startQueue(PlayerUtil.getPlayerData(p))) {
                    TextUtil.errorMessage(p, "A queue can not be started at this moment in time");
                };
            }
            case "accept" -> {
                if (Queue.getInstance().playerReadied(PlayerUtil.getPlayerData(p))) {
                    p.sendMessage(TextUtil.makeText("Successfully readied up!", TextUtil.GREEN));
                    SoundUtil.playHighPitchPling(p);
                } else {
                    TextUtil.errorMessage(p, "There is no queue currently happening or you've already readied up");
                }
            }
            default -> {
                TextUtil.errorMessage(p, "Incorrect command syntax");
                return false;
            }
        }
        return true;
    }

    @Override
    public void execute(@NotNull CommandSourceStack commandSourceStack, @NotNull String[] args) {

        if (!(commandSourceStack.getSender() instanceof Player p)) {
            return;
        }

        if (args.length < 1) {
            return;
        }

        switch (args[0].toLowerCase()) {
            case "start" -> {
                if (!Queue.getInstance().startQueue(PlayerUtil.getPlayerData(p))) {
                    TextUtil.errorMessage(p, "A queue can not be started at this moment in time");
                }
            }

            case "accept" -> {
                if (Queue.getInstance().playerReadied(PlayerUtil.getPlayerData(p))) {
                    p.sendMessage(TextUtil.makeText("Successfully readied up!", TextUtil.GREEN));
                    SoundUtil.playHighPitchPling(p);
                } else {
                    TextUtil.errorMessage(p, "There is no queue currently happening or you've already readied up");
                }
            }

            default -> {
                TextUtil.errorMessage(p, "Incorrect command syntax");
            }
        }
    }
}
