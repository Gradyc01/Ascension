package org.bingo4.MainMenuUI;

import org.bingo4.Bingo4;
import org.bingo4.Interfaces.AscensionMenuGUI;
import org.bingo4.Kits.KitBookGUI;
import org.bingo4.MainMenuUI.BingoBoard.BingoBoardGUI;
import org.bingo4.MainMenuUI.Command.CommandGUI;
import org.bingo4.MainMenuUI.Map.MapGUI;
import org.bingo4.MainMenuUI.Settings.SettingGUI;
import org.bingo4.Player.Data.PlayerData;
import org.bingo4.Teams.Team;
import org.bingo4.Utility.TextUtil;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.List;

public class MainMenuGUI extends AscensionMenuGUI {

    public MainMenuGUI(PlayerData playerData) {
        super(playerData, (char) 6, TextUtil.makeText("Bingo4", TextUtil.AQUA), true);

//        inventory.setItem(21, makeMainMenuBoardButton(Material.CRAFTING_TABLE, "Unlocks"));
        inventory.setItem(22, makeMainMenuBoardButton(Material.ENCHANTED_BOOK, "Board"));
//        inventory.setItem(23, makeMainMenuBoardButton(Material.COMPARATOR, "Commands"));
//        inventory.setItem(30, makeMainMenuBoardButton(Material.DIAMOND_SWORD, "Skills"));
//        inventory.setItem(31, makeMainMenuBoardButton(Material.FEATHER, "Scavenger"));
//        inventory.setItem(32, makeMainMenuBoardButton(Material.FILLED_MAP, "Events"));
//        inventory.setItem(20, makeMainMenuBoardButton(Material.CHEST, "Team Backpack", "Grace Period Only!"));
//        inventory.setItem(40, makeMainMenuBoardButton(Material.ANVIL, "Soul Shop"));
        inventory.setItem(45, makeMainMenuBoardButton(Material.COMMAND_BLOCK, "Settings"));
//        inventory.setItem(24, makeMainMenuBoardButton(Material.ENDER_CHEST, "Personal Backpack"));
//        if (plugin.getGameState().inLobby()) {
//            inventory.setItem(40, makeMainMenuBoardButton(Material.IRON_CHESTPLATE, "Kits"));
//            inventory.setItem(53, makeMainMenuBoardButton(Material.ANVIL, "Soul Shop"));
//        }


        inventory.setItem(49, getCloseButton());
        playerHeadButton(13);
    }

    @Override
    public void interactWithGUIButtons(InventoryClickEvent event) {
        ItemStack item = event.getCurrentItem();
        if (item == null) {
            return;
        }
        switch (item.getType()) {
            case Material.ENCHANTED_BOOK -> {
                new BingoBoardGUI(playerData);
            }
            case Material.ENDER_CHEST -> {
//                playerData.getPlayerBackpack().openBackpack();
            }
            case Material.COMPARATOR -> {
                new CommandGUI(playerData);
            }
            case Material.CRAFTING_TABLE -> {
//                new UnlocksGUI(playerData, (char) 1);
            }
            case Material.DIAMOND_SWORD -> {
//                new SkillsGUI(playerData);
            }
            case Material.FILLED_MAP -> {
                new MapGUI(playerData);
            }
            case Material.FEATHER -> {
//                Scavenger scavenger = Bingo4.getInstance().getSettingsUI().getSettings().getTimeline().getScavenger();
//                if (scavenger == null) {
//                    TextUtil.errorMessage(player, "Scavenger is not available at this time!");
//                    return;
//                }
//                new ScavengerGUI(scavenger, playerData,false);
            }
            case Material.IRON_CHESTPLATE -> {
                new KitBookGUI(playerData);
            }
            case Material.COMMAND_BLOCK -> {
                new SettingGUI(playerData);
            }
            case Material.ANVIL -> {
//                TextUtil.errorMessage(player, "Future feature currently not available!");;
//                new SoulShopGUI(playerData);
            }
            case Material.CHEST -> {
                Team team = playerData.getPlayerTeam().getTeam();
                if (team != null) {
//                    team.getTeamBackpack().openInventory(playerData);
                } else {
                    TextUtil.errorMessage(player, "You are currently not in a party!");
                }
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
//        ItemStack button = new ItemStack(material);
//        ItemMeta buttonMeta = button.getItemMeta();
//        buttonMeta.displayName(TextUtil.makeText(name, TextUtil.AQUA));
//        AttributeModifier buttonModifier = new AttributeModifier(NamespacedKey.minecraft("hide_main_menu"),
//                2, AttributeModifier.Operation.ADD_NUMBER);
//        buttonMeta.addAttributeModifier(Attribute.ATTACK_DAMAGE, buttonModifier);
//        buttonMeta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
//        button.setItemMeta(buttonMeta);
//        return button;
        return makeMainMenuBoardButton(material, name, "");
    }

    private ItemStack makeMainMenuBoardButton(Material material, String name, String lore) {
        ItemStack button = new ItemStack(material);
        ItemMeta buttonMeta = button.getItemMeta();
        buttonMeta.displayName(TextUtil.makeText(name, TextUtil.AQUA));
        if (!lore.isBlank()) buttonMeta.lore(List.of(TextUtil.makeText(lore, TextUtil.RED)));
        AttributeModifier buttonModifier = new AttributeModifier(NamespacedKey.minecraft("hide_main_menu"),
                2, AttributeModifier.Operation.ADD_NUMBER);
        buttonMeta.addAttributeModifier(Attribute.ATTACK_DAMAGE, buttonModifier);
        buttonMeta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
        button.setItemMeta(buttonMeta);
        return button;
    }
}
