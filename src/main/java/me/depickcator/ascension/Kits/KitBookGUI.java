package me.depickcator.ascension.Kits;

import me.depickcator.ascension.Kits.Kits.Kit;
import me.depickcator.ascension.MainMenuUI.MainMenuGUI;
import me.depickcator.ascension.Player.Data.PlayerUtil;
import me.depickcator.ascension.Utility.SoundUtil;
import me.depickcator.ascension.Utility.TextUtil;
import me.depickcator.ascension.Interfaces.AscensionGUI;
import me.depickcator.ascension.Items.Uncraftable.KitBook;
import me.depickcator.ascension.Player.Data.PlayerData;
import net.kyori.adventure.text.Component;
import org.bukkit.Material;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class KitBookGUI extends AscensionGUI {
    private final KitBook kitBook;
    public KitBookGUI(PlayerData playerData) {
        super(playerData, (char) 3, TextUtil.makeText("Choose a Kit", TextUtil.AQUA), true);
        kitBook = KitBook.getInstance();
        makeKitButtons();
        infoButton();
        if (plugin.getGameState().inLobby()) {
            inventory.setItem(22, getCloseButton());
            inventory.setItem(21, goBackItem());
        }
    }

    private void makeKitButtons() {
        int index = 10;
        for (Kit kit : kitBook.getKits()) {
            inventory.setItem(index, kit.getMascot());
            index+=2;
        }
    }

    private void infoButton() {
        Component title = TextUtil.makeText("INFO", TextUtil.YELLOW, true, false);
//
        List<Component> lore = new ArrayList<>(List.of(
                TextUtil.makeText("[Left Click]", TextUtil.GOLD).append(TextUtil.makeText(" Pick Kit", TextUtil.GREEN)),
                TextUtil.makeText("[Right Click]", TextUtil.GOLD).append(TextUtil.makeText(" View Kit", TextUtil.GREEN))
        ));

        inventory.setItem(18, initExplainerItem(Material.REDSTONE_TORCH, lore, title));
    }


    @Override
    public void interactWithGUIButtons(InventoryClickEvent event) {
        if (!isHolding(KitBook.getInstance().getResult()) && plugin.getGameState().inGame()) {
            event.setCancelled(true);
            player.closeInventory();
            return;
        }
        if (plugin.getGameState().inLobby()) {
            ItemStack item = event.getCurrentItem();
            if (item.equals(getCloseButton())) {
                event.setCancelled(true);
                player.closeInventory();
            } else if (item.equals(goBackItem())) {
                new MainMenuGUI(playerData);
            }
        }
//        Kit kit = Kit.getKit(event.getCurrentItem());
        Kit kit = kitBook.getKit(event.getCurrentItem());
        if (kit == null) return;
        if (event.isLeftClick()) {
            if (plugin.getGameState().inLobby()) {
//                TextUtil.errorMessage(player, "You are unable to pick this kit at this time");
                viewKit(event, kit);
            } else {
                getKit(event, kit);
            }
        } else if (event.isRightClick()) {
            viewKit(event, kit);
        }
    }

    private void getKit(InventoryClickEvent event, Kit kit) {
        Component name = TextUtil.makeText(kit.getDisplayName(), TextUtil.GOLD);
        Component text1 = TextUtil.makeText("You have chosen the ", TextUtil.DARK_GREEN);
        Component text2 = TextUtil.makeText(" Kit", TextUtil.DARK_GREEN);
        SoundUtil.playHighPitchPling(player);
        player.sendMessage(text1.append(name).append(text2));
        PlayerUtil.giveItem(player, kit.getKitItems());
        player.getInventory().getItemInMainHand().setAmount(0);
        event.setCancelled(true);
        player.closeInventory();
    }

    private void viewKit(InventoryClickEvent event, Kit kit) {
        new ViewKitGUI(playerData, kit, this);
    }

}
