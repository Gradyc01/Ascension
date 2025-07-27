package me.depickcator.ascension.Timeline.Events.SoulShop.Algorithms;

import me.depickcator.ascension.Items.Craftable.Craft;
import me.depickcator.ascension.Items.UnlockUtil;
import me.depickcator.ascension.Items.UnlocksData;
import me.depickcator.ascension.MainMenuUI.BingoBoard.OpenRecipe;
import me.depickcator.ascension.Player.Data.PlayerData;
import me.depickcator.ascension.Player.Data.PlayerUnlocks;
import me.depickcator.ascension.Timeline.Events.SoulShop.Shop;
import me.depickcator.ascension.Timeline.Events.SoulShop.SoulShopItem;
import net.kyori.adventure.text.serializer.plain.PlainTextComponentSerializer;
import org.bukkit.inventory.CraftingRecipe;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.Recipe;

import java.util.ArrayList;
import java.util.List;

public class Personal extends SoulShopAlgorithm {
    private final Shop shop;
    public Personal(PlayerData playerData, Shop shop) {
        this.shop = shop;
        getItemsPlayerNeeds(playerData);
        getItemsFromTheBoard(playerData);
    }

    private void getItemsPlayerNeeds(PlayerData playerData) {
        PlayerUnlocks playerUnlocks = playerData.getPlayerUnlocks();
        UnlocksData unlocksData =  plugin.getUnlocksData();
        List<Craft> crafts = new ArrayList<>(unlocksData.getUnlocksTier(3));
        crafts.addAll(unlocksData.getUnlocksTier(4));
        crafts.addAll(unlocksData.getUnlocksTier(5));
        List<ItemStack> items = new ArrayList<>();
        for (Craft craft : crafts) {
            String key = craft.getKey();
            if (playerUnlocks.hasUnlock(key) || playerUnlocks.getUnlockCount(key) >= UnlockUtil.getMaxCrafts(key)) continue;
            items.addAll(getItemsFromRecipe(craft.getRecipe()));
        }
        buildSoulShopItemsFromItemStacks(pickItemsFromList(items, 6), List.of(shop));
    }

    private void getItemsFromTheBoard(PlayerData playerData) {
        List<Boolean> booleans = plugin.getBingoData().getItemsCompleted(playerData.getPlayer());
        List<ItemStack> bingoItems = plugin.getBingoData().getItems();
        List<ItemStack> items =  new ArrayList<>();
        for (int i = 0; i < bingoItems.size(); i++) {
            if (booleans.get(i)) continue;
            ItemStack item = bingoItems.get(i);
            for (Recipe recipe : new OpenRecipe(playerData, item).findRecipesForItem(item)) {
                if (recipe instanceof CraftingRecipe crafting) {
                    if (containsString(crafting.getKey().asString(), expensiveList)) continue;
                    items.addAll(getItemsFromRecipe(crafting));
                }
            }
        }
        buildSoulShopItemsFromItemStacks(pickItemsFromList(items, 5), List.of(shop));
    }
}
