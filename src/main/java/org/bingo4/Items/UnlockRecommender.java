package org.bingo4.Items;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.event.ClickEvent;
import net.kyori.adventure.text.event.HoverEvent;
import org.bingo4.Player.Data.PlayerData;
import org.bingo4.Player.Data.PlayerInventoryTracker;
import org.bingo4.Player.Data.PlayerStats;
import org.bingo4.Player.Data.PlayerUtil;
import org.bingo4.Utility.TextUtil;
import org.bukkit.Bukkit;
import org.bukkit.Keyed;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.inventory.*;

import java.util.*;

/**
 * Recommends VANILLA crafting recipes to players as soon as they gather everything needed to craft them.
 * Only shaped and shapeless crafting recipes are supported.
 *
 * Recipes can be added/removed either individually (by Recipe or NamespacedKey) or in bulk by their result ItemStack.
 */
public class UnlockRecommender {
    private static UnlockRecommender instance;

    /*Which recipes an ingredient material is part of*/
    private final Map<Material, List<CraftingRecipe>> ingredients;
    /*Every recipe currently registered in the recommender*/
    private final Map<NamespacedKey, CraftingRecipe> recipes;
    /*Click codes sent to players so that they can open the recipe in a crafting table*/
    private final Map<UUID, CraftingRecipe> craftCodeMap;

    private UnlockRecommender() {
        ingredients = new HashMap<>();
        recipes = new HashMap<>();
        craftCodeMap = new HashMap<>();
    }

    public static UnlockRecommender getInstance() {
        if (instance == null) {
            instance = new UnlockRecommender();
        }
        return instance;
    }

    // ------------------------------------------------------------------ adding

    /*Adds a vanilla Recipe to the Unlock Recommender so that it could be recommended to players in the future
     * Returns true if the recipe was added, false if it was unsupported or already added*/
    public boolean addRecipe(Recipe recipe) {
        if (!isSupported(recipe)) return false;
        CraftingRecipe craftingRecipe = (CraftingRecipe) recipe;
        if (recipes.containsKey(craftingRecipe.getKey())) return false;

        Collection<RecipeChoice> choices = getChoices(craftingRecipe);
        if (choices.isEmpty()) return false;
        recipes.put(craftingRecipe.getKey(), craftingRecipe);

        for (RecipeChoice choice : choices) {
            for (Material material : getMaterials(choice)) {
                addRecipeToMaterial(material, craftingRecipe);
            }
        }
        return true;
    }

    /*Adds all the Recipes in recipes, returns how many were added*/
    public int addRecipes(Collection<? extends Recipe> recipes) {
        int added = 0;
        for (Recipe recipe : recipes) {
            if (addRecipe(recipe)) added++;
        }
        return added;
    }

    /*Adds every vanilla recipe that has result (ignoring the amount) as its result
     * Returns how many recipes were added*/
    public int addRecipes(ItemStack result) {
        return addRecipes(Bukkit.getRecipesFor(result));
    }

    /*Adds the vanilla recipe registered on the server with key, returns whether it was added*/
    public boolean addRecipe(NamespacedKey key) {
        Recipe recipe = Bukkit.getRecipe(key);
        return recipe != null && addRecipe(recipe);
    }

    // ------------------------------------------------------------------ removing

    /*Removes the specific Recipe from the Unlock Recommender, returns whether it was removed*/
    public boolean removeRecipe(Recipe recipe) {
        if (!(recipe instanceof Keyed keyed)) return false;
        return removeRecipe(keyed.getKey());
    }

    /*Removes the recipe with key from the Unlock Recommender, returns whether it was removed*/
    public boolean removeRecipe(NamespacedKey key) {
        CraftingRecipe removed = recipes.remove(key);
        if (removed == null) return false;
        Iterator<Map.Entry<Material, List<CraftingRecipe>>> it = ingredients.entrySet().iterator();
        while (it.hasNext()) {
            List<CraftingRecipe> list = it.next().getValue();
            list.removeIf(r -> r.getKey().equals(key));
            if (list.isEmpty()) it.remove();
        }
        craftCodeMap.values().removeIf(r -> r.getKey().equals(key));
        return true;
    }

    /*Removes every recipe that has result (ignoring the amount) as its result
     * Returns how many recipes were removed*/
    public int removeRecipes(ItemStack result) {
        List<NamespacedKey> toRemove = new ArrayList<>();
        for (CraftingRecipe recipe : recipes.values()) {
            if (recipe.getResult().isSimilar(result)) toRemove.add(recipe.getKey());
        }
        for (NamespacedKey key : toRemove) removeRecipe(key);
        return toRemove.size();
    }

    /*Removes every recipe from the Unlock Recommender*/
    public void clearRecipes() {
        ingredients.clear();
        recipes.clear();
        craftCodeMap.clear();
    }

    /*Returns whether the recipe is currently in the Unlock Recommender*/
    public boolean hasRecipe(Recipe recipe) {
        return recipe instanceof Keyed keyed && recipes.containsKey(keyed.getKey());
    }

    /*Returns a copy of all the recipes in the Unlock Recommender*/
    public Collection<CraftingRecipe> getRecipes() {
        return new ArrayList<>(recipes.values());
    }

    // ------------------------------------------------------------------ checking

    /*Checks ItemStack item and see if the new addition of this ItemStack can make any recipes craftable
     * ItemStack item's amount should be the amount the item increased by*/
    public void checkMaterial(ItemStack item, Player player) {
        Material material = item.getType();
        PlayerData playerData = PlayerUtil.getPlayerData(player);
        if (!playerData.getPlayerStats().getSetting(PlayerStats.craftNotifications)) return;
        findCraftableRecipes(item, playerData);
        TextUtil.debugText("Player " + player.getName() + " Triggered check for material " + material.name());
    }

    /*Used to reset the craftCodeMap typically called at the end of the game*/
    public void resetCraftCodes() {
        craftCodeMap.clear();
    }

    /*Get the recipe associated with UUID: uuid from the craft code map
     * Returns the recipe found if no recipe is found return null*/
    public CraftingRecipe getRecipeFromCode(UUID uuid) {
        return craftCodeMap.remove(uuid);
    }

    /*Finds recipes that became craftable with the new addition of the new ItemStack item for the player
     * and tells the player about each one*/
    private List<CraftingRecipe> findCraftableRecipes(ItemStack item, PlayerData playerData) {
        List<CraftingRecipe> found = new ArrayList<>();
        List<CraftingRecipe> candidates = ingredients.get(item.getType());
        if (candidates == null) return found;
        Player player = playerData.getPlayer();
        for (CraftingRecipe recipe : new ArrayList<>(candidates)) {
            if (canCraftRecipe(recipe, player, item)) {
                found.add(recipe);
                sendPlayerCraftableMessage(recipe, player);
            }
        }
        return found;
    }

    /*Checks if the recipe can be crafted by the player with the addition of the newItem
     * Only returns True if it can craft the recipe and the addition of the newItem ItemStack is what made it craftable*/
    private boolean canCraftRecipe(CraftingRecipe recipe, Player player, ItemStack newItem) {
        PlayerData playerData = PlayerUtil.getPlayerData(player);
        PlayerInventoryTracker tracker = playerData.getPlayerInventoryTracker();
        if (recipe instanceof ShapedRecipe shapedRecipe) {
            for (Map.Entry<Character, RecipeChoice> entry : shapedRecipe.getChoiceMap().entrySet()) {
                if (entry.getValue() == null) continue;
                if (!tracker.canSatisfyRecipeChoice(entry.getValue(), getChoiceCount(shapedRecipe.getShape(), entry.getKey()), newItem)) {
                    return false;
                }
            }
            return true;
        }
        if (recipe instanceof ShapelessRecipe shapelessRecipe) {
            for (RecipeChoice choice : shapelessRecipe.getChoiceList()) {
                if (!tracker.canSatisfyRecipeChoice(choice, 1, newItem)) return false;
            }
            return true;
        }
        return false;
    }

    // ------------------------------------------------------------------ helpers

    /*Only shaped and shapeless crafting recipes can be recommended*/
    private boolean isSupported(Recipe recipe) {
        return recipe instanceof ShapedRecipe || recipe instanceof ShapelessRecipe;
    }

    /*Returns an int showcasing how many of target is in the String[] input*/
    private int getChoiceCount(String[] input, char target) {
        String combined = String.join("", input);
        long count = combined.chars().filter(ch -> ch == target).count();
        return (int) count;
    }

    /*Adds recipe to be associated with Material material in ingredients*/
    private void addRecipeToMaterial(Material material, CraftingRecipe recipe) {
        List<CraftingRecipe> list = ingredients.computeIfAbsent(material, m -> new ArrayList<>());
        if (list.stream().noneMatch(r -> r.getKey().equals(recipe.getKey()))) {
            list.add(recipe);
        }
    }

    /*Returns every non-empty RecipeChoice of the recipe*/
    private Collection<RecipeChoice> getChoices(CraftingRecipe recipe) {
        List<RecipeChoice> choices = new ArrayList<>();
        if (recipe instanceof ShapedRecipe shaped) {
            for (RecipeChoice choice : shaped.getChoiceMap().values()) {
                if (choice != null) choices.add(choice);
            }
        } else if (recipe instanceof ShapelessRecipe shapeless) {
            for (RecipeChoice choice : shapeless.getChoiceList()) {
                if (choice != null) choices.add(choice);
            }
        }
        return choices;
    }

    /*Returns every Material that can satisfy the RecipeChoice*/
    private Collection<Material> getMaterials(RecipeChoice choice) {
        Set<Material> materials = new HashSet<>();
        if (choice instanceof RecipeChoice.MaterialChoice materialChoice) {
            materials.addAll(materialChoice.getChoices());
        } else if (choice instanceof RecipeChoice.ExactChoice exactChoice) {
            for (ItemStack item : exactChoice.getChoices()) {
                if (item != null) materials.add(item.getType());
            }
        }
        return materials;
    }

    /*Name of the recipe's result, translated on the player's client*/
    private Component getResultName(CraftingRecipe recipe) {
        return Component.translatable(recipe.getResult().getType().translationKey());
    }

    /*Tells player that the recipe is craftable and sends him a message telling him about it*/
    private void sendPlayerCraftableMessage(CraftingRecipe recipe, Player player) {
        UUID uuid = UUID.randomUUID();
        craftCodeMap.put(uuid, recipe);
        Component name = getResultName(recipe);
        Component text = TextUtil.makeText("You have all the items to craft a ", TextUtil.YELLOW)
                .append(name.color(TextUtil.AQUA))
                .append(TextUtil.makeText(" Click Here", TextUtil.GOLD, true, false)
                        .hoverEvent(
                                HoverEvent.showText(
                                        TextUtil.makeText("Click here to craft ", TextUtil.DARK_PURPLE)
                                                .append(name.color(TextUtil.DARK_PURPLE))))
                        .clickEvent(ClickEvent.runCommand("/craft " + uuid)))
                .append(TextUtil.makeText(" to craft", TextUtil.YELLOW));
        player.sendMessage(text);
    }
}