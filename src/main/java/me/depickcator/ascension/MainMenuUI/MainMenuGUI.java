package me.depickcator.ascension.MainMenuUI;

import me.depickcator.ascension.Ascension;
import me.depickcator.ascension.Kits.KitBookGUI;
import me.depickcator.ascension.Utility.TextUtil;
import me.depickcator.ascension.Interfaces.AscensionGUI;
import me.depickcator.ascension.Player.Data.PlayerData;
import me.depickcator.ascension.Timeline.Events.Scavenger.Scavenger;
import me.depickcator.ascension.Timeline.Events.Scavenger.ScavengerGUI;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class MainMenuGUI extends AscensionGUI {

    public MainMenuGUI(PlayerData playerData) {
        super(playerData, (char) 6, TextUtil.makeText("Ascension", TextUtil.AQUA), true);
        inventory.setItem(21, makeMainMenuBoardButton(Material.CRAFTING_TABLE, "Unlocks"));
        inventory.setItem(22, makeMainMenuBoardButton(Material.ENCHANTED_BOOK, "Board"));
        inventory.setItem(23, makeMainMenuBoardButton(Material.COMPARATOR, "Commands"));
        inventory.setItem(30, makeMainMenuBoardButton(Material.DIAMOND_SWORD, "Skills"));
        inventory.setItem(31, makeMainMenuBoardButton(Material.FEATHER, "Scavenger"));
        inventory.setItem(32, makeMainMenuBoardButton(Material.FILLED_MAP, "Events"));
        if (plugin.getGameState().inLobby()) inventory.setItem(40, makeMainMenuBoardButton(Material.IRON_CHESTPLATE, "Kits"));


        inventory.setItem(49, getCloseButton());
        playerHeadButton(13);
    }

    @Override
    public void interactWithGUIButtons(InventoryClickEvent event) {
        ItemStack item = event.getCurrentItem();
        if (item == null || !event.isLeftClick()) {
            return;
        }
        switch (item.getType()) {
            case Material.ENCHANTED_BOOK -> {
                player.performCommand("openmenu board");
            }
            case Material.COMPARATOR -> {
                player.performCommand("openmenu commands");
            }
            case Material.CRAFTING_TABLE -> {
                player.performCommand("openmenu unlocks-1");
            }
            case Material.DIAMOND_SWORD -> {
                player.performCommand("openmenu skills");
            }
            case Material.FILLED_MAP -> {
                player.performCommand("openmenu events");
            }
            case Material.FEATHER -> {
                Scavenger scavenger = Ascension.getInstance().getSettingsUI().getSettings().getTimeline().getScavenger();
                if (scavenger == null) {
                    TextUtil.errorMessage(player, "Scavenger is not available at this time!");
                    return;
                }
                new ScavengerGUI(scavenger, playerData,false);
            }
            case Material.IRON_CHESTPLATE -> {
                new KitBookGUI(playerData);
            }
            case Material.BARRIER -> {
                event.setCancelled(true);
                player.closeInventory();
            }
            default -> {

            }
        }
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
}
