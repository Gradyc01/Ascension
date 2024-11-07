package me.depickcator.ascensionBingo.Teams;

import me.depickcator.ascensionBingo.AscensionBingo;
import me.depickcator.ascensionBingo.General.GameStates;
import me.depickcator.ascensionBingo.Player.PlayerData;
import me.depickcator.ascensionBingo.Player.PlayerUtil;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import static org.bukkit.Bukkit.getPlayer;

public class TeamCommand implements CommandExecutor {
    private AscensionBingo plugin;
    public TeamCommand(AscensionBingo plugin) {
        this.plugin = plugin;
    }
    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, org.bukkit.command.@NotNull Command command, @NotNull String s, @NotNull String[] strings) {
        if (!(commandSender instanceof Player)) {
            return false;
        }
        if (strings.length < 1 || strings.length > 2) return false;

        Player p = ((Player) commandSender).getPlayer();
        try {
            assert p != null;
        } catch (Exception e) {
            return false;
        }
        if (!plugin.getGameState().checkState(GameStates.LOBBY)) {
            p.sendMessage("You may not use this command currently!");
            return false;
        }
        commandFinder(p, strings);
        return false;
    }


    private void commandFinder(Player p, String[] args) {
        switch (args[0].toLowerCase()) {
            case "invite" -> {
//                if (args[1].toLowerCase())
                Player invited = Bukkit.getPlayer(args[1]);
                if (invited == null) {
                    p.sendMessage("failed to find player");
                    return;
                }
                PlayerData pData = PlayerUtil.getPlayerData(p);
                if (pData == null) {
                    p.sendMessage("failed to playerData");
                    return;
                }
                pData.sendInvite(invited);

                p.sendMessage("PLACEHOLDER MESSAGE FOR WHEN SOMEONE INVITES THE TEAM");
            }
            case "leave" -> {
                PlayerData playerData = AscensionBingo.playerDataMap.get(p.getUniqueId());
                if (playerData == null) {
                    p.sendMessage("failed to playerData");
                    return;
                }
                playerData.leaveTeam();
            }
            case "accept" -> {
                PlayerData playerData = AscensionBingo.playerDataMap.get(p.getUniqueId());
                if (playerData == null) {
                    p.sendMessage("failed to playerData");
                    return;
                }
                playerData.acceptInvite();
            }
            case "reject" -> {
                PlayerData playerData = AscensionBingo.playerDataMap.get(p.getUniqueId());
                if (playerData == null) {
                    p.sendMessage("failed to playerData");
                    return;
                }
                playerData.rejectInvite();
                p.sendMessage("PLACEHOLDER MESSAGE FOR WHEN SOMEONE REJECTS THE TEAM");
            }
            case "list" -> {
                PlayerData playerData = AscensionBingo.playerDataMap.get(p.getUniqueId());
                if (playerData == null) {
                    p.sendMessage("failed to playerData");
                    return;
                }
                playerData.getTeam().teamList(p);
            }
            default -> {
                p.sendMessage("You did not use this command properly!");
            }
        }
    }

}
