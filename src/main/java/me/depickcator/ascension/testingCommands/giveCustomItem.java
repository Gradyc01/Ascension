package me.depickcator.ascension.testingCommands;

import me.depickcator.ascension.Ascension;
import me.depickcator.ascension.Utility.TextUtil;
import me.depickcator.ascension.Items.Craftable.Craft;
import me.depickcator.ascension.Items.Craftable.Vanilla.*;
import net.kyori.adventure.text.Component;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class giveCustomItem implements CommandExecutor {
    private final Ascension plugin;
    public giveCustomItem() {
        plugin = Ascension.getInstance();
    }

    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {
        if (!(commandSender instanceof Player)) return false;
        Player p = ((Player) commandSender).getPlayer();
        if (p == null || strings.length != 1) return false;

        String name = strings[0];

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
        if (name.equals("help")) {
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
                        p.getInventory().addItem(c.getResult());
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
}
