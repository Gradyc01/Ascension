package me.depickcator.ascensionBingo.testingCommands;

import me.depickcator.ascensionBingo.AscensionBingo;
import me.depickcator.ascensionBingo.Items.UnlockUtil;
import me.depickcator.ascensionBingo.Player.PlayerUnlocks;
import me.depickcator.ascensionBingo.Player.PlayerUtil;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;

public class setUnlockToken implements CommandExecutor {
    private AscensionBingo plugin;
    public setUnlockToken(AscensionBingo plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {
        if (!(commandSender instanceof Player)) return false;
        Player p = ((Player) commandSender).getPlayer();
        if (p == null || strings.length != 2) return false;

        String name = strings[0];
        int tokens = Integer.parseInt(strings[1]);

        Player player = plugin.getServer().getPlayer(name);
        if (player == null) {
            return false;
        }
        PlayerUnlocks playerUnlocks = Objects.requireNonNull(PlayerUtil.getPlayerData(player)).getPlayerUnlocks();
        playerUnlocks.setUnlockTokens(tokens);
        p.sendMessage("Player unlocks failed");
        return false;
    }

    private boolean validRecipeName(String recipeName) {
        if (recipeName == null) return false;
        return UnlockUtil.isAUnlock(recipeName);
    }
}
