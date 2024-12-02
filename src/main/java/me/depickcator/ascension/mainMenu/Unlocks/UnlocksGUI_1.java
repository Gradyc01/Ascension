package me.depickcator.ascension.mainMenu.Unlocks;

import me.depickcator.ascension.Ascension;
import me.depickcator.ascension.General.SoundUtil;
import me.depickcator.ascension.General.TextUtil;
import me.depickcator.ascension.Interfaces.AscensionGUI;
import me.depickcator.ascension.Items.Craftable.Craft;
import me.depickcator.ascension.Player.PlayerUnlocks;
import me.depickcator.ascension.Player.PlayerUtil;
import net.kyori.adventure.text.Component;
import org.apache.commons.lang3.tuple.MutablePair;
import org.apache.commons.lang3.tuple.Pair;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Objects;

public class UnlocksGUI_1 implements AscensionGUI, UnlocksGUI {
    public final static String menuName = "UNLOCKS_Page_1";

    private final int GUISize = 6 * 9;
    private final Inventory inventory;
    private Player player;
    private Ascension plugin;
    public UnlocksGUI_1(Ascension plugin, Player p) {
        this.plugin = plugin;
        player = p;
        inventory = Bukkit.createInventory(p, GUISize, Component.text("Unlocks").color(TextUtil.AQUA));
        if (player != null) {
            p.openInventory(inventory);
        }
        setItemBackground(inventory,GUISize);
        setUnlocks();
        setBuyBarriers();
        setGUIItems(inventory, nextPage(), 53);
        setGUIItems(inventory, explainerItem(), 50);
        inventory.setItem(48, goBackItem());
        playerHeadButton(inventory, 49, p);
        if (player != null) {
            Pair<Inventory, AscensionGUI> pair2 = new MutablePair<>(inventory,this);
            Ascension.guiMap.put(p.getUniqueId(), pair2);
        }
    }

    private void setBuyBarriers() {
        PlayerUnlocks playerUnlocks = Objects.requireNonNull(PlayerUtil.getPlayerData(player)).getPlayerUnlocks();
        if (playerUnlocks.canUnlockTier2()) {
            placeBarrierInGui(2, unlockedItem(), inventory);
        } else {
            placeBarrierInGui(2, lockedItem(), inventory);
        }
        if (playerUnlocks.canUnlockTier3()) {
            placeBarrierInGui(5, unlockedItem(), inventory);
        } else {
            placeBarrierInGui(5, lockedItem(), inventory);
        }
        if (playerUnlocks.canUnlockTier4()) {
            placeBarrierInGui(8, unlockedItem(), inventory);
        } else {
            placeBarrierInGui(8, lockedItem(), inventory);
        }
    }

    private void setUnlocks() {
        PlayerUnlocks playerUnlocks = Objects.requireNonNull(PlayerUtil.getPlayerData(player)).getPlayerUnlocks();
        placeUnlockInGUI(plugin.getUnlocksData().getTier1Unlocks(), 0, playerUnlocks, inventory);
        placeUnlockInGUI(plugin.getUnlocksData().getTier2Unlocks(), 3, playerUnlocks, inventory);
        placeUnlockInGUI(plugin.getUnlocksData().getTier3Unlocks(), 6, playerUnlocks, inventory);
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
        if (item.equals(lockedItem())) {
//            plugin.getServer().broadcast(Component.text("You are already unlocked."));
            return;
        }
        if (item.equals(nextPage())) {
            new UnlocksGUI_2(plugin, player);
            return;
        }
        if (item.equals(goBackItem())) {
            p.performCommand("open-main-menu");
            return;
        }
        determineRecipeShape(event, p, item, plugin);

    }

    @Override
    public void interactWithGUIButtonsViewRecipe(Pair<Craft, Integer> unlock, Player p) {
        new ViewRecipeGUI(p, plugin, unlock.getLeft(), this);
    }

    @Override
    public void interactWithGUIButtonsUnlock(Pair<Craft, Integer> unlock, Player p) {
        PlayerUnlocks playerUnlocks = Objects.requireNonNull(PlayerUtil.getPlayerData(player)).getPlayerUnlocks();
        if (playerUnlocks.unlockUnlock(unlock.getLeft(), unlock.getRight())) {
            new UnlocksGUI_1(plugin, p);
        } else {
            SoundUtil.playErrorSoundEffect(p);
            p.sendMessage(TextUtil.makeText("Unable to unlock craft", TextUtil.RED));
        };
    }

    private ItemStack nextPage() {
        ItemStack item = new ItemStack(Material.ARROW);
        ItemMeta meta = item.getItemMeta();
        meta.displayName(TextUtil.makeText("Next Page", TextUtil.DARK_GRAY));
        meta.setCustomModelData(999999);
        item.setItemMeta(meta);
        return item;
    }



}
