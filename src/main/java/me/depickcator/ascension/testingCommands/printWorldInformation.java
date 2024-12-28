package me.depickcator.ascension.testingCommands;

import me.depickcator.ascension.Ascension;
import me.depickcator.ascension.Utility.TextUtil;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class printWorldInformation implements CommandExecutor {
    private final Ascension plugin;
    public printWorldInformation() {
        this.plugin = Ascension.getInstance();
    }

    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {
        if (!(commandSender instanceof Player)) {
            return false;
        }
        Player p = (Player) commandSender;
        int globalScore = plugin.getLocationCheck().getPercentageScore();
        TextColor color = globalScore >= 50 ? globalScore >= 80 ? TextUtil.GREEN : TextUtil.YELLOW : TextUtil.RED;
        Component text = TextUtil.topBorder(TextUtil.DARK_GRAY);
        text = text.append(TextUtil.makeText("\n                World Score: " +  globalScore + "%", color, true, false));
        text = text.append(plugin.getLocationCheck().printLocations());
        text = text.append(TextUtil.bottomBorder(TextUtil.DARK_GRAY));
        p.sendMessage(text);

        return true;
    }


}
