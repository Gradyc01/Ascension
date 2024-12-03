package me.depickcator.ascension.mainMenu;

import me.depickcator.ascension.Ascension;
import me.depickcator.ascension.General.TextUtil;
import me.depickcator.ascension.Interfaces.AscensionGUI;
import me.depickcator.ascension.Player.Cooldowns.CombatTimer;
import me.depickcator.ascension.Timeline.Events.Scavenger.Scavenger;
import me.depickcator.ascension.Timeline.Events.Scavenger.ScavengerGUI;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextColor;
import org.apache.commons.lang3.tuple.MutablePair;
import org.apache.commons.lang3.tuple.Pair;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeModifier;
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
        inventory = Bukkit.createInventory(p,GUISize,Component.text("Ascension").color(TextColor.color(0,255,255)));

        if (p == null || CombatTimer.getInstance().isOnCooldown(p)) {
            return false;
        }
        p.openInventory(inventory);
        setItemBackground(inventory, GUISize);
        inventory.setItem(21, makeMainMenuBoardButton(Material.DIAMOND_SWORD, "Unlocks"));
        inventory.setItem(22, makeMainMenuBoardButton(Material.ENCHANTED_BOOK, "Board"));
        inventory.setItem(23, makeMainMenuBoardButton(Material.COMPARATOR, "Commands"));
        inventory.setItem(31, makeMainMenuBoardButton(Material.FEATHER, "Scavenger"));
        closeGUIButton(inventory, 49);
        playerHeadButton(inventory, 13, p);
//        closeGUIButton(inventory, 49);



        Pair<Inventory, AscensionGUI> pair2 = new MutablePair<>(inventory,this);
        Ascension.guiMap.put(p.getUniqueId(), pair2);

        return false;
    }

    private ItemStack makeMainMenuBoardButton(Material material, String name) {
        ItemStack button = new ItemStack(material);
        ItemMeta buttonMeta = button.getItemMeta();
        buttonMeta.displayName(TextUtil.makeText(name, TextUtil.AQUA));
        AttributeModifier buttonModifier = new AttributeModifier(NamespacedKey.minecraft("hide_main_menu"),
                2, AttributeModifier.Operation.ADD_NUMBER);
        buttonMeta.addAttributeModifier(Attribute.ATTACK_DAMAGE, buttonModifier);
        buttonMeta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
        button.setItemMeta(buttonMeta);
        return button;
    }

    @Override
    public String getGUIName() {
        return menuName;
    }

    @Override
    public void interactWithGUIButtons(InventoryClickEvent event, Player p) {
        ItemStack item = event.getCurrentItem();
        if (item == null || !event.isLeftClick()) {
            return;
        }
        switch (item.getType()) {
            case Material.ENCHANTED_BOOK -> {
                p.performCommand("openmenu board");
            }
            case Material.COMPARATOR -> {
                p.performCommand("openmenu commands");
            }
            case Material.DIAMOND_SWORD -> {
                p.performCommand("openmenu unlocks-1");
            }
            case Material.FEATHER -> {
                Scavenger scavenger = Ascension.getInstance().getTimeline().getScavenger();
                if (scavenger == null) {
                    TextUtil.errorMessage(p, "Scavenger is not available at this time!");
                    return;
                }
                new ScavengerGUI(scavenger, p,false);
            }
            case Material.BARRIER -> {
                event.setCancelled(true);
                p.closeInventory();
            }
            default -> {

            }
        }
//        p.playSound(p.getLocation(), Sound.BLOCK_NOTE_BLOCK_PLING, 1.0f, 0.0f);
    }
}
