package me.depickcator.ascension.testingCommands;

import me.depickcator.ascension.General.GatekeeperWither;
import me.depickcator.ascension.Utility.TextUtil;
import org.bukkit.attribute.Attribute;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Wither;
import org.jetbrains.annotations.NotNull;

public class Debugger implements CommandExecutor {

   private static boolean debugger = false;
    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {
        if (!(commandSender instanceof Player)) {
            return false;
        }
        Player p = (Player) commandSender;
        p.sendMessage(TextUtil.makeText("Changed State of the debugger", TextUtil.DARK_GREEN));
        debugger = !debugger;
        if (debugger) {
            Wither e = (Wither) new GatekeeperWither(p.getLocation()).getBukkitEntity();
            e.customName(TextUtil.makeText("Gatekeeper", TextUtil.GRAY));
            e.getAttribute(Attribute.MOVEMENT_SPEED).setBaseValue(0);
        }
        return true;
    }

    public static boolean getDebugger() {
        return debugger;
    }

}
