package me.depickcator.ascension.commands;

import me.depickcator.ascension.Ascension;
import me.depickcator.ascension.General.TextUtil;
import me.depickcator.ascension.Player.Data.PlayerData;
import me.depickcator.ascension.Player.Data.PlayerUtil;
import net.kyori.adventure.text.Component;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class Shout implements CommandExecutor {
    private final Ascension plugin;
    public Shout() {
        this.plugin = Ascension.getInstance();
    }

    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {
        if (!checkCommandUsage(commandSender, strings)) return false;
        Player p = (Player) commandSender;
        Component shoutTag = TextUtil.makeText("[SHOUT] ", TextUtil.GOLD);
        Component text = shoutTag.append(TextUtil.makeText(p.getName() + ": ", TextUtil.DARK_GRAY));
        for (String str : strings) {
            text = text.append(TextUtil.makeText(str + " ", TextUtil.GRAY));
        }
        TextUtil.broadcastMessage(text);
        return true;
    }

    private boolean checkCommandUsage(CommandSender sender, String[] strings) {
        if (!(sender instanceof Player)) return false;
        Player p = (Player) sender;
        if (!plugin.getGameState().inGame()) {
            TextUtil.errorMessage(p, "This command is only available in a game!");
            return false;
        }
        if (PlayerUtil.getPlayerData(p).getPlayerState() != PlayerData.STATE_ALIVE) {
            TextUtil.errorMessage(p, "You may not currently use this command");
            return false;
        }
        if (strings.length == 0) {
            TextUtil.errorMessage(p, "Must contain a message!");
            return false;
        }
        return true;
    }
}
