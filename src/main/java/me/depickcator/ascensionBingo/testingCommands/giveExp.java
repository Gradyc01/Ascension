package me.depickcator.ascensionBingo.testingCommands;

import me.depickcator.ascensionBingo.AscensionBingo;
import me.depickcator.ascensionBingo.General.TextUtil;
import me.depickcator.ascensionBingo.Player.PlayerSkills;
import me.depickcator.ascensionBingo.Player.PlayerUnlocks;
import me.depickcator.ascensionBingo.Player.PlayerUtil;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;

public class giveExp implements CommandExecutor {
    private final AscensionBingo plugin;
    public giveExp(AscensionBingo plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {
        if (!(commandSender instanceof Player)) return false;
        Player p = ((Player) commandSender).getPlayer();
        if (p == null || strings.length != 3) return false;

        String name = strings[0];
        String skillName = strings[1].toLowerCase();
        int exp = Integer.parseInt(strings[2]);

        Player player = plugin.getServer().getPlayer(name);
        if (player == null) {
            return false;
        }
        PlayerSkills playerSkills = Objects.requireNonNull(PlayerUtil.getPlayerData(player)).getPlayerSkills();
        switch (skillName) {
            case "combat": {
                playerSkills.getCombat().addExp(exp);
                return true;
            }
            case "foraging": {
//                playerSkills.getCombat().addExp(6);
                return true;
            }
            default: {
                p.sendMessage(TextUtil.makeText("ERROR", TextUtil.WHITE));
                return false;
            }
        }
//        playerSkills.
//        p.sendMessage("Player unlocks failed");

    }
}
