package me.depickcator.ascension.commands;

import me.depickcator.ascension.Items.Craftable.Craft;
import me.depickcator.ascension.Items.UnlockRecommender;
import me.depickcator.ascension.Utility.TextUtil;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.*;
import org.bukkit.inventory.view.builder.LocationInventoryViewBuilder;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;
import java.util.UUID;

public class CraftCommand implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String @NotNull [] args) {
        if (args.length != 1 || !(sender instanceof Player)) return false;
        Player player = (Player) sender;
//        UUID uuid = args[0]
        UUID uuid = UUID.fromString(args[0]);
        Craft c = UnlockRecommender.getInstance().getCraftFromCode(uuid);
        if (c == null) {
            TextUtil.errorMessage(player, "Something went wrong! Try crafting it in the crafting table");
            return false;
        }
        openCraftingInventory(player, c.getRecipe());
        return true;
    }

    private void openCraftingInventory(Player player, Recipe recipe) {
        LocationInventoryViewBuilder builder = MenuType.CRAFTING.builder();
        builder.location(player.getLocation());
        InventoryView view = builder.build(player);
        CraftingInventory inv = (CraftingInventory) view.getTopInventory();
        ItemStack[] matrix = {
                new ItemStack(Material.AIR), new ItemStack(Material.AIR), new ItemStack(Material.AIR),
                new ItemStack(Material.AIR), new ItemStack(Material.AIR), new ItemStack(Material.AIR),
                new ItemStack(Material.AIR), new ItemStack(Material.AIR), new ItemStack(Material.AIR)};
        boolean[] booleans = buildBooleanMatrix(recipe);
        if (recipe instanceof ShapedRecipe) {
            ShapedRecipe shapedRecipe = (ShapedRecipe) recipe;
            for (ItemStack item : player.getInventory().getContents()) {
                if (item == null) continue;
                for (Map.Entry<Character, RecipeChoice> entry : shapedRecipe.getChoiceMap().entrySet()) {
                    if (entry.getValue().test(item)) {
                        placeShapedItemsIntoMatrix(matrix, booleans, shapedRecipe.getShape(), item, entry.getKey());
                    };
                }
            }
        }
        if (recipe instanceof ShapelessRecipe shapelessRecipe) {
            Collection<RecipeChoice> choices = shapelessRecipe.getChoiceList();
            for (ItemStack item : player.getInventory().getContents()) {
                if (item == null) continue;
                for (RecipeChoice choice : new ArrayList<>(choices)) {
                    if (choice.test(item)) {
                        placeShapelessItemsIntoMatrix(matrix, booleans, item);
                        choices.remove(choice);
                    };
                }
            }
        }

        inv.setMatrix(matrix);
        player.openInventory(view);
    }

    private void placeShapedItemsIntoMatrix(ItemStack[] matrix, boolean[] booleans, String[] shape, ItemStack item, char c) {
        String string = parseShapeString(shape);
        for (int i = 0; i < string.length(); i++) {
            char ch = string.charAt(i);
            boolean b = booleans[i];
            if (!b && ch == c && item.getAmount() > 0) {
                ItemStack newItem = item.clone();
                newItem.setAmount(1);
                matrix[i] = newItem;
                booleans[i] = true;
                item.setAmount(item.getAmount() - 1);
            }
        }
    }

    private void placeShapelessItemsIntoMatrix(ItemStack[] matrix, boolean[] booleans, ItemStack item) {
        for (int i = 0; i < matrix.length; i++) {
            boolean b = booleans[i];
            if (!b && item.getAmount() > 0) {
                ItemStack newItem = item.clone();
                newItem.setAmount(1);
                matrix[i] = newItem;
                booleans[i] = true;
                item.setAmount(item.getAmount() - 1);
                break;
            }
        }
    }

    private boolean[] buildBooleanMatrix(Recipe recipe) {
        boolean[] matrix = {
                false, false, false, false ,false ,false, false, false, false
        };
        if (recipe instanceof ShapedRecipe shaped) {
            String string = parseShapeString(shaped.getShape());
            for (int i = 0; i < string.length(); i++) {
                if (string.charAt(i) == ' ') {
                    matrix[i] = true;
                }
            }
            return matrix;
        }
        if (recipe instanceof ShapelessRecipe shapeless) {
            int choiceListSize = shapeless.getChoiceList().size();
//            for (int i = 0; i < shapeless.getChoiceList().size(); i++) {
//                matrix[i] = false;
//            }
            for (int i = 0; i < matrix.length; i++) {
                matrix[i] = i >= choiceListSize;
            }
            return matrix;
        }
        return matrix;
    }

    private String parseShapeString(String[] shape) {
        int len = shape[0].length();
        String str = "";
        for (String s : shape) {
            str += s;
            if (len == 2) str += " ";
            if (len == 1) str += "  ";
        }
        return str;
    }
}
