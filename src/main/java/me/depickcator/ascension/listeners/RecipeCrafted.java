package me.depickcator.ascension.listeners;

import me.depickcator.ascension.Ascension;
import me.depickcator.ascension.Items.Craftable.Craft;
import me.depickcator.ascension.Player.Data.PlayerData;
import me.depickcator.ascension.Player.Data.PlayerStats;
import me.depickcator.ascension.Utility.SoundUtil;
import me.depickcator.ascension.Utility.TextUtil;
import me.depickcator.ascension.Items.UnlockUtil;
import me.depickcator.ascension.Player.Data.PlayerUnlocks;
import me.depickcator.ascension.Player.Data.PlayerUtil;
import net.kyori.adventure.text.Component;
import org.apache.commons.lang3.tuple.Pair;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.CraftItemEvent;
import org.bukkit.event.inventory.PrepareItemCraftEvent;
import org.bukkit.inventory.*;

import java.util.EventListener;
import java.util.Objects;

public class RecipeCrafted implements EventListener, Listener {
    public RecipeCrafted() {
    }

    @EventHandler
    public void onPrepareCraft(PrepareItemCraftEvent event) {
        Recipe recipe = event.getRecipe();
        if (!isCraftingRecipe(recipe)) {
            return;
        }
        String recipeKey = getKey(recipe);
        if (!UnlockUtil.isAUnlock(recipeKey)) {
            return;
        }
//        Player player = (Player) event.getView().getPlayer();
//        PlayerUnlocks playerUnlocks = Objects.requireNonNull(PlayerUtil.getPlayerData(player)).getPlayerUnlocks();

//        int currentCrafts;
//        if (playerUnlocks.hasUnlock(recipeKey)) {
//            currentCrafts = playerUnlocks.getUnlockCount(recipeKey);
//        } else {
//            event.getInventory().setResult(null);
//            return;
//        }
//
        if (/*currentCrafts > UnlockUtil.getMaxCrafts(recipeKey) ||*/ calculateCraftAmount(event) > UnlockUtil.getMaxCrafts(recipeKey)) {
            event.getInventory().setResult(null); // Block crafting
        }
    }

    @EventHandler
    public void onCraftItem(CraftItemEvent event) {
        Recipe recipe = event.getRecipe();
        if (!isCraftingRecipe(recipe)) return;
        String recipeKey = getKey(recipe);
        craftingCustomItemEvent(event, recipeKey);
        if (!event.isCancelled() && event.getCurrentItem() != null) {
            Player p = (Player) event.getWhoClicked();
            PlayerData playerData = PlayerUtil.getPlayerData(p);
            playerData.getPlayerInventoryTracker().needsUpdate();
        }
    }

    /*Handles everything to do with crafting a custom item (Unlocks mostly)*/
    private void craftingCustomItemEvent(CraftItemEvent event, String recipeKey) {
        if (!UnlockUtil.isAUnlock(recipeKey)) return;

        Player player = (Player) event.getWhoClicked();
        int numberOfCrafts = calculateCraftAmount(event);

        if (!canCraftItem(event, player, numberOfCrafts, recipeKey)) return;
        PlayerUnlocks playerUnlocks = PlayerUtil.getPlayerData(player).getPlayerUnlocks();

        String displayName = UnlockUtil.getDisplayName(recipeKey);
        Craft c = Ascension.getInstance().getUnlocksData().findUnlock(displayName).getLeft();
        if (!c.uponCrafted(event, player)) {
            event.setCancelled(true);
            return;
        };

        //Check if player is crafting over the limit
        if (!playerUnlocks.addUnlockCount(recipeKey, numberOfCrafts)) {
            Component t1 = TextUtil.makeText("You can't craft that many!", TextUtil.RED);
            Component craftText = TextUtil.makeText(" (" + playerUnlocks.getUnlockCount(recipeKey) + "/" + UnlockUtil.getMaxCrafts(recipeKey) + ")", TextUtil.AQUA);
            player.sendMessage(t1.append(craftText));
            SoundUtil.playErrorSoundEffect(player);
            event.setCancelled(true);
            return;
        }

        Component text1 = TextUtil.makeText("You crafted ", TextUtil.AQUA);
        Component text2 = TextUtil.makeText(displayName, TextUtil.GOLD);
        Component craftText = TextUtil.makeText(" (" + playerUnlocks.getUnlockCount(recipeKey)  + "/" + UnlockUtil.getMaxCrafts(recipeKey) + ")", TextUtil.AQUA);
        player.sendMessage(text1.append(text2).append(craftText));
    }

    private boolean canCraftItem(CraftItemEvent event, Player player, int numberOfCrafts, String recipeKey) {
        //Check if Inventory is big enough
        if (event.isShiftClick() && !hasEnoughEmptySlots(player, numberOfCrafts)) {
            TextUtil.errorMessage(player, "Your inventory is too full to craft that much!");
            event.setCancelled(true);
            return false;
        }

        PlayerData playerData = PlayerUtil.getPlayerData(player);
        PlayerUnlocks playerUnlocks = playerData.getPlayerUnlocks();
        int currentCrafts = getCurrentCrafts(playerData, recipeKey);

        //Check if craft is Unlocked
        if (currentCrafts == -1) {
            TextUtil.errorMessage(player, "You do not have this item unlocked!");
            event.getInventory().setResult(null);
            return false;
        }

        //Check if player has reached the limit
        if (currentCrafts >= UnlockUtil.getMaxCrafts(recipeKey)) {
            event.getInventory().setResult(null);
            TextUtil.errorMessage(player, "You have reached the crafting limit for this item!");
            return false;
        }
        return true;
    }

    /*Gets the recipe key for Recipe recipe*/
    private String getKey(Recipe recipe) {
        if (recipe instanceof ShapedRecipe) {
            return ((ShapedRecipe) recipe).getKey().getKey();
        }
        if (recipe instanceof ShapelessRecipe) {
            return ((ShapelessRecipe) recipe).getKey().getKey();
        }
        return null;
    }

    /*Calculates the amount of times this crafting inventory could theoretically craft right now*/
    private int calculateCraftAmount(PrepareItemCraftEvent event) {
        int maxCraftableAmount = Integer.MAX_VALUE;

        // Calculate the maximum possible crafts based on the items in the crafting grid
        for (ItemStack item : event.getInventory().getMatrix()) {
            if (item != null && item.getType() != Material.AIR) {
                maxCraftableAmount = Math.min(maxCraftableAmount, item.getAmount());
            }
        }

        return maxCraftableAmount;
    }

    /*Is there an amount enough empty slots in player p's inventory to fit amount items*/
    private boolean hasEnoughEmptySlots(Player p, int amount) {
        PlayerInventory inv = p.getInventory();
        int i = 0;
        int iter = 0;
        for (ItemStack item : inv.getContents()) {
            if (iter > 35) return false;
            if (item == null) i++;
            if (i >= amount) return true;
            iter++;
        }
        return false;
    }

    /*Checks if it is a crafting recipe*/
    private boolean isCraftingRecipe(Recipe recipe) {
        if (recipe == null) {
            return false;
        }
        return recipe instanceof ShapedRecipe || recipe instanceof ShapelessRecipe;
    }

    /*Checks how many times this specific unlock has been crafted,
    * If the Unlock is not unlocked, it tries to auto unlocked if player has setting turned on
    * Else returns -1 representing not unlocked*/
    private int getCurrentCrafts(PlayerData playerData, String recipeKey) {
        PlayerUnlocks playerUnlocks = playerData.getPlayerUnlocks();
        PlayerStats playerStats = playerData.getPlayerStats();
        if (playerUnlocks.hasUnlock(recipeKey)) {
            return playerUnlocks.getUnlockCount(recipeKey);
        } else {
            String displayName = UnlockUtil.getDisplayName(recipeKey);
            Pair<Craft, Integer> craft = Ascension.getInstance().getUnlocksData().findUnlock(displayName);

            if (playerStats.getSetting(PlayerStats.autoPurchaseUnlocks)
                    && playerUnlocks.unlockUnlock(craft.getLeft(), craft.getRight())) {
                return playerUnlocks.getUnlockCount(recipeKey);
            }
            return -1;
        }
    }

    /*Calculates the amount of times this crafting inventory could craft right now*/
    private int calculateCraftAmount(CraftItemEvent event) {

        // Check if shift-click is being used
        if (event.getInventory().getResult() == null) {
            return 0;
        }
        if (event.isShiftClick()) {
            int maxCraftableAmount = Integer.MAX_VALUE;

            // Calculate the maximum possible crafts based on the items in the crafting grid
            for (ItemStack item : event.getInventory().getMatrix()) {
                if (item != null && item.getType() != Material.AIR) {
                    maxCraftableAmount = Math.min(maxCraftableAmount, item.getAmount());
                }
            }

            return maxCraftableAmount/* * resultCount*/;  // Total items crafted with shift-click
        } else {
            return 1;
        }
    }
}
