package me.depickcator.ascension.testingCommands;

import me.depickcator.ascension.Player.Data.PlayerData;
import me.depickcator.ascension.Player.Data.PlayerUtil;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class printPlayerDataCommand implements CommandExecutor {

    public printPlayerDataCommand() {
    }
    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {
        if (!(commandSender instanceof Player)) {
            return false;
        }

        Player p = ((Player) commandSender).getPlayer();
        try {
            assert p != null;
        } catch (NullPointerException e) {
            return false;
        }
        Player invited = Bukkit.getPlayer(strings[0]);
        if (invited == null) {
            p.sendMessage("failed to find player");
            return false;
        }
        PlayerData playerData = PlayerUtil.getPlayerData(invited);
        assert playerData != null;
        p.sendMessage("Player: " + playerData.getPlayer().getName());
//        p.sendMessage("Player has pending Invite: " + playerData.getPendingTeamInvite().toString());
//        p.sendMessage("Player has an invite from: " + playerData.getInviteFromWho().getPlayer().getName());
        p.sendMessage("Player is on team" + playerData.getPlayerTeam().getTeam().getTeamKey());
        for (Player i : playerData.getPlayerTeam().getTeam().getTeamMembers()) {
            p.sendMessage("Team: " + i.getName());
        }
        return true;
    }

}
