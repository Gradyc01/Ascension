package me.depickcator.ascension.Interfaces;

import me.depickcator.ascension.Ascension;
import me.depickcator.ascension.Utility.TextUtil;
import me.depickcator.ascension.Player.Data.PlayerData;
import me.depickcator.ascension.Player.Data.PlayerSkills;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextColor;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;

import java.util.ArrayList;
import java.util.List;


public abstract class AscensionMenuGUI extends AscensionGUI {
    protected final Player player;
    protected final PlayerData playerData;
    /* Creates a GUI for playerData that is GUI lines tall */
    public AscensionMenuGUI(PlayerData playerData, char GUILines, Component name, boolean setBackground) {
        super(playerData, GUILines, name);
        this.playerData = playerData;
        this.player = playerData.getPlayer();
        if (setBackground) {
            setBackground();
        }
        open(player);
    }

    /* Checks whether a player is holding item
     * Returns true if Yes
     * False Otherwise */
    protected boolean isHolding(ItemStack item) {
//        ItemStack held = player.getInventory().getItemInMainHand();
//        return equalItems(held, item);
        return isHolding(player, item);
    }

    private void setBackground() {
        ItemStack item = new ItemStack(Material.GRAY_STAINED_GLASS_PANE);
        ItemMeta meta = item.getItemMeta();
        meta.displayName(Component.text(" ").color(TextColor.color(185, 185, 185)));
        item.setItemMeta(meta);
        for (int i = 0; i < GUISize; i++) {
            inventory.setItem(i, item);
        }
    }

    /* The standardized close button */
    protected ItemStack getCloseButton() {
        ItemStack button = new ItemStack(Material.BARRIER);
        ItemMeta buttonMeta = button.getItemMeta();
        Component title = TextUtil.makeText("Close", TextUtil.DARK_RED);
        title = title.decoration(TextDecoration.ITALIC, false);
        buttonMeta.displayName(title);
        buttonMeta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
        button.setItemMeta(buttonMeta);
        return button;
    }

    /* The standardized go back button */
    protected ItemStack goBackItem() {
        ItemStack item = new ItemStack(Material.ARROW);
        ItemMeta meta = item.getItemMeta();
        meta.displayName(TextUtil.makeText("Go Back", TextUtil.DARK_GRAY));
        item.setItemMeta(meta);
        return item;
    }

    /* The standardized net page button */
    protected ItemStack nextPageItem() {
        ItemStack item = new ItemStack(Material.ARROW);
        ItemMeta meta = item.getItemMeta();
        meta.displayName(TextUtil.makeText("Next Page", TextUtil.DARK_GRAY));
        item.setItemMeta(meta);
        return item;
    }

    /* The standardized previous page button */
    protected ItemStack previousPageItem() {
        ItemStack item = new ItemStack(Material.ARROW);
        ItemMeta meta = item.getItemMeta();
        meta.displayName(TextUtil.makeText("Previous Page", TextUtil.DARK_GRAY));
        item.setItemMeta(meta);
        return item;
    }

    /* The standardized Player Head button */
    protected void playerHeadButton(int index) {
        PlayerSkills playerSkills = playerData.getPlayerSkills();
        ItemStack button = new ItemStack(Material.PLAYER_HEAD);
        SkullMeta headMeta = (SkullMeta) button.getItemMeta();
        headMeta.setPlayerProfile(player.getPlayerProfile());
        Component title = Component.text("Your Stats").color(TextColor.color(TextUtil.GOLD));
        title = title.decoration(TextDecoration.ITALIC, false);
        headMeta.displayName(title);
        headMeta.lore(List.of(
                TextUtil.makeText("Souls:         " + playerData.getPlayerUnlocks().getUnlockTokens(), TextUtil.BLUE),
                TextUtil.makeText("Combat:   " + TextUtil.toRomanNumeral(playerSkills.getCombat().getExpLevel()) +
                "  "+ playerSkills.getCombat().getExpOverTotalNeeded(), TextUtil.BLUE),
                TextUtil.makeText("Mining:     " + TextUtil.toRomanNumeral(playerSkills.getMining().getExpLevel()) +
                "  "+ playerSkills.getMining().getExpOverTotalNeeded(), TextUtil.BLUE),
                TextUtil.makeText("Foraging: " + TextUtil.toRomanNumeral(playerSkills.getForaging().getExpLevel()) +
                "  "+ playerSkills.getForaging().getExpOverTotalNeeded(), TextUtil.BLUE),
                TextUtil.makeText("Board:    " + TextUtil.toRomanNumeral(playerSkills.getBoardEfficiency().getExpLevel()) +
                "  "+ playerSkills.getBoardEfficiency().getExpOverTotalNeeded(), TextUtil.BLUE)

        ));
        button.setItemMeta(headMeta);
        inventory.setItem(index, button);
    }

    /* The standardized Explainer item */
    protected ItemStack initExplainerItem(Material material, List<Component> lore, Component name) {
        ItemStack item = new ItemStack(material);
        ItemMeta meta = item.getItemMeta();
        AttributeModifier buttonModifier = new AttributeModifier(NamespacedKey.minecraft("hide_main_menu"),
                2, AttributeModifier.Operation.ADD_NUMBER);
        meta.addAttributeModifier(Attribute.ATTACK_DAMAGE, buttonModifier);
        meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
        meta.displayName(name);
        meta.lore(lore);
        item.setItemMeta(meta);
        return item;
    }

    /* Triggers when a player interacts with an item in the GUI */
    public abstract void interactWithGUIButtons(InventoryClickEvent event);


}
