package me.depickcator.ascension.Kits;

import me.depickcator.ascension.Ascension;
import me.depickcator.ascension.General.TextUtil;
import me.depickcator.ascension.Interfaces.AscensionGUI;
import me.depickcator.ascension.Kits.Kits.Kit;
import me.depickcator.ascension.Player.PlayerData;
import net.kyori.adventure.text.Component;
import org.apache.commons.lang3.tuple.MutablePair;
import org.apache.commons.lang3.tuple.Pair;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class ViewKitGUI implements AscensionGUI {
    public final static String menuName = "View-Kit";

    private final int GUISize = 6 * 9;
    private final Inventory inventory;
    private final PlayerData playerData;
    private final Player player;
    private final Kit kit;
    private final AscensionGUI parent;
    public ViewKitGUI(PlayerData playerData, Kit kit, AscensionGUI gui) {
        this.playerData = playerData;
        this.player = this.playerData.getPlayer();
        this.parent = gui;
        this.kit = kit;

        Component kitDisplayName = TextUtil.makeText(kit.getDisplayName(), TextUtil.GOLD);
        inventory = Bukkit.createInventory(player, GUISize, TextUtil.makeText("Viewing ", TextUtil.AQUA).append(kitDisplayName));
        player.openInventory(inventory);
        setItemBackground(inventory,GUISize);
        inventory.setItem(48, goBackItem());
        playerHeadButton(inventory, 49, player);
        inventory.setItem(4, kit.getMascot());
        kitItems();

        Pair<Inventory, AscensionGUI> pair2 = new MutablePair<>(inventory,this);
        Ascension.guiMap.put(player.getUniqueId(), pair2);
    }

    private void kitItems() {
        int index = 19;
        for (ItemStack item : kit.getKitItems()) {
            inventory.setItem(index, item);
            if (index % 9 == 7) {
                index+=3;
            } else {
                index++;
            }
        }
    }



    @Override
    public String getGUIName() {
        return menuName;
    }

    @Override
    public void interactWithGUIButtons(InventoryClickEvent event, Player p) {
        if (event.getCurrentItem() != null && event.getCurrentItem().equals(goBackItem())) {
            if (parent instanceof KitBookGUI) {
                new KitBookGUI(playerData);
            }
        }
    }

}
