package org.bingo4.MainMenuUI;

import io.papermc.paper.command.brigadier.BasicCommand;
import io.papermc.paper.command.brigadier.CommandSourceStack;
import org.bingo4.Player.Cooldowns.CombatTimer;
import org.bingo4.Player.Data.PlayerData;
import org.bingo4.Player.Data.PlayerUtil;
import org.bingo4.Utility.TextUtil;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

// Include the correct imports for your project:
// import ...CombatTimer;
// import ...MainMenuGUI;

public class OpenMainMenuCommand implements CommandExecutor, BasicCommand {

    @Override
    public boolean onCommand(
            @NotNull CommandSender commandSender,
            @NotNull Command command,
            @NotNull String s,
            @NotNull String[] strings
    ) {
        if (!(commandSender instanceof Player p)) {
            return false;
        }

        return openMenu(p);
    }

    @Override
    public void execute(
            @NotNull CommandSourceStack commandSourceStack,
            @NotNull String[] args
    ) {
        if (!(commandSourceStack.getSender() instanceof Player p)) {
            return;
        }

        openMenu(p);
    }

    private boolean openMenu(Player p) {
        PlayerData pD = PlayerUtil.getPlayerData(p);

        if (CombatTimer.getInstance().isOnCooldown(p) || pD == null) {
            return false;
        }

        if (!pD.checkState(PlayerData.STATE_ALIVE)) {
            TextUtil.errorMessage(p, "You can't use this command in your state!");
            return false;
        }

        new MainMenuGUI(pD);
        return true;
    }
}