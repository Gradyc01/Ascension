package me.depickcator.ascensionBingo.testingCommands;

import me.depickcator.ascensionBingo.AscensionBingo;
import me.depickcator.ascensionBingo.Player.PlayerData;
import me.depickcator.ascensionBingo.Player.PlayerUtil;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;
import java.util.UUID;

public class printPlayerDataCommand implements CommandExecutor {
    AscensionBingo plugin;

    public printPlayerDataCommand(AscensionBingo plugin) {
        this.plugin = plugin;
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
        p.sendMessage("Player is on team" + playerData.getTeam().getTeamKey());
        for (Player i : playerData.getTeam().getTeamMembers()) {
            p.sendMessage("Team: " + i.getName());
        }
        return true;
    }

}
