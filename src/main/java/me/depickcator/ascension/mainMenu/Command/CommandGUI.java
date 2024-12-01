package me.depickcator.ascension.mainMenu.Command;

import me.depickcator.ascension.Ascension;
import me.depickcator.ascension.General.TextUtil;
import me.depickcator.ascension.Interfaces.AscensionGUI;
import me.depickcator.ascension.Player.PlayerData;
import me.depickcator.ascension.mainMenu.Command.Commands.Commands;
import me.depickcator.ascension.mainMenu.Command.Commands.FoodDrops;
import me.depickcator.ascension.mainMenu.Command.Commands.NightVision;
import me.depickcator.ascension.mainMenu.Command.Commands.SendCoords;
import net.kyori.adventure.text.Component;
import org.apache.commons.lang3.tuple.MutablePair;
import org.apache.commons.lang3.tuple.Pair;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.*;

public class CommandGUI implements AscensionGUI {
    public final static String menuName = "Command-GUI";

    private final int GUISize = 6 * 9;
    private final Inventory inventory;
    private final Player player;
    private final PlayerData playerData;
    private final Ascension plugin;
    private final HashMap<ItemStack, Commands> map;
    public CommandGUI(Ascension plugin, PlayerData playerData) {
        this.plugin = plugin;
        player = playerData.getPlayer();
        this.playerData = playerData;

        map = new HashMap<>();


        inventory = Bukkit.createInventory(player, GUISize, TextUtil.makeText("Commands", TextUtil.AQUA));
        player.openInventory(inventory);
        setItemBackground(inventory,GUISize);
        initializeButtons(new NightVision(), 16, true, playerData.getPlayerStats().isNightVision());
        initializeButtons(new FoodDrops(), 25, true, playerData.getPlayerStats().isFoodDrops());
        initializeButtons(new SendCoords(plugin), 10);
        inventory.setItem(48, goBackItem());
        playerHeadButton(inventory, 49, player);

        Pair<Inventory, AscensionGUI> pair2 = new MutablePair<>(inventory,this);
        Ascension.guiMap.put(player.getUniqueId(), pair2);
    }

    private void initializeButtons(Commands c, int index) {
        initializeButtons(c, index, false, false);
    }
    private void initializeButtons(Commands c, int index, boolean addLore, boolean isEnabled) {
        ItemStack item = c.getButton().clone();
        item.setItemMeta(addTitleAndLore(item, addLore, isEnabled));
        map.put(item, c);
        inventory.setItem(index, item);
    }

    private ItemMeta addTitleAndLore(ItemStack item, boolean addLore, boolean isEnabled) {
        ItemMeta meta = item.getItemMeta();
        if (addLore) {
            meta.lore(addOnOffLore(isEnabled));
        }
        return meta;
    }
    private List<Component> addOnOffLore(boolean onOff) {
        List<Component> lore = new ArrayList<>();
        if (onOff) {
            lore.add(TextUtil.makeText("[Enabled]", TextUtil.GREEN));
        } else {
            lore.add(TextUtil.makeText("[Disabled]", TextUtil.RED));
        }
        return lore;
    }

//    private ItemStack sendCoordsButton() {
//        ItemStack item = new ItemStack(Material.COMPASS);
//        item.setItemMeta(addTitleAndLore(item, "Send Coordinates", false));
//        return item;
//    }
//    private ItemStack spawnTeleportButton() {
//        ItemStack item = new ItemStack(Material.RED_BED);
//        item.setItemMeta(addTitleAndLore(item, "Teleport to Spawn", false));
//        return item;
//    }
//    private ItemStack surfaceTeleportButton() {
//        ItemStack item = new ItemStack(Material.GRASS_BLOCK);
//        item.setItemMeta(addTitleAndLore(item, "Surface", false));
//        return item;
//    }
//
//    private ItemStack foodDropsButton() {
//        ItemStack item = new ItemStack(Material.COOKED_BEEF);
//        item.setItemMeta(addTitleAndLore(item, "Food Drops", true));
//        return item;
//    }




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
        if (map.containsKey(item)) {
            Commands c = map.get(item);
            c.uponEvent(event, playerData);
            new CommandGUI(plugin, playerData);
            return;
        }
        if (item.equals(goBackItem())) {
            p.performCommand("open-main-menu");
        }



    }
}
