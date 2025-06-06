package me.depickcator.ascension.Teams;

import me.depickcator.ascension.Ascension;
import me.depickcator.ascension.General.Game.GameStates;
import me.depickcator.ascension.Utility.TextUtil;
import me.depickcator.ascension.Player.Data.PlayerData;
import me.depickcator.ascension.Player.Data.PlayerUtil;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class TeamCommand implements CommandExecutor, TabCompleter {
    private final Ascension plugin;
    public TeamCommand() {
        this.plugin = Ascension.getInstance();
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
        if (!plugin.getGameState().checkState(GameStates.LOBBY_NORMAL)) {
            TextUtil.errorMessage(p,"You may not use this command currently!");
            return false;
        }
        commandFinder(p, strings);
        return false;
    }


    private void commandFinder(Player p, String[] args) {
        PlayerData pD = PlayerUtil.getPlayerData(p);
        switch (args[0].toLowerCase()) {
            case "invite" -> {
//                if (args[1].toLowerCase())
                Player invited = Bukkit.getPlayer(args[1]);
                if (invited == null || invited.equals(p)) {
                    TextUtil.errorMessage(p, "Failed to find player.");
                    return;
                }
                pD.getPlayerTeam().sendInvite(invited);
            }
            case "leave" -> {
                pD.getPlayerTeam().leaveTeam();
            }
            case "accept" -> {
                Player sender = Bukkit.getPlayer(args[1]);
                if (sender == null || sender.equals(p)) {
                    TextUtil.errorMessage(p, "Failed to find player.");
                    return;
                }
                pD.getPlayerTeam().acceptInvite(PlayerUtil.getPlayerData(sender));
            }
            case "reject" -> {
                Player sender = Bukkit.getPlayer(args[1]);
                if (sender == null || sender.equals(p)) {
                    TextUtil.errorMessage(p, "Failed to find player.");
                    return;
                }
                pD.getPlayerTeam().rejectInvite(PlayerUtil.getPlayerData(sender));
            }
            case "list" -> {
                try {
                    pD.getPlayerTeam().getTeam().teamList(pD);
                } catch (Exception e) {
                    TextUtil.errorMessage(p, "You are currently not in a party");
                }

            }
            default -> {
//                TextUtil.errorMessage(p, "You did not use this command properly!");
                p.performCommand("party invite " + args[0]);
            }
        }
    }

    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {
        if (strings.length == 1) {
            return List.of("invite", "leave", "accept", "reject", "list");
        } else {
            return null;
        }
    }
}
