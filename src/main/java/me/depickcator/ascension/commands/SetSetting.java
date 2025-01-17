package me.depickcator.ascension.commands;

import me.depickcator.ascension.Ascension;
import me.depickcator.ascension.General.GameStates;
import me.depickcator.ascension.Player.Data.PlayerData;
import me.depickcator.ascension.Player.Data.PlayerUtil;
import me.depickcator.ascension.Settings.Presets.*;
import me.depickcator.ascension.Settings.SettingObserver;
import me.depickcator.ascension.Settings.GUIs.SettingsGUI;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;


public class SetSetting implements CommandExecutor, TabCompleter {
    private final SettingObserver settingUI;
    public SetSetting() {
        this.settingUI = Ascension.getInstance().getSettingsUI();
    }

    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {
        if (!(commandSender instanceof Player)) return false;
        Player p = ((Player) commandSender).getPlayer();
        PlayerData playerData = PlayerUtil.getPlayerData(p);
        if (playerData == null || !Ascension.getInstance().getGameState().checkState(GameStates.LOBBY_NORMAL)) return false;

        if (strings.length != 1) {
            new SettingsGUI(playerData);
            return true;
        }
        String mode = strings[0];
        switch (mode.toLowerCase()) {
            case "standard" -> settingUI.setSettings(new Standard());
            case "quickplay" -> settingUI.setSettings(new Quickplay());
            case "brawl" -> settingUI.setSettings(new Brawl());
            case "instant" -> settingUI.setSettings(new InstantAscension());
            case "testing" -> settingUI.setSettings(new Testing());
        }
        return false;
    }

    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {
        return List.of(
                "standard",
                "quickplay",
                "brawl",
                "testing",
                "instant"
        );
    }
}
