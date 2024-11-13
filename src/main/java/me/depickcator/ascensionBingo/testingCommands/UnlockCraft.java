package me.depickcator.ascensionBingo.testingCommands;

import me.depickcator.ascensionBingo.AscensionBingo;
import me.depickcator.ascensionBingo.Items.UnlockUtil;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;


public class UnlockCraft implements CommandExecutor {
    private AscensionBingo plugin;
    public UnlockCraft(AscensionBingo plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {
//        if (!(commandSender instanceof Player)) return false;
//        Player p = ((Player) commandSender).getPlayer();
//        if (p == null || strings.length != 2) return false;
//
//        String name = strings[0];
//        String recipeName = strings[1];
//
//        if (!validRecipeName(recipeName)) {
//            p.sendMessage("Invalid recipe name.");
//            return false;
//        }
//        Player player = plugin.getServer().getPlayer(name);
//        if (player == null) {
//            p.sendMessage("Player not found.");
//            return false;
//        }
//
//        PlayerUnlocks playerUnlocks = Objects.requireNonNull(PlayerUtil.getPlayerData(player)).getPlayerUnlocks();
//        if (playerUnlocks.unlockUnlock(recipeName, -1)) {
//            p.sendMessage("Recipe Unlocked");
//            return true;
//        };
//
//        p.sendMessage("Player does not have that recipe");
        return false;
    }

    private boolean validRecipeName(String recipeName) {
        if (recipeName == null) return false;
        return UnlockUtil.isAUnlock(recipeName);
    }
}
