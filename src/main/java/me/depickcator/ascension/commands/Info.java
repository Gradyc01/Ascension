package me.depickcator.ascension.commands;

import me.depickcator.ascension.Ascension;
import me.depickcator.ascension.General.LocationChecker.LocationCheck;
import me.depickcator.ascension.Player.Data.PlayerData;
import me.depickcator.ascension.Player.Data.PlayerUtil;
import me.depickcator.ascension.Settings.Settings;
import me.depickcator.ascension.Utility.TextUtil;
import net.kyori.adventure.text.Component;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.List;


public class Info implements CommandExecutor {
    private Settings settings;
    public Info() {
    }

    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {
        if (!(commandSender instanceof Player)) return false;
        Player p = ((Player) commandSender).getPlayer();
        PlayerData playerData = PlayerUtil.getPlayerData(p);
        if (playerData == null) return false;
        this.settings = Ascension.getInstance().getSettingsUI().getSettings();

        Component text = TextUtil.topBorder(TextUtil.YELLOW);
        text = text.append(TextUtil.makeText("\n                     INFO", TextUtil.YELLOW, true, false));
        text = text.append(TextUtil.makeText("\n Preset: " + settings.getName(), TextUtil.GOLD));
        text = text.append(TextUtil.makeText("\n\n Score Requirement: " + settings.getAscensionGameScoreRequirement(), TextUtil.AQUA));
        text = text.append(TextUtil.makeText("\n World Border: " + settings.getWorldBorderSize(), TextUtil.AQUA));
        text = text.append(TextUtil.makeText("\n Team Size: " + settings.getTeamSize(), TextUtil.AQUA));
        List<Integer> distribution = settings.getItemDistribution();
        text = text.append(TextUtil.makeText("\n\n Easy Items: " + distribution.get(0), TextUtil.GREEN));
        text = text.append(TextUtil.makeText("\n Medium Items: " + distribution.get(1), TextUtil.YELLOW));
        text = text.append(TextUtil.makeText("\n Hard Items: " + distribution.get(2), TextUtil.RED));
        text = text.append(TextUtil.makeText("\n Custom Items: " + distribution.get(3), TextUtil.BLUE));
        LocationCheck locationCheck = Ascension.getInstance().getLocationCheck();
        if (locationCheck.isCheckCompleted()) text = text.append(TextUtil.makeText("\n\n World Score: " + locationCheck.getPercentageScore() +  "%", TextUtil.AQUA));

        text = text.append(TextUtil.bottomBorder(TextUtil.YELLOW));

        p.sendMessage(text);

        return true;
    }

}
