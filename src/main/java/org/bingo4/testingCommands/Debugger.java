package org.bingo4.testingCommands;

import io.papermc.paper.command.brigadier.BasicCommand;
import io.papermc.paper.command.brigadier.CommandSourceStack;
import org.bingo4.Utility.TextUtil;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class Debugger implements CommandExecutor, BasicCommand {

    private static boolean debugger = false;

    // Legacy Bukkit command implementation
    @Override
    public boolean onCommand(
            @NotNull CommandSender commandSender,
            @NotNull Command command,
            @NotNull String s,
            @NotNull String[] strings
    ) {
        return toggleDebugger(commandSender);
    }

    // New Paper command implementation
    @Override
    public void execute(
            @NotNull CommandSourceStack commandSourceStack,
            @NotNull String[] args
    ) {
        toggleDebugger(commandSourceStack.getSender());
    }

    private boolean toggleDebugger(CommandSender commandSender) {
        if (!(commandSender instanceof Player p)) {
            return false;
        }

        p.sendMessage(TextUtil.makeText(
                "Changed State of the debugger",
                TextUtil.DARK_GREEN
        ));

        debugger = !debugger;

        if (debugger) {
//            Wither e = (Wither) new GatekeeperWither(p.getLocation()).getBukkitEntity();
//            e.customName(TextUtil.makeText("Gatekeeper", TextUtil.GRAY));
//            e.getAttribute(Attribute.MOVEMENT_SPEED).setBaseValue(0);
        }

        return true;
    }

    public static boolean getDebugger() {
        return debugger;
    }
}