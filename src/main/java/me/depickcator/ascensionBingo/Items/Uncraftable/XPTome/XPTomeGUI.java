package me.depickcator.ascensionBingo.Items.Uncraftable.XPTome;



import me.depickcator.ascensionBingo.AscensionBingo;
import me.depickcator.ascensionBingo.General.SoundUtil;
import me.depickcator.ascensionBingo.General.TextUtil;
import me.depickcator.ascensionBingo.Interfaces.AscensionGUI;
import me.depickcator.ascensionBingo.Items.Unlocks.Crafts;
import me.depickcator.ascensionBingo.LootTables.Blocks.ForageBlocks.ForageBlocks;
import me.depickcator.ascensionBingo.Player.PlayerData;
import me.depickcator.ascensionBingo.Player.PlayerUnlocks;
import me.depickcator.ascensionBingo.Player.PlayerUtil;
import me.depickcator.ascensionBingo.mainMenu.Unlocks.UnlocksGUI;
import me.depickcator.ascensionBingo.mainMenu.Unlocks.UnlocksGUI_2;
import me.depickcator.ascensionBingo.mainMenu.Unlocks.ViewRecipeGUI;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextColor;
import net.kyori.adventure.text.format.TextDecoration;
import org.apache.commons.lang3.tuple.MutablePair;
import org.apache.commons.lang3.tuple.Pair;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Objects;

public class XPTomeGUI implements AscensionGUI {
    public final static String menuName = "XPTomeGUI";

    private final int GUISize = 3 * 9;
    private final Inventory inventory;
    private Player player;
    private AscensionBingo plugin;
    public XPTomeGUI(AscensionBingo plugin, Player p) {
        this.plugin = plugin;
        player = p;
        inventory = Bukkit.createInventory(p, GUISize, Component.text("     Pick a type of Experience").color(TextUtil.AQUA));
        if (player != null) {
            p.openInventory(inventory);
        }
        setItemBackground(inventory,GUISize);
        combatButton();
        foragingButton();
        miningButton();
        if (player != null) {
            Pair<Inventory, AscensionGUI> pair2 = new MutablePair<>(inventory,this);
            AscensionBingo.guiMap.put(p.getUniqueId(), pair2);
        }
    }

    private void combatButton() {
        ItemStack button = new ItemStack(Material.IRON_SWORD);
        ItemMeta buttonMeta = button.getItemMeta();
        buttonMeta.displayName(TextUtil.makeText("Combat", TextUtil.DARK_GREEN));
        buttonMeta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
        buttonMeta.setCustomModelData(35);
        setGUIItems(inventory, button, buttonMeta,13);
    }

    private void foragingButton() {
        ItemStack button = new ItemStack(Material.IRON_AXE);
        ItemMeta buttonMeta = button.getItemMeta();
        buttonMeta.displayName(TextUtil.makeText("Foraging", TextUtil.DARK_GREEN));
        buttonMeta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
        buttonMeta.setCustomModelData(36);
        setGUIItems(inventory, button, buttonMeta,15);
    }

    private void miningButton() {
        ItemStack button = new ItemStack(Material.IRON_PICKAXE);
        ItemMeta buttonMeta = button.getItemMeta();
        buttonMeta.displayName(TextUtil.makeText("Mining", TextUtil.DARK_GREEN));
        buttonMeta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
        buttonMeta.setCustomModelData(37);
        setGUIItems(inventory, button, buttonMeta,11);
    }


    @Override
    public String getGUIName() {
        return menuName;
    }

    @Override
    public void interactWithGUIButtons(InventoryClickEvent event, Player p) {

        PlayerData playerData = PlayerUtil.getPlayerData(p);
        if (event.isShiftClick() || playerData == null) {
            return;
        }
        if (!p.getInventory().getItemInMainHand().equals(XPTome.result())) {
            event.setCancelled(true);
            p.closeInventory();
            return;
        }
        switch (event.getCurrentItem().getType()) {
            case IRON_AXE -> {
                playerData.getPlayerSkills().getForaging().addExp(70);
                successfulPurchase(event);
            }
            case IRON_PICKAXE -> {
                playerData.getPlayerSkills().getMining().addExp(70);
                successfulPurchase(event);
            }
            case IRON_SWORD -> {
                playerData.getPlayerSkills().getCombat().addExp(70);
                successfulPurchase(event);
            }
            default -> {
                event.setCancelled(true);
                return;
            }
        }
    }

    private void successfulPurchase(InventoryClickEvent event) {
        SoundUtil.playHighPitchPling(player);
        event.setCancelled(true);
        player.closeInventory();
        player.getInventory().getItemInMainHand().setAmount(0);
    }
}
