package me.depickcator.ascension.Interfaces;

import me.depickcator.ascension.Ascension;
import me.depickcator.ascension.Utility.TextUtil;
import me.depickcator.ascension.Player.Data.PlayerData;
import me.depickcator.ascension.Player.Data.PlayerSkills;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextColor;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;

import java.util.ArrayList;
import java.util.List;


public abstract class AscensionGUI extends ItemComparison{
    private final int GUISize;
    protected final Inventory inventory;
    protected final Player player;
    protected final Ascension plugin;
    protected final PlayerData playerData;

    public AscensionGUI(PlayerData playerData, char GUILines, Component name, boolean setBackground) {
        this.playerData = playerData;
        this.player = playerData.getPlayer();
        plugin = Ascension.getInstance();
        GUISize = GUILines * 9;
        inventory = Bukkit.createInventory(player, GUISize, name);
        player.openInventory(inventory);
        if (setBackground) {
            setBackground();
        }
        plugin.registerGUI(player, inventory, this);
    }

    private void setBackground() {
        ItemStack item = new ItemStack(Material.GRAY_STAINED_GLASS_PANE);
        ItemMeta meta = item.getItemMeta();
        meta.displayName(Component.text(" ").color(TextColor.color(185, 185, 185)));
        meta.setCustomModelData(0x010000);
        item.setItemMeta(meta);
        for (int i = 0; i < GUISize; i++) {
            inventory.setItem(i, item);
        }
    }


    protected ItemStack getCloseButton() {
        ItemStack button = new ItemStack(Material.BARRIER);
        ItemMeta buttonMeta = button.getItemMeta();
        Component title = TextUtil.makeText("Close", TextUtil.DARK_RED);
        title = title.decoration(TextDecoration.ITALIC, false);
        buttonMeta.displayName(title);
        buttonMeta.setCustomModelData(0x030000);
        buttonMeta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
        button.setItemMeta(buttonMeta);
        return button;
    }

    protected ItemStack goBackItem() {
        ItemStack item = new ItemStack(Material.ARROW);
        ItemMeta meta = item.getItemMeta();
        meta.displayName(TextUtil.makeText("Go Back", TextUtil.DARK_GRAY));
        meta.setCustomModelData(0x040000);
        item.setItemMeta(meta);
        return item;
    }

    protected ItemStack nextPageItem() {
        ItemStack item = new ItemStack(Material.ARROW);
        ItemMeta meta = item.getItemMeta();
        meta.displayName(TextUtil.makeText("Next Page", TextUtil.DARK_GRAY));
        meta.setCustomModelData(0x070000);
        item.setItemMeta(meta);
        return item;
    }

    protected ItemStack previousPageItem() {
        ItemStack item = new ItemStack(Material.ARROW);
        ItemMeta meta = item.getItemMeta();
        meta.displayName(TextUtil.makeText("Previous Page", TextUtil.DARK_GRAY));
        meta.setCustomModelData(0x080000);
        item.setItemMeta(meta);
        return item;
    }

    protected void playerHeadButton(int index) {
        PlayerSkills playerSkills = playerData.getPlayerSkills();
        ItemStack button = new ItemStack(Material.PLAYER_HEAD);
        SkullMeta headMeta = (SkullMeta) button.getItemMeta();
        headMeta.setPlayerProfile(player.getPlayerProfile());
        Component title = Component.text("Your Stats").color(TextColor.color(TextUtil.GOLD));
        title = title.decoration(TextDecoration.ITALIC, false);
        headMeta.displayName(title);

        List<Component> lore = new ArrayList<>();
        Component unlockTokensText = TextUtil.makeText("Souls:         " + playerData.getPlayerUnlocks().getUnlockTokens(), TextUtil.BLUE);

        Component combatText = TextUtil.makeText("Combat:   " +
                TextUtil.toRomanNumeral(playerSkills.getCombat().getExpLevel()) +
                "  "+ playerSkills.getCombat().getExpOverTotalNeeded(), TextUtil.BLUE);
        Component miningText = TextUtil.makeText("Mining:     " +
                TextUtil.toRomanNumeral(playerSkills.getMining().getExpLevel()) +
                "  "+ playerSkills.getMining().getExpOverTotalNeeded(), TextUtil.BLUE);
        Component foragingText = TextUtil.makeText("Foraging: " +
                TextUtil.toRomanNumeral(playerSkills.getForaging().getExpLevel()) +
                "  "+ playerSkills.getForaging().getExpOverTotalNeeded(), TextUtil.BLUE);
        lore.add(unlockTokensText);
        lore.add(combatText);
        lore.add(miningText);
        lore.add(foragingText);

        headMeta.lore(lore);
        headMeta.setCustomModelData(0x020000);
        button.setItemMeta(headMeta);
        inventory.setItem(index, button);
    }

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

    protected boolean isHolding(ItemStack item) {
        ItemStack held = player.getInventory().getItemInMainHand();
//        return held.equals(item);
        return equalItems(held, item);
    }

    public abstract void interactWithGUIButtons(InventoryClickEvent event);


}
