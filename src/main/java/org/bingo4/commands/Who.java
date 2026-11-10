package org.bingo4.commands;

import io.papermc.paper.command.brigadier.BasicCommand;
import io.papermc.paper.command.brigadier.CommandSourceStack;
import org.bingo4.Player.Data.PlayerData;
import org.bingo4.Player.Data.PlayerUtil;
import org.bingo4.Teams.Team;
import org.bingo4.Teams.TeamUtil;
import org.bingo4.Utility.TextUtil;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class Who implements CommandExecutor, BasicCommand {

    public Who() {
    }

    // Legacy Bukkit command implementation
    @Override
    public boolean onCommand(
            @NotNull CommandSender commandSender,
            @NotNull Command command,
            @NotNull String s,
            @NotNull String[] strings
    ) {
        return executeCommand(commandSender);
    }

    // New Paper command implementation
    @Override
    public void execute(
            @NotNull CommandSourceStack commandSourceStack,
            @NotNull String[] args
    ) {
        executeCommand(commandSourceStack.getSender());
    }

    private boolean executeCommand(CommandSender commandSender) {
        if (!(commandSender instanceof Player p)) {
            return false;
        }

        Component text = TextUtil.topBorder(TextUtil.YELLOW);

        for (Team team : TeamUtil.getEveryTeam()) {
            text = text.append(TextUtil.makeText("\n"));

            for (Player player : team.getTeamMembers()) {
                PlayerData pD = PlayerUtil.getPlayerData(player);

                if (pD == null) {
                    continue;
                }

                TextColor color = pD.checkState(PlayerData.STATE_ALIVE)
                        ? TextUtil.GREEN
                        : pD.checkState(PlayerData.STATE_DEAD)
                        ? TextUtil.RED
                        : TextUtil.GRAY;

                text = text.append(TextUtil.makeText(" " + player.getName(), color));
            }
        }

        text = text.append(TextUtil.bottomBorder(TextUtil.YELLOW));
        p.sendMessage(text);

        return true;
    }
}