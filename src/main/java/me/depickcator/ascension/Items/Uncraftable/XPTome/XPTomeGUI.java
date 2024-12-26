package me.depickcator.ascension.Items.Uncraftable.XPTome;



import me.depickcator.ascension.Utility.SoundUtil;
import me.depickcator.ascension.Utility.TextUtil;
import me.depickcator.ascension.Interfaces.AscensionGUI;
import me.depickcator.ascension.Player.Data.PlayerData;
import net.kyori.adventure.text.Component;
import org.bukkit.Material;
import org.bukkit.event.inventory.InventoryClickEvent;

import java.util.ArrayList;
import java.util.List;

public class XPTomeGUI extends AscensionGUI {
    public final static String menuName = "XPTomeGUI";

    // private Ascension plugin;
    public XPTomeGUI(PlayerData playerData) {
        super(playerData, (char) 3, Component.text("     Pick a type of Experience").color(TextUtil.AQUA), true);
        makeExpButton(Material.IRON_PICKAXE, "Mining", 11);
        makeExpButton(Material.IRON_SWORD, "Combat", 13);
        makeExpButton(Material.IRON_AXE, "Foraging", 15);

    }

    private void makeExpButton(Material material, String name, int index) {
//        ItemStack item = new ItemStack(material);
//        ItemMeta meta = item.getItemMeta();
//        meta.displayName(TextUtil.makeText(name, TextUtil.DARK_GREEN));
//        meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
//        meta.setCustomModelData(0x040000);
//        item.setItemMeta(meta);
//        inventory.setItem(index, item);
        Component title = TextUtil.makeText(name, TextUtil.DARK_GREEN);
        List<Component> lore = new ArrayList<>(List.of(
                TextUtil.makeText(""),
                TextUtil.makeText("[Click to Claim]", TextUtil.GOLD)
        ));
        inventory.setItem(index, initExplainerItem(material, lore, title));
    }

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

