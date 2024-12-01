package me.depickcator.ascension.Kits;

import me.depickcator.ascension.Ascension;
import me.depickcator.ascension.General.SoundUtil;
import me.depickcator.ascension.General.TextUtil;
import me.depickcator.ascension.Interfaces.AscensionGUI;
import me.depickcator.ascension.Items.Uncraftable.KitBook;
import me.depickcator.ascension.Kits.Kits.Kit;
import me.depickcator.ascension.Player.PlayerData;
import net.kyori.adventure.text.Component;
import org.apache.commons.lang3.tuple.MutablePair;
import org.apache.commons.lang3.tuple.Pair;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

public class KitBookGUI implements AscensionGUI {
    public final static String menuName = "Kit-Book";

    private final int GUISize = 3 * 9;
    private final Inventory inventory;
    private final PlayerData playerData;
    private final Player player;
    public KitBookGUI(PlayerData playerData) {
        this.playerData = playerData;
        this.player = this.playerData.getPlayer();
        inventory = Bukkit.createInventory(player, GUISize, TextUtil.makeText("Choose a Kit", TextUtil.AQUA));
        player.openInventory(inventory);
        setItemBackground(inventory,GUISize);
        makeKitButtons();
        infoButton();

        Pair<Inventory, AscensionGUI> pair2 = new MutablePair<>(inventory,this);
        Ascension.guiMap.put(player.getUniqueId(), pair2);
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
    public String getGUIName() {
        return menuName;
    }

    @Override
    public void interactWithGUIButtons(InventoryClickEvent event, Player p) {
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
