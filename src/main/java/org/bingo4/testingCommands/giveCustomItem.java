
package org.bingo4.testingCommands;

import io.papermc.paper.command.brigadier.BasicCommand;
import io.papermc.paper.command.brigadier.CommandSourceStack;

import org.bingo4.Bingo4;
import org.bingo4.Interfaces.Summonable;
import org.bingo4.Items.Craftable.Craft;
import org.bingo4.Items.Craftable.Vanilla.*;
import org.bingo4.Items.CustomItem;
import org.bingo4.Items.Uncraftable.*;
import org.bingo4.Items.Uncraftable.HadesBook.HadesBook;
import org.bingo4.Items.Uncraftable.NetherStar.NetherStar;
import org.bingo4.Items.Uncraftable.Skulls.*;
import org.bingo4.Items.Uncraftable.ToolVoucher.ToolVoucher;
import org.bingo4.Items.Uncraftable.VoidBox.VoidBox;
import org.bingo4.Items.Uncraftable.XPTome.XPTome;
import org.bingo4.Player.Data.PlayerUtil;
import org.bingo4.Utility.TextUtil;

import net.kyori.adventure.text.Component;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public class giveCustomItem implements BasicCommand, CommandExecutor, TabCompleter {

    private final Bingo4 plugin;

    public giveCustomItem() {
        plugin = Bingo4.getInstance();
    }

    // --------------------------------------------------
    // BasicCommand implementation
    // --------------------------------------------------

    @Override
    public void execute(@NotNull CommandSourceStack stack, @NotNull String[] args) {
        CommandSender sender = stack.getSender();

        if (!(sender instanceof Player player)) return;
        if (args.length == 0 || args.length > 3) return;

        List<CustomItem> allItems = getAllCustomItems();

        if (args[0].equalsIgnoreCase("help")) {
            Component text = TextUtil.makeText("  All Item strings: ", TextUtil.GRAY);

            for (CustomItem item : allItems) {
                text = text.append(
                        TextUtil.makeText("   [" + item.getKey() + "]", TextUtil.GRAY)
                );
            }

            player.sendMessage(text);
            return;
        }

        Player target = player;
        String itemName;

        // /givecustomitem <item>
        // /givecustomitem <item> <amount>
        // /givecustomitem <player> <item>
        // /givecustomitem <player> <item> <amount>

        if (args.length >= 2) {
            Player possibleTarget = Bukkit.getPlayerExact(args[0]);

            if (possibleTarget != null) {
                target = possibleTarget;
                itemName = args[1];
            } else {
                itemName = args[0];
            }
        } else {
            itemName = args[0];
        }

        int count = 1;

        if (args.length == 2 && itemName.equals(args[0])) {
            if (args[1].matches("\\d+")) {
                count = Integer.parseInt(args[1]);
            }
        } else if (args.length == 3) {
            try {
                count = Integer.parseInt(args[2]);
            } catch (NumberFormatException e) {
                TextUtil.errorMessage(player, "Invalid amount!");
                return;
            }
        }

        if (count <= 0) {
            TextUtil.errorMessage(player, "Amount must be greater than zero!");
            return;
        }

        for (CustomItem customItem : allItems) {
            if (!customItem.getKey().equalsIgnoreCase(itemName)) continue;

            if (customItem instanceof Summonable summonable) {
                summonable.summonEffect(target);
                summonable.initEntity(target);
                return;
            }

            ItemStack item = customItem.getResult();

            for (int i = 0; i < count; i++) {
                PlayerUtil.giveItem(target, item);
            }

            target.sendMessage(
                    TextUtil.makeText("Item Found!", TextUtil.DARK_GREEN)
            );

            return;
        }

        TextUtil.errorMessage(player, "Unable to find unlock");
    }

    @Override
    public @Nullable List<String> suggest(
            @NotNull CommandSourceStack stack,
            @NotNull String[] args
    ) {
        List<String> suggestions = new ArrayList<>();

        if (args.length == 1) {
            suggestions.add("help");

            for (Player player : Bukkit.getOnlinePlayers()) {
                suggestions.add(player.getName());
            }

            for (CustomItem item : getAllCustomItems()) {
                suggestions.add(item.getKey());
            }

        } else if (args.length == 2) {
            Player possibleTarget = Bukkit.getPlayerExact(args[0]);

            if (possibleTarget != null) {
                for (CustomItem item : getAllCustomItems()) {
                    suggestions.add(item.getKey());
                }
            } else {
                suggestions.add("1");
                suggestions.add("2");
                suggestions.add("5");
                suggestions.add("10");
            }

        } else if (args.length == 3) {
            suggestions.add("1");
            suggestions.add("2");
            suggestions.add("5");
            suggestions.add("10");
        }

        return suggestions;
    }

    // --------------------------------------------------
    // Legacy CommandExecutor implementation (fallback)
    // --------------------------------------------------

    @Override
    public boolean onCommand(
            @NotNull CommandSender commandSender,
            @NotNull Command command,
            @NotNull String s,
            @NotNull String[] strings
    ) {
        if (!(commandSender instanceof Player player)) return false;
        if (strings.length == 0 || strings.length > 3) return false;

        List<CustomItem> allItems = getAllCustomItems();

        if (strings[0].equalsIgnoreCase("help")) {
            Component text = TextUtil.makeText("  All Item strings: ", TextUtil.GRAY);

            for (CustomItem item : allItems) {
                text = text.append(
                        TextUtil.makeText("   [" + item.getKey() + "]", TextUtil.GRAY)
                );
            }

            player.sendMessage(text);
            return true;
        }

        Player target = player;
        String itemName;

        if (strings.length >= 2 && Bukkit.getPlayerExact(strings[0]) != null) {
            target = Bukkit.getPlayerExact(strings[0]);
            itemName = strings[1];
        } else {
            itemName = strings[0];
        }

        int count = 1;

        try {
            if (strings.length == 2 && !itemName.equals(strings[0])) {
                count = Integer.parseInt(strings[1]);
            } else if (strings.length == 3) {
                count = Integer.parseInt(strings[2]);
            }
        } catch (NumberFormatException e) {
            return false;
        }

        if (count <= 0) return false;

        for (CustomItem customItem : allItems) {
            if (!customItem.getKey().equalsIgnoreCase(itemName)) continue;

            if (customItem instanceof Summonable summonable) {
                summonable.summonEffect(target);
                summonable.initEntity(target);
                return true;
            }

            for (int i = 0; i < count; i++) {
                PlayerUtil.giveItem(target, customItem.getResult());
            }

            target.sendMessage(
                    TextUtil.makeText("Item Found!", TextUtil.DARK_GREEN)
            );

            return true;
        }

        TextUtil.errorMessage(player, "Unable to find unlock");
        return false;
    }

    // --------------------------------------------------
    // Legacy TabCompleter implementation (fallback)
    // --------------------------------------------------

    @Override
    public @Nullable List<String> onTabComplete(
            @NotNull CommandSender commandSender,
            @NotNull Command command,
            @NotNull String s,
            @NotNull String[] strings
    ) {
        List<String> suggestions = new ArrayList<>();

        if (strings.length == 1) {
            suggestions.add("help");

            for (Player player : Bukkit.getOnlinePlayers()) {
                suggestions.add(player.getName());
            }

            for (CustomItem item : getAllCustomItems()) {
                suggestions.add(item.getKey());
            }

        } else if (strings.length == 2) {
            for (CustomItem item : getAllCustomItems()) {
                suggestions.add(item.getKey());
            }
        }

        return suggestions;
    }

    // --------------------------------------------------
    // Shared item retrieval
    // --------------------------------------------------

    private List<CustomItem> getAllCustomItems() {
        List<List<Craft>> allCraft = plugin.getUnlocksData().getAllUnlocks();

        allCraft.add(new ArrayList<>(List.of(
                WoodenSword.getInstance(),
                StoneSword.getInstance(),
                IronSword.getInstance(),
                DiamondSword.getInstance(),
                NetheriteSword.getInstance(),
                WoodenAxe.getInstance(),
                StoneAxe.getInstance(),
                IronAxe.getInstance(),
                DiamondAxe.getInstance(),
                NetheriteAxe.getInstance(),
                Shield.getInstance(),
                Mace.getInstance()
        )));

        List<CustomItem> items = new ArrayList<>(List.of(
                HadesBook.getInstance(),
                XPTome.getInstance(),
                EnlightenedNugget.getInstance(),
                ToolVoucher.getInstance(),
                KitBook.getInstance(),
                MainMenu.getInstance(),
                ShardOfTheFallen.getInstance(),
                TeammateTracker.getInstance(),
                CreeperHead.getInstance(),
                SkeletonSkull.getInstance(),
                ZombieHead.getInstance(),
                PlayerHead.getInstance(),
                RejuvenationBook.getInstance(),
                NetherStar.getInstance(),
                Anduril.getInstance(),
                HardenedSaddle.getInstance(),
                RepairKit.getInstance(),
                VoidBox.getInstance()
        ));

        for (List<Craft> craft : allCraft) {
            items.addAll(craft);
        }

        return items;
    }
}
