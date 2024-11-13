package me.depickcator.ascensionBingo.mainMenu;

import me.depickcator.ascensionBingo.AscensionBingo;
import me.depickcator.ascensionBingo.Interfaces.AscensionGUI;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextColor;
import net.kyori.adventure.text.format.TextDecoration;
import org.apache.commons.lang3.tuple.MutablePair;
import org.apache.commons.lang3.tuple.Pair;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.jetbrains.annotations.NotNull;

public class OpenMainMenuCommand implements CommandExecutor, AscensionGUI {
    public final static String menuName = "MAIN-MENU";
    private int GUISize = 6 * 9;
    private Inventory inventory;
    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {
        if (!(commandSender instanceof Player)) {
            return false;
        }
        Player p = ((Player) commandSender).getPlayer();
        inventory = Bukkit.createInventory(p,GUISize,Component.text("Ascension Bingo").color(TextColor.color(0,255,255)));

        if (p == null) {
            return false;
        }
        p.openInventory(inventory);
        setItemBackground(inventory, GUISize);
        bingoBoardButton();
        viewCommandsButton();
        viewUnlocksButton();
        playerHeadButton(inventory, 49, p);
//        closeGUIButton(inventory, 49);



        Pair<Inventory, AscensionGUI> pair2 = new MutablePair<>(inventory,this);
        AscensionBingo.guiMap.put(p.getUniqueId(), pair2);

        return false;
    }

    private void bingoBoardButton() {
        ItemStack button = new ItemStack(Material.ENCHANTED_BOOK);
        ItemMeta buttonMeta = button.getItemMeta();
        Component title = Component.text("View Bingo Board").color(TextColor.color(0,255,255));
        title = title.decoration(TextDecoration.ITALIC, false);
        buttonMeta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
        buttonMeta.displayName(title);
        buttonMeta.setCustomModelData(1);
        setGUIItems(inventory, button, buttonMeta,22);
        //stackItemMeta.lore();
    }

    private void viewUnlocksButton() {
        ItemStack button = new ItemStack(Material.DIAMOND_SWORD);
        ItemMeta buttonMeta = button.getItemMeta();
        Component title = Component.text("View Unlocks").color(TextColor.color(0,255,255));
        title = title.decoration(TextDecoration.ITALIC, false);
        buttonMeta.displayName(title);
        buttonMeta.setCustomModelData(3); // Use this when using the same item
        setGUIItems(inventory, button, buttonMeta,20);
    }

    private void viewCommandsButton() {
        ItemStack button = new ItemStack(Material.REPEATING_COMMAND_BLOCK);
        ItemMeta buttonMeta = button.getItemMeta();
        Component title = Component.text("View Commands").color(TextColor.color(0,255,255));
        title = title.decoration(TextDecoration.ITALIC, false);
        buttonMeta.displayName(title);
        buttonMeta.setCustomModelData(4); // Use this when using the same item
        setGUIItems(inventory, button, buttonMeta,24);
    }

    @Override
    public String getGUIName() {
        return menuName;
    }

    @Override
    public void interactWithGUIButtons(InventoryClickEvent event, Player p) {
        ItemStack item = event.getCurrentItem();
        if (item == null) {
            return;
        }
        switch (item.getType()) {
            case Material.ENCHANTED_BOOK -> {
//                p.getInventory().setHelmet(new ItemStack(Material.BLACK_STAINED_GLASS));
                p.performCommand("openmenu board");
                p.playSound(p.getLocation(), Sound.BLOCK_NOTE_BLOCK_PLING, 1.0f, 0.0f);
            }
            case Material.REPEATING_COMMAND_BLOCK -> {
                p.sendMessage(Component.text("PlaceHolder Text For Commands"));
            }
            case Material.DIAMOND_SWORD -> {
                p.performCommand("openmenu unlocks-1");
                p.playSound(p.getLocation(), Sound.BLOCK_NOTE_BLOCK_PLING, 1.0f, 0.0f);
            }
            default -> {

            }
        }
    }
}
