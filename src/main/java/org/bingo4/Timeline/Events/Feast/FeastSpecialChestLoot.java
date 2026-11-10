//package org.bingo4.Timeline.Events.Feast;
//
//import org.bingo4.Interfaces.CustomChestLoot;
//import org.bingo4.Items.Craftable.Unlocks.MakeshiftSkull;
//import org.bingo4.Items.Uncraftable.EnlightenedNugget;
//import org.bingo4.Items.Uncraftable.HadesBook.HadesBook;
//import org.bingo4.Items.Uncraftable.NetherStar.NetherStar;
//import org.bukkit.inventory.Inventory;
//import org.bukkit.inventory.ItemStack;
//
//import java.util.ArrayList;
//import java.util.Collection;
//import java.util.Random;
//
//public class FeastSpecialChestLoot extends CustomChestLoot {
//    private static FeastSpecialChestLoot instance;
//    @Override
//    public Collection<ItemStack> populateLoot(Inventory inv, Random r, double luck) {
////        return List.of();
//        ArrayList<ItemStack> items = new ArrayList<>();
////        items.add(new ItemStack(Material.NETHER_STAR, 2));
//        items.add(MakeshiftSkull.getInstance().getResult());
//        items.add(NetherStar.getInstance().getResult(2));
//        items.add(HadesBook.getInstance().getResult());
////        ItemStack item = EnlightenedNugget.getInstance().getResult();
////        item.setAmount(3);
////        items.add(item);
//        items.add(EnlightenedNugget.getInstance().getResult(3));
//
//        return placeInInventory(inv, r, items);
//    }
//
//
//    public static FeastSpecialChestLoot getInstance() {
//        if (instance == null) instance = new FeastSpecialChestLoot();
//        return instance;
//    }
//}
