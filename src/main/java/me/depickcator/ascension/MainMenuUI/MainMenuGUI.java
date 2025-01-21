package me.depickcator.ascension.MainMenuUI;

import me.depickcator.ascension.Ascension;
import me.depickcator.ascension.Kits.KitBookGUI;
import me.depickcator.ascension.MainMenuUI.BingoBoard.BingoBoardGUI;
import me.depickcator.ascension.MainMenuUI.Command.CommandGUI;
import me.depickcator.ascension.MainMenuUI.Map.MapGUI;
import me.depickcator.ascension.MainMenuUI.Skills.SkillsGUI;
import me.depickcator.ascension.MainMenuUI.Unlocks.UnlocksGUI;
import me.depickcator.ascension.Teams.Team;
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

import java.util.List;

public class MainMenuGUI extends AscensionGUI {

    public MainMenuGUI(PlayerData playerData) {
        super(playerData, (char) 6, TextUtil.makeText("Ascension", TextUtil.AQUA), true);

        inventory.setItem(21, makeMainMenuBoardButton(Material.CRAFTING_TABLE, "Unlocks"));
        inventory.setItem(22, makeMainMenuBoardButton(Material.ENCHANTED_BOOK, "Board"));
        inventory.setItem(23, makeMainMenuBoardButton(Material.COMPARATOR, "Commands"));
        inventory.setItem(30, makeMainMenuBoardButton(Material.DIAMOND_SWORD, "Skills"));
        inventory.setItem(31, makeMainMenuBoardButton(Material.FEATHER, "Scavenger"));
        inventory.setItem(32, makeMainMenuBoardButton(Material.FILLED_MAP, "Events"));
        inventory.setItem(20, makeMainMenuBoardButton(Material.CHEST, "Team Backpack", "Grace Period Only!"));
        inventory.setItem(24, makeMainMenuBoardButton(Material.ANVIL, "Coming Soon..."));
        if (plugin.getGameState().inLobby())
            inventory.setItem(40, makeMainMenuBoardButton(Material.IRON_CHESTPLATE, "Kits"));


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
                new BingoBoardGUI(playerData);
            }
            case Material.COMPARATOR -> {
                new CommandGUI(playerData);
            }
            case Material.CRAFTING_TABLE -> {
                new UnlocksGUI(playerData, (char) 1);
            }
            case Material.DIAMOND_SWORD -> {
                new SkillsGUI(playerData);
            }
            case Material.FILLED_MAP -> {
                new MapGUI(playerData);
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
            case Material.ANVIL -> {
                TextUtil.errorMessage(player, "Future feature currently not available!");;
            }
            case Material.CHEST -> {
                Team team = playerData.getPlayerTeam().getTeam();
                if (team != null) {
                    team.getTeamBackpack().openInventory(playerData);
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
