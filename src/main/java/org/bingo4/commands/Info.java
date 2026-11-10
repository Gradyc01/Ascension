package org.bingo4.commands;

import io.papermc.paper.command.brigadier.BasicCommand;
import io.papermc.paper.command.brigadier.CommandSourceStack;
import org.bingo4.Bingo4;
import org.bingo4.General.LocationChecker.LocationCheck;
import org.bingo4.Player.Data.PlayerData;
import org.bingo4.Player.Data.PlayerUtil;
import org.bingo4.Settings.Settings;
import org.bingo4.Utility.TextUtil;
import net.kyori.adventure.text.Component;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class Info implements CommandExecutor, BasicCommand {

    public Info() {
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

        PlayerData playerData = PlayerUtil.getPlayerData(p);
        if (playerData == null) {
            return false;
        }

        Settings settings = Bingo4.getInstance()
                .getSettingsUI()
                .getSettings();

        Component text = TextUtil.topBorder(TextUtil.YELLOW);

        text = text.append(TextUtil.makeText(
                "\n                     INFO",
                TextUtil.YELLOW, true, false
        ));

        text = text.append(TextUtil.makeText(
                "\n Preset: " + settings.getName(),
                TextUtil.GOLD
        ));

        text = text.append(TextUtil.makeText(
                "\n\n Score Requirement: " + settings.getBingoLinesRequirement(),
                TextUtil.AQUA
        ));

        text = text.append(TextUtil.makeText(
                "\n World Border: " + settings.getWorldBorderSize(),
                TextUtil.AQUA
        ));

        text = text.append(TextUtil.makeText(
                "\n Team Size: " + settings.getTeamSize(),
                TextUtil.AQUA
        ));

        List<Integer> distribution = settings.getItemDistribution();

        text = text.append(TextUtil.makeText(
                "\n\n Easy Items: " + distribution.get(0),
                TextUtil.GREEN
        ));

        text = text.append(TextUtil.makeText(
                "\n Medium Items: " + distribution.get(1),
                TextUtil.YELLOW
        ));

        text = text.append(TextUtil.makeText(
                "\n Hard Items: " + distribution.get(2),
                TextUtil.RED
        ));

        text = text.append(TextUtil.makeText(
                "\n Custom Items: " + distribution.get(3),
                TextUtil.BLUE
        ));

        LocationCheck locationCheck = Bingo4.getInstance().getLocationCheck();

        if (locationCheck.isCheckCompleted()) {
            text = text.append(TextUtil.makeText(
                    "\n\n World Score: " + locationCheck.getPercentageScore() + "%",
                    TextUtil.AQUA
            ));
        }

        text = text.append(TextUtil.bottomBorder(TextUtil.YELLOW));

        p.sendMessage(text);

        return true;
    }
}