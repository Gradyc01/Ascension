package org.bingo4.commands;

import io.papermc.paper.command.brigadier.BasicCommand;
import io.papermc.paper.command.brigadier.CommandSourceStack;
import org.bingo4.Bingo4;
import org.bingo4.Player.Data.PlayerData;
import org.bingo4.Player.Data.PlayerUtil;
import org.bingo4.Utility.TextUtil;
import net.kyori.adventure.text.Component;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class Shout implements CommandExecutor, BasicCommand {

    private final Bingo4 plugin;

    public Shout() {
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

    private boolean executeCommand(CommandSender commandSender, String[] strings) {
        if (!checkCommandUsage(commandSender, strings)) {
            return false;
        }

        Player p = (Player) commandSender;

        Component shoutTag = TextUtil.makeText("[SHOUT] ", TextUtil.GOLD);
        Component text = shoutTag.append(
                TextUtil.makeText(p.getName() + ": ", TextUtil.DARK_GRAY)
        );

        for (String str : strings) {
            text = text.append(TextUtil.makeText(str + " ", TextUtil.GRAY));
        }

        TextUtil.broadcastMessage(text);
        return true;
    }

    private boolean checkCommandUsage(CommandSender sender, String[] strings) {
        if (!(sender instanceof Player p)) {
            return false;
        }

        if (!plugin.getGameState().inGame()) {
            TextUtil.errorMessage(p, "This command is only available in a game!");
            return false;
        }

        PlayerData playerData = PlayerUtil.getPlayerData(p);

        if (playerData == null || playerData.getPlayerState() != PlayerData.STATE_ALIVE) {
            TextUtil.errorMessage(p, "You may not currently use this command");
            return false;
        }

        if (strings.length == 0) {
            TextUtil.errorMessage(p, "Must contain a message!");
            return false;
        }

        return true;
    }
}