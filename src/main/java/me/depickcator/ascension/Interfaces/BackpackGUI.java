package me.depickcator.ascension.Interfaces;

import me.depickcator.ascension.Player.Data.PlayerData;
import me.depickcator.ascension.Utility.TextUtil;
import net.kyori.adventure.text.Component;
import org.bukkit.Material;
import org.bukkit.event.inventory.InventoryClickEvent;
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
        if (item == null) return;
        if (equalItems(item, backpackItem) || item.equals(barrierItem)) {
            event.setCancelled(true);
        }
    }
}
