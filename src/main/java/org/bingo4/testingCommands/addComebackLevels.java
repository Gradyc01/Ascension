package org.bingo4.testingCommands;

import io.papermc.paper.command.brigadier.BasicCommand;
import io.papermc.paper.command.brigadier.CommandSourceStack;
import org.bingo4.Bingo4;
import org.bingo4.Player.Data.PlayerData;
import org.bingo4.Player.Data.PlayerItems;
import org.bingo4.Player.Data.PlayerUtil;
import org.bingo4.Player.Interfaces.WeaponInfo;
import org.bingo4.Structures.CampManager;
import org.bingo4.Structures.Structures;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

import static org.bingo4.Player.Interfaces.WeaponInfo.MELEE;
import static org.bingo4.Player.Interfaces.WeaponInfo.PROJECTILE;
import static org.bingo4.Structures.Structures.*;
import static org.bingo4.Teams.TeamComeback.COMEBACK_TIER;

public class addComebackLevels implements CommandExecutor, TabCompleter, BasicCommand {

    private final Bingo4 plugin = Bingo4.getInstance();

    @Override
    public boolean onCommand(
            @NotNull CommandSender sender,
            @NotNull Command command,
            @NotNull String label,
            @NotNull String @NotNull [] args
    ) {
        return executeCommand(sender, args);
    }

    @Override
    public void execute(
            @NotNull CommandSourceStack commandSourceStack,
            @NotNull String[] args
    ) {
        executeCommand(commandSourceStack.getSender(), args);
    }

    private boolean executeCommand(CommandSender sender, String[] args) {
        if (!(sender instanceof Player p)) {
            return false;
        }

        if (args.length == 2) {
            Player target = Bukkit.getPlayer(args[0]);
            int tierChange = Integer.parseInt(args[1]);

            PlayerData playerData = PlayerUtil.getPlayerData(target);
            if (playerData == null) return false;
            playerData.getPlayerItems().changePlayerWeaponTier(tierChange, MELEE, COMEBACK_TIER);
            playerData.getPlayerItems().changePlayerWeaponTier(tierChange, PROJECTILE, COMEBACK_TIER);
        }

        return true;
    }

    // Legacy Bukkit tab completion
    @Override
    public @Nullable List<String> onTabComplete(
            @NotNull CommandSender sender,
            @NotNull Command command,
            @NotNull String label,
            @NotNull String @NotNull [] args
    ) {
        return PlayerUtil.getAllPlayingPlayers().stream().map(playerData -> playerData.getPlayer().getName()).toList();
    }

    // Paper tab completion
    @Override
    public @NotNull List<String> suggest(
            @NotNull CommandSourceStack commandSourceStack,
            @NotNull String[] args
    ) {
        return PlayerUtil.getAllPlayingPlayers().stream().map(playerData -> playerData.getPlayer().getName()).toList();
    }
}