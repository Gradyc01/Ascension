package me.depickcator.ascensionBingo.mainMenu.Unlocks;

import me.depickcator.ascensionBingo.AscensionBingo;
import me.depickcator.ascensionBingo.General.TextUtil;
import me.depickcator.ascensionBingo.Items.Craftable.Crafts;
import me.depickcator.ascensionBingo.Player.PlayerUnlocks;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;
import net.kyori.adventure.text.format.TextDecoration;
import org.apache.commons.lang3.tuple.Pair;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public interface UnlocksGUI {
    default void placeBarrierInGui(int startingIndex, ItemStack item, Inventory inventory) {
        for (int i = startingIndex; i < 45; i+=9) {
            inventory.setItem(i, item);
        }
    }

    default void placeUnlockInGUI(ArrayList<Crafts> arr, int startingIndex, PlayerUnlocks playerUnlocks, Inventory inventory) {
        int i = 0;
        int index = startingIndex;

        for (Crafts c : arr) {
            ItemStack item;
            ItemMeta meta;
            if (playerUnlocks.hasUnlock(c.getKey())) {
                item = unlockedItem();
                meta = item.getItemMeta();
                meta.displayName(Component.text(c.getDisplayName()).color(TextUtil.DARK_GREEN).decoration(TextDecoration.ITALIC, false));
            } else {
                item = c.getResult();
                meta = item.getItemMeta();
                meta.displayName(Component.text(c.getDisplayName()).color(TextUtil.RED).decoration(TextDecoration.ITALIC, false));
                meta.lore(addPurchaseLore(c, meta.lore()));
                item.setAmount(1);
            }
            item.setItemMeta(meta); //Sets the Meta to Button Meta
            inventory.setItem(index, item);
            if (i == 0) {
                i++;
                index++;
            } else {
                i=0;
                index+=8;
            }
        }
    }

    default List<Component> addPurchaseLore(Crafts craft, List<Component> lore) {
        List<Component> purchaseLore;
        if (lore != null) {
            purchaseLore = new ArrayList<>(lore);
        } else {
            purchaseLore = new ArrayList<>();
        }

        Component costText = TextUtil.makeText("[" + craft.getCraftCost() + " Souls]", TextUtil.GOLD);
        purchaseLore.add(costText);
        return purchaseLore;
    }

    default ItemStack unlockedItem() {
        ItemStack unlocked = new ItemStack(Material.GREEN_STAINED_GLASS_PANE);
        ItemMeta meta = unlocked.getItemMeta();
        meta.displayName(TextUtil.makeText("UNLOCKED", TextUtil.DARK_GREEN));
        meta.setCustomModelData(999999);
        unlocked.setItemMeta(meta);
        return unlocked;
    }

    default ItemStack lockedItem() {
        ItemStack item = new ItemStack(Material.RED_STAINED_GLASS_PANE);
        ItemMeta meta = item.getItemMeta();
        meta.displayName(TextUtil.makeText("LOCKED", TextUtil.RED));
        meta.setCustomModelData(999999);
        item.setItemMeta(meta);
        return item;
    }

    default ItemStack explainerItem() {
        ItemStack item = new ItemStack(Material.EMERALD);
        ItemMeta meta = item.getItemMeta();
        meta.displayName(TextUtil.makeText("INFORMATION", TextUtil.YELLOW));
        meta.setCustomModelData(999999);

        List<Component> lore = new ArrayList<>();
        lore.add(TextUtil.makeText("", TextUtil.YELLOW));
        lore.add(TextUtil.makeText("    [Left Click]", TextUtil.GOLD));
        lore.add(TextUtil.makeText(" To buy a Unlock", TextUtil.DARK_PURPLE));
        lore.add(TextUtil.makeText("    [Right Click]", TextUtil.GOLD));
        lore.add(TextUtil.makeText("To view the recipe", TextUtil.DARK_PURPLE));
        meta.lore(lore);
        item.setItemMeta(meta);
        return item;
    }

    default void determineRecipeShape(InventoryClickEvent event, Player p, ItemStack item, AscensionBingo plugin) {
        String displayName = ((TextComponent) Objects.requireNonNull(item.getItemMeta().displayName())).content();
        Pair<Crafts, Integer> unlock = plugin.getUnlocksData().findUnlock(displayName);
        if (unlock == null) return;
        if (event.getClick().isLeftClick() && !item.equals(unlockedItem())) {
            interactWithGUIButtonsUnlock(unlock, p);
        } else if (event.getClick().isRightClick()) {
            interactWithGUIButtonsViewRecipe(unlock, p);
        }
    }

    void interactWithGUIButtonsViewRecipe(Pair<Crafts, Integer> unlock, Player p);

    void interactWithGUIButtonsUnlock(Pair<Crafts, Integer> unlock, Player p);

    

}
