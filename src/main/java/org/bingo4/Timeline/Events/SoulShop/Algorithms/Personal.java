//package org.bingo4.Timeline.Events.SoulShop.Algorithms;
//
//import org.bingo4.Items.Craftable.Craft;
//import org.bingo4.Items.UnlockUtil;
//import org.bingo4.Items.UnlocksData;
//import org.bingo4.MainMenuUI.BingoBoard.OpenRecipe;
//import org.bingo4.Player.Data.PlayerData;
//import org.bingo4.Player.Data.PlayerUnlocks;
//import org.bingo4.Timeline.Events.SoulShop.Shop;
//import org.bukkit.inventory.CraftingRecipe;
//import org.bukkit.inventory.ItemStack;
//import org.bukkit.inventory.Recipe;
//
//import java.util.ArrayList;
//import java.util.List;
//
//public class Personal extends SoulShopAlgorithm {
//    private final Shop shop;
//    public Personal(PlayerData playerData, Shop shop) {
//        this.shop = shop;
//        getItemsPlayerNeeds(playerData);
//        getItemsFromTheBoard(playerData);
//    }
//
//    private void getItemsPlayerNeeds(PlayerData playerData) {
//        PlayerUnlocks playerUnlocks = playerData.getPlayerUnlocks();
//        UnlocksData unlocksData =  plugin.getUnlocksData();
//        List<Craft> crafts = new ArrayList<>(unlocksData.getUnlocksTier(3));
//        crafts.addAll(unlocksData.getUnlocksTier(4));
//        crafts.addAll(unlocksData.getUnlocksTier(5));
//        List<ItemStack> items = new ArrayList<>();
//        for (Craft craft : crafts) {
//            String key = craft.getKey();
//            if (playerUnlocks.hasUnlock(key) || playerUnlocks.getUnlockCount(key) >= UnlockUtil.getMaxCrafts(key)) continue;
//            items.addAll(getItemsFromRecipe(craft.getRecipe()));
//        }
//        buildSoulShopItemsFromItemStacks(pickItemsFromList(items, 6), List.of(shop));
//    }
//
//    private void getItemsFromTheBoard(PlayerData playerData) {
//        List<Boolean> booleans = plugin.getBingoData().getItemsCompleted(playerData.getPlayer());
//        List<ItemStack> bingoItems = plugin.getBingoData().getItems();
//        List<ItemStack> items =  new ArrayList<>();
//        for (int i = 0; i < bingoItems.size(); i++) {
//            if (booleans.get(i)) continue;
//            ItemStack item = bingoItems.get(i);
//            for (Recipe recipe : new OpenRecipe(playerData, item).findRecipesForItem(item)) {
//                if (recipe instanceof CraftingRecipe crafting) {
//                    if (containsString(crafting.getKey().asString(), expensiveList)) continue;
//                    items.addAll(getItemsFromRecipe(crafting));
//                }
//            }
//        }
//        buildSoulShopItemsFromItemStacks(pickItemsFromList(items, 5), List.of(shop));
//    }
//}
