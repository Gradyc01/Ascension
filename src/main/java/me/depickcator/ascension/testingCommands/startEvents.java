package me.depickcator.ascension.testingCommands;

import me.depickcator.ascension.Timeline.Events.CarePackage.CarePackage;
import me.depickcator.ascension.Timeline.Events.Feast.Feast;
import me.depickcator.ascension.Timeline.Events.Scavenger.Scavenger;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;


public class startEvents implements CommandExecutor {
    // private final Ascension plugin;
    public startEvents() {
        // this.plugin = Ascension.getInstance();
    }

    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {
        if (!(commandSender instanceof Player)) return false;
        Player p = ((Player) commandSender).getPlayer();
        if (p == null || strings.length != 1) return false;

        String name = strings[0];


        if (name.equalsIgnoreCase("carepackage")) {
            new CarePackage();
        } else if (name.equalsIgnoreCase("scavenger")) {
            Scavenger scavenger = new Scavenger();
            scavenger.announceTrades();
            scavenger.announceSpawnLocation();
            scavenger.spawnInScavenger();
        } else if (name.equalsIgnoreCase("feast")) {
            new Feast();
        }
        return false;
    }
}
