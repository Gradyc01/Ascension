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

public class MainMenuGUI extends AscensionGUI {

    public MainMenuGUI(PlayerData playerData) {
        super(playerData, (char) 6, TextUtil.makeText("Ascension", TextUtil.AQUA), true);
        int[] layout = plugin.getGameState().inLobby() ? lobbyUILayout() : gameUILayout();

        inventory.setItem(layout[0], makeMainMenuBoardButton(Material.CRAFTING_TABLE, "Unlocks"));
        inventory.setItem(layout[1], makeMainMenuBoardButton(Material.ENCHANTED_BOOK, "Board"));
        inventory.setItem(layout[2], makeMainMenuBoardButton(Material.COMPARATOR, "Commands"));
        inventory.setItem(layout[3], makeMainMenuBoardButton(Material.DIAMOND_SWORD, "Skills"));
        inventory.setItem(layout[4], makeMainMenuBoardButton(Material.FEATHER, "Scavenger"));
        inventory.setItem(layout[5], makeMainMenuBoardButton(Material.FILLED_MAP, "Events"));
        inventory.setItem(layout[6], makeMainMenuBoardButton(Material.CHEST, "Team Backpack"));
        if (plugin.getGameState().inLobby()) inventory.setItem(layout[7], makeMainMenuBoardButton(Material.IRON_CHESTPLATE, "Kits"));


        inventory.setItem(49, getCloseButton());
        playerHeadButton(13);
    }

    private int[] lobbyUILayout() {
        return new int[]{21, 22, 23, 30, 31, 32, 20, 24};
    }

    private int[] gameUILayout() {
        return new int[]{21, 22, 23, 30, 31, 32, 40};
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
