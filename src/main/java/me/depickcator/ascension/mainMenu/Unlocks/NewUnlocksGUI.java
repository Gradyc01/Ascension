package me.depickcator.ascension.MainMenu.Unlocks;

import me.depickcator.ascension.Ascension;
import me.depickcator.ascension.General.TextUtil;
import me.depickcator.ascension.Interfaces.AscensionGUI;
import me.depickcator.ascension.Items.Craftable.Craft;
import me.depickcator.ascension.Items.UnlocksData;
import me.depickcator.ascension.Player.PlayerData;
import me.depickcator.ascension.Player.PlayerUnlocks;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;
import net.kyori.adventure.text.format.TextDecoration;
import org.apache.commons.lang3.tuple.MutablePair;
import org.apache.commons.lang3.tuple.Pair;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class NewUnlocksGUI implements AscensionGUI {
    public final static String menuName = "unlocks";

    private final int GUISize = 6 * 9;
    private final Inventory inventory;
    private final Player player;
    private final Ascension plugin;
    private final PlayerData playerData;
    private final char pageNumber;
    private ArrayList<ItemStack> pages;
    public NewUnlocksGUI(PlayerData playerData, char pageNumber) {
        this.plugin = Ascension.getInstance();
        this.playerData = playerData;
        this.pageNumber = pageNumber;
        player = playerData.getPlayer();
        inventory = Bukkit.createInventory(player, GUISize, TextUtil.makeText("Unlocks: Tier " + TextUtil.toRomanNumeral(pageNumber), TextUtil.AQUA));
        player.openInventory(inventory);
        setItemBackground(inventory, GUISize);
        setPage(pageNumber);
        setPageTabs();
        inventory.setItem(48, goBackItem());
        inventory.setItem(50, explainerItem());
        playerHeadButton(inventory, 49, player);
        Pair<Inventory, AscensionGUI> pair2 = new MutablePair<>(inventory,this);
        Ascension.guiMap.put(player.getUniqueId(), pair2);

    }

    private void setPageTabs() {
        pages = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            ItemStack item = i + 1 == pageNumber ? lockableItem(Material.GREEN_STAINED_GLASS_PANE) : lockableItem(Material.BLUE_STAINED_GLASS_PANE);
            ItemMeta meta = item.getItemMeta();
            meta.displayName(TextUtil.makeText("Tier " + TextUtil.toRomanNumeral((i + 1)), TextUtil.AQUA));
            item.setItemMeta(meta);
            inventory.setItem(i + 2, item);
            pages.add(item);
        }
    }

    private void setPage(char pageNumber) {
        UnlocksData unlocksData = plugin.getUnlocksData();
        PlayerUnlocks playerUnlocks = playerData.getPlayerUnlocks();
        List<Craft> unlocks;
        boolean canUnlock;
        switch (pageNumber) {
            case 1 -> {
                unlocks = unlocksData.getTier1Unlocks();
                canUnlock = true;
            }
            case 2 -> {
                unlocks = unlocksData.getTier2Unlocks();
                canUnlock = playerUnlocks.canUnlockTier2();
            }
            case 3 -> {
                unlocks = unlocksData.getTier3Unlocks();
                canUnlock = playerUnlocks.canUnlockTier3();
            }
            case 4 -> {
                unlocks = unlocksData.getTier4Unlocks();
                canUnlock = playerUnlocks.canUnlockTier4();
            }
            case 5 -> {
                unlocks = unlocksData.getTier5Unlocks();
                canUnlock = playerUnlocks.canUnlockTier5();
            }
            default -> {
                return;
            }
        }
        setUnlocks(unlocks);
        setBarriers(canUnlock);
    }

    private void setUnlocks(List<Craft> unlocks) {
        int index = 20;
        PlayerUnlocks playerUnlocks = playerData.getPlayerUnlocks();
        for (Craft c : unlocks) {
            ItemStack item = c.getResult().clone();
            ItemMeta meta = item.getItemMeta();
            item.setAmount(1);
            meta.lore(addPurchaseLore(c, meta.lore(), playerUnlocks));
            if (playerUnlocks.hasUnlock(c.getKey())) {
                meta.displayName(Component.text(c.getDisplayName()).color(TextUtil.DARK_GREEN).decoration(TextDecoration.ITALIC, false));
                meta.setEnchantmentGlintOverride(true);
            } else {
                meta.displayName(Component.text(c.getDisplayName()).color(TextUtil.RED).decoration(TextDecoration.ITALIC, false));
                meta.setEnchantmentGlintOverride(false);
            }
            item.setItemMeta(meta); //Sets the Meta to Button Meta
            inventory.setItem(index, item);
            index = (index % 9 == 6) ? index + 5 : index + 1;
        }
    }

    private List<Component> addPurchaseLore(Craft craft, List<Component> lore, PlayerUnlocks playerUnlocks) {
        List<Component> purchaseLore;
        if (lore != null) {
            purchaseLore = new ArrayList<>(lore);
        } else {
            purchaseLore = new ArrayList<>();
        }

        if (!playerUnlocks.hasUnlock(craft.getKey())) {
            Component costText = TextUtil.makeText("[" + craft.getCraftCost() + " Souls]", TextUtil.GOLD);
            purchaseLore.add(costText);
        } else {
            Component costText = TextUtil.makeText("[Unlocked]", TextUtil.GREEN);
            purchaseLore.add(costText);
        }

        return purchaseLore;
    }

    private void setBarriers(boolean isUnlocked) {
        int flip = 0;
        ItemStack item = isUnlocked ? lockableItem(Material.GREEN_STAINED_GLASS_PANE) : lockableItem(Material.RED_STAINED_GLASS_PANE);
        List<Integer> starts = new ArrayList<>(List.of(11, 47, 10, 16));
        for (int start : starts) {
            int index = start;
            for (int i = 0; i < 5; i++) {
                inventory.setItem(index, item);
                index = flip > 1 ? index + 9: index + 1;
            }
            flip++;
        }

    }

    private ItemStack lockableItem(Material material) {
        ItemStack unlocked = new ItemStack(material);
        ItemMeta meta = unlocked.getItemMeta();
        meta.displayName(TextUtil.makeText("", TextUtil.DARK_GREEN));
        meta.setCustomModelData(999999);
        unlocked.setItemMeta(meta);
        return unlocked;
    }


    @Override
    public String getGUIName() {
        return menuName;
    }

    @Override
    public void interactWithGUIButtons(InventoryClickEvent event, Player p) {
        ItemStack item = event.getCurrentItem();
        if (item == null || !item.getItemMeta().hasDisplayName()) {
            return;
        }
        if (pages.contains(item)) {
            int index = pages.indexOf(item);
            new NewUnlocksGUI(playerData, (char) (index + 1));
            return;
        }
        if (item.equals(goBackItem())) {
            p.performCommand("open-main-menu");
            return;
        }
        determineRecipeShape(event, p, item);
    }

    private void determineRecipeShape(InventoryClickEvent event, Player p, ItemStack item) {
        String displayName = ((TextComponent) Objects.requireNonNull(item.getItemMeta().displayName())).content();
        Pair<Craft, Integer> unlock = plugin.getUnlocksData().findUnlock(displayName);
        if (unlock == null) return;
        if (event.getClick().isLeftClick()/* && !item.equals(unlockedItem())*/) {
            interactWithGUIButtonsUnlock(unlock);
        } else if (event.getClick().isRightClick()) {
            interactWithGUIButtonsViewRecipe(unlock);
        }
    }

    public void interactWithGUIButtonsViewRecipe(Pair<Craft, Integer> unlock) {
        new ViewRecipeGUI(playerData, unlock.getLeft(), pageNumber);
    }

    public void interactWithGUIButtonsUnlock(Pair<Craft, Integer> unlock) {
        PlayerUnlocks playerUnlocks = playerData.getPlayerUnlocks();
        if (playerUnlocks.unlockUnlock(unlock.getLeft(), unlock.getRight())) {
            new NewUnlocksGUI(playerData, pageNumber);
        } else {
            TextUtil.errorMessage(player, "Unable to unlock this craft.");
        };
    }

    private ItemStack explainerItem() {
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





}
