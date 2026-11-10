package org.bingo4.commands;

import io.papermc.paper.command.brigadier.BasicCommand;
import io.papermc.paper.command.brigadier.CommandSourceStack;
import org.bingo4.Bingo4;
import org.bingo4.General.Game.GameStates;
import org.bingo4.Player.Data.PlayerUtil;
import org.bingo4.Settings.GUIs.SettingsGUI;
import org.bingo4.Settings.Presets.*;
import org.bingo4.Settings.SettingObserver;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Arrays;
import java.util.List;

public class SetSetting implements CommandExecutor, TabCompleter, BasicCommand {

    private final SettingObserver settingUI;

    private static final List<String> SETTINGS = List.of(
            "standard",
            "quickplay",
            "brawl",
            "testing",
            "instant",
            "quickboard"
    );

    public SetSetting() {
        this.settingUI = Bingo4.getInstance().getSettingsUI();
    }

    // Legacy Bukkit command implementation
    @Override
    public boolean onCommand(
            @NotNull CommandSender commandSender,
            @NotNull Command command,
            @NotNull String s,
            @NotNull String[] strings
    ) {
        return executeCommand(commandSender, strings);
    }

    // New Paper command implementation
    @Override
    public void execute(
            @NotNull CommandSourceStack commandSourceStack,
            @NotNull String[] args
    ) {
        executeCommand(commandSourceStack.getSender(), args);
    }

    private boolean executeCommand(CommandSender commandSender, String[] strings) {
        if (strings.length == 0 && commandSender instanceof Player player) {
            new SettingsGUI(PlayerUtil.getPlayerData(player));
            return true;
        }

        if (strings.length == 0) {
            return false;
        }

        boolean update = true;

        if (strings.length >= 2 && strings[strings.length - 1].equals("false")) {
            update = false;
        } else if (!Bingo4.getInstance().getGameState().checkState(GameStates.LOBBY_NORMAL)) {
            return false;
        }

        String mode = String.join("", Arrays.copyOfRange(strings, 0, strings.length - 1));

        if (update) {
            mode += strings[strings.length - 1];
        }

        switch (mode.toLowerCase()) {
            case "standard" -> settingUI.setSettings(new Standard(), update);
            case "quickplay" -> settingUI.setSettings(new Quickplay(), update);
            case "brawl" -> settingUI.setSettings(new Brawl(), update);
            case "instant", "boardonly" -> settingUI.setSettings(new InstantAscension(), update);
            case "quickboard" -> settingUI.setSettings(new QuickBoard(), update);
            case "testing" -> settingUI.setSettings(new Testing(), update);
        }

        return false;
    }

    public static void runSettingMode(String settingMode, boolean update) {
        org.bingo4.Settings.SettingObserver settingUI = Bingo4.getInstance().getSettingsUI();
        switch (settingMode.toLowerCase()) {
            case "standard" -> settingUI.setSettings(new Standard(), update);
            case "quickplay" -> settingUI.setSettings(new Quickplay(), update);
            case "brawl" -> settingUI.setSettings(new Brawl(), update);
            case "instant", "boardonly" -> settingUI.setSettings(new InstantAscension(), update);
            case "quickboard" -> settingUI.setSettings(new QuickBoard(), update);
            case "testing" -> settingUI.setSettings(new Testing(), update);
        }
    }

    // Legacy Bukkit tab completion
    @Override
    public @Nullable List<String> onTabComplete(
            @NotNull CommandSender commandSender,
            @NotNull Command command,
            @NotNull String s,
            @NotNull String[] strings
    ) {
        return getSuggestions();
    }

    // New Paper tab completion
    @Override
    public @NotNull List<String> suggest(
            @NotNull CommandSourceStack commandSourceStack,
            @NotNull String[] args
    ) {
        return getSuggestions();
    }

    private List<String> getSuggestions() {
        return SETTINGS;
    }
}