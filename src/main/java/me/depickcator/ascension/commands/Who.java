package me.depickcator.ascension.commands;

import me.depickcator.ascension.Player.Data.PlayerData;
import me.depickcator.ascension.Player.Data.PlayerUtil;
import me.depickcator.ascension.Teams.Team;
import me.depickcator.ascension.Teams.TeamUtil;
import me.depickcator.ascension.Utility.TextUtil;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;



public class Who implements CommandExecutor {
    public Who() {
    }

    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {
        if (!(commandSender instanceof Player)) return false;
        Player p = ((Player) commandSender).getPlayer();
//        PlayerData playerData = PlayerUtil.getPlayerData(p);
//        if (playerData == null) return false;
        Component text = TextUtil.topBorder(TextUtil.YELLOW);

        for (Team team : TeamUtil.getEveryTeam()) {
            text = text.append(TextUtil.makeText("\n"));
            for (Player player : team.getTeamMembers()) {
                PlayerData pD = PlayerUtil.getPlayerData(player);
                TextColor color = pD.checkState(PlayerData.STATE_ALIVE) ? TextUtil.GREEN :
                        pD.checkState(PlayerData.STATE_DEAD) ? TextUtil.RED : TextUtil.GRAY;
                text = text.append(TextUtil.makeText(" " + player.getName(), color));
            }
        }

        text = text.append(TextUtil.bottomBorder(TextUtil.YELLOW));
        p.sendMessage(text);


//        p.sendMessage();

        return true;
    }

}
