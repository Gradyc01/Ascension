package me.depickcator.ascension.Kits;

import me.depickcator.ascension.General.SoundUtil;
import me.depickcator.ascension.General.TextUtil;
import me.depickcator.ascension.Interfaces.AscensionGUI;
import me.depickcator.ascension.Items.Uncraftable.KitBook;
import me.depickcator.ascension.Kits.Kits.Kit;
import me.depickcator.ascension.Player.Data.PlayerData;
import net.kyori.adventure.text.Component;
import org.bukkit.Material;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

public class KitBookGUI extends AscensionGUI {
    public KitBookGUI(PlayerData playerData) {
        super(playerData, (char) 3, TextUtil.makeText("Choose a Kit", TextUtil.AQUA), true);
        makeKitButtons();
        infoButton();
    }

    private void makeKitButtons() {
        int index = 10;
        for (Kit kit : Kit.getKits().values()) {
            inventory.setItem(index, kit.getMascot());
            index+=2;
        }
    }

    private void infoButton() {
        ItemStack item = new ItemStack(Material.EMERALD);
        ItemMeta meta = item.getItemMeta();
        meta.displayName(TextUtil.makeText("      INFO", TextUtil.YELLOW, true, false));
        meta.setCustomModelData(999999);

        List<Component> lore = new ArrayList<>();
        Component leftClickText = TextUtil.makeText("[Left Click]", TextUtil.GOLD);
        Component rightClickText = TextUtil.makeText("[Right Click]", TextUtil.GOLD);
        Component leftClickExplanation = TextUtil.makeText(" Pick Kit", TextUtil.GREEN);
        Component rightClickExplanation = TextUtil.makeText(" View Kit", TextUtil.GREEN);
        lore.add(TextUtil.makeText("", TextUtil.YELLOW));;
        lore.add(leftClickText.append(leftClickExplanation));;
        lore.add(rightClickText.append(rightClickExplanation));;
        meta.lore(lore);
        item.setItemMeta(meta);
        inventory.setItem(18, item);
    }


    @Override
    public void interactWithGUIButtons(InventoryClickEvent event) {
        if (!player.getInventory().getItemInMainHand().equals(KitBook.item())) {
            event.setCancelled(true);
            player.closeInventory();
            return;
        }
        Kit kit = Kit.getKit(event.getCurrentItem());
        if (kit == null) return;
        if (event.isLeftClick()) {
            getKit(event, kit);
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
        kit.giveKitItems(player);
        player.getInventory().getItemInMainHand().setAmount(0);
        event.setCancelled(true);
        player.closeInventory();
    }

    private void viewKit(InventoryClickEvent event, Kit kit) {
        new ViewKitGUI(playerData, kit, this);
    }

}
