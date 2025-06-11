package me.depickcator.ascension.Items;

import me.depickcator.ascension.Ascension;
import me.depickcator.ascension.Items.Craftable.Craft;
import me.depickcator.ascension.Player.Data.*;
import me.depickcator.ascension.Utility.TextUtil;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.event.ClickEvent;
import net.kyori.adventure.text.event.HoverEvent;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.*;

import java.util.*;

public class UnlockRecommender {
    private final Map<Material, List<Craft>> ingredients;
    private static UnlockRecommender instance;
    private final Map<UUID, Craft> craftCodeMap;
    private UnlockRecommender() {
        ingredients = new HashMap<>();
        craftCodeMap = new HashMap<>();
    }

    public static UnlockRecommender getInstance() {
        if (instance == null) {
            instance = new UnlockRecommender();
        }
        return instance;
    }

    /*Add Craft to the Unlock Recommender so that it could be recommended to players in the future*/
    public void addCraft(Craft craft) {
        Collection<RecipeChoice> choices = getChoiceMap(craft.getRecipe());
        if (choices == null) return;
        for (RecipeChoice choice : choices) {
            if (choice instanceof RecipeChoice.MaterialChoice materialChoice) {
                for (Material material : materialChoice.getChoices()) {
                    addCraftToMaterial(material, craft);
                }
            }
            if (choice instanceof RecipeChoice.ExactChoice exactChoice) {
                for (ItemStack item : exactChoice.getChoices()) {
                    addCraftToMaterial(item.getType(), craft);
                }
            }
        }
    }

    /*Checks ItemStack item and see if the new addition of this ItemStack can Unlock any crafts
    * ItemStack item's amount should be the amount the item increased by*/
    public void checkMaterial(ItemStack item, Player player) {
        Material material = item.getType();
        PlayerData playerData = PlayerUtil.getPlayerData(player);
        if (!playerData.getPlayerStats().getSetting(PlayerStats.craftNotifications)) return;
        String debugText = "Triggered check for material " + material.name() + "    ";
        findCraftableUnlocks(item, playerData);
        TextUtil.debugText(debugText);
    }

    /*Used to reset the craftCodeMap typically called at the end of the game*/
    public void resetCraftCodes() {
        craftCodeMap.clear();
    }

    /*Get the craft associated with UUID: uuid from the craft code map
    * Returns the craft found if no craft is found return null*/
    public Craft getCraftFromCode(UUID uuid) {
        Craft c = craftCodeMap.get(uuid);
        if (c != null) craftCodeMap.remove(uuid);
        return c;
    }

    /*Finds Craftable unlocks with the new addition of the new ItemStack item for Player player*/
    private List<Craft> findCraftableUnlocks(ItemStack item, PlayerData playerData) {
        String debugText = "";
        List<Craft> crafts = ingredients.get(item.getType());
        List<Craft> craftsToUnlock = new ArrayList<>();
        if (crafts == null) return craftsToUnlock;
        PlayerUnlocks playerUnlocks = playerData.getPlayerUnlocks();
        Player player = playerData.getPlayer();
        for (Craft craft : crafts) {
            debugText += "Checking craft " + craft.getDisplayName()+ "    ";
            if (shouldBeShown(playerUnlocks, craft) &&
                canCraftRecipe(craft.getRecipe(), player, item)) {
                craftsToUnlock.add(craft);
                sendPlayerCraftableMessage(craft, player);
            }
        }
        TextUtil.debugText(debugText);
        return craftsToUnlock;
    }

    /*Checks that whether to show Craft: craft for Player in playerUnlocks*/
    private boolean shouldBeShown(PlayerUnlocks playerUnlocks, Craft craft) {
        int unlockCount = playerUnlocks.getUnlockCount(craft.getKey());
        return (unlockCount != -1 && unlockCount < UnlockUtil.getMaxCrafts(craft.getKey())) ||
                playerUnlocks.canBeUnlocked(craft, Ascension.getInstance().getUnlocksData().findUnlock(craft.getDisplayName()).getRight());
    }

    /*Checks if Recipe recipe can be crafted and whether it should be displayed for Player player with the addition of the newItem
    * Only returns True if it can craft Recipe recipe and the addition of the newItem ItemStack is what made it craftable*/
    private boolean canCraftRecipe(Recipe recipe, Player player, ItemStack newItem) {
        PlayerData playerData = PlayerUtil.getPlayerData(player);
        PlayerInventoryTracker tracker = playerData.getPlayerInventoryTracker();
        if (recipe instanceof ShapedRecipe) {
            ShapedRecipe shapedRecipe = (ShapedRecipe) recipe;
            for (Map.Entry<Character, RecipeChoice> entry : shapedRecipe.getChoiceMap().entrySet()) {
                if (!tracker.canSatisfyRecipeChoice(entry.getValue(), getChoiceCount(shapedRecipe.getShape(), entry.getKey()), newItem)) {
                    return false;
                }
            }
            return true;
        }
        if (recipe instanceof ShapelessRecipe) {
            ShapelessRecipe shapelessRecipe = (ShapelessRecipe) recipe;
            for (RecipeChoice entry : shapelessRecipe.getChoiceList()) {
                if (!tracker.canSatisfyRecipeChoice(entry, 1, newItem)) return false;
            }
            return true;
        }
        return false;
    }

    /*Returns an int showcasing how many of target is in the String[] input*/
    private int getChoiceCount(String[] input, char target) {
        String combined = String.join("", input);
        long count = combined.chars().filter(ch -> ch == target).count();
        return (int) count;
    }

    /*Adds Craft craft to be associated with Material material in ingredients*/
    private void addCraftToMaterial(Material material, Craft craft) {
        List<Craft> crafts = ingredients.get(material) == null ? new ArrayList<>() : ingredients.get(material);
        if (!crafts.contains(craft)) {
            crafts.add(craft);
            ingredients.put(material, crafts);
        }
    }

    /*Retuens a collection of RecipeChoice for both types of recipe
    * Returns null if the Recipe is not one of those instances*/
    private Collection<RecipeChoice> getChoiceMap(Recipe recipe) {
        if (recipe instanceof ShapedRecipe) {
            return ((ShapedRecipe) recipe).getChoiceMap().values();
        } else if (recipe instanceof ShapelessRecipe) {
            return ((ShapelessRecipe) recipe).getChoiceList();
        }
        return null;
    }

    /*Tells player that the Craft craft is craftable and sends him a message telling him about it*/
    private void sendPlayerCraftableMessage(Craft craft, Player player) {
        UUID uuid = UUID.randomUUID();
        craftCodeMap.put(uuid, craft);
        Component text = TextUtil.makeText("You have all the items to craft a ", TextUtil.YELLOW)
                .append(TextUtil.makeText(craft.getDisplayName(), TextUtil.AQUA))
                .append(TextUtil.makeText(" Click Here", TextUtil.GOLD, true, false)
                        .hoverEvent(
                                HoverEvent.showText(
                                        TextUtil.makeText("Click here to craft " + craft.getDisplayName(), TextUtil.DARK_PURPLE)))
                        .clickEvent(ClickEvent.runCommand("/craft " + uuid)))
                .append(TextUtil.makeText(" to craft", TextUtil.YELLOW));
        player.sendMessage(text);
    }
}
