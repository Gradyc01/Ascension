package me.depickcator.ascension.MainMenu.Unlocks;
//package me.depickcator.ascension.MainMenu.Unlocks;
//
//import me.depickcator.ascension.Ascension;
//import me.depickcator.ascension.Utility.TextUtil;
//import me.depickcator.ascension.Items.Craftable.Craft;
//import me.depickcator.ascension.Player.Data.PlayerUnlocks;
//import net.kyori.adventure.text.Component;
//import net.kyori.adventure.text.TextComponent;
//import net.kyori.adventure.text.format.TextDecoration;
//import org.apache.commons.lang3.tuple.Pair;
//import org.bukkit.Material;
//import org.bukkit.entity.Player;
//import org.bukkit.event.inventory.InventoryClickEvent;
//import org.bukkit.inventory.Inventory;
//import org.bukkit.inventory.ItemStack;
//import org.bukkit.inventory.meta.ItemMeta;
//
//import java.util.ArrayList;
//import java.util.List;
//import java.util.Objects;
//
//public interface UnlocksGUI {
//    default void placeBarrierInGui(int startingIndex, ItemStack item, Inventory inventory) {
//        for (int i = startingIndex; i < 45; i+=9) {
//            inventory.setItem(i, item);
//        }
//    }
//
//    default void placeUnlockInGUI(ArrayList<Craft> arr, int startingIndex, PlayerUnlocks playerUnlocks, Inventory inventory) {
//        int i = 0;
//        int index = startingIndex;
//
//        for (Craft c : arr) {
//            ItemStack item;
//            ItemMeta meta;
//            item = c.getResult().clone();
//            meta = item.getItemMeta();
//            item.setAmount(1);
//            meta.lore(addPurchaseLore(c, meta.lore(), playerUnlocks));
//            if (playerUnlocks.hasUnlock(c.getKey())) {
//                meta.displayName(Component.text(c.getDisplayName()).color(TextUtil.DARK_GREEN).decoration(TextDecoration.ITALIC, false));
//                meta.setEnchantmentGlintOverride(true);
//            } else {
//                meta.displayName(Component.text(c.getDisplayName()).color(TextUtil.RED).decoration(TextDecoration.ITALIC, false));
//                meta.setEnchantmentGlintOverride(false);
//            }
//            item.setItemMeta(meta); //Sets the Meta to Button Meta
//            inventory.setItem(index, item);
//            if (i == 0) {
//                i++;
//                index++;
//            } else {
//                i=0;
//                index+=8;
//            }
//        }
//    }
//
//    default List<Component> addPurchaseLore(Craft craft, List<Component> lore, PlayerUnlocks playerUnlocks) {
//        List<Component> purchaseLore;
//        if (lore != null) {
//            purchaseLore = new ArrayList<>(lore);
//        } else {
//            purchaseLore = new ArrayList<>();
//        }
//
//        if (!playerUnlocks.hasUnlock(craft.getKey())) {
//            Component costText = TextUtil.makeText("[" + craft.getCraftCost() + " Souls]", TextUtil.GOLD);
//            purchaseLore.add(costText);
//        } else {
//            Component costText = TextUtil.makeText("[Unlocked]", TextUtil.GREEN);
//            purchaseLore.add(costText);
//        }
//
//        return purchaseLore;
//    }
//
//    default ItemStack unlockedItem() {
//        ItemStack unlocked = new ItemStack(Material.GREEN_STAINED_GLASS_PANE);
//        ItemMeta meta = unlocked.getItemMeta();
//        meta.displayName(TextUtil.makeText("UNLOCKED", TextUtil.DARK_GREEN));
//        meta.setCustomModelData(999999);
//        unlocked.setItemMeta(meta);
//        return unlocked;
//    }
//
//    default ItemStack lockedItem() {
//        ItemStack item = new ItemStack(Material.RED_STAINED_GLASS_PANE);
//        ItemMeta meta = item.getItemMeta();
//        meta.displayName(TextUtil.makeText("LOCKED", TextUtil.RED));
//        meta.setCustomModelData(999999);
//        item.setItemMeta(meta);
//        return item;
//    }
//
//    default ItemStack explainerItem() {
//        ItemStack item = new ItemStack(Material.EMERALD);
//        ItemMeta meta = item.getItemMeta();
//        meta.displayName(TextUtil.makeText("INFORMATION", TextUtil.YELLOW));
//        meta.setCustomModelData(999999);
//
//        List<Component> lore = new ArrayList<>();
//        lore.add(TextUtil.makeText("", TextUtil.YELLOW));
//        lore.add(TextUtil.makeText("    [Left Click]", TextUtil.GOLD));
//        lore.add(TextUtil.makeText(" To buy a Unlock", TextUtil.DARK_PURPLE));
//        lore.add(TextUtil.makeText("    [Right Click]", TextUtil.GOLD));
//        lore.add(TextUtil.makeText("To view the recipe", TextUtil.DARK_PURPLE));
//        meta.lore(lore);
//        item.setItemMeta(meta);
//        return item;
//    }
//
//    default void determineRecipeShape(InventoryClickEvent event, Player p, ItemStack item, Ascension plugin) {
//        String displayName = ((TextComponent) Objects.requireNonNull(item.getItemMeta().displayName())).content();
//        Pair<Craft, Integer> unlock = plugin.getUnlocksData().findUnlock(displayName);
//        if (unlock == null) return;
//        if (event.getClick().isLeftClick()/* && !item.equals(unlockedItem())*/) {
//            interactWithGUIButtonsUnlock(unlock, p);
//        } else if (event.getClick().isRightClick()) {
//            interactWithGUIButtonsViewRecipe(unlock, p);
//        }
//    }
//
//    void interactWithGUIButtonsViewRecipe(Pair<Craft, Integer> unlock, Player p);
//
//    void interactWithGUIButtonsUnlock(Pair<Craft, Integer> unlock, Player p);
//
//
//
//}
