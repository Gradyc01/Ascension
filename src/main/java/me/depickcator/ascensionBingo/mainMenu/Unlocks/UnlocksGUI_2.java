package me.depickcator.ascensionBingo.mainMenu.Unlocks;

import me.depickcator.ascensionBingo.AscensionBingo;
import me.depickcator.ascensionBingo.General.SoundUtil;
import me.depickcator.ascensionBingo.General.TextUtil;
import me.depickcator.ascensionBingo.Interfaces.AscensionGUI;
import me.depickcator.ascensionBingo.Items.Craftable.Crafts;
import me.depickcator.ascensionBingo.Player.PlayerUnlocks;
import me.depickcator.ascensionBingo.Player.PlayerUtil;
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

public class UnlocksGUI_2 implements AscensionGUI, UnlocksGUI {
    public final static String menuName = "UNLOCKS_Page_2";

    private final int GUISize = 6 * 9;
    private final Inventory inventory;
    private Player player;
    private AscensionBingo plugin;
    public UnlocksGUI_2(AscensionBingo plugin, Player p) {
        this.plugin = plugin;
        player = p;
        inventory = Bukkit.createInventory(p, GUISize, Component.text("Unlocks").color(TextUtil.AQUA));
        if (player != null) {
            p.openInventory(inventory);
        }
        setItemBackground(inventory,GUISize);
        setUnlocks();
        setBuyBarriers();
        setGUIItems(inventory, previousPage(), 45);
        setGUIItems(inventory, explainerItem(), 50);
        inventory.setItem(48, goBackItem());
        playerHeadButton(inventory, 49, p);
        if (player != null) {
            Pair<Inventory, AscensionGUI> pair2 = new MutablePair<>(inventory,this);
            AscensionBingo.guiMap.put(p.getUniqueId(), pair2);
        }
    }

    private void setBuyBarriers() {
        PlayerUnlocks playerUnlocks = Objects.requireNonNull(PlayerUtil.getPlayerData(player)).getPlayerUnlocks();
        if (playerUnlocks.canUnlockTier4()) {
            placeBarrierInGui(1, unlockedItem(), inventory);
        } else {
            placeBarrierInGui(1, lockedItem(), inventory);
        }
        if (playerUnlocks.canUnlockTier5()) {
            placeBarrierInGui(4, unlockedItem(), inventory);
        } else {
            placeBarrierInGui(4, lockedItem(), inventory);
        }
    }

    private void setUnlocks() {
        PlayerUnlocks playerUnlocks = Objects.requireNonNull(PlayerUtil.getPlayerData(player)).getPlayerUnlocks();
        placeUnlockInGUI(plugin.getUnlocksData().getTier4Unlocks(), 2, playerUnlocks, inventory);
        placeUnlockInGUI(plugin.getUnlocksData().getTier5Unlocks(), 5, playerUnlocks, inventory);
    }

    @Override
    public String getGUIName() {
        return menuName;
    }

    @Override
    public void interactWithGUIButtons(InventoryClickEvent event, Player p) {
        ItemStack item = event.getCurrentItem();
        if (item == null || !item.getItemMeta().hasDisplayName() || item.equals(lockedItem())) {
            return;
        }
        if (item.equals(previousPage())) {
            new UnlocksGUI_1(plugin, player);
            return;
        }
        if (item.equals(goBackItem())) {
            p.performCommand("open-main-menu");
            return;
        }
        determineRecipeShape(event, p, item, plugin);
    }

    @Override
    public void interactWithGUIButtonsViewRecipe(Pair<Crafts, Integer> unlock, Player p) {
        new ViewRecipeGUI(p, plugin, unlock.getLeft(), this);
    }

    @Override
    public void interactWithGUIButtonsUnlock(Pair<Crafts, Integer> unlock, Player p) {
        PlayerUnlocks playerUnlocks = Objects.requireNonNull(PlayerUtil.getPlayerData(player)).getPlayerUnlocks();
        if (playerUnlocks.unlockUnlock(unlock.getLeft(), unlock.getRight())) {
            new UnlocksGUI_2(plugin, p);
        } else {
            SoundUtil.playErrorSoundEffect(p);
            p.sendMessage(TextUtil.makeText("Unable to unlock craft", TextUtil.RED));
        };
    }

    private ItemStack previousPage() {
        ItemStack item = new ItemStack(Material.ARROW);
        ItemMeta meta = item.getItemMeta();
        meta.displayName(TextUtil.makeText("Previous Page", TextUtil.DARK_GRAY));
        meta.setCustomModelData(999999);
        item.setItemMeta(meta);
        return item;
    }




}
