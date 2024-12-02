package me.depickcator.ascension.Items.Uncraftable.XPTome;



import me.depickcator.ascension.Ascension;
import me.depickcator.ascension.General.SoundUtil;
import me.depickcator.ascension.General.TextUtil;
import me.depickcator.ascension.Interfaces.AscensionGUI;
import me.depickcator.ascension.Player.PlayerData;
import me.depickcator.ascension.Player.PlayerUtil;
import net.kyori.adventure.text.Component;
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

public class XPTomeGUI implements AscensionGUI {
    public final static String menuName = "XPTomeGUI";

    private final int GUISize = 3 * 9;
    private final Inventory inventory;
    private Player player;
    // private Ascension plugin;
    public XPTomeGUI(Player p) {
        // this.plugin = plugin;
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
            Ascension.guiMap.put(p.getUniqueId(), pair2);
        }
    }

    private void combatButton() {
        ItemStack button = new ItemStack(Material.IRON_SWORD);
        ItemMeta buttonMeta = button.getItemMeta();
        buttonMeta.displayName(TextUtil.makeText("Combat", TextUtil.DARK_GREEN));
        buttonMeta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
        buttonMeta.setCustomModelData(0x040000);
        setGUIItems(inventory, button, buttonMeta,13);
    }

    private void foragingButton() {
        ItemStack button = new ItemStack(Material.IRON_AXE);
        ItemMeta buttonMeta = button.getItemMeta();
        buttonMeta.displayName(TextUtil.makeText("Foraging", TextUtil.DARK_GREEN));
        buttonMeta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
        buttonMeta.setCustomModelData(0x050000);
        setGUIItems(inventory, button, buttonMeta,15);
    }

    private void miningButton() {
        ItemStack button = new ItemStack(Material.IRON_PICKAXE);
        ItemMeta buttonMeta = button.getItemMeta();
        buttonMeta.displayName(TextUtil.makeText("Mining", TextUtil.DARK_GREEN));
        buttonMeta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
        buttonMeta.setCustomModelData(0x060000);
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
        if (!p.getInventory().getItemInMainHand().equals(XPTome.getInstance().getItem())) {
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

