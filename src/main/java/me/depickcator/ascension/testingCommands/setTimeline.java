package me.depickcator.ascension.testingCommands;

import me.depickcator.ascension.Ascension;
import me.depickcator.ascension.General.TextUtil;
import me.depickcator.ascension.Player.PlayerData;
import me.depickcator.ascension.Player.PlayerUtil;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;


public class setTimeline implements CommandExecutor {
    private Ascension plugin;
    public setTimeline(Ascension plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {
        if (!(commandSender instanceof Player)) return false;
        Player p = ((Player) commandSender).getPlayer();
        PlayerData playerData = PlayerUtil.getPlayerData(p);
        if (playerData == null) return false;

        if (strings.length != 2) return false;

        int time = Integer.parseInt(strings[1]);
        plugin.getTimeline().setTime(time);
        plugin.getServer().broadcast(TextUtil.makeText("[Debug ] Set timeline to " + time, TextUtil.GRAY));
        return false;
    }
}