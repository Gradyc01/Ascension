package me.depickcator.ascension.testingCommands;

import me.depickcator.ascension.Ascension;
import me.depickcator.ascension.Items.CustomItem;
import me.depickcator.ascension.Items.Uncraftable.*;
import me.depickcator.ascension.Items.Uncraftable.HadesBook.HadesBook;
import me.depickcator.ascension.Items.Uncraftable.NetherStar.NetherStar;
import me.depickcator.ascension.Items.Uncraftable.Skulls.CreeperHead;
import me.depickcator.ascension.Items.Uncraftable.Skulls.PlayerHead;
import me.depickcator.ascension.Items.Uncraftable.Skulls.SkeletonSkull;
import me.depickcator.ascension.Items.Uncraftable.Skulls.ZombieHead;
import me.depickcator.ascension.Items.Uncraftable.ToolVoucher.ToolVoucher;
import me.depickcator.ascension.Items.Uncraftable.XPTome.XPTome;
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

        List<CustomItem> allItems = getAllCustomItems();
        if (strings[0].equals("help")) {
            Component text = TextUtil.makeText("  All Item strings: ", TextUtil.GRAY);
            for (CustomItem i : allItems) {
                text = text.append(TextUtil.makeText("   [" + i.getKey() + "]", TextUtil.GRAY));
            }
            p.sendMessage(text);
            return true;
        }

        String name = strings[1];
        if (Bukkit.getPlayer(strings[0]) != null) {
            p = Bukkit.getPlayer(strings[0]);
        }
        for (CustomItem customItem : allItems) {
            if (customItem.getKey().equals(name)) {
                ItemStack item = customItem.getResult();
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
        TextUtil.errorMessage(p, "Unable to find unlock");
        return false;
    }

    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {
        List<String> arr = new ArrayList<>();
        if (strings.length == 1) {
            for (Player p : Bukkit.getOnlinePlayers()) {
                arr.add(p.getName());
            }
        } else if (strings.length == 2) {
            for (CustomItem i : getAllCustomItems()) {
//                for (Craft c : craft) {
//                    arr.add(c.getKey());
//                }
                arr.add(i.getKey());
            }
        }

        return arr;
    }

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
                RepairKit.getInstance()
                ));
        for (List<Craft> craft : allCraft) {
            items.addAll(craft);
        }
        return items;
    }
}
