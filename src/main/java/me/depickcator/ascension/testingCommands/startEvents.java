package me.depickcator.ascension.testingCommands;

import me.depickcator.ascension.Items.ItemList;
import me.depickcator.ascension.Player.Data.PlayerUtil;
import me.depickcator.ascension.Timeline.Events.Ascension.AscensionEvent;
import me.depickcator.ascension.Timeline.Events.CarePackage.CarePackage;
import me.depickcator.ascension.Timeline.Events.Feast.Feast;
import me.depickcator.ascension.Timeline.Events.Scavenger.Scavenger;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;


public class startEvents implements CommandExecutor {
    // private final Ascension plugin;
    public startEvents() {
        // this.plugin = Ascension.getInstance();
    }

    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {
        if (!(commandSender instanceof Player)) return false;
        Player p = ((Player) commandSender).getPlayer();
        if (p == null || strings.length < 1) return false;

        String name = strings[0];


        if (name.equalsIgnoreCase("carepackage")) {
            new CarePackage(100);
        } else if (name.equalsIgnoreCase("scavenger")) {
            Scavenger scavenger = new Scavenger(100);
            scavenger.announceTrades();
            scavenger.announceSpawnLocation();
            scavenger.spawnInScavenger();
        } else if (name.equalsIgnoreCase("feast")) {
            new Feast();
        } else if (name.equalsIgnoreCase("ascension_loc")) {
            new AscensionEvent(100);
        } else if (name.equalsIgnoreCase("items")) {
            String c = strings[1];
            ItemList a = new ItemList();
            List<ItemStack> items = new ArrayList<>();
            if (c.equalsIgnoreCase("easy")) {
                items.addAll(a.getEasyItems().getItems());
            } else if (c.equalsIgnoreCase("medium")) {
                items.addAll(a.getMediumItems().getItems());
            } else if (c.equalsIgnoreCase("hard")) {
                items.addAll(a.getHardItems().getItems());
            } else if (c.equalsIgnoreCase("custom")) {
                items.addAll(a.getCustomItems().getItems());
            }
            for (ItemStack item : items) {
                PlayerUtil.giveItem(p, item);
            }
        }
        return false;
    }
}
