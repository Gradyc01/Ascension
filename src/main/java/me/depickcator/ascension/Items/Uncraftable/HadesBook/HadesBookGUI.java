package me.depickcator.ascension.Items.Uncraftable.HadesBook;



import me.depickcator.ascension.General.SoundUtil;
import me.depickcator.ascension.General.TextUtil;
import me.depickcator.ascension.Interfaces.AscensionGUI;
import me.depickcator.ascension.Player.Data.PlayerData;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

import java.util.List;

public class HadesBookGUI extends AscensionGUI {
    private List<ItemStack> items;
    public HadesBookGUI(PlayerData playerData) {
        super(playerData, (char) 6, TextUtil.makeText("Hades' Book", TextUtil.GOLD), true);
        items = plugin.getBingoData().getItems();

        loadBingoBoard();
    }

    private void loadBingoBoard() {
        char index = 2;
        for (ItemStack item : items) {
            inventory.setItem(index, item);
            if (((index - 6) % 9) == 0) {
                index+=4;
            }
            index++;
        }
    }

    @Override
    public void interactWithGUIButtons(InventoryClickEvent event) {
        if (event.getCurrentItem() != null && event.isLeftClick() && items.contains(event.getCurrentItem())) {
            if (player.getInventory().getItemInMainHand().equals(HadesBook.getInstance().getItem())) {
                player.getWorld().dropItem(player.getLocation(), event.getCurrentItem());
                successfulPurchase(event);
            }
        }
    }

    private void successfulPurchase(InventoryClickEvent event) {
        SoundUtil.playHighPitchPling(player);
        event.setCancelled(true);
        player.closeInventory();
        player.getInventory().getItemInMainHand().setAmount(0);
        player.sendMessage(TextUtil.makeText("You have selected the item: ", TextUtil.GREEN).append(event.getCurrentItem().displayName()));
    }
}

