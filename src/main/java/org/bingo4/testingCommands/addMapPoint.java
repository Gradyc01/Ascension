package org.bingo4.testingCommands;

import io.papermc.paper.command.brigadier.BasicCommand;
import io.papermc.paper.command.brigadier.CommandSourceStack;
import org.bingo4.Bingo4;
import org.bingo4.Items.Uncraftable.Minimap;
import org.bingo4.MainMenuUI.Map.MapItem;
import org.bingo4.Player.Data.PlayerUtil;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class addMapPoint implements CommandExecutor, TabCompleter, BasicCommand {

    private final Bingo4 plugin = Bingo4.getInstance();

    @Override
    public boolean onCommand(
            @NotNull CommandSender sender,
            @NotNull Command command,
            @NotNull String label,
            @NotNull String @NotNull [] args
    ) {
        return executeCommand(sender, args);
    }

    @Override
    public void execute(
            @NotNull CommandSourceStack commandSourceStack,
            @NotNull String[] args
    ) {
        executeCommand(commandSourceStack.getSender(), args);
    }

    private boolean executeCommand(CommandSender sender, String[] args) {
        if (!(sender instanceof Player p)) {
            return false;
        }

        if (args.length == 0) {
            PlayerUtil.giveItem(
                    p,
                    Minimap.getInstance().getResult(PlayerUtil.getPlayerData(p))
            );
        } else if (args.length == 2) {
            try {
                plugin.getSettingsUI()
                        .getSettings()
                        .getTimeline()
                        .getMapItems()
                        .addMapItem(new MapItem(
                                "dep",
                                Integer.parseInt(args[0]),
                                Integer.parseInt(args[1]),
                                MapItem.CARE_PACKAGE
                        ));
            } catch (NumberFormatException e) {
                return false;
            }
        }

        return true;
    }

    // Legacy Bukkit tab completion
    @Override
    public @Nullable List<String> onTabComplete(
            @NotNull CommandSender sender,
            @NotNull Command command,
            @NotNull String label,
            @NotNull String @NotNull [] args
    ) {
        return getSuggestions();
    }

    // Paper tab completion
    @Override
    public @NotNull List<String> suggest(
            @NotNull CommandSourceStack commandSourceStack,
            @NotNull String[] args
    ) {
        return getSuggestions();
    }

    private List<String> getSuggestions() {
        return List.of(
//                "camp_jungle",
//                "camp_desert"
        );
    }
}