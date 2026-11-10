//package org.bingo4.Timeline.Events.SoulShop.Algorithms;
//
//import org.bingo4.Interfaces.CustomChestLootPool;
//import org.bingo4.Interfaces.LootPoolItem;
//import org.bingo4.Items.Uncraftable.NetherStar.NetherStar;
//import org.bingo4.Items.Uncraftable.RejuvenationBook;
//import org.bingo4.Items.Uncraftable.RepairKit;
//import org.bingo4.Items.Uncraftable.ShardOfTheFallen;
//import org.bingo4.Items.Uncraftable.ToolVoucher.ToolVoucher;
//import org.bingo4.Timeline.Events.SoulShop.Shop;
//import org.bingo4.Timeline.Events.SoulShop.SoulShopItem;
//import org.bukkit.Material;
//import org.bukkit.inventory.ItemStack;
//
//import java.util.Collection;
//import java.util.List;
//
//public class Global extends SoulShopAlgorithm {
//    private final CustomChestLootPool chestLootPool;
//    public Global(List<Shop> shops) {
//        chestLootPool = initChestLootPool();
//        Collection<ItemStack> collection = chestLootPool.getRandomItemFromList(r, 3, true);
////        collection.add(RepairKit.getInstance().getResult());
//        buildSoulShopItemsFromItemStacks(collection, shops);
//        for (Shop shop : shops) {
//            addItem(new SoulShopItem("Repair Kit", 500, r.nextInt(2, 6),
//                    RepairKit.getInstance().getResult(), shop));
//        }
//    }
//
//    private CustomChestLootPool initChestLootPool() {
//        return new CustomChestLootPool(
//                new LootPoolItem(new ItemStack(Material.ARROW, r.nextInt(1, 4) * 16), 2),
//                new LootPoolItem(new ItemStack(Material.GOLDEN_APPLE, r.nextInt(2, 6)), 2),
//                new LootPoolItem(NetherStar.getInstance().getResult()),
//                new LootPoolItem(ShardOfTheFallen.getInstance().getResult(r.nextInt(2, 5))),
//                new LootPoolItem(RejuvenationBook.getInstance().getResult()),
//                new LootPoolItem(ToolVoucher.getInstance().getResult())
//        );
//    }
//
//
//
//
//}
