package me.depickcator.ascension.MainMenuUI.Unlocks;

import me.depickcator.ascension.Utility.TextUtil;
import me.depickcator.ascension.Interfaces.AscensionGUI;
import me.depickcator.ascension.Items.Craftable.Craft;
import me.depickcator.ascension.Player.Data.PlayerData;
import net.kyori.adventure.text.Component;
import org.bukkit.Material;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.*;
import org.bukkit.inventory.meta.ItemMeta;


import java.util.List;
import java.util.Map;

public class ViewRecipeGUI extends AscensionGUI {
    private final int[] craftingGridSlots = {
            11, 12, 13,
            20, 21, 22,
            29, 30, 31
    };
    private final char pageNumber;
//    private UnlocksGUI goBackGUI;
    public ViewRecipeGUI(PlayerData playerData, Craft craft, char pageNumber) {
        super(playerData, (char) 6, Component.text("Recipe: " + craft.getDisplayName()).color(TextUtil.AQUA), true);
        this.pageNumber = pageNumber;
        makeRecipeGUI(craft);
        inventory.setItem(48, goBackPage());
        playerHeadButton(49);
    }

    private void makeRecipeGUI(Craft craft) {
        for (int i : craftingGridSlots) {
            inventory.setItem(i, new ItemStack(Material.AIR));
        }
        Recipe recipe = craft.getRecipe();
        if (recipe instanceof ShapedRecipe shapedRecipe) {
            shapedRecipeGUI(shapedRecipe);
        } else if (recipe instanceof ShapelessRecipe shapelessRecipe) {
            shapelessRecipeGUI(shapelessRecipe);
        }
        inventory.setItem(24, recipe.getResult());
    }
    private void shapelessRecipeGUI(ShapelessRecipe shapelessRecipe) {
        List<RecipeChoice> ingredients = shapelessRecipe.getChoiceList();
        for (int i = 0; i < ingredients.size(); i++) {
            RecipeChoice choice = ingredients.get(i);
            int slotIndex = craftingGridSlots[i]; // Place ingredients in the order they appear
            if (choice != null) {
                ItemStack displayItem = getDisplayItem(choice);
                inventory.setItem(slotIndex, displayItem);
            }
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
                if (ingredient != null) {
                    ItemStack displayItem = getDisplayItem(ingredient);
                    inventory.setItem(slotIndex, displayItem);
                } else {
                    inventory.setItem(slotIndex, new ItemStack(Material.AIR));
                }
            }
        }
    }
    private ItemStack getDisplayItem(RecipeChoice choice) {
        if (choice instanceof RecipeChoice.MaterialChoice materialChoice) {
            // If it's a MaterialChoice, get the first material as a representative
            return new ItemStack(materialChoice.getChoices().getFirst());
        } else if (choice instanceof RecipeChoice.ExactChoice exactChoice) {
            // If it's an ExactChoice, get the first item in the list
            return exactChoice.getChoices().get(0);
        }
        return new ItemStack(Material.AIR); // Default to AIR if no valid choice is found
    }

    @Override
    public void interactWithGUIButtons(InventoryClickEvent event) {
        ItemStack item = event.getCurrentItem();
        if (item == null) {
            return;
        }
        if (item.getType() == Material.ARROW) {
            new UnlocksGUI(playerData, pageNumber);
        }
    }
    private ItemStack goBackPage() {
        ItemStack item = new ItemStack(Material.ARROW);
        ItemMeta meta = item.getItemMeta();
        meta.displayName(TextUtil.makeText("Go Back", TextUtil.DARK_GRAY));
        meta.setCustomModelData(999999);
        item.setItemMeta(meta);
        return item;
    }

}
