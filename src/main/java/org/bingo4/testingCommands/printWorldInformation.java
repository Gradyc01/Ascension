package org.bingo4.testingCommands;

import io.papermc.paper.command.brigadier.BasicCommand;
import io.papermc.paper.command.brigadier.CommandSourceStack;
import org.bingo4.Bingo4;
import org.bingo4.Utility.TextUtil;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class printWorldInformation implements CommandExecutor, BasicCommand {

    private final Bingo4 plugin;

    public printWorldInformation() {
        this.plugin = Bingo4.getInstance();
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

        int globalScore = plugin.getLocationCheck().getPercentageScore();

        TextColor color = globalScore >= 50
                ? globalScore >= 80
                ? TextUtil.GREEN
                : TextUtil.YELLOW
                : TextUtil.RED;

        Component text = TextUtil.topBorder(TextUtil.DARK_GRAY);

        text = text.append(TextUtil.makeText(
                "\n                World Score: " + globalScore + "%",
                color,
                true,
                false
        ));

        text = text.append(plugin.getLocationCheck().printLocations());
        text = text.append(TextUtil.bottomBorder(TextUtil.DARK_GRAY));

        p.sendMessage(text);

        return true;
    }
}