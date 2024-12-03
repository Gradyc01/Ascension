package me.depickcator.ascension.mainMenu.Unlocks;

import me.depickcator.ascension.Ascension;
import me.depickcator.ascension.General.TextUtil;
import me.depickcator.ascension.Interfaces.AscensionGUI;
import me.depickcator.ascension.Items.Craftable.Craft;
import net.kyori.adventure.text.Component;
import org.apache.commons.lang3.tuple.MutablePair;
import org.apache.commons.lang3.tuple.Pair;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.*;
import org.bukkit.inventory.meta.ItemMeta;


import java.util.List;
import java.util.Map;

public class ViewRecipeGUI implements AscensionGUI {
    public final static String menuName = "VIEW-RECIPE";
    private final int[] craftingGridSlots = {
            11, 12, 13,
            20, 21, 22,
            29, 30, 31
    };
    private final int GUISize = 6 * 9;
    private final Inventory inventory;
    private Player player;
    private Ascension plugin;
    private UnlocksGUI goBackGUI;
    public ViewRecipeGUI(Player player, Ascension plugin, Craft craft, UnlocksGUI goBackGUI) {
        this.player = player;
        this.plugin = plugin;
        this.goBackGUI = goBackGUI;
        inventory = Bukkit.createInventory(player, GUISize, Component.text("Recipe: " + craft.getDisplayName()).color(TextUtil.AQUA));
        if (player == null) {
            return;
        }
        player.openInventory(inventory);
        setItemBackground(inventory,GUISize);
        makeRecipeGUI(craft);
        inventory.setItem(48, goBackPage());
        playerHeadButton(inventory, 49, player);

        Pair<Inventory, AscensionGUI> pair2 = new MutablePair<>(inventory,this);
        Ascension.guiMap.put(player.getUniqueId(), pair2);

    }

    private void makeRecipeGUI(Craft craft) {
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
    public String getGUIName() {
        return menuName;
    }

    @Override
    public void interactWithGUIButtons(InventoryClickEvent event, Player p) {
        ItemStack item = event.getCurrentItem();
        if (item == null) {
            return;
        }
        if (item.getType() == Material.ARROW) {
            if (goBackGUI instanceof UnlocksGUI_1) {
                new UnlocksGUI_1(plugin, player);
            } else if (goBackGUI instanceof UnlocksGUI_2) {
                new UnlocksGUI_2(plugin, player);
            }
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
