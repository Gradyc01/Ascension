package org.bingo4.Items.Uncraftable.XPTome;


import org.bingo4.Interfaces.AscensionMenuGUI;
import org.bingo4.Player.Data.PlayerData;
import org.bingo4.Utility.SoundUtil;
import org.bingo4.Utility.TextUtil;
import net.kyori.adventure.text.Component;
import org.bukkit.Material;
import org.bukkit.event.inventory.InventoryClickEvent;

import java.util.ArrayList;
import java.util.List;

public class XPTomeGUI extends AscensionMenuGUI {
    public final static String menuName = "XPTomeGUI";

    // private Bingo4 plugin;
    public XPTomeGUI(PlayerData playerData) {
        super(playerData, (char) 3, Component.text("     Pick a type of Experience").color(TextUtil.AQUA), true);
        makeExpButton(Material.IRON_PICKAXE, "Mining", 11);
        makeExpButton(Material.IRON_SWORD, "Combat", 13);
        makeExpButton(Material.IRON_AXE, "Foraging", 15);

    }

    private void makeExpButton(Material material, String name, int index) {
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

