package me.depickcator.ascension.Items.Uncraftable.HadesBook;



import me.depickcator.ascension.Ascension;
import me.depickcator.ascension.General.SoundUtil;
import me.depickcator.ascension.General.TextUtil;
import me.depickcator.ascension.Interfaces.AscensionGUI;
import net.kyori.adventure.text.Component;
import org.apache.commons.lang3.tuple.MutablePair;
import org.apache.commons.lang3.tuple.Pair;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.List;

public class HadesBookGUI implements AscensionGUI {
    public final static String menuName = "HadesBookGUI";

    private final int GUISize = 6 * 9;
    private final Inventory inventory;
    private Player player;
    private Ascension plugin;
    private List<ItemStack> items;
    public HadesBookGUI(Player p) {
        this.plugin = Ascension.getInstance();
        player = p;
        inventory = Bukkit.createInventory(p, GUISize, Component.text("Hades' Book").color(TextUtil.GOLD));
        items = plugin.getBingoData().getItems();
        p.openInventory(inventory);

        setItemBackground(inventory,GUISize);
        loadBingoBoard();
        Pair<Inventory, AscensionGUI> pair2 = new MutablePair<>(inventory,this);
        Ascension.guiMap.put(p.getUniqueId(), pair2);
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
    public String getGUIName() {
        return menuName;
    }

    @Override
    public void interactWithGUIButtons(InventoryClickEvent event, Player p) {
        if (event.getCurrentItem() != null && event.isLeftClick() && items.contains(event.getCurrentItem())) {
            if (p.getInventory().getItemInMainHand().equals(HadesBook.getInstance().getItem())) {
                p.getWorld().dropItem(p.getLocation(), event.getCurrentItem());
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

