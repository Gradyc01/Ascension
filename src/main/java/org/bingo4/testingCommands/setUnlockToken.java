//package org.bingo4.testingCommands;
//
//import org.bingo4.Bingo4;
//import org.bingo4.Player.Data.PlayerData;
//import org.bingo4.Player.Data.PlayerUnlocks;
//import org.bingo4.Player.Data.PlayerUtil;
//import org.bukkit.command.Command;
//import org.bukkit.command.CommandExecutor;
//import org.bukkit.command.CommandSender;
//import org.bukkit.entity.Player;
//import org.jetbrains.annotations.NotNull;
//
//
//public class setUnlockToken implements CommandExecutor {
//    private final Bingo4 plugin;
//    public setUnlockToken() {
//        this.plugin = Bingo4.getInstance();
//    }
//
//    @Override
//    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {
//        if (!(commandSender instanceof Player)) return false;
//        Player p = ((Player) commandSender).getPlayer();
//        if (p == null || strings.length != 2) return false;
//
//        String name = strings[0];
//        int tokens;
//        if (strings[1].equals("max")) {
//            tokens = 9999999;
//        } else {
//            tokens = Integer.parseInt(strings[1]);
//        }
////        p.getInventory().addItem();
//
//        Player player = plugin.getServer().getPlayer(name);
//        if (player == null) {
//            return false;
//        }
//        PlayerData pD = PlayerUtil.getPlayerData(player);
//        PlayerUnlocks playerUnlocks = pD.getPlayerUnlocks();
//        playerUnlocks.setUnlockTokens(tokens);
//
//        p.sendMessage("Player unlocks failed");
//        return false;
//    }
//}
