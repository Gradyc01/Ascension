package org.bingo4.testingCommands;

import io.papermc.paper.command.brigadier.BasicCommand;
import io.papermc.paper.command.brigadier.CommandSourceStack;
import org.bingo4.Items.ItemList;
import org.bingo4.Player.Data.PlayerUtil;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class startEvents implements CommandExecutor, BasicCommand {

    private static final List<String> EVENTS =
            List.of("carepackage", "scavenger", "feast", "ascension_loc", "items");

    private static final List<String> ITEM_TYPES =
            List.of("easy", "medium", "hard", "custom");

    public startEvents() {
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

    private boolean executeCommand(CommandSender commandSender, String[] args) {
        if (!(commandSender instanceof Player p)) {
            return false;
        }

        if (args.length < 1) {
            return false;
        }

        String name = args[0];

        if (name.equalsIgnoreCase("carepackage")) {
//            new CarePackage(100);

        } else if (name.equalsIgnoreCase("scavenger")) {
//            Scavenger scavenger = new Scavenger(100);
//            scavenger.announceTrades();
//            scavenger.announceSpawnLocation();
//            scavenger.spawnInScavenger();

        } else if (name.equalsIgnoreCase("feast")) {
//            new Feast();

        } else if (name.equalsIgnoreCase("ascension_loc")) {
//            new AscensionEvent(100);

        } else if (name.equalsIgnoreCase("items")) {
            if (args.length < 2) {
                return false;
            }

            ItemList itemList = new ItemList();
            List<ItemStack> items = new ArrayList<>();

            String category = args[1];

            if (category.equalsIgnoreCase("easy")) {
                items.addAll(itemList.getEasyItems().getItems());
            } else if (category.equalsIgnoreCase("medium")) {
                items.addAll(itemList.getMediumItems().getItems());
            } else if (category.equalsIgnoreCase("hard")) {
                items.addAll(itemList.getHardItems().getItems());
            } else if (category.equalsIgnoreCase("custom")) {
                items.addAll(itemList.getCustomItems().getItems());
            } else {
                return false;
            }

            for (ItemStack item : items) {
                PlayerUtil.giveItem(p, item);
            }

        } else {
            return false;
        }

        return true;
    }

    // Paper tab completion
    @Override
    public @NotNull List<String> suggest(
            @NotNull CommandSourceStack commandSourceStack,
            @NotNull String[] args
    ) {
        if (args.length == 1) {
            String input = args[0].toLowerCase();

            return EVENTS.stream()
                    .filter(event -> event.startsWith(input))
                    .toList();
        }

        if (args.length == 2 && args[0].equalsIgnoreCase("items")) {
            String input = args[1].toLowerCase();

            return ITEM_TYPES.stream()
                    .filter(type -> type.startsWith(input))
                    .toList();
        }

        return List.of();
    }
}