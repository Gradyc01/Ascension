package me.depickcator.ascension.Player.Data;

import it.unimi.dsi.fastutil.Pair;
import me.depickcator.ascension.Ascension;
import me.depickcator.ascension.Items.UnlockRecommender;
import me.depickcator.ascension.Utility.TextUtil;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.RecipeChoice;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PlayerInventoryTracker implements PlayerDataObservers{
    private final PlayerData playerData;
    private Player player;
    private Map<Material, Integer> inventoryMaterials;
    private boolean needsUpdate;
    public PlayerInventoryTracker(PlayerData playerData) {
        this.playerData = playerData;
        player = playerData.getPlayer();
        inventoryMaterials = new HashMap<>();
        needsUpdate = false;
    }

    /*Resets the caching and re-parses this players inventory*/
    public void reparseInventory() {
        new BukkitRunnable() {
            @Override
            public void run() {
                Map<Material, Integer> newInventoryMaterials = new HashMap<>();
                List<Pair<Material, Integer>> materialChecks = new ArrayList<>();
                for (ItemStack item : player.getInventory().getContents()) {
                    if (item == null) continue;
                    shouldMatCheck(item, newInventoryMaterials, materialChecks);
                }
                inventoryMaterials = newInventoryMaterials;
                for (Pair<Material, Integer> pair : materialChecks) {
                    UnlockRecommender.getInstance().checkMaterial(new ItemStack(pair.left(), pair.right()), player);
                }
                needsUpdate = false;
                playerData.getPlayerStats().addInventoryRefresh();
            }
        }.runTaskLater(Ascension.getInstance(), 1);
    }

    /*Adds item using addItems() and then runs to check if the number of the item has increased
    * If it has increased add this item's material into matChecks with the amount it changed by*/
    private void shouldMatCheck(ItemStack item, Map<Material, Integer> invMats, List<Pair<Material, Integer>> matChecks) {
        addItems(item, item.getAmount(), invMats);
        int amountInOldInv = this.inventoryMaterials.getOrDefault(item.getType(), 0);
        int amountInNewInv = invMats.get(item.getType());
        if (amountInNewInv > amountInOldInv) matChecks.add(Pair.of(item.getType(), amountInNewInv - amountInOldInv));
    }

    /*Adds ItemStack item to be accounted for in the inventory tracker
    * Returns true if it was a new Item False otherwise*/
    public boolean addItems(ItemStack item) {
        return addItems(item, item.getAmount());
    }

    /*Adds ItemStack item to be accounted for in the inventory tracker
    Amount is the amount to be changed sometimes not decided by item.getAmount()
     * Returns true if it was a new Item False otherwise*/
    public boolean addItems(ItemStack item, int amount) {
        return addItems(item, amount, inventoryMaterials);
    }

    /*Adds ItemStack item to be accounted for in the Map: map
    Amount is the amount to be changed sometimes not decided by item.getAmount()
     * Returns true if it was a new Item False otherwise*/
    private boolean addItems(ItemStack item, int amount, Map<Material, Integer> map) {
        Material material = item.getType();
        int count = map.getOrDefault(material, 0);
        boolean newItem = count <= 0;
        map.put(material, count + amount);
        TextUtil.debugText("[Inventory Tracker] " +
                player.getName() + "    Material: " + material.name() + "   " + count + " ----> " + (count+amount));
        return newItem;
    }

    /*Removes ItemStack item to be accounted for in the inventory tracker*/
    public void removeItems(ItemStack item) {
        removeItems(item, item.getAmount());
    }

    /*Removes ItemStack item to be accounted for in the inventory tracker*/
    public void removeItems(ItemStack item, int amount) {
        Material material = item.getType();
        int count = inventoryMaterials.getOrDefault(material, -1);
        if (count - amount <= 0) {
            inventoryMaterials.remove(material);
            return;
        }
        inventoryMaterials.put(material, count - amount);
        TextUtil.debugText("[Inventory Tracker] " +
                player.getName() + "    Material: " + material.name() + "   " + count + " ----> " + (count-amount));
    }

    /*Checks to see if RecipeChoice choice is satisfied for this player*/
    public boolean canSatisfyRecipeChoice(RecipeChoice choice, int amountRequired, ItemStack newItem) {
//        int amountRequired = getChoiceCount(strings, character);
        if (choice instanceof RecipeChoice.ExactChoice) {
            RecipeChoice.ExactChoice exact = (RecipeChoice.ExactChoice) choice;
            for (ItemStack item : exact.getChoices()) {
                if (item == null) continue;
                if (checkForRequireAmount(item.getType(), exact, amountRequired, newItem)) {
                    return true;
                }
            }
            return false;
        } else {
            RecipeChoice.MaterialChoice material = (RecipeChoice.MaterialChoice) choice;
            for (Material mat : material.getChoices()) {
                if (checkForRequireAmount(mat, material, amountRequired, newItem)) {
                    return true;
                }
            }
            return false;
        }
    }

    /*Checks thats the RecipeChoice is satisfied properly for the material mat*/
    private boolean checkForRequireAmount(Material mat, RecipeChoice choice, int amountRequired, ItemStack newItem) {
        int count = inventoryMaterials.getOrDefault(mat, -1);
//        TextUtil.debugText("Count: " + count
//                + " Mat: " + mat.name()
//                + " New Item: " + newItem.getType().name()
//                + " Required: " + amountRequired+ " Minus: " + (count - newItem.getAmount()));
        boolean isThereEnoughOfMaterial = count >= amountRequired;
        //Checks to see if the material being tested is the same as the item the just came in the inventory if not the same the
        //newItem also can't be a part of the recipe choice
        boolean notSameMatOrInChoice = (mat != newItem.getType() && !choice.test(newItem));
        boolean firstTimeSatisfied = mat == newItem.getType() && (count - newItem.getAmount()) < amountRequired;
//        TextUtil.debugText(isThereEnoughOfMaterial + " && (" + notSameMatOrInChoice + " || " + firstTimeSatisfied + ")");
        return isThereEnoughOfMaterial && (notSameMatOrInChoice || firstTimeSatisfied);
    }

    public boolean isNeedsUpdate() {
        return needsUpdate;
    }

    /*Called when the inventory needs to be updated*/
    public void needsUpdate() {
        needsUpdate = true;
    }

    @Override
    public void updatePlayer() {
        player = playerData.getPlayer();
    }
}
