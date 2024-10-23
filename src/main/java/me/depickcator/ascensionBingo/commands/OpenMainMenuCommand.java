package me.depickcator.ascensionBingo.commands;

import me.depickcator.ascensionBingo.AscensionBingo;
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
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.jetbrains.annotations.NotNull;

import static io.papermc.paper.command.brigadier.argument.ArgumentTypes.itemStack;
import static me.depickcator.ascensionBingo.AscensionBingo.guiMap;

public class OpenMainMenuCommand implements CommandExecutor {
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

        if (p != null) {
            p.openInventory(inventory);
        }
        setItemBackground();
        bingoBoardButton();
        viewCommandsButton();
        viewUnlocksButton();
        closeGUIButton();

        if (p != null) {
//            AscensionBingo.guiMap.put(p.getUniqueId(), inventory);
            Pair<Inventory, String> pair = new MutablePair<>(inventory, menuName);

            AscensionBingo.guiMap.put(p.getUniqueId(), pair);
        }
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
        setGUIItems(button, buttonMeta,22);
        //stackItemMeta.lore();
    }

    private void closeGUIButton() {
        ItemStack button = new ItemStack(Material.BARRIER);
        ItemMeta buttonMeta = button.getItemMeta();
        Component title = Component.text("Close").color(TextColor.color(255,0,0));
        title = title.decoration(TextDecoration.ITALIC, false);
        buttonMeta.displayName(title);
        buttonMeta.setCustomModelData(2); // Use this when using the same item
        setGUIItems(button, buttonMeta,49);
    }

    private void viewUnlocksButton() {
        ItemStack button = new ItemStack(Material.DIAMOND_SWORD);
        ItemMeta buttonMeta = button.getItemMeta();
        Component title = Component.text("View Unlocks").color(TextColor.color(0,255,255));
        title = title.decoration(TextDecoration.ITALIC, false);
        buttonMeta.displayName(title);
        buttonMeta.setCustomModelData(3); // Use this when using the same item
        setGUIItems(button, buttonMeta,20);
    }

    private void viewCommandsButton() {
        ItemStack button = new ItemStack(Material.REPEATING_COMMAND_BLOCK);
        ItemMeta buttonMeta = button.getItemMeta();
        Component title = Component.text("View Commands").color(TextColor.color(0,255,255));
        title = title.decoration(TextDecoration.ITALIC, false);
        buttonMeta.displayName(title);
        buttonMeta.setCustomModelData(4); // Use this when using the same item
        setGUIItems(button, buttonMeta,24);
    }

    public static void interactWithGUIButtons(ItemStack item, Player p, Inventory inv) {
        switch (item.getType()) {
            case Material.ENCHANTED_BOOK -> {
                p.getInventory().setHelmet(new ItemStack(Material.BLACK_STAINED_GLASS));
                p.playSound(p.getLocation(), Sound.BLOCK_NOTE_BLOCK_PLING, 1.0f, 0.0f);
            }
            case Material.REPEATING_COMMAND_BLOCK -> {
                p.sendMessage(Component.text("PlaceHolder Text For Commands"));
            }
            case Material.DIAMOND_SWORD -> {
                p.sendMessage(Component.text("PlaceHolder Text For Unlocks"));
            }
            case Material.BARRIER -> {
                inv.close();
            }
            default -> {

            }
        }
    }
    //TODO: TURN INTO A INTERFACE METHOD
    private void setGUIItems (ItemStack button, ItemMeta buttonMeta, int index) {
        button.setItemMeta(buttonMeta); //Sets the Meta to Button Meta
        button.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
        inventory.setItem(index, button);
    }

    //TODO: TURN INTO A INTERFACE METHOD
    private void setItemBackground () {
        ItemStack button = new ItemStack(Material.LIGHT_GRAY_STAINED_GLASS_PANE);
        ItemMeta buttonMeta = button.getItemMeta();
        buttonMeta.displayName(Component.text(" ").color(TextColor.color(185, 185, 185)));
        buttonMeta.setCustomModelData(0);
        button.setItemMeta(buttonMeta); //Sets the Meta to Button Meta
        for (int i = 0; i < GUISize; i++) {
            inventory.setItem(i, button);
        }
    }
}
