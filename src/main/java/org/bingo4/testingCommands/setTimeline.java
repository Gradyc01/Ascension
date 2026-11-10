package org.bingo4.testingCommands;

import io.papermc.paper.command.brigadier.BasicCommand;
import io.papermc.paper.command.brigadier.CommandSourceStack;
import org.bingo4.Bingo4;
import org.bingo4.Player.Data.PlayerData;
import org.bingo4.Player.Data.PlayerUtil;
import org.bingo4.Timeline.Timeline;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class setTimeline implements CommandExecutor, BasicCommand {

    public setTimeline() {
    }

    // Legacy Bukkit command implementation
    @Override
    public boolean onCommand(
            @NotNull CommandSender commandSender,
            @NotNull Command command,
            @NotNull String s,
            @NotNull String[] strings
    ) {
        return executeCommand(commandSender, strings);
    }

    // New Paper command implementation
    @Override
    public void execute(
            @NotNull CommandSourceStack commandSourceStack,
            @NotNull String[] args
    ) {
        executeCommand(commandSourceStack.getSender(), args);
    }

    private boolean executeCommand(CommandSender commandSender, String[] args) {
        if (!(commandSender instanceof Player p)) {
            return false;
        }

        PlayerData playerData = PlayerUtil.getPlayerData(p);
        if (playerData == null) {
            return false;
        }

        if (args.length != 2) {
            return false;
        }

        int time;

        try {
            time = Integer.parseInt(args[1]);
        } catch (NumberFormatException e) {
            return false;
        }

        Timeline timeline = Bingo4.getInstance()
                .getSettingsUI()
                .getSettings()
                .getTimeline();

        String mode = args[0];

        if (mode.equalsIgnoreCase("set")) {
            timeline.setTime(time);
        } else if (mode.equalsIgnoreCase("run")) {
            timeline.pauseTimeline();
            timeline.setTime(time);
            timeline.startTimeline();
        } else {
            return false;
        }

        return true;
    }
}