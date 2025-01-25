package me.depickcator.ascension.testingCommands;

import me.depickcator.ascension.Ascension;
import me.depickcator.ascension.Player.Data.PlayerUtil;
import me.depickcator.ascension.Utility.TextUtil;
import me.depickcator.ascension.Items.Craftable.Craft;
import me.depickcator.ascension.Items.Craftable.Vanilla.*;
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

public class giveCustomItem implements CommandExecutor, TabCompleter {
    private final Ascension plugin;
    public giveCustomItem() {
        plugin = Ascension.getInstance();
    }

    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {
        if (!(commandSender instanceof Player)) return false;
        Player p = ((Player) commandSender).getPlayer();
        if (p == null || strings.length == 0 || strings.length > 3) return false;

        String name = strings[1];
        p = Bukkit.getPlayer(strings[0]);

        ArrayList<ArrayList<Craft>> allCraft = getAllCustomItems();
        if (strings[0].equals("help")) {
            Component text = TextUtil.makeText("  All Item strings: ", TextUtil.GRAY);
            for (ArrayList<Craft> craft : allCraft) {
                for (Craft c : craft) {
                    text = text.append(TextUtil.makeText("   [" + c.getKey() + "]", TextUtil.GRAY));
                }
            }
            p.sendMessage(text);
        } else {
            for (ArrayList<Craft> craft : allCraft) {
                for (Craft c : craft) {
                    if (c.getKey().equals(name)) {
//                        p.getInventory().addItem(c.getResult());
                        ItemStack item = c.getResult();
                        int count;
                        if (strings.length == 2) {
                            count = 1;
                        } else {
                            count = Integer.parseInt(strings[2]);
                        }
                        for (int i = 0; i < count; i++) {
                            PlayerUtil.giveItem(p, item);
                        }
                        p.sendMessage(TextUtil.makeText("Item Found!", TextUtil.DARK_GREEN));
                        return true;
                    }
                }
            }
            TextUtil.errorMessage(p, "Unable to find unlock");
            return false;
        }
        return true;


    }

    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {
        List<String> arr = new ArrayList<>();
        if (strings.length == 1) {
            for (Player p : Bukkit.getOnlinePlayers()) {
                arr.add(p.getName());
            }
        } else if (strings.length == 2) {
            for (ArrayList<Craft> craft : getAllCustomItems()) {
                for (Craft c : craft) {
                    arr.add(c.getKey());
                }
            }
        }

        return arr;
    }

    private ArrayList<ArrayList<Craft>> getAllCustomItems() {
        ArrayList<ArrayList<Craft>> allCraft = plugin.getUnlocksData().getAllUnlocks();
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
                Shield.getInstance()
        )));
        return allCraft;
    }
}
