package me.depickcator.ascension.testingCommands;

import me.depickcator.ascension.Ascension;
import me.depickcator.ascension.Player.Data.PlayerData;
import me.depickcator.ascension.Player.Data.PlayerUtil;
import me.depickcator.ascension.Settings.Presets.Quickplay;
import me.depickcator.ascension.Settings.Presets.Standard;
import me.depickcator.ascension.Settings.SettingObserver;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;


public class SetSetting implements CommandExecutor {
    private final SettingObserver settingUI;
    public SetSetting() {
        this.settingUI = Ascension.getInstance().getSettingsUI();
    }

    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {
        if (!(commandSender instanceof Player)) return false;
        Player p = ((Player) commandSender).getPlayer();
        PlayerData playerData = PlayerUtil.getPlayerData(p);
        if (playerData == null) return false;

        if (strings.length != 1) return false;
        String mode = strings[0];
        if (mode.equals("standard")) {
            settingUI.setSettings(new Standard());
        } else if (mode.equals("quickplay")) {
            settingUI.setSettings(new Quickplay());
        }
        return false;
    }
}
