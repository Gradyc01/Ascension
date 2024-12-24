package me.depickcator.ascension.testingCommands;

import me.depickcator.ascension.Ascension;
import me.depickcator.ascension.General.TextUtil;
import me.depickcator.ascension.Player.Data.PlayerData;
import me.depickcator.ascension.Player.Data.PlayerUtil;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;


public class setTimeline implements CommandExecutor {
    private final Ascension plugin;
    public setTimeline() {
        this.plugin = Ascension.getInstance();
    }

    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {
        if (!(commandSender instanceof Player)) return false;
        Player p = ((Player) commandSender).getPlayer();
        PlayerData playerData = PlayerUtil.getPlayerData(p);
        if (playerData == null) return false;

        if (strings.length != 2) return false;
        String mode = strings[0];
        int time = Integer.parseInt(strings[1]);
        if (mode.equals("set")) {
            plugin.getTimeline().setTime(time);
        } else if (mode.equals("run")) {
            plugin.getTimeline().pauseTimeline();
            plugin.getTimeline().setTime(time);
            plugin.getTimeline().startTimeline();
        }

        TextUtil.debugText("Set timeline to " + time);
        return false;
    }
}
