package me.depickcator.ascension.testingCommands;

import me.depickcator.ascension.Ascension;
import me.depickcator.ascension.Timeline.Events.CarePackage.CarePackage;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;


public class startEvents implements CommandExecutor {
    private Ascension plugin;
    public startEvents(Ascension plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {
        if (!(commandSender instanceof Player)) return false;
        Player p = ((Player) commandSender).getPlayer();
        if (p == null || strings.length != 1) return false;

        String name = strings[0];


        if (name.equalsIgnoreCase("carepackage")) {
            new CarePackage();
        }
        return false;
    }
}
