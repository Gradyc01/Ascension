package me.depickcator.ascension.Interfaces;

import me.depickcator.ascension.Player.Data.PlayerData;
import me.depickcator.ascension.Player.Data.PlayerInventoryTracker;
import me.depickcator.ascension.Player.Data.PlayerUtil;
import me.depickcator.ascension.Utility.ItemComparison;
import me.depickcator.ascension.Utility.TextUtil;
import net.kyori.adventure.text.Component;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.List;

public class BackpackGUI extends AscensionGUI {
    private final ItemStack backpackItem; //The item that represents the backpack
    private final ItemStack barrierItem;
    private boolean isUpgraded;
    public BackpackGUI(PlayerData playerData, ItemStack backpackItem) {
        super(playerData, (char) 6, TextUtil.makeText(playerData.getPlayer().getName() + "'s Backpack", TextUtil.GREEN));
        this.backpackItem = backpackItem;
        this.barrierItem = initBarrier();
        this.isUpgraded = false;
        setBarriers(true);
    }

    /*Adds or removes the barriers on the bottom half of the inventory
    depending on whether setBarriers is true or false*/
    private void setBarriers(boolean setBarriers) {
        ItemStack item = setBarriers ? barrierItem: new ItemStack(Material.AIR);
        for (int i = 27; i < 54; i++) {
            inventory.setItem(i, item);
        }
    }

    private ItemStack initBarrier() {
        ItemStack item = new ItemStack(Material.BARRIER);
        ItemMeta meta = item.getItemMeta();
        meta.displayName(TextUtil.makeText("Can't be used", TextUtil.RED));
        List<Component> lore = List.of(
                TextUtil.makeText("Must upgrade this backpack by", TextUtil.DARK_PURPLE),
                TextUtil.makeText("re-crafting a backpack with this one", TextUtil.DARK_PURPLE)
        );
        meta.lore(lore);
        item.setItemMeta(meta);
        return item;
    }

    public boolean openRestOfBackpack() {
        if (!isUpgraded) {
            setBarriers(false);
            isUpgraded = true;
            return true;
        }
        return false;
    }


    @Override
    public void interactWithGUIButtons(InventoryClickEvent event) {
        ItemStack item = event.getCurrentItem();
        Player p =  (Player) event.getWhoClicked();
        PlayerInventoryTracker tracker = PlayerUtil.getPlayerData(p).getPlayerInventoryTracker();
        if (event.getClick().equals(ClickType.NUMBER_KEY)) {
            ItemStack hotbarItem = p.getInventory().getContents()[event.getHotbarButton()];
            if (hotbarItem != null && hotbarItem.equals(backpackItem)) event.setCancelled(true);
        }
        if (item == null) return;
        if (item.equals(backpackItem) || item.equals(barrierItem)) {
            event.setCancelled(true);
        }
        if (event.getCurrentItem() != null && event.getCurrentItem().getType() != Material.AIR) tracker.needsUpdate();
    }

    @Override
    public boolean runWhenCloseGUI(InventoryCloseEvent event) {
        PlayerInventoryTracker tracker = PlayerUtil.getPlayerData((Player) event.getPlayer()).getPlayerInventoryTracker();
        return true;
    }
}
