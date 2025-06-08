package me.depickcator.ascension.MainMenuUI.Unlocks;

import me.depickcator.ascension.Interfaces.AscensionMenuGUI;
import me.depickcator.ascension.Items.Craftable.Craft;
import me.depickcator.ascension.Player.Data.PlayerData;
import me.depickcator.ascension.Utility.TextUtil;
import org.bukkit.Material;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.*;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class ViewRecipeGUI extends AscensionMenuGUI {
    private final int[] craftingGridSlots = {
            11, 12, 13,
            20, 21, 22,
            29, 30, 31
    };
    private final HashMap<Integer, List<ItemStack>> displayMap;
    private BukkitTask display;
    private final char pageNumber;
    public ViewRecipeGUI(PlayerData playerData, Craft craft, char pageNumber) {
        super(playerData, (char) 6, TextUtil.makeText("Recipe: " + craft.getDisplayName(), TextUtil.AQUA), true);
        this.pageNumber = pageNumber;
        displayMap = initDisplayMap();
        makeRecipeGUI(craft);
        inventory.setItem(48, goBackItem());
        playerHeadButton(49);
    }

    private HashMap<Integer, List<ItemStack>> initDisplayMap() {
        HashMap<Integer, List<ItemStack>> displayMap = new HashMap<>();
        for (int i : craftingGridSlots) {
            displayMap.put(i, new ArrayList<>(List.of(
                    new ItemStack(Material.AIR)
            )));
        }
        return displayMap;
    }

    private void makeRecipeGUI(Craft craft) {
        Recipe recipe = craft.getRecipe();
        if (recipe instanceof ShapedRecipe shapedRecipe) {
            shapedRecipeGUI(shapedRecipe);
        } else if (recipe instanceof ShapelessRecipe shapelessRecipe) {
            shapelessRecipeGUI(shapelessRecipe);
        }
        displayItems();
        inventory.setItem(24, recipe.getResult());
    }

    private void shapelessRecipeGUI(ShapelessRecipe shapelessRecipe) {
        List<RecipeChoice> ingredients = shapelessRecipe.getChoiceList();
        for (int i = 0; i < ingredients.size(); i++) {
            RecipeChoice choice = ingredients.get(i);
            int slotIndex = craftingGridSlots[i]; // Place ingredients in the order they appear
            displayMap.put(slotIndex, getDisplayItems(choice));

        }
    }
    private void shapedRecipeGUI(ShapedRecipe shapedRecipe) {
        String[] shape = shapedRecipe.getShape();
        Map<Character, RecipeChoice> dict = shapedRecipe.getChoiceMap();
        for (int row = 0; row < shape.length; row++) {
            String line = shape[row];
            for (int col = 0; col < line.length(); col++) {
                char symbol = line.charAt(col);
                RecipeChoice ingredient = dict.get(symbol);
                int slotIndex = craftingGridSlots[row * 3 + col];
                displayMap.put(slotIndex, getDisplayItems(ingredient));
            }
        }
    }

    private List<ItemStack> getDisplayItems(RecipeChoice choice) {
        return switch (choice) {
            case RecipeChoice.MaterialChoice materialChoice ->
                // If it's a MaterialChoice, get the first material as a representative
                    materialChoice.getChoices().stream()
                            .map(ItemStack::new)
                            .collect(Collectors.toCollection(ArrayList::new));
            case RecipeChoice.ExactChoice exactChoice ->
                // If it's an ExactChoice, get the first item in the list
                    exactChoice.getChoices();
            case null, default -> List.of(new ItemStack(Material.AIR));
        };
    }

    private void displayItems() {
        display = new BukkitRunnable() {
            List<ItemStack> items = new ArrayList<>();
            @Override
            public void run() {
                items = getItems(items);
                display(items);
            }

            private List<ItemStack> getItems(List<ItemStack> items) {
                List<ItemStack> ans = new ArrayList<>();
                if (items.size() != 9) {
                    for (int i : craftingGridSlots) {
                        ans.add(displayMap.get(i).getFirst());
                    }
                } else {
                    for (int i = 0; i < craftingGridSlots.length; i++) {
                        List<ItemStack> itemList = displayMap.get(craftingGridSlots[i]);
                        int index = itemList.indexOf(items.get(i));
                        int newIndex = index + 1 >= itemList.size() ? 0 : index + 1;
                        ans.add(itemList.get(newIndex));
                    }
                }
                return ans;
            }

            private void display(List<ItemStack> items) {
                for (int i = 0; i < items.size(); i++) {
                    inventory.setItem(craftingGridSlots[i], items.get(i));
                }
            }


        }.runTaskTimer(plugin, 0, 40);
    }


    @Override
    public void interactWithGUIButtons(InventoryClickEvent event) {
        ItemStack item = event.getCurrentItem();
        if (item == null) return;

        if (item.equals(goBackItem())) {
            new UnlocksGUI(playerData, pageNumber);
        }
    }

    @Override
    public boolean runWhenCloseGUI(InventoryCloseEvent event) {
        display.cancel();
        return true;
    }
}
