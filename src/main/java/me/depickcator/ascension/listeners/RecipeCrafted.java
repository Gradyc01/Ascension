package me.depickcator.ascension.listeners;

import me.depickcator.ascension.Ascension;
import me.depickcator.ascension.Items.Craftable.Craft;
import me.depickcator.ascension.Utility.TextUtil;
import me.depickcator.ascension.Items.UnlockUtil;
import me.depickcator.ascension.Player.Data.PlayerUnlocks;
import me.depickcator.ascension.Player.Data.PlayerUtil;
import net.kyori.adventure.text.Component;
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

//        plugin.getServer().broadcast(Component.text("Prepare Item Craft Event Triggered"));
        Recipe recipe = event.getRecipe();
        if (!isCraftingRecipe(recipe)) {
            return;
        }
        String recipeKey = getKey(recipe);
        if (!UnlockUtil.isAUnlock(recipeKey)) {
            return;
        }
        Player player = (Player) event.getView().getPlayer();
        PlayerUnlocks playerUnlocks = Objects.requireNonNull(PlayerUtil.getPlayerData(player)).getPlayerUnlocks();

        int currentCrafts;
        if (playerUnlocks.hasUnlock(recipeKey)) {
            currentCrafts = playerUnlocks.getUnlockCount(recipeKey);
        } else {
            event.getInventory().setResult(null);
            return;
        }

        if (currentCrafts > UnlockUtil.getMaxCrafts(recipeKey) || calculateCraftAmount(event) > UnlockUtil.getMaxCrafts(recipeKey)) {
            event.getInventory().setResult(null); // Block crafting
        }
    }

    @EventHandler
    public void onCraftItem(CraftItemEvent event) {
        Recipe recipe = event.getRecipe();
        if (!isCraftingRecipe(recipe)) return;
        String recipeKey = getKey(recipe);
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

        PlayerUnlocks playerUnlocks = PlayerUtil.getPlayerData(player).getPlayerUnlocks();
        int currentCrafts = getCurrentCrafts(playerUnlocks, recipeKey);

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

    private String getKey(Recipe recipe) {
        if (recipe instanceof ShapedRecipe) {
            return ((ShapedRecipe) recipe).getKey().getKey();
        }
        if (recipe instanceof ShapelessRecipe) {
            return ((ShapelessRecipe) recipe).getKey().getKey();
        }
        return null;
    }

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

    private boolean isCraftingRecipe(Recipe recipe) {
        if (recipe == null) {
            return false;
        }
        return recipe instanceof ShapedRecipe || recipe instanceof ShapelessRecipe;
    }

    private int getCurrentCrafts(PlayerUnlocks playerUnlocks, String recipeKey) {
        if (playerUnlocks.hasUnlock(recipeKey)) {
            return playerUnlocks.getUnlockCount(recipeKey);
        } else {
            return -1;
        }
    }

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
