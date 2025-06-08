package me.depickcator.ascension.MainMenuUI.Unlocks;

import me.depickcator.ascension.Utility.TextUtil;
import me.depickcator.ascension.Interfaces.AscensionMenuGUI;
import me.depickcator.ascension.Items.Craftable.Craft;
import me.depickcator.ascension.Items.UnlocksData;
import me.depickcator.ascension.Player.Data.PlayerData;
import me.depickcator.ascension.Player.Data.PlayerUnlocks;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;
import net.kyori.adventure.text.format.TextDecoration;
import org.apache.commons.lang3.tuple.Pair;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


public class UnlocksGUI extends AscensionMenuGUI {
    private final Integer[] barrierNums = {47, 46, 37, 28, 19, 10, 11, 12, 13, 14, 15, 16, 25, 34, 43, 52, 51};
    private final char pageNumber;
    private ArrayList<ItemStack> pages;

    /* pageNumber is 1 indexed */
    public UnlocksGUI(PlayerData playerData, char pageNumber) {
        super(playerData, (char) 6, TextUtil.makeText("Unlocks: Tier " + TextUtil.toRomanNumeral(pageNumber), TextUtil.AQUA), true);
        this.pageNumber = pageNumber;
        setPage(pageNumber);
        setPageTabs();
        inventory.setItem(48, goBackItem());
        inventory.setItem(50, explainerItem());
        if (pageNumber != 5) inventory.setItem(53, nextPageItem());
        if (pageNumber != 1) {
            inventory.setItem(45, previousPageItem());
        } else {
            inventory.setItem(45, initInfoItem());
        }

        playerHeadButton(49);
    }

    private ItemStack initInfoItem() {
        List<Component> lore = new ArrayList<>(List.of(
                TextUtil.makeText("Gather Souls to be able to purchase Unlocks.", TextUtil.DARK_PURPLE),
                TextUtil.makeText("Each Unlock has a max use count and a associated Tier.", TextUtil.DARK_PURPLE),
                TextUtil.makeText("In order to unlock higher tiered Unlocks you must", TextUtil.DARK_PURPLE),
                TextUtil.makeText("purchase more unlocks from the tier below to fill up", TextUtil.DARK_PURPLE),
                TextUtil.makeText("the red surrounding till its green", TextUtil.DARK_PURPLE)
        ));
        Component title = TextUtil.makeText("Information", TextUtil.RED);
        return initExplainerItem(Material.REDSTONE_TORCH, lore, title);
    }

    private void setPageTabs() {
        pages = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            ItemStack item = i + 1 == pageNumber ? lockableItem(Material.LIME_STAINED_GLASS_PANE) : lockableItem(Material.BLUE_STAINED_GLASS_PANE);
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
        List<Craft> unlocks = unlocksData.getUnlocksTier(pageNumber);
//        boolean canUnlock = playerUnlocks.canUnlockTier(pageNumber);
        double percentage = playerUnlocks.unlockTierPercentage(pageNumber + 1);
        setUnlocks(unlocks);
        setBarriers(percentage);
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

        purchaseLore.add(TextUtil.makeText("[" + craft.getMaxCrafts() + " Uses]", TextUtil.GREEN));

        if (!playerUnlocks.hasUnlock(craft.getKey())) {
            Component costText = TextUtil.makeText("[" + craft.getCraftCost() + " Souls]", TextUtil.GOLD);
            purchaseLore.add(costText);
        } else {
            Component costText = TextUtil.makeText("[Unlocked]", TextUtil.GREEN);
            purchaseLore.add(costText);
        }

        return purchaseLore;
    }

    private void setBarriers(double percentage) {
        ItemStack unlockedItem = lockableItem(Material.GREEN_STAINED_GLASS_PANE);
        ItemStack lockedItem = lockableItem(Material.RED_STAINED_GLASS_PANE);
        int unlockedPanels;
        if (percentage != -1) {
            unlockedPanels = (int) (barrierNums.length * percentage);
        } else {
            unlockedPanels = barrierNums.length;
            unlockedItem = lockableItem(Material.BLACK_STAINED_GLASS_PANE);
        }
//        TextUtil.debugText("Unlocked Panels = " + barrierNums.length + " * " + percentage + "= " + unlockedPanels);
        for (int i = 0; i < barrierNums.length; i++) {
            ItemStack item = i < unlockedPanels ? unlockedItem : lockedItem;
            inventory.setItem(barrierNums[i], item);
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
    public void interactWithGUIButtons(InventoryClickEvent event) {
        ItemStack item = event.getCurrentItem();
        if (item == null || !item.getItemMeta().hasDisplayName()) {
            return;
        }
        if (pages.contains(item)) {
            int index = pages.indexOf(item);
            new UnlocksGUI(playerData, (char) (index + 1));
            return;
        }

        if (item.equals(goBackItem())) {
            player.performCommand("open-main-menu");
            return;
        } else if (item.equals(nextPageItem())) {
            new UnlocksGUI(playerData, (char)(pageNumber + 1));
        } else if (item.equals(previousPageItem())) {
            new UnlocksGUI(playerData, (char)(pageNumber - 1));
        }
        determineRecipeShape(event, player, item);
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
            new UnlocksGUI(playerData, pageNumber);
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
