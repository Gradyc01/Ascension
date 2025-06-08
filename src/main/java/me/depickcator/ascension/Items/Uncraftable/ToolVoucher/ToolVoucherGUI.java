package me.depickcator.ascension.Items.Uncraftable.ToolVoucher;


import me.depickcator.ascension.Interfaces.AscensionMenuGUI;
import me.depickcator.ascension.Player.Data.PlayerData;
import me.depickcator.ascension.Player.Data.PlayerUnlocks;
import me.depickcator.ascension.Player.Data.PlayerUtil;
import me.depickcator.ascension.Utility.SoundUtil;
import me.depickcator.ascension.Utility.TextUtil;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ToolVoucherGUI extends AscensionMenuGUI {
    private HashMap<ItemStack, VouchableTool> items;

    // private Ascension plugin;
    public ToolVoucherGUI(PlayerData playerData, List<VouchableTool> tools) {
        super(playerData, (char) 5, TextUtil.makeText("Redeem a Tool", TextUtil.AQUA), true);
        initTools(tools);
    }

    private void initTools(List<VouchableTool> tools) {
        this.items = new HashMap<>();
        for (VouchableTool tool : tools) {
            initTool(tool);
        }
    }

    private void initTool(VouchableTool tool) {
        ItemStack displayItem = tool.item().clone();
        ItemMeta meta = displayItem.getItemMeta();
        meta.lore(new ArrayList<>(List.of(TextUtil.makeText("[" + tool.cost() + " Souls]", TextUtil.GOLD))));
        displayItem.setItemMeta(meta);
        this.items.put(displayItem, tool);
        inventory.setItem(tool.index(), displayItem);
    }


    @Override
    public void interactWithGUIButtons(InventoryClickEvent event) {
        event.setCancelled(true);
        if (event.isShiftClick() || playerData == null) {
            return;
        }
        if (!player.getInventory().getItemInMainHand().equals(ToolVoucher.getInstance().getItem())) {
            player.closeInventory();
            return;
        }
        ItemStack item = event.getCurrentItem();
        VouchableTool tool = this.items.get(item);
        if (tool != null) {
            PlayerUnlocks playerUnlocks = playerData.getPlayerUnlocks();
            if (playerUnlocks.getUnlockTokens() >= tool.cost()) {
                player.getInventory().getItemInMainHand().setAmount(0);
                PlayerUtil.giveItem(player, tool.item());
                playerUnlocks.addUnlockTokens(-tool.cost());
                player.sendMessage(TextUtil.makeText("You have redeemed ", TextUtil.GREEN)
                        .append(TextUtil.makeText(tool.displayName(), TextUtil.GOLD)));
                SoundUtil.playHighPitchPling(player);
                player.closeInventory();
            } else {
                TextUtil.errorMessage(player, "You don't have the required souls to redeem this item");
            }
        }
    }
}

