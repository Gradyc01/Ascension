package me.depickcator.ascension.Kits;

import me.depickcator.ascension.Kits.Kits.Kit2;
import me.depickcator.ascension.Utility.TextUtil;
import me.depickcator.ascension.Interfaces.AscensionGUI;
import me.depickcator.ascension.Kits.Kits.Kit;
import me.depickcator.ascension.Player.Data.PlayerData;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

public class ViewKitGUI extends AscensionGUI {
    private final Kit2 kit;
    private final AscensionGUI parent;
    public ViewKitGUI(PlayerData playerData, Kit2 kit, AscensionGUI gui) {
        super(playerData, (char) 6, TextUtil.makeText("Viewing ", TextUtil.AQUA).append(TextUtil.makeText(kit.getDisplayName(), TextUtil.GOLD)), true);
        this.parent = gui;
        this.kit = kit;

        inventory.setItem(48, goBackItem());
        playerHeadButton(49);
        inventory.setItem(4, kit.getMascot());
        kitItems();
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
    public void interactWithGUIButtons(InventoryClickEvent event) {
        if (event.getCurrentItem() != null && event.getCurrentItem().equals(goBackItem())) {
            if (parent instanceof KitBookGUI) {
                new KitBookGUI(playerData);
            }
        }
    }

}
