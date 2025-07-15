package me.depickcator.ascension.commands;

import me.depickcator.ascension.Player.Data.PlayerData;
import me.depickcator.ascension.Player.Data.PlayerUtil;
import me.depickcator.ascension.Teams.Team;
import me.depickcator.ascension.Utility.TextUtil;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;


public class Backpack implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {
        if (!(commandSender instanceof Player)) return false;
        Player player = (Player) commandSender;
        PlayerData pD = PlayerUtil.getPlayerData(player);
//        Team team = pD.getPlayerTeam().getTeam();
//        if (team == null) {
//            TextUtil.errorMessage(player, "You are currently not in a party!");
//            return false;
//        }

        pD.getPlayerBackpack().openBackpack();
        return true;
    }

}
