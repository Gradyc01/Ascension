//package org.bingo4.testingCommands;
//
//import org.bingo4.Bingo4;
//import org.bingo4.Interfaces.Summonable;
//import org.bingo4.Items.Craftable.Craft;
//import org.bingo4.Items.Craftable.Vanilla.*;
//import org.bingo4.Items.CustomItem;
//import org.bingo4.Items.Uncraftable.*;
//import org.bingo4.Items.Uncraftable.HadesBook.HadesBook;
//import org.bingo4.Items.Uncraftable.NetherStar.NetherStar;
//import org.bingo4.Items.Uncraftable.Skulls.CreeperHead;
//import org.bingo4.Items.Uncraftable.Skulls.PlayerHead;
//import org.bingo4.Items.Uncraftable.Skulls.SkeletonSkull;
//import org.bingo4.Items.Uncraftable.Skulls.ZombieHead;
//import org.bingo4.Items.Uncraftable.ToolVoucher.ToolVoucher;
//import org.bingo4.Items.Uncraftable.XPTome.XPTome;
//import org.bingo4.Player.Data.PlayerUtil;
//import org.bingo4.Utility.TextUtil;
//import net.kyori.adventure.text.Component;
//import org.bukkit.Bukkit;
//import org.bukkit.command.Command;
//import org.bukkit.command.CommandExecutor;
//import org.bukkit.command.CommandSender;
//import org.bukkit.command.TabCompleter;
//import org.bukkit.entity.Player;
//import org.bukkit.inventory.ItemStack;
//import org.jetbrains.annotations.NotNull;
//import org.jetbrains.annotations.Nullable;
//
//import java.util.ArrayList;
//import java.util.List;
//
//public class giveCustomItem implements CommandExecutor, TabCompleter {
//    private final Bingo4 plugin;
//    public giveCustomItem() {
//        plugin = Bingo4.getInstance();
//    }
//
//    @Override
//    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {
//        if (!(commandSender instanceof Player)) return false;
//        Player p = ((Player) commandSender).getPlayer();
//        if (p == null || strings.length == 0 || strings.length > 3) return false;
//
//        List<CustomItem> allItems = getAllCustomItems();
//        if (strings[0].equals("help")) {
//            Component text = TextUtil.makeText("  All Item strings: ", TextUtil.GRAY);
//            for (CustomItem i : allItems) {
//                text = text.append(TextUtil.makeText("   [" + i.getKey() + "]", TextUtil.GRAY));
//            }
//            p.sendMessage(text);
//            return true;
//        }
//
//        String name = strings[1];
//        if (Bukkit.getPlayer(strings[0]) != null) {
//            p = Bukkit.getPlayer(strings[0]);
//        }
//        for (CustomItem customItem : allItems) {
//            if (customItem.getKey().equals(name)) {
//                ItemStack item = customItem.getResult();
//                int count = strings.length == 2 ? 1 : Integer.parseInt(strings[2]);
//                if (customItem instanceof Summonable summonable) {
//                    summonable.summonEffect(p);
//                    summonable.initEntity(p);
//                    return true;
//                }
//                for (int i = 0; i < count; i++) {
//                    PlayerUtil.giveItem(p, item);
//                }
//                p.sendMessage(TextUtil.makeText("Item Found!", TextUtil.DARK_GREEN));
//                return true;
//            }
//        }
//        TextUtil.errorMessage(p, "Unable to find unlock");
//        return false;
//    }
//
//    @Override
//    public @Nullable List<String> onTabComplete(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {
//        List<String> arr = new ArrayList<>();
//        if (strings.length == 1) {
//            for (Player p : Bukkit.getOnlinePlayers()) {
//                arr.add(p.getName());
//            }
//        } else if (strings.length == 2) {
//            for (CustomItem i : getAllCustomItems()) {
////                for (Craft c : craft) {
////                    arr.add(c.getKey());
////                }
//                arr.add(i.getKey());
//            }
//        }
//
//        return arr;
//    }
//
//    private List<CustomItem> getAllCustomItems() {
//        List<List<Craft>> allCraft = plugin.getUnlocksData().getAllUnlocks();
//        allCraft.add(new ArrayList<>(List.of(
//                WoodenSword.getInstance(),
//                StoneSword.getInstance(),
//                IronSword.getInstance(),
//                DiamondSword.getInstance(),
//                NetheriteSword.getInstance(),
//                WoodenAxe.getInstance(),
//                StoneAxe.getInstance(),
//                IronAxe.getInstance(),
//                DiamondAxe.getInstance(),
//                NetheriteAxe.getInstance(),
//                Shield.getInstance(),
//                Mace.getInstance()
//        )));
//        List<CustomItem> items = new ArrayList<>(List.of(
//                HadesBook.getInstance(),
//                XPTome.getInstance(),
//                EnlightenedNugget.getInstance(),
//                ToolVoucher.getInstance(),
//                KitBook.getInstance(),
//                MainMenu.getInstance(),
//                ShardOfTheFallen.getInstance(),
//                TeammateTracker.getInstance(),
//                CreeperHead.getInstance(),
//                SkeletonSkull.getInstance(),
//                ZombieHead.getInstance(),
//                PlayerHead.getInstance(),
//                RejuvenationBook.getInstance(),
//                NetherStar.getInstance(),
//                Anduril.getInstance(),
//                HardenedSaddle.getInstance(),
//                RepairKit.getInstance()
//                ));
//        for (List<Craft> craft : allCraft) {
//            items.addAll(craft);
//        }
//        return items;
//    }
//}
