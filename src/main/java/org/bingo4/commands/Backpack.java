//package org.bingo4.commands;
//
//import io.papermc.paper.command.brigadier.BasicCommand;
//import io.papermc.paper.command.brigadier.CommandSourceStack;
//import org.bingo4.Player.Data.PlayerData;
//import org.bingo4.Player.Data.PlayerUtil;
//import org.bukkit.command.Command;
//import org.bukkit.command.CommandExecutor;
//import org.bukkit.command.CommandSender;
//import org.bukkit.entity.Player;
//import org.jetbrains.annotations.NotNull;
//
//public class Backpack implements CommandExecutor, BasicCommand {
//
//    @Override
//    public boolean onCommand(
//            @NotNull CommandSender commandSender,
//            @NotNull Command command,
//            @NotNull String s,
//            @NotNull String[] strings
//    ) {
//        return executeCommand(commandSender);
//    }
//
//    @Override
//    public void execute(
//            @NotNull CommandSourceStack commandSourceStack,
//            @NotNull String[] args
//    ) {
//        executeCommand(commandSourceStack.getSender());
//    }
//
//    private boolean executeCommand(CommandSender commandSender) {
//        if (!(commandSender instanceof Player player)) {
//            return false;
//        }
//
//        PlayerData pD = PlayerUtil.getPlayerData(player);
//        if (pD == null) {
//            return false;
//        }
//
//        pD.getPlayerBackpack().openBackpack();
//        return true;
//    }
//}