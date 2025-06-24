package me.depickcator.ascension.Timeline.Events.SoulShop.Algorithms;

import me.depickcator.ascension.Timeline.Events.SoulShop.Shop;
import org.bukkit.inventory.CraftingRecipe;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.Recipe;

import java.util.ArrayList;
import java.util.List;

public class Global extends SoulShopAlgorithm {
    public Global(List<Shop> shops) {

    }

//    private void getItemsFromTheBoard(List<Shop> shops) {
//        List<ItemStack> bingoItems = plugin.getBingoData().getItems();
//        List<ItemStack> items =  new ArrayList<>();
//        for (ItemStack item : bingoItems) {
//            for (Recipe recipe : plugin.getServer().getRecipesFor(item)) {
//                if (recipe instanceof CraftingRecipe crafting) {
//                    if (containsString(crafting.getKey().asString(), expensiveList)) continue;
//                    items.addAll(getItemsFromRecipe(crafting));
//                }
//            }
//        }
//        buildSoulShopItemsFromItemStacks(pickItemsFromList(items, 7), shops);
//    }




}
