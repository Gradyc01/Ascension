package me.depickcator.ascension.Items.Uncraftable.XPTome;



import me.depickcator.ascension.General.SoundUtil;
import me.depickcator.ascension.General.TextUtil;
import me.depickcator.ascension.Interfaces.AscensionGUI;
import me.depickcator.ascension.Player.PlayerData;
import net.kyori.adventure.text.Component;
import org.bukkit.Material;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class XPTomeGUI extends AscensionGUI {
    public final static String menuName = "XPTomeGUI";

    private final int GUISize = 3 * 9;
    // private Ascension plugin;
    public XPTomeGUI(PlayerData playerData) {
        super(playerData, (char) 3, Component.text("     Pick a type of Experience").color(TextUtil.AQUA), true);
        makeExpButton(Material.IRON_PICKAXE, "Mining", 11);
        makeExpButton(Material.IRON_SWORD, "Combat", 13);
        makeExpButton(Material.IRON_AXE, "Foraging", 15);

    }

    private void makeExpButton(Material material, String name, int index) {
        ItemStack button = new ItemStack(material);
        ItemMeta buttonMeta = button.getItemMeta();
        buttonMeta.displayName(TextUtil.makeText(name, TextUtil.DARK_GREEN));
        buttonMeta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
        buttonMeta.setCustomModelData(0x040000);
        button.setItemMeta(buttonMeta);
        inventory.setItem(index, button);
    }

//    private void combatButton() {
//        ItemStack button = new ItemStack(Material.IRON_SWORD);
//        ItemMeta buttonMeta = button.getItemMeta();
//        buttonMeta.displayName(TextUtil.makeText("Combat", TextUtil.DARK_GREEN));
//        buttonMeta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
//        buttonMeta.setCustomModelData(0x040000);
//        setGUIItems(inventory, button, buttonMeta,13);
//    }
//
//    private void foragingButton() {
//        ItemStack button = new ItemStack(Material.IRON_AXE);
//        ItemMeta buttonMeta = button.getItemMeta();
//        buttonMeta.displayName(TextUtil.makeText("Foraging", TextUtil.DARK_GREEN));
//        buttonMeta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
//        buttonMeta.setCustomModelData(0x050000);
//        setGUIItems(inventory, button, buttonMeta,15);
//    }
//
//    private void miningButton() {
//        ItemStack button = new ItemStack(Material.IRON_PICKAXE);
//        ItemMeta buttonMeta = button.getItemMeta();
//        buttonMeta.displayName(TextUtil.makeText("Mining", TextUtil.DARK_GREEN));
//        buttonMeta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
//        buttonMeta.setCustomModelData(0x060000);
//        setGUIItems(inventory, button, buttonMeta,11);
//    }


    @Override
    public void interactWithGUIButtons(InventoryClickEvent event) {

        if (event.isShiftClick() || playerData == null) {
            return;
        }
        if (!player.getInventory().getItemInMainHand().equals(XPTome.getInstance().getItem())) {
            event.setCancelled(true);
            player.closeInventory();
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

