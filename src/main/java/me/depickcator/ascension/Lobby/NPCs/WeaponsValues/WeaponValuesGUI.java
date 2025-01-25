package me.depickcator.ascension.Lobby.NPCs.WeaponsValues;

import me.depickcator.ascension.Interfaces.AscensionGUI;
import me.depickcator.ascension.Items.Craftable.Vanilla.*;
import me.depickcator.ascension.Player.Data.PlayerData;
import me.depickcator.ascension.Utility.TextUtil;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

public class WeaponValuesGUI extends AscensionGUI {
    public WeaponValuesGUI(PlayerData playerData) {
        super(playerData, (char) 4, TextUtil.makeText("Weapon Damage Values", TextUtil.AQUA), true);
        inventory.setItem(31, getCloseButton());
        initWeapons();
    }

    private void initWeapons() {
        inventory.setItem(11, WoodenSword.getInstance().getResult());
        inventory.setItem(12, StoneSword.getInstance().getResult());
        inventory.setItem(13, IronSword.getInstance().getResult());
        inventory.setItem(14, DiamondSword.getInstance().getResult());
        inventory.setItem(15, NetheriteSword.getInstance().getResult());
        inventory.setItem(20, WoodenAxe.getInstance().getResult());
        inventory.setItem(21, StoneAxe.getInstance().getResult());
        inventory.setItem(22, IronAxe.getInstance().getResult());
        inventory.setItem(23, DiamondAxe.getInstance().getResult());
        inventory.setItem(24, NetheriteAxe.getInstance().getResult());
    }

    @Override
    public void interactWithGUIButtons(InventoryClickEvent event) {
        ItemStack item = event.getCurrentItem();
        if (item.equals(getCloseButton())) {
            event.setCancelled(true);
            player.closeInventory();
        }
    }
}
