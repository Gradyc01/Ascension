//package me.depickcator.ascension.Interfaces;
//
//import me.depickcator.ascension.General.TextUtil;
//import me.depickcator.ascension.Player.Data.PlayerData;
//import me.depickcator.ascension.Player.Data.PlayerSkills;
//import me.depickcator.ascension.Player.Data.PlayerUtil;
//import net.kyori.adventure.text.Component;
//import net.kyori.adventure.text.format.TextColor;
//import net.kyori.adventure.text.format.TextDecoration;
//import org.bukkit.Material;
//import org.bukkit.entity.Player;
//import org.bukkit.event.inventory.InventoryClickEvent;
//import org.bukkit.inventory.Inventory;
//import org.bukkit.inventory.ItemFlag;
//import org.bukkit.inventory.ItemStack;
//import org.bukkit.inventory.meta.ItemMeta;
//import org.bukkit.inventory.meta.SkullMeta;
//
//import java.util.ArrayList;
//import java.util.List;
//
//public interface AscensionGUI {
//
//    default void setGUIItems(Inventory inventory, ItemStack button, ItemMeta buttonMeta, int index) {
//        button.setItemMeta(buttonMeta); //Sets the Meta to Button Meta
//        button.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
//        inventory.setItem(index, button);
//    }
//
//    default void setItemBackground(Inventory inventory, int GUISize) {
//        ItemStack button = new ItemStack(Material.GRAY_STAINED_GLASS_PANE);
//        ItemMeta buttonMeta = button.getItemMeta();
//        buttonMeta.displayName(Component.text(" ").color(TextColor.color(185, 185, 185)));
//        buttonMeta.setCustomModelData(0x010000);
//        button.setItemMeta(buttonMeta); //Sets the Meta to Button Meta
//        for (int i = 0; i < GUISize; i++) {
//            inventory.setItem(i, button);
//        }
//    }
//
//    default void closeGUIButton(Inventory inventory, int index) {
//        ItemStack button = getCloseButton();
//        button.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
//        inventory.setItem(index, button);
//    }
//
//    default void playerHeadButton(Inventory inventory, int index, Player player) {
//        PlayerData playerData = PlayerUtil.getPlayerData(player);
//        PlayerSkills playerSkills = playerData.getPlayerSkills();
//        ItemStack button = new ItemStack(Material.PLAYER_HEAD);
//        SkullMeta headMeta = (SkullMeta) button.getItemMeta();
//        headMeta.setPlayerProfile(player.getPlayerProfile());
//        Component title = Component.text("Your Stats").color(TextColor.color(TextUtil.GOLD));
//        title = title.decoration(TextDecoration.ITALIC, false);
//        headMeta.displayName(title);
//
//        List<Component> lore = new ArrayList<>();
//        Component unlockTokensText = TextUtil.makeText("Souls:         " + playerData.getPlayerUnlocks().getUnlockTokens(), TextUtil.BLUE);
//
//        Component combatText = TextUtil.makeText("Combat:   " +
//                TextUtil.toRomanNumeral(playerSkills.getCombat().getExpLevel()) +
//                "  "+ playerSkills.getCombat().getExpOverTotalNeeded(), TextUtil.BLUE);
//        Component miningText = TextUtil.makeText("Mining:     " +
//                TextUtil.toRomanNumeral(playerSkills.getMining().getExpLevel()) +
//                "  "+ playerSkills.getMining().getExpOverTotalNeeded(), TextUtil.BLUE);
//        Component foragingText = TextUtil.makeText("Foraging: " +
//                TextUtil.toRomanNumeral(playerSkills.getForaging().getExpLevel()) +
//                "  "+ playerSkills.getForaging().getExpOverTotalNeeded(), TextUtil.BLUE);
//        lore.add(unlockTokensText);
//        lore.add(combatText);
//        lore.add(miningText);
//        lore.add(foragingText);
//
//        headMeta.lore(lore);
//        headMeta.setCustomModelData(0x020000);
//        setGUIItems(inventory, button, headMeta, index);
//
//    }
//
//    static ItemStack getCloseButton() {
//        ItemStack button = new ItemStack(Material.BARRIER);
//        ItemMeta buttonMeta = button.getItemMeta();
//        Component title = Component.text("Close").color(TextColor.color(255,0,0));
//        title = title.decoration(TextDecoration.ITALIC, false);
//        buttonMeta.displayName(title);
//        buttonMeta.setCustomModelData(0x030000);
//        button.setItemMeta(buttonMeta);
//        return button;
//    }
//
//    default ItemStack goBackItem() {
//        ItemStack item = new ItemStack(Material.ARROW);
//        ItemMeta meta = item.getItemMeta();
//        meta.displayName(TextUtil.makeText("Go Back", TextUtil.DARK_GRAY));
//        meta.setCustomModelData(0x040000);
//        item.setItemMeta(meta);
//        return item;
//    }
//
//    String getGUIName();
//
//    void interactWithGUIButtons(InventoryClickEvent event, Player p);
//}
